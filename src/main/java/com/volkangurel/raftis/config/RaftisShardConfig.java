package com.volkangurel.raftis.config;

import java.util.*;

public class RaftisShardConfig {
    private final List<RaftisShardHostConfig> hosts = new ArrayList<RaftisShardHostConfig>();
    private final Set<Integer> slots = new HashSet<Integer>();

    public RaftisShardConfig() {
    }

    public RaftisShardConfig addSlot(Integer slot) {
        this.slots.add(slot);
        return this;
    }

    public RaftisShardConfig addSlots(Collection<Integer> slots) {
        this.slots.addAll(slots);
        return this;
    }

    public RaftisShardConfig addShardHostConfig(RaftisShardHostConfig host) {
        this.hosts.add(host);
        return this;
    }

    public List<RaftisShardHostConfig> getHosts() {
        return hosts;
    }

    public Set<Integer> getSlots() {
        return slots;
    }

}
