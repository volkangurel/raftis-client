package com.volkangurel.raftis;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Raftis {

    String set(String key, String value);

    String get(String key);

    Boolean exists(String key);

//    String type(String key);

    Long expire(String key, int seconds);
//
//    Long expireAt(String key, long unixTime);

    Long ttl(String key);

//    Boolean setbit(String key, long offset, boolean value);
//
//    Boolean setbit(String key, long offset, String value);
//
//    Boolean getbit(String key, long offset);
//
//    Long setrange(String key, long offset, String value);
//
//    String getrange(String key, long startOffset, long endOffset);

    String getSet(String key, String value);

    Long setnx(String key, String value);

    //String setex(String key, int seconds, String value);

    Long decrBy(String key, long integer);

    Long decr(String key);

    Long incrBy(String key, long integer);

    Long incr(String key);

    Long append(String key, String value);

//    String substr(String key, int start, int end);

    Long hset(String key, String field, String value);

    String hget(String key, String field);

    Long hsetnx(String key, String field, String value);

    String hmset(String key, Map<String, String> hash);

    List<String> hmget(String key, String... fields);

    Long hincrBy(String key, String field, long value);

    Boolean hexists(String key, String field);

    Long hdel(String key, String... field);

    Long hlen(String key);

    Set<String> hkeys(String key);

    List<String> hvals(String key);

    Map<String, String> hgetAll(String key);

    Long rpush(String key, String... string);

    Long lpush(String key, String... string);

    Long llen(String key);

    List<String> lrange(String key, long start, long end);

    String ltrim(String key, long start, long end);

    String lindex(String key, long index);

    String lset(String key, long index, String value);

    Long lrem(String key, long count, String value);

    String lpop(String key);

    String rpop(String key);

    Long sadd(String key, String... member);

    Set<String> smembers(String key);

    Long srem(String key, String... member);

    String spop(String key);

    Long scard(String key);

    Boolean sismember(String key, String member);

    String srandmember(String key);

    Long strlen(String key);

    Long lpushx(String key, String... string);

    Long rpushx(String key, String... string);

    Long del(String key);
//
//    String echo(String string);
//
//    Long bitcount(final String key);
//
//    Long bitcount(final String key, long start, long end);

}
