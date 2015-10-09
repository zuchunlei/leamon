package leamon.multicast;

import java.util.HashMap;
import java.util.Map;

/**
 * HashMap的测试类，该类主要测试Key对象的hashCode与equals方法对Map容器的影响
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
     * HashMap容器的Key键类型
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
