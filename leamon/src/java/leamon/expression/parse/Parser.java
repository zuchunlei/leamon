package leamon.expression.parse;

import java.util.Stack;

import leamon.expression.Operator;
import leamon.expression.OperatorSetting;

/**
 * ���ʽ������
 */
public class Parser {

    /**
     * ����׺���ʽת����Ϊ��׺��ʽ
     * 
     * @param expression
     * @return
     */
    public static char[] parse(String expression) {
        char[] array = expression.toCharArray();// ���ʽ����׺�ַ�����
        StringBuffer result = new StringBuffer();

        Stack<Operator> stack = new Stack<Operator>();

        for (char c : array) {
            if (OperatorSetting.isOp(c)) {
                Operator current = OperatorSetting.getOperator(c);

                if (current.isMatch()) {// ƥ���������һ��Ϊ������

                    while (!stack.isEmpty() && !current.match(stack.peek().getDescription())) {
                        result.append(stack.pop().getDescription());
                    }

                    stack.pop();// ����"("

                } else {// ��ͨ������

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
