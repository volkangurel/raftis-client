package com.volkangurel.raftis;

import com.volkangurel.raftis.config.RaftisConfig;
import com.volkangurel.raftis.config.RaftisPoolConfig;
import com.volkangurel.raftis.config.RaftisShardConfig;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


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
                slotReplicaSets.put(slot, replicaSet);
            }
        }
    }

    @Override
    protected RaftisReplicaSet getReplicaSet(String key) {
        int slot = slotForKey(key);
        return slotReplicaSets.get(slot);
    }

    private int slotForKey(String key) {
        ByteBuffer bytes = StandardCharsets.UTF_8.encode(key);
        int sum = 0;
        for (int i : bytes.array()) {
            sum = (sum * 17) + i;
        }
        return sum % config.getNumSlots();
    }
}
