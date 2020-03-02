package leamon.expression.calculator;

import java.util.Stack;

import leamon.expression.CalculateUnit;
import leamon.expression.Operator;
import leamon.expression.OperatorSetting;

/**
 * 表达式
 */
public class Expression {

    /**
     * @param array
     *            后缀表达式
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
     * 表达式树节点
     */
    private static class TreeNode implements CalculateUnit {
        char data;// 操作数

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
