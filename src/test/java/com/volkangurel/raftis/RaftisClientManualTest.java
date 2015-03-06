package com.volkangurel.raftis;


import com.volkangurel.raftis.config.RaftisConfig;
import com.volkangurel.raftis.config.RaftisPoolConfig;
import com.volkangurel.raftis.config.RaftisShardConfig;
import com.volkangurel.raftis.config.RaftisShardHostConfig;


public class RaftisClientManualTest {
    private static void runExplicitConfig() {
        RaftisConfig config = new RaftisConfig()
                .setNumSlots(1)
                .setLocalGroup("local1")
                .addShardConfig(new RaftisShardConfig()
                        .addSlot(0)
                        .addShardHostConfig(new RaftisShardHostConfig
                                .Builder("localhost", "local1")
                                .port(8369)
                                .build())
                        .addShardHostConfig(new RaftisShardHostConfig
                                .Builder("localhost", "local2")
                                .port(8370)
                                .build())
                        .addShardHostConfig(new RaftisShardHostConfig
                                .Builder("localhost", "local3")
                                .port(8371)
                                .build()));
        RaftisPoolConfig poolConfig = new RaftisPoolConfig()
                .setMaxIdle(10)
                .setTimeout(1000);

        RaftisClient client = new RaftisClientImpl(config, poolConfig);

        client.set("foo", "bar");
        System.out.println(client.get("foo"));
    }

    private static void runSeedConfig() {
        RaftisPoolConfig poolConfig = new RaftisPoolConfig()
                .setMaxIdle(10)
                .setTimeout(1000);
        RaftisClient client = new RaftisClientImpl
                .Builder(poolConfig, "local2")
                .addSeed(new RaftisShardHostConfig
                        .Builder("localhost", "local3")
                        .port(8370)
                        .build())
                .build();
        client.set("foo", "bar");
        System.out.println(client.get("foo"));
    }

    public static void main(String[] s) {
        runSeedConfig();
    }
}
