package com.volkangurel.raftis.config.json;

public class RaftisJsonMeConfig {
    private String redisAddr;
    private String flotillaAddr;
    private String group;

    public void setRedisAddr(String redisAddr) {
        this.redisAddr = redisAddr;
    }

    public void setFlotillaAddr(String flotillaAddr) {
        this.flotillaAddr = flotillaAddr;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getRedisAddr() {
        return redisAddr;
    }

    public String getFlotillaAddr() {
        return flotillaAddr;
    }

    public String getGroup() {
        return group;
    }
}
