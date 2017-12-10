package leamon.enhance;

import java.util.Date;
import java.util.UUID;

/**
 * ��Ա�ڲ���/�������ڲ���ı���������Ա�ڲ���һ�£�
 * <p>
 * �ڳ�Ա�ڲ����б��������Զ�����һ���ֶ�Ϊthis$0�����õ�ǰ���и��ڲ���������Χ�ڶ���ʵ���������ڸ��ڲ���ʵ��������Ϊfinal OuterClass
 * this$0��
 * <p>
 * �����Ա�ڲ����з�����Χ��ʵ�����������������Χ����Ϊ�ڲ��������Χ��ʵ�������һ����̬�������þ�̬����������Ϊstatic Object
 * access$0(OuterClass)�� ����Object�ǵ�ǰ��Χ��OuterClassʵ����value�����͡�
 * <p>
 * ��Ա�ڲ��������Χ��Ķ��ʵ���򣬱������ͻ�Ϊ��Χ��������������access$�����ľ�̬��������
 * <p>
 * ����̬�ڲ��������Χ��ľ�̬����߼��ͨ����Χ������������Χ���ʵ����ʱ��
 * ������������Χ����֯�뾲̬����������̬�ڲ���ʵ��ȥ���ã��Ӷ��ﵽ������Χ�ྲ̬����ʵ�����Ч����
 * <p>
 * �����ڵľֲ��ڲ��������Ҫcapture�����ڵ����ɱ���������������ھֲ��ڲ�����������Ӧ��ʵ������������Щ���ɱ�����ֵ��
 * ����ֲ��ڲ������Χ����Ϊ�Ǿ�̬�������������ͬ��������һ����Ϊthis$0�����õ�ǰ���и��ڲ���������Χ��ʵ����
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


    public void handle() {
        final String code = getCode();
        /**
         * �˴���RunnanleΪ�����ڵľֲ��ڲ��࣬����ڲ������Χ����Ϊ�Ǿ�̬���������������֯��һ����Ϊthis$0�����õ�ǰ���и��ڲ���������Χ��ʵ����
         * ����ֲ��ڲ���Ҫcapture(����)���������ɱ���(�ֲ������뷽������)������������ھֲ��ڲ�����������Ӧ��ʵ������������Щ���ɱ�����ֵ��
         * �ֲ��ڲ������Ҫcapture�����ڵ����ɱ�������Ҫ������ɱ���������final���Ρ�java���Թ淶Ҫ��
         */
        new Thread(new Runnable() {
            public void run() {
                System.out.println(code);
                System.out.println(value);
            }
        }).start();
    }

    public String getCode() {
        return UUID.randomUUID().toString();
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
