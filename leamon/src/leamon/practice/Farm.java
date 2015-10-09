package leamon.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * ũ��
 */
public class Farm {

    private List<Cow> cows = new ArrayList<Cow>();// ĸţ����
    private Cow ancestor;// ����

    public Farm() {
        ancestor = new Cow();
        cows.add(ancestor);
    }

    public void grow() {
        List<Cow> list = new ArrayList<Cow>();

        for (Cow cow : cows) {
            Cow child = cow.grow();

            if (child != null) {
                list.add(child);
            }
            list.add(cow);
        }

        this.cows = list;
    }

    public int count() {
        return cows.size();
    }

    public static void main(String[] args) {
        Farm farm = new Farm();

        for (int i = 0; i < 20; i++) {
            farm.grow();
        }

        System.out.println(farm.count());

        List<Cow> childs = farm.ancestor.getChilds();
        System.out.println(childs.size());
    }
}

/**
 * ��ţ
 */
class Cow {

    private int age;// ����
    private Cow parent;// ���ڵ�
    private Cow child;// �ӽڵ�
    private Cow sibling;// �ֵܽڵ�

    public Cow() {
        age = 0;
    }

    public Cow grow() {
        Cow cow = null;

        if (++age >= 4) {
            cow = new Cow();
            cow.parent = this;
            if (child == null) {// ��ǰCow�Ѿ������ӽڵ�
                child = cow;
            } else {
                Cow c = child;
                while (c.sibling != null) {
                    c = c.sibling;
                }
                c.sibling = cow;
            }
        }

        return cow;
    }

    /**
     * ��ȡCow�ĺ���
     * 
     * @return
     */
    public List<Cow> getChilds() {
        List<Cow> result = new ArrayList<Cow>();
        Cow c = child;

        while (c != null) {
            result.add(c);
            c = c.sibling;
        }

        return result;
    }

    /**
     * ��ȡCow���ֵ�
     * 
     * @return
     */
    public List<Cow> getSiblings() {
        List<Cow> result = null;
        if (parent != null) {
            result = parent.getChilds();
            result.remove(this);
        } else {
            result = new ArrayList<Cow>();
        }
        return result;
    }
}
/* ũ��һСĸţ��ĸţ�������Сĸţ����20���ũ���ж���ͷĸţ�� */