package leamon.expression;

/**
 * 操作符号
 */
public interface Operator {

	/**
	 * 操作符的计算方式
	 * 
	 * @param leftValue
	 *            左值
	 * @param rightValue
	 *            右值
	 * @return 返回值
	 */
	double calculate(double leftValue, double rightValue);

	/**
	 * 当前运算符优先级是否低于给定的运算符
	 * 
	 * @param op
	 * @return
	 */
	boolean underPriority(Operator op);

	/**
	 * 获得当前运算符的优先级
	 * 
	 * @return
	 */
	int getPriority();

	/**
	 * 当前运算符是否为封闭运算符，主要只")"、"}"等
	 * 
	 * @return
	 */
	boolean isMatch();

	/**
	 * 如果当前封闭运算符是否与给定的运算符匹配
	 * 
	 * @param c
	 * @return
	 */
	boolean match(char c);

	/**
	 * 获得运算符的描述信息，主要返回符号描述，例如"+"、"-"等
	 * 
	 * @return
	 */
	char getDescription();
}