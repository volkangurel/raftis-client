package com.volkangurel.raftis.config.json;

import java.util.List;

public class RaftisJsonShardConfig {
    private List<Integer> slots;
    private List<RaftisJsonHostConfig> hosts;

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }

    public void setHosts(List<RaftisJsonHostConfig> hosts) {
        this.hosts = hosts;
    }


    public List<Integer> getSlots() {
        return slots;
    }

    public List<RaftisJsonHostConfig> getHosts() {
        return hosts;
    }

}
