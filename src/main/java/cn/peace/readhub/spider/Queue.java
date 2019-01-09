package cn.peace.readhub.spider;

import cn.peace.readhub.domain.AccRcdDO;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Queue {
    public static ConcurrentLinkedQueue<AccRcdDO> accQueue = new ConcurrentLinkedQueue<AccRcdDO>();
}
