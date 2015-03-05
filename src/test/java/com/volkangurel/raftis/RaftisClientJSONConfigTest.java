package com.volkangurel.raftis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkangurel.raftis.config.json.RaftisJsonConfig;
import com.volkangurel.raftis.config.json.RaftisJsonHostConfig;
import com.volkangurel.raftis.config.json.RaftisJsonShardConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class RaftisClientJSONConfigTest {
    private static final String json = "{\"shards\": [{\"slots\": [0], \"hosts\": [{\"host\": \"raftis-0-lvs.example.com\", \"group\": \"lvs\"}, {\"host\": \"raftis-0-slc.example.com\", \"group\": \"slc\"}, {\"host\": \"raftis-0-phx.example.com\", \"group\": \"phx\"}]}, {\"slots\": [1], \"hosts\": [{\"host\": \"raftis-1-slc.example.com\", \"group\": \"slc\"}, {\"host\": \"raftis-1-phx.example.com\", \"group\": \"phx\"}, {\"host\": \"raftis-1-lvs.example.com\", \"group\": \"lvs\"}]}, {\"slots\": [2], \"hosts\": [{\"host\": \"raftis-2-phx.example.com\", \"group\": \"phx\"}, {\"host\": \"raftis-2-lvs.example.com\", \"group\": \"lvs\"}, {\"host\": \"raftis-2-slc.example.com\", \"group\": \"slc\"}]}, {\"slots\": [3], \"hosts\": [{\"host\": \"raftis-3-lvs.example.com\", \"group\": \"lvs\"}, {\"host\": \"raftis-3-slc.example.com\", \"group\": \"slc\"}, {\"host\": \"raftis-3-phx.example.com\", \"group\": \"phx\"}]}, {\"slots\": [4], \"hosts\": [{\"host\": \"raftis-4-lvs.example.com\", \"group\": \"lvs\"}, {\"host\": \"raftis-4-phx.example.com\", \"group\": \"phx\"}, {\"host\": \"raftis-4-slc.example.com\", \"group\": \"slc\"}]}], \"numSlots\": 5}";

    @Test
    public void deserializationTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RaftisJsonConfig config = mapper.readValue(json, RaftisJsonConfig.class);

        Assert.assertEquals(config.getNumSlots().longValue(), 5L);

        RaftisJsonShardConfig shard = config.getShards().get(0);
        Assert.assertEquals(shard.getSlots().get(0).longValue(), 0L);

        RaftisJsonHostConfig host = shard.getHosts().get(0);
        Assert.assertEquals(host.getHost(), "raftis-0-lvs.example.com");
        Assert.assertEquals(host.getGroup(), "lvs");
    }
}
