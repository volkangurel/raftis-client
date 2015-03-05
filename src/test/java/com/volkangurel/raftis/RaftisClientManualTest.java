package com.volkangurel.raftis;


import com.volkangurel.raftis.config.RaftisConfig;
import com.volkangurel.raftis.config.RaftisPoolConfig;
import com.volkangurel.raftis.config.RaftisShardConfig;
import com.volkangurel.raftis.config.RaftisShardHostConfig;


public class RaftisClientManualTest {
    private static void run() {
        RaftisConfig config = new RaftisConfig()
                .setNumSlots(1)
                .setLocalGroup("local1")
                .addShardConfig(new RaftisShardConfig()
                        .addSlot(0)
                        .addShardHostConfig(new RaftisShardHostConfig
                                .Builder("localhost", "local1")
                                .port(16379)
                                .build())
                        .addShardHostConfig(new RaftisShardHostConfig
                                .Builder("localhost", "local2")
                                .port(16389)
                                .build())
                        .addShardHostConfig(new RaftisShardHostConfig
                                .Builder("localhost", "local3")
                                .port(16399)
                                .build()));
        RaftisPoolConfig poolConfig = new RaftisPoolConfig()
                .setMaxIdle(10)
                .setTimeout(1000);

        RaftisClient client = new RaftisClientImpl(config, poolConfig);

        client.set("foo", "bar");
        System.out.println(client.get("foo"));
    }

    public static void main(String[] s) {
        run();
    }
}
