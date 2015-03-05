package com.volkangurel.raftis.config.json;


import java.util.List;

public class RaftisJsonConfig {
    private Integer numSlots;
    private List<RaftisJsonShardConfig> shards;

    public void setNumSlots(Integer numSlots) {
        this.numSlots = numSlots;
    }

    public void setShards(List<RaftisJsonShardConfig> shards) {
        this.shards = shards;
    }

    public Integer getNumSlots() {
        return numSlots;
    }

    public List<RaftisJsonShardConfig> getShards() {
        return shards;
    }

}
