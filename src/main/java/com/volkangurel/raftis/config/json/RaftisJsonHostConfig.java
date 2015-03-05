package com.volkangurel.raftis.config.json;

public class RaftisJsonHostConfig {
    private String host;
    private String group;

    public void setGroup(String group) {
        this.group = group;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getGroup() {
        return group;
    }

    public String getHost() {
        return host;
    }

}
