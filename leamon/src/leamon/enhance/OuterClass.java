package leamon.enhance;

import java.util.Date;

/**
 * ��Ա�ڲ���/�������ڲ���ı���������Ա�ڲ���һ�£�
 * 
 * �ڳ�Ա�ڲ����б��������Զ�����һ���ֶ�Ϊthis$0�����õ�ǰ���и��ڲ���������Χ�ڶ���ʵ���������ڸ��ڲ���ʵ��������Ϊfinal OuterClass
 * this$0��
 * 
 * �����Ա�ڲ����з�����Χ��ʵ�����������������Χ����Ϊ�ڲ��������Χ��ʵ�������һ����̬�������þ�̬����������Ϊstatic Object
 * access$0(OuterClass)�� ����Object�ǵ�ǰ��Χ��OuterClassʵ����value�����͡�
 * 
 * ��Ա�ڲ��������Χ��Ķ��ʵ���򣬱������ͻ�Ϊ��Χ��������������access$�����ľ�̬��������
 * 
 * ����̬�ڲ��������Χ��ľ�̬����߼��ͨ����Χ������������Χ���ʵ����ʱ��
 * ������������Χ����֯�뾲̬����������̬�ڲ���ʵ��ȥ���ã��Ӷ��ﵽ������Χ�ྲ̬����ʵ�����Ч����
 * 
 */
public class OuterClass {

    private static int count = 10;

    private static Object lock = new Object();

    private Object value = new Object();

    private Date time = new Date();

    // static Object access$0(OuterClass p) {return p.value ;} ������֯��

    // static Date access$1(OuterClass p) {return p.time ;} ������֯��

    // static int access$2() {return count ;} ������֯��

    // static Object access$3() {return lock ;} ������֯��

    public InnerClass getInnerClassInstance() {
        return new InnerClass();
    }

    /**
     * �ڳ�Ա�ڲ����б��������Զ�����֯��һ����Ϊthis$0�����õ�ǰ���и��ڲ���������Χ��ʵ���� ���Ҹ��ڲ���ʵ����this$0����Ϊfinal
     * OuterClass this$0 !
     */
    class InnerClass {

        // final OuterClass this$0 = OuterClassInstance; ������֯��

        /**
         * ��ǰ����������Χ���ʵ����value
         */
        public Object getValue() {
            return value;
        }

        /**
         * ��ǰ����������Χ���ʵ����time
         */
        public Date getTime() {
            return time;
        }
    }

    /**
     * ��̬�ڲ���Ķ��󲻳��ж���Χ������ã���̬�ڲ��಻��ֱ�ӷ�����Χ��ʵ���򣬵���ֱ�ӿ��Է�����Χ��ľ�̬��
     */
    static class StaticInnerClass {

        /**
         * ��ǰ����������Χ��ľ�̬��count
         */
        public int getCount() {
            return count;
        }

        /**
         * ��ǰ����������Χ��ľ�̬��lock
         */
        public Object getLock() {
            return lock;
        }

        /**
         * ��ǰ����������Χ���ʵ����value
         */
        public Object getValue() {
            return getOutClassInstance().value;
        }

        /**
         * ��ǰ����������Χ���ʵ����time
         */
        public Date getTime() {
            return getOutClassInstance().time;
        }

        public OuterClass getOutClassInstance() {
            return new OuterClass();
        }

    }

}
