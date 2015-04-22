package com.volkangurel.raftis.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkangurel.raftis.config.json.RaftisJsonConfig;
import com.volkangurel.raftis.config.json.RaftisJsonHostConfig;
import com.volkangurel.raftis.config.json.RaftisJsonShardConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RaftisConfig {
    public static final int DEFAULT_RAFTIS_PORT = 8369;

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
        config.setLocalGroup(jsonConfig.getMe().getGroup());
        for (RaftisJsonShardConfig jsonShardConfig : jsonConfig.getShards()) {
            RaftisShardConfig shardConfig = new RaftisShardConfig();
            shardConfig.addSlots(jsonShardConfig.getSlots());
            for (RaftisJsonHostConfig jsonShardHostConfig: jsonShardConfig.getHosts()) {
                String[] hostPort = jsonShardHostConfig.getRedisAddr().split(":", 2);
                String host = hostPort[0];
                int port;
                if (hostPort.length > 1) {
                    port = Integer.valueOf(hostPort[1]);
                } else {
                    port = DEFAULT_RAFTIS_PORT;
                }
                shardConfig.addShardHostConfig(new RaftisShardHostConfig
                        .Builder(host, jsonShardHostConfig.getGroup())
                        .port(port)
                        .build());
            }
            config.addShardConfig(shardConfig);
        }
        return config;
    }

}
