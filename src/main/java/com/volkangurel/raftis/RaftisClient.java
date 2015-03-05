package com.volkangurel.raftis;

import com.volkangurel.raftis.pool.RaftisPool;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;


public abstract class RaftisClient implements Raftis {

    // based on key, finds replica set
    protected abstract RaftisReplicaSet getReplicaSet(String key);

    private <V> V doCall(RaftisPool pool, ReadCallable<V> callable) {
        Jedis jedis = null;
        boolean error = true;
        V value;
        try {
            jedis = pool.getResource();
            value = callable.call(jedis);
            error = false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (jedis != null) {
                if (error) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
            }
        }
        return value;
    }

    private <V> V doWrite(String  key, ReadCallable<V> callable) {
        return doCall(getReplicaSet(key).getPoolForWrite(), callable);
    }

    private <V> V doRead(String  key, ReadCallable<V> callable) {
        return doCall(getReplicaSet(key).getPoolForRead(), callable);
    }

    // writes
    private static interface ReadCallable<V> {
        public V call(Jedis jedis);
    }

    @Override
    public String set(final String key, final String value) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.set(key, value);
            }
        });
    }

    @Override
    public String getSet(final String key, final String value) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.getSet(key, value);
            }
        });
    }

    @Override
    public Long setnx(final String key, final String value) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.setnx(key, value);
            }
        });
    }

    @Override
    public Long expire(final String key, final int seconds) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.expire(key, seconds);
            }
        });
    }

    @Override
    public Long decrBy(final String key, final long integer) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.decrBy(key, integer);
            }
        });
    }

    @Override
    public Long decr(final String key) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.decr(key);
            }
        });
    }

    @Override
    public Long incrBy(final String key, final long integer) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.incrBy(key, integer);
            }
        });
    }

    @Override
    public Long incr(final String key) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }

    @Override
    public Long append(final String key, final String value) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.append(key, value);
            }
        });
    }

    @Override
    public Long hset(final String key, final String field, final String value) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.hset(key, field, value);
            }
        });
    }

    @Override
    public Long hsetnx(final String key, final String field, final String value) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.hsetnx(key, field, value);
            }
        });
    }

    @Override
    public String hmset(final String key, final Map<String, String> hash) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.hmset(key, hash);
            }
        });
    }

    @Override
    public Long hincrBy(final String key, final String field, final long value) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.hincrBy(key, field, value);
            }
        });
    }

    @Override
    public Long hdel(final String key, final String... field) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.hdel(key, field);
            }
        });
    }

    @Override
    public Long rpush(final String key, final String... string) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.rpush(key, string);
            }
        });
    }

    @Override
    public Long lpush(final String key, final String... string) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.lpush(key, string);
            }
        });
    }

    @Override
    public String ltrim(final String key, final long start, final long end) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.ltrim(key, start, end);
            }
        });
    }

    @Override
    public String lset(final String key, final long index, final String value) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.lset(key, index, value);
            }
        });
    }

    @Override
    public Long lrem(final String key, final long count, final String value) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.lrem(key, count, value);
            }
        });
    }

    @Override
    public String lpop(final String key) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.lpop(key);
            }
        });
    }

    @Override
    public String rpop(final String key) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.rpop(key);
            }
        });
    }

    @Override
    public Long sadd(final String key, final String... member) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.sadd(key, member);
            }
        });
    }

    @Override
    public Long srem(final String key, final String... member) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.srem(key, member);
            }
        });
    }

    @Override
    public String spop(final String key) {
        return doWrite(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.spop(key);
            }
        });
    }

    @Override
    public Long lpushx(final String key, final String... string) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.lpushx(key, string);
            }
        });
    }

    @Override
    public Long rpushx(final String key, final String... string) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.rpushx(key, string);
            }
        });
    }

    @Override
    public Long del(final String key) {
        return doWrite(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    // reads
    @Override
    public String get(final String key) {
        return doRead(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    @Override
    public Boolean exists(final String key) {
        return doRead(key, new ReadCallable<Boolean>() {
            @Override
            public Boolean call(Jedis jedis) {
                return jedis.exists(key);
            }
        });
    }

    @Override
    public Long ttl(final String key) {
        return doRead(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.ttl(key);
            }
        });
    }

    @Override
    public String hget(final String key, final String field) {
        return doRead(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    @Override
    public List<String> hmget(final String key, final String... fields) {
        return doRead(key, new ReadCallable<List<String>>() {
            @Override
            public List<String> call(Jedis jedis) {
                return jedis.hmget(key, fields);
            }
        });
    }

    @Override
    public Boolean hexists(final String key, final String field) {
        return doRead(key, new ReadCallable<Boolean>() {
            @Override
            public Boolean call(Jedis jedis) {
                return jedis.hexists(key, field);
            }
        });
    }

    @Override
    public Long hlen(final String key) {
        return doRead(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.hlen(key);
            }
        });
    }

    @Override
    public Set<String> hkeys(final String key) {
        return doRead(key, new ReadCallable<Set<String>>() {
            @Override
            public Set<String> call(Jedis jedis) {
                return jedis.hkeys(key);
            }
        });
    }

    @Override
    public List<String> hvals(final String key) {
        return doRead(key, new ReadCallable<List<String>>() {
            @Override
            public List<String> call(Jedis jedis) {
                return jedis.hvals(key);
            }
        });
    }

    @Override
    public Map<String, String> hgetAll(final String key) {
        return doRead(key, new ReadCallable<Map<String, String>>() {
            @Override
            public Map<String, String> call(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    @Override
    public Long llen(final String key) {
        return doRead(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    @Override
    public List<String> lrange(final String key, final long start, final long end) {
        return doRead(key, new ReadCallable<List<String>>() {
            @Override
            public List<String> call(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        });
    }

    @Override
    public String lindex(final String key, final long index) {
        return doRead(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.lindex(key, index);
            }
        });
    }

    @Override
    public Set<String> smembers(final String key) {
        return doRead(key, new ReadCallable<Set<String>>() {
            @Override
            public Set<String> call(Jedis jedis) {
                return jedis.smembers(key);
            }
        });
    }

    @Override
    public Long scard(final String key) {
        return doRead(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.scard(key);
            }
        });
    }

    @Override
    public Boolean sismember(final String key, final String member) {
        return doRead(key, new ReadCallable<Boolean>() {
            @Override
            public Boolean call(Jedis jedis) {
                return jedis.sismember(key, member);
            }
        });
    }

    @Override
    public String srandmember(final String key) {
        return doRead(key, new ReadCallable<String>() {
            @Override
            public String call(Jedis jedis) {
                return jedis.srandmember(key);
            }
        });
    }

    @Override
    public Long strlen(final String key) {
        return doRead(key, new ReadCallable<Long>() {
            @Override
            public Long call(Jedis jedis) {
                return jedis.strlen(key);
            }
        });
    }

}
