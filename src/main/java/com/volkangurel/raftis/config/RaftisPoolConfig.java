package com.volkangurel.raftis.config;


public class RaftisPoolConfig {
    private volatile int timeout = 5000;
    private volatile int maxIdle = 10;

    public RaftisPoolConfig setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public RaftisPoolConfig setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getMaxIdle() {
        return maxIdle;
    }
}
