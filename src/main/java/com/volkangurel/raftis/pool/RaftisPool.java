package com.volkangurel.raftis.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;


public class RaftisPool extends Pool<Jedis> {

    private final RaftisPoolFactory factory;

    /**
     *
     * @param poolConfig
     * @param host
     * @param port
     * @param timeout in milliseconds
     */
    public RaftisPool(final GenericObjectPoolConfig poolConfig, final String host,
                      final int port, final int timeout) {
        this(poolConfig, new RaftisPoolFactory(host, port, timeout));
    }

    public RaftisPool(final GenericObjectPoolConfig poolConfig, final RaftisPoolFactory factory) {
        super(poolConfig, factory);
        this.factory = factory;
    }

    @Override
    public void returnBrokenResource(final Jedis resource) {
        returnBrokenResourceObject(resource);
    }

    @Override
    public void returnResource(final Jedis resource) {
        resource.resetState();
        returnResourceObject(resource);
    }

    public int getConnectionsCreated() {
        return factory.getConnectionsCreated();
    }

    public int getConnectionsActive() {
        return factory.getConnectionsActive();
    }

    public long getConnectionsRequested() {
        return factory.getConnectionsRequested();
    }

}
