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
    private final String myName;

    public RaftisReplicaSetImpl(RaftisShardConfig config, RaftisConfig raftisConfig,
                                RaftisPoolConfig poolConfig) {
        this.config = config;
        this.raftisConfig = raftisConfig;
        this.poolConfig = poolConfig;

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(poolConfig.getMaxIdle());
        StringBuffer name = new StringBuffer();
        for (RaftisShardHostConfig host : config.getHosts()) {
            if (pools.containsKey(host.getGroup())) {
                throw new RuntimeException("Duplicate group in replica set: " + host.getGroup() + " config: " + config.toString());
            }
            name.append("|" + host.getHost() +":"+ host.getPort() + ":" + host.getGroup() + "|");
            pools.put(host.getGroup(),
                    new RaftisPool(jedisPoolConfig, host.getHost(), host.getPort(), poolConfig.getTimeout()));
        }
        myName = name.toString();
    }

    @Override
    RaftisPool getPoolForRead() {
        RaftisPool pool = pools.get(raftisConfig.getLocalGroup());
        if (pool == null) {
            throw new RuntimeException("pool not found for local group " + raftisConfig.getLocalGroup());
        }
        return pool;
    }

    @Override
    RaftisPool getPoolForWrite() {
        RaftisPool pool = pools.get(raftisConfig.getLocalGroup());
        if (pool == null) {
            throw new RuntimeException("pool not found for local group " + raftisConfig.getLocalGroup());
        }
        return pool;
    }

    @Override
    public String toString() {
        return myName;
    }
}
