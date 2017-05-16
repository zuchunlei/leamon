package leamon.enhance;

import java.util.Date;
import java.util.UUID;

/**
 * 成员内部类/（匿名内部类的编译机制与成员内部类一致）
 * <p>
 * 在成员内部类中编译器会自动编译一个字段为this$0来引用当前持有该内部类对象的外围内对象实例。并且在该内部类实例域声明为final OuterClass
 * this$0。
 * <p>
 * 如果成员内部类有访问外围类实例域，则编译器会在外围类中为内部类访问外围类实例域添加一个静态方法。该静态方法的声明为static Object
 * access$0(OuterClass)， 其中Object是当前外围类OuterClass实例域value的类型。
 * <p>
 * 成员内部类访问外围类的多个实例域，编译器就会为外围类声明几个类似access$这样的静态方法！！
 * <p>
 * 当静态内部类访问外围类的静态域或者间接通过外围对象来访问外围类的实例域时，
 * 编译器会在外围类中织入静态方法来供静态内部类实例去调用，从而达到访问外围类静态域与实例域的效果！
 * <p>
 * 方法内的局部内部类如果需要capture方法内的自由变量，则编译器会在局部内部类中生成相应的实例域来保持这些自由变量的值。
 * 如果局部内部类的外围方法为非静态方法，则编译器同样会生成一个域为this$0来引用当前持有该内部类对象的外围类实例。
 */
public class OuterClass {

    private static int count = 10;

    private static Object lock = new Object();

    private Object value = new Object();

    private Date time = new Date();

    // static Object access$0(OuterClass p) {return p.value ;} 编译器织入

    // static Date access$1(OuterClass p) {return p.time ;} 编译器织入

    // static int access$2() {return count ;} 编译器织入

    // static Object access$3() {return lock ;} 编译器织入

    public InnerClass getInnerClassInstance() {
        return new InnerClass();
    }


    public void handle() {
        final String code = getCode();
        /**
         * 此处的Runnanle为方法内的局部内部类，如果内部类的外围方法为非静态方法，则编译器会织入一个域为this$0来引用当前持有该内部类对象的外围类实例。
         * 如果局部内部类要capture(捕获)方法内自由变量(局部变量与方法参数)，则编译器会在局部内部类中生成相应的实例域来保持这些自由变量的值。
         * 局部内部类如果要capture方法内的自由变量，则要求该自由变量必须由final修饰。java语言规范要求！
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
     * 在成员内部类中编译器会自动编译织入一个域为this$0来引用当前持有该内部类对象的外围类实例。 并且该内部类实例域this$0声明为final
     * OuterClass this$0 !
     */
    class InnerClass {

        // final OuterClass this$0 = OuterClassInstance; 编译器织入

        /**
         * 当前方法访问外围类的实例域value
         */
        public Object getValue() {
            return value;
        }

        /**
         * 当前方法访问外围类的实例域time
         */
        public Date getTime() {
            return time;
        }
    }

    /**
     * 静态内部类的对象不持有对外围类的引用，静态内部类不能直接访问外围的实例域，但是直接可以访问外围类的静态域。
     */
    static class StaticInnerClass {

        /**
         * 当前方法访问外围类的静态域count
         */
        public int getCount() {
            return count;
        }

        /**
         * 当前方法访问外围类的静态域lock
         */
        public Object getLock() {
            return lock;
        }

        /**
         * 当前方法访问外围类的实例域value
         */
        public Object getValue() {
            return getOutClassInstance().value;
        }

        /**
         * 当前方法访问外围类的实例域time
         */
        public Date getTime() {
            return getOutClassInstance().time;
        }

        public OuterClass getOutClassInstance() {
            return new OuterClass();
        }

    }

}
