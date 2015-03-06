package com.volkangurel.raftis.config.json;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RaftisJsonConfig {
    private Integer numSlots;
    private List<RaftisJsonShardConfig> shards;
    private RaftisJsonMeConfig me;

    public void setNumSlots(Integer numSlots) {
        this.numSlots = numSlots;
    }

    public void setShards(List<RaftisJsonShardConfig> shards) {
        this.shards = shards;
    }

    public void setMe(RaftisJsonMeConfig me) {
        this.me = me;
    }

    public Integer getNumSlots() {
        return numSlots;
    }

    public List<RaftisJsonShardConfig> getShards() {
        return shards;
    }

    public RaftisJsonMeConfig getMe() {
        return me;
    }

}
