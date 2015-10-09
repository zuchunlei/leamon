package leamon.practice;

import java.util.ArrayList;
import java.util.List;

/**
 * 农场
 */
public class Farm {

    private List<Cow> cows = new ArrayList<Cow>();// 母牛集合
    private Cow ancestor;// 祖先

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
 * 奶牛
 */
class Cow {

    private int age;// 年龄
    private Cow parent;// 父节点
    private Cow child;// 子节点
    private Cow sibling;// 兄弟节点

    public Cow() {
        age = 0;
    }

    public Cow grow() {
        Cow cow = null;

        if (++age >= 4) {
            cow = new Cow();
            cow.parent = this;
            if (child == null) {// 当前Cow已经存在子节点
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
     * 获取Cow的孩子
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
     * 获取Cow的兄弟
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
/* 农场一小母牛，母牛四岁可生小母牛。问20年后，农场有多少头母牛？ */