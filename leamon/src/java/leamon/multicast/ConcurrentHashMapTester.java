package leamon.multicast;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 并发Map测试类
 */
public class ConcurrentHashMapTester {

    public static void main(String[] args) {
        /**
         * ConcurrentHashMap主要的实现原理就是"锁分离"，ConcurrentHashMap内部使用段(Segment)
         * 来表示这些不同的部分，每个段其实就是一个独立的hash table，它们有自己的锁。
         * 
         * 只要多个修改操作发生在不同的段上，它们就可以并发进行。
         */
        final ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();

        new Thread() {
            @Override
            public void run() {
                map.putIfAbsent("zuchunlei", new Object() {
                    @Override
                    public String toString() {
                        return "zuchunlei";
                    }

                });
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                map.putIfAbsent("zuchunlei", new Object() {
                    @Override
                    public String toString() {
                        return "zuchunlei-1";
                    }

                });
            }
        }.start();

    }
}
