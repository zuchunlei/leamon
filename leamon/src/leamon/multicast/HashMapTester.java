package leamon.multicast;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap�Ĳ����࣬������Ҫ����Key�����hashCode��equals������Map������Ӱ��
 */
public class HashMapTester {

    public static void main(String[] args) {
        Map<Person, Object> map = new HashMap<Person, Object>();

        for (int i = 0; i < 20; i++) {
            map.put(new Person(i), new Object());
        }

        System.out.println(map.size());
    }

    /**
     * HashMap������Key������
     */
    static class Person {
        private int id;

        public Person(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Person;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }
}
