package com.volkangurel.raftis.config;

public class RaftisShardHostConfig {
    private final String host;
    private final int port;
    private final String group;

    private RaftisShardHostConfig(String host, int port, String group) {
        this.host = host;
        this.port = port;
        this.group = group;
    }

    public static final class Builder {
        private final String host;
        private final String group;
        private int port = 6379;

        public Builder(String host, String group) {
            this.host = host;
            this.group = group;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public RaftisShardHostConfig build() {
            return new RaftisShardHostConfig(this.host, this.port, this.group);
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getGroup() {
        return group;
    }

}
