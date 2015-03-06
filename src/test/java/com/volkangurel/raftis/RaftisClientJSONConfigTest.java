package com.volkangurel.raftis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkangurel.raftis.config.json.RaftisJsonConfig;
import com.volkangurel.raftis.config.json.RaftisJsonHostConfig;
import com.volkangurel.raftis.config.json.RaftisJsonShardConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class RaftisClientJSONConfigTest {
    private static final String json = "{\n"+
            "    \"me\": {\n"+
            "        \"flotillaAddr\": \"raftis-0-slc-7231.example.com:1103\",\n"+
            "        \"group\": \"slc\",\n"+
            "        \"redisAddr\": \"raftis-0-slc-7231.example.com:8369\"\n"+
            "    },\n"+
            "    \"shards\": [\n"+
            "        {\n"+
            "            \"hosts\": [\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-0-lvs-7232.example.com:1103\",\n"+
            "                    \"group\": \"lvs\",\n"+
            "                    \"redisAddr\": \"raftis-0-lvs-7232.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-0-slc-7231.example.com:1103\",\n"+
            "                    \"group\": \"slc\",\n"+
            "                    \"redisAddr\": \"raftis-0-slc-7231.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-0-phx-7229.example.com:1103\",\n"+
            "                    \"group\": \"phx\",\n"+
            "                    \"redisAddr\": \"raftis-0-phx-7229.example.com:8369\"\n"+
            "                }\n"+
            "            ],\n"+
            "            \"slots\": [ 0, 5, 10, 15, 20, 25, 30, 35, 40, 45 ]\n"+
            "        },\n"+
            "        {\n"+
            "            \"hosts\": [\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-1-slc-7239.example.com:1103\",\n"+
            "                    \"group\": \"slc\",\n"+
            "                    \"redisAddr\": \"raftis-1-slc-7239.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-1-phx-7233.example.com:1103\",\n"+
            "                    \"group\": \"phx\",\n"+
            "                    \"redisAddr\": \"raftis-1-phx-7233.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-1-lvs-7228.example.com:1103\",\n"+
            "                    \"group\": \"lvs\",\n"+
            "                    \"redisAddr\": \"raftis-1-lvs-7228.example.com:8369\"\n"+
            "                }\n"+
            "            ],\n"+
            "            \"slots\": [ 1, 6, 11, 16, 21, 26, 31, 36, 41, 46 ]\n"+
            "        },\n"+
            "        {\n"+
            "            \"hosts\": [\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-2-phx-7238.example.com:1103\",\n"+
            "                    \"group\": \"phx\",\n"+
            "                    \"redisAddr\": \"raftis-2-phx-7238.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-2-lvs-7230.example.com:1103\",\n"+
            "                    \"group\": \"lvs\",\n"+
            "                    \"redisAddr\": \"raftis-2-lvs-7230.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-2-slc-7226.example.com:1103\",\n"+
            "                    \"group\": \"slc\",\n"+
            "                    \"redisAddr\": \"raftis-2-slc-7226.example.com:8369\"\n"+
            "                }\n"+
            "            ],\n"+
            "            \"slots\": [ 2, 7, 12, 17, 22, 27, 32, 37, 42, 47 ]\n"+
            "        },\n"+
            "        {\n"+
            "            \"hosts\": [\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-3-lvs-7236.example.com:1103\",\n"+
            "                    \"group\": \"lvs\",\n"+
            "                    \"redisAddr\": \"raftis-3-lvs-7236.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-3-slc-7235.example.com:1103\",\n"+
            "                    \"group\": \"slc\",\n"+
            "                    \"redisAddr\": \"raftis-3-slc-7235.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-3-phx-7234.example.com:1103\",\n"+
            "                    \"group\": \"phx\",\n"+
            "                    \"redisAddr\": \"raftis-3-phx-7234.example.com:8369\"\n"+
            "                }\n"+
            "            ],\n"+
            "            \"slots\": [ 3, 8, 13, 18, 23, 28, 33, 38, 43, 48 ]\n"+
            "        },\n"+
            "        {\n"+
            "            \"hosts\": [\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-4-lvs-7240.example.com:1103\",\n"+
            "                    \"group\": \"lvs\",\n"+
            "                    \"redisAddr\": \"raftis-4-lvs-7240.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-4-phx-7237.example.com:1103\",\n"+
            "                    \"group\": \"phx\",\n"+
            "                    \"redisAddr\": \"raftis-4-phx-7237.example.com:8369\"\n"+
            "                },\n"+
            "                {\n"+
            "                    \"flotillaAddr\": \"raftis-4-slc-7227.example.com:1103\",\n"+
            "                    \"group\": \"slc\",\n"+
            "                    \"redisAddr\": \"raftis-4-slc-7227.example.com:8369\"\n"+
            "                }\n"+
            "            ],\n"+
            "            \"slots\": [ 4, 9, 14, 19, 24, 29, 34, 39, 44, 49\n"+
            "            ]\n"+
            "        }\n"+
            "    ],\n"+
            "    \"numSlots\": 50\n"+
            "}\n";


    @Test
    public void deserializationTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RaftisJsonConfig config = mapper.readValue(json, RaftisJsonConfig.class);

        Assert.assertEquals(50L, config.getNumSlots().longValue());

        RaftisJsonShardConfig shard = config.getShards().get(0);
        Assert.assertEquals(0L, shard.getSlots().get(0).longValue());

        RaftisJsonHostConfig host = shard.getHosts().get(0);
        Assert.assertEquals("raftis-0-lvs-7232.example.com:8369", host.getRedisAddr());
        Assert.assertEquals("lvs", host.getGroup());
    }

}
