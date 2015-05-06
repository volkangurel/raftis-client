package com.volkangurel.raftis;

import com.volkangurel.raftis.config.RaftisConfig;
import com.volkangurel.raftis.config.RaftisPoolConfig;
import com.volkangurel.raftis.config.RaftisShardConfig;
import com.volkangurel.raftis.config.RaftisShardHostConfig;
import com.volkangurel.raftis.pool.RaftisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;


public final class RaftisClientImpl extends RaftisClient {

    private final RaftisConfig config;
    private final RaftisPoolConfig poolConfig;
    private final Map<Integer, RaftisReplicaSet> slotReplicaSets = new HashMap<Integer, RaftisReplicaSet>();

    public RaftisClientImpl(RaftisConfig config, RaftisPoolConfig poolConfig) {
        this.config = config;
        this.poolConfig = poolConfig;

        for (RaftisShardConfig shardConfig : config.getShards()) {
            RaftisReplicaSetImpl replicaSet = new RaftisReplicaSetImpl(shardConfig, config, poolConfig);
            for (Integer slot : shardConfig.getSlots()) {
                System.err.println("Storing set " + replicaSet + " for slot " + slot);
                slotReplicaSets.put(slot, replicaSet);
            }
        }
    }

    public static class Builder {
        private final RaftisPoolConfig poolConfig;
        private final List<RaftisShardHostConfig> seeds = new ArrayList<RaftisShardHostConfig>();
        private final String localGroup;

        public Builder(RaftisPoolConfig poolConfig, String localGroup) {
            this.poolConfig = poolConfig;
            this.localGroup = localGroup;
        }

        public Builder addSeed(RaftisShardHostConfig seed) {
            this.seeds.add(seed);
            return this;
        }

        public Builder addSeeds(Collection<RaftisShardHostConfig> seeds) {
            this.seeds.addAll(seeds);
            return this;
        }

        public RaftisClientImpl build() {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMinIdle(0);
            jedisPoolConfig.setMaxIdle(1);
            for (RaftisShardHostConfig seed : seeds) {
                RaftisPool pool = new RaftisPool(jedisPoolConfig,
                        seed.getHost(), seed.getPort(), poolConfig.getTimeout());
                Jedis jedis = null;
                try {
                    jedis = pool.getResource();
                    String json = jedis.configGet("cluster").get(1);
                    System.err.println("Got cluster json " + json + " from seed " + seed.getHost() + ":" + seed.getPort());
                    RaftisConfig config = RaftisConfig.parseJSON(json);
                    System.err.println("Parsed config " + config.toString());
                    config.setLocalGroup(localGroup);
                    pool.returnResource(jedis);
                    pool.destroy();

                    boolean validLocalGroup = true;
                    for (RaftisShardConfig shard : config.getShards()) {
                        for (RaftisShardHostConfig host : shard.getHosts()) {
                            if (host.getGroup() == localGroup) {
                                validLocalGroup = true;
                                break;
                            }
                        }
                        if (validLocalGroup) break;
                    }
                    if (!validLocalGroup) {
                        throw new RuntimeException("invalid local group: " + localGroup);
                    }
                    return new RaftisClientImpl(config, poolConfig);
                } catch (JedisConnectionException e) {
                    if (jedis != null) {
                        pool.returnBrokenResource(jedis);
                    }
                    pool.destroy();
                }
            }
            throw new RuntimeException("all seeds failed");
        }
    }

    @Override
    protected RaftisReplicaSet getReplicaSet(String key) {
        int slot = slotForKey(key);
        System.out.println("Got slot " + slot + " for key " + key);
        RaftisReplicaSet ret = slotReplicaSets.get(slot);
        System.out.println("Replicas for slot: " + ret);
        return ret;

    }

    private int slotForKey(String key) {
        if (key == null) { return 0; }
        byte[] b = key.getBytes();
        int sum = 0;
        for (byte i : b) {
            sum = (sum * 17) + (byte)i;
        }
        return Math.abs(sum) % config.getNumSlots();
    }
}
