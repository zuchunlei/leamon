package leamon.multicast;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ����Map������
 */
public class ConcurrentHashMapTester {

    public static void main(String[] args) {
        /**
         * ConcurrentHashMap��Ҫ��ʵ��ԭ�����"������"��ConcurrentHashMap�ڲ�ʹ�ö�(Segment)
         * ����ʾ��Щ��ͬ�Ĳ��֣�ÿ������ʵ����һ��������hash table���������Լ�������
         * 
         * ֻҪ����޸Ĳ��������ڲ�ͬ�Ķ��ϣ����ǾͿ��Բ������С�
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
