package leamon.expression;

/**
 * ��������
 */
public interface Operator {

	/**
	 * �������ļ��㷽ʽ
	 * 
	 * @param leftValue
	 *            ��ֵ
	 * @param rightValue
	 *            ��ֵ
	 * @return ����ֵ
	 */
	double calculate(double leftValue, double rightValue);

	/**
	 * ��ǰ��������ȼ��Ƿ���ڸ����������
	 * 
	 * @param op
	 * @return
	 */
	boolean underPriority(Operator op);

	/**
	 * ��õ�ǰ����������ȼ�
	 * 
	 * @return
	 */
	int getPriority();

	/**
	 * ��ǰ������Ƿ�Ϊ������������Ҫֻ")"��"}"��
	 * 
	 * @return
	 */
	boolean isMatch();

	/**
	 * �����ǰ���������Ƿ�������������ƥ��
	 * 
	 * @param c
	 * @return
	 */
	boolean match(char c);

	/**
	 * ����������������Ϣ����Ҫ���ط�������������"+"��"-"��
	 * 
	 * @return
	 */
	char getDescription();
}