package com.volkangurel.raftis;

import com.volkangurel.raftis.config.RaftisConfig;
import com.volkangurel.raftis.config.RaftisShardConfig;
import com.volkangurel.raftis.config.RaftisShardHostConfig;
import com.volkangurel.raftis.pool.RaftisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public class RaftisReplicaSetImpl extends RaftisReplicaSet {

    private final RaftisShardConfig config;
    private final RaftisConfig raftisConfig;
    private final Map<String, RaftisPool> pools = new HashMap<String, RaftisPool>();

    public RaftisReplicaSetImpl(RaftisShardConfig config, RaftisConfig raftisConfig) {
        this.config = config;
        this.raftisConfig = raftisConfig;

        for (RaftisShardHostConfig host : config.getHosts()) {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxIdle(raftisConfig.getMaxIdle());
            if (pools.containsKey(host.getGroup())) {
                throw new RuntimeException("");
            }
            pools.put(host.getGroup(),
                    new RaftisPool(poolConfig, host.getHost(), host.getPort(), raftisConfig.getTimeout()));
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
