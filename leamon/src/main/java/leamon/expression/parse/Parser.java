package leamon.expression.parse;

import java.util.Stack;

import leamon.expression.Operator;
import leamon.expression.OperatorSetting;

/**
 * 表达式解析器
 */
public class Parser {

    /**
     * 将中缀表达式转换成为后缀形式
     * 
     * @param expression
     * @return
     */
    public static char[] parse(String expression) {
        char[] array = expression.toCharArray();// 表达式的中缀字符数组
        StringBuffer result = new StringBuffer();

        Stack<Operator> stack = new Stack<Operator>();

        for (char c : array) {
            if (OperatorSetting.isOp(c)) {
                Operator current = OperatorSetting.getOperator(c);

                if (current.isMatch()) {// 匹配操作符，一般为右括号

                    while (!stack.isEmpty() && !current.match(stack.peek().getDescription())) {
                        result.append(stack.pop().getDescription());
                    }

                    stack.pop();// 弹出"("

                } else {// 普通操作符

                    while (!stack.isEmpty() && current.underPriority(stack.peek())) {
                        result.append(stack.pop().getDescription());
                    }

                    stack.push(current);
                }
            } else {
                result.append(c);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop().getDescription());
        }

        return result.toString().toCharArray();
    }

}
