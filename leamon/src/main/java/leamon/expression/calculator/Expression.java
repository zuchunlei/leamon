package leamon.expression.calculator;

import java.util.Stack;

import leamon.expression.CalculateUnit;
import leamon.expression.Operator;
import leamon.expression.OperatorSetting;

/**
 * ���ʽ
 */
public class Expression {

    /**
     * @param array
     *            ��׺���ʽ
     * @return
     */
    public static CalculateUnit getExpression(char[] array) {
        Stack<TreeNode> stack = new Stack<TreeNode>();

        for (char c : array) {
            TreeNode node = new TreeNode(c);

            if (OperatorSetting.isOp(c)) {
                node.right = stack.pop();
                node.left = stack.pop();
            }

            stack.push(node);
        }

        return stack.pop();
    }

    /**
     * ���ʽ���ڵ�
     */
    private static class TreeNode implements CalculateUnit {
        char data;// ������

        TreeNode left;
        TreeNode right;

        private TreeNode(char value) {
            this.data = value;
            this.left = this.right = null;
        }

        private TreeNode(char value, TreeNode left, TreeNode right) {
            this.data = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public double calculate() {
            if (left == null || right == null) {
                return Double.valueOf(String.valueOf(data));
            }

            double leftValue = left.calculate();
            double rightValue = right.calculate();

            Operator op = OperatorSetting.getOperator(data);

            return op.calculate(leftValue, rightValue);
        }
    }

}
