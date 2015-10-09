package leamon.practice;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 500��С��Χ��һȦ���ӵ�һ����ʼ������1��2��3��1��2��3��1��2��3��... ... ÿ�α�3��С���˳�
 * �����ʣ�µ��Ǹ�С��������ǰ500�����ǵڼ�����
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
