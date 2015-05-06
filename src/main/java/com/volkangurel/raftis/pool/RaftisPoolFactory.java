package com.volkangurel.raftis.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by vgurel on 3/5/15.
 */
public class RaftisPoolFactory implements PooledObjectFactory<Jedis> {

    private final String host;
    private final int port;
    private final int timeout;

    private final AtomicInteger connectionsCreated = new AtomicInteger();
    private final AtomicInteger connectionsActive = new AtomicInteger();
    private final AtomicLong connectionsRequested = new AtomicLong();

    public RaftisPoolFactory(final String host, final int port, final int timeout) {
        super();
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    @Override
    public PooledObject<Jedis> makeObject() throws Exception {
        final Jedis jedis = new Jedis(this.host, this.port, this.timeout);
        jedis.connect();
        connectionsCreated.incrementAndGet();
        return new DefaultPooledObject<Jedis>(jedis);
    }

    @Override
    public void destroyObject(final PooledObject<Jedis> pooledJedis) throws Exception {
        final BinaryJedis jedis = pooledJedis.getObject();
        if (jedis.isConnected()) {
            try {
                try {
                    jedis.quit();
                } catch (Exception e) {
                }
                jedis.disconnect();
            } catch (Exception e) {
                // empty
            }
        }
        connectionsCreated.decrementAndGet();
    }

    @Override
    public boolean validateObject(final PooledObject<Jedis> pooledJedis) {
        final BinaryJedis jedis = pooledJedis.getObject();
        try {
            return jedis.isConnected() && jedis.ping().equals("PONG");
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public void activateObject(final PooledObject<Jedis> pooledJedis) throws Exception {
        connectionsActive.incrementAndGet();
        connectionsRequested.incrementAndGet();
    }

    @Override
    public void passivateObject(final PooledObject<Jedis> pooledJedis) throws Exception {
        connectionsActive.decrementAndGet();
    }

    public int getConnectionsCreated() {
        return connectionsCreated.get();
    }

    public int getConnectionsActive() {
        return connectionsActive.get();
    }

    public long getConnectionsRequested() {
        return connectionsRequested.get();
    }

}
