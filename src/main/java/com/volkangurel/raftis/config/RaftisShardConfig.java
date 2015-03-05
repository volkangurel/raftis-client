package com.volkangurel.raftis.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RaftisShardConfig {
    private final List<RaftisShardHostConfig> hosts = new ArrayList<RaftisShardHostConfig>();
    private final Set<Integer> slots = new HashSet<Integer>();

    public RaftisShardConfig() {
    }

    public RaftisShardConfig addSlot(Integer slot) {
        slots.add(slot);
        return this;
    }

    public RaftisShardConfig addShardHostConfig(RaftisShardHostConfig host) {
        hosts.add(host);
        return this;
    }

    public List<RaftisShardHostConfig> getHosts() {
        return hosts;
    }

    public Set<Integer> getSlots() {
        return slots;
    }

}
