package leamon.enhance;

/**
 * ��Ա�ڲ���/�������ڲ���ı���������Ա�ڲ���һ�£�
 * 
 * �ڳ�Ա�ڲ����б��������Զ�����һ���ֶ�Ϊthis$0�����õ�ǰ���и��ڲ���������Χ�ڶ���ʵ���������ڸ��ڲ���ʵ��������Ϊfinal OuterClass
 * this$0��
 * 
 * �����Ա�ڲ����з�����Χ��ʵ�����������������Χ����Ϊ�ڲ��������Χ��ʵ�������һ����̬�������þ�̬����������Ϊstatic Object
 * access$0(OuterClass)�� ����Object�ǵ�ǰ��Χ��OuterClassʵ����value�����͡�
 */
public class OuterClass {

	private Object value = new Object();

	// static Object access$0(OuterClass p) {return p.value} ; ������֯��

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
	}

}
