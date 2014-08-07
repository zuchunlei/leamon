package leamon.enhance;

import java.util.Date;

/**
 * 成员内部类/（匿名内部类的编译机制与成员内部类一致）
 * 
 * 在成员内部类中编译器会自动编译一个字段为this$0来引用当前持有该内部类对象的外围内对象实例。并且在该内部类实例域声明为final OuterClass
 * this$0。
 * 
 * 如果成员内部类有访问外围类实例域，则编译器会在外围类中为内部类访问外围类实例域添加一个静态方法。该静态方法的声明为static Object
 * access$0(OuterClass)， 其中Object是当前外围类OuterClass实例域value的类型。
 * 
 * 成员内部类访问外围类的多个实例域，编译器就会为外围类声明几个类似access$这样的静态方法！！
 */
public class OuterClass {

	private Object value = new Object();

	private Date time = new Date();

	// static Object access$0(OuterClass p) {return p.value} ; 编译器织入

	// static Date access$1(OuterClass p) {return p.time} ; 编译器织入

	public InnerClass getInnerClassInstance() {
		return new InnerClass();
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

}
