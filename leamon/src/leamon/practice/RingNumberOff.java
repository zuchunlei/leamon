package leamon.practice;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 500个小孩围成一圈，从第一个开始报数：1，2，3，1，2，3，1，2，3，... ... 每次报3的小孩退出
 * 问最后剩下的那个小孩，在以前500人里是第几个？
 */
public class RingNumberOff {

    public static void main(String[] args) {
        Queue<Child> childs = new LinkedList<Child>();

        for (int i = 1; i <= 500; i++) {
            childs.add(new Child(i));
        }
        Child result = null;
        int i = 0;
        while (!childs.isEmpty()) {
            if (++i != 3) {
                childs.add(childs.poll());
            } else {
                result = childs.poll();
                i = 0;
            }
        }

        System.out.println(result.getIndex());
    }

    static class Child {
        private int index;

        public Child(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

}
