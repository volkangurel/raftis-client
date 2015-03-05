package com.volkangurel.raftis.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkangurel.raftis.config.json.RaftisJsonConfig;
import com.volkangurel.raftis.config.json.RaftisJsonHostConfig;
import com.volkangurel.raftis.config.json.RaftisJsonShardConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaftisConfig {
    private volatile String localGroup;
    private volatile int numSlots;
    private final List<RaftisShardConfig> shards = new ArrayList<RaftisShardConfig>();

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

    private static final ObjectMapper mapper = new ObjectMapper();
    public static RaftisConfig parseJSON(final String json) {
        RaftisJsonConfig jsonConfig;
        try {
            jsonConfig = mapper.readValue(json, RaftisJsonConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        RaftisConfig config = new RaftisConfig();
        config.setNumSlots(jsonConfig.getNumSlots());
        for (RaftisJsonShardConfig jsonShardConfig : jsonConfig.getShards()) {
            RaftisShardConfig shardConfig = new RaftisShardConfig();
            shardConfig.addSlots(jsonShardConfig.getSlots());
            for (RaftisJsonHostConfig jsonShardHostConfig: jsonShardConfig.getHosts()) {
                shardConfig.addShardHostConfig(new RaftisShardHostConfig
                        .Builder(jsonShardHostConfig.getHost(), jsonShardHostConfig.getGroup())
                        .build());
            }
            config.addShardConfig(shardConfig);
        }
        return config;
    }

}
