package com.volkangurel.raftis;

import com.volkangurel.raftis.pool.RaftisPool;

public abstract class RaftisReplicaSet {

    abstract RaftisPool getPoolForRead();

    abstract RaftisPool getPoolForWrite();
}
