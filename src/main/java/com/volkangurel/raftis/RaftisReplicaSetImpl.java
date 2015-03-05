package com.volkangurel.raftis;

import com.volkangurel.raftis.config.RaftisConfig;
import com.volkangurel.raftis.config.RaftisPoolConfig;
import com.volkangurel.raftis.config.RaftisShardConfig;
import com.volkangurel.raftis.config.RaftisShardHostConfig;
import com.volkangurel.raftis.pool.RaftisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public class RaftisReplicaSetImpl extends RaftisReplicaSet {

    private final RaftisShardConfig config;
    private final RaftisConfig raftisConfig;
    private final RaftisPoolConfig poolConfig;
    private final Map<String, RaftisPool> pools = new HashMap<String, RaftisPool>();

    public RaftisReplicaSetImpl(RaftisShardConfig config, RaftisConfig raftisConfig,
                                RaftisPoolConfig poolConfig) {
        this.config = config;
        this.raftisConfig = raftisConfig;
        this.poolConfig = poolConfig;

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(poolConfig.getMaxIdle());

        for (RaftisShardHostConfig host : config.getHosts()) {
            if (pools.containsKey(host.getGroup())) {
                throw new RuntimeException("Duplicate group in replica set: " + host.getGroup());
            }
            pools.put(host.getGroup(),
                    new RaftisPool(jedisPoolConfig, host.getHost(), host.getPort(), poolConfig.getTimeout()));
        }
    }

    @Override
    RaftisPool getPoolForRead() {
        return pools.get(raftisConfig.getLocalGroup());
    }

    @Override
    RaftisPool getPoolForWrite() {
        return pools.get(raftisConfig.getLocalGroup());
    }
}
