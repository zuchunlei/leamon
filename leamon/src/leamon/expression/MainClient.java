package leamon.expression;

import leamon.expression.calculator.Expression;
import leamon.expression.operator.CommonOperator;
import leamon.expression.operator.MatchOperator;
import leamon.expression.parse.Parser;

/**
 * 表达式运算的测试主类
 */
public class MainClient {

	// 对操作符对象的注册一般都处于初始化代码块中，例如Servlet的init方法。
	static {
		OperatorSetting.setOperator('+', new CommonOperator('+', 1) {
			@Override
			public double calculate(double leftValue, double rightValue) {
				return leftValue + rightValue;
			}
		});
		
		OperatorSetting.setOperator('-', new CommonOperator('-', 1) {
			@Override
			public double calculate(double leftValue, double rightValue) {
				return leftValue - rightValue;
			}
		});
		
		OperatorSetting.setOperator('*', new CommonOperator('*', 2) {
			@Override
			public double calculate(double leftValue, double rightValue) {
				return leftValue * rightValue;
			}
		});
		
		OperatorSetting.setOperator('/', new CommonOperator('/', 2) {
			@Override
			public double calculate(double leftValue, double rightValue) {
				return leftValue / rightValue;
			}
		});
		
		OperatorSetting.setOperator('(', new CommonOperator('(', -1) {
			@Override
			public boolean underPriority(Operator op) {
				return false;
			}
		});
		
		OperatorSetting.setOperator('^', new CommonOperator('^', 3) {
			@Override
			public double calculate(double leftValue, double rightValue) {
				return Math.pow(leftValue, rightValue);
			}
		});

		OperatorSetting.setOperator(')', new MatchOperator(')', 4, '('));
	}

	public static void main(String[] args) {

		String express = "(5+7)*2+6/2-(6+3)/2+4^2";

		CalculateUnit unit = Expression.getExpression(Parser.parse(express));

		System.out.println(unit.calculate());
	}
}
