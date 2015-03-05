package com.volkangurel.raftis.config;


import java.util.ArrayList;
import java.util.List;

public class RaftisConfig {
    private volatile String localGroup;
    private volatile int numSlots;
    private final List<RaftisShardConfig> shards = new ArrayList<RaftisShardConfig>();

    // pool config
    private volatile int timeout = 1000;
    private volatile int maxIdle = 10;

    public RaftisConfig() {
    }

    public RaftisConfig setLocalGroup(String localGroup) {
        this.localGroup = localGroup;
        return this;
    }

    public RaftisConfig setNumSlots(int numSlots) {
        this.numSlots = numSlots;
        return this;
    }

    public RaftisConfig addShardConfig(RaftisShardConfig shard) {
        shards.add(shard);
        return this;
    }

    public int getNumSlots() {
        return numSlots;
    }

    public List<RaftisShardConfig> getShards() {
        return shards;
    }

    public String getLocalGroup() {
        return this.localGroup;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

}
