package leamon.datastruct.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * ���ʽ��
 */
public class ExpressionTree {

    private static Map<String, Operator> operators = new HashMap<String, Operator>();

    static {
        operators.put("+", new Operator() {
            @Override
            public double operate(double d1, double d2) {
                return d1 + d2;
            }
        });

        operators.put("-", new Operator() {
            @Override
            public double operate(double d1, double d2) {
                return d1 - d2;
            }
        });

        operators.put("*", new Operator() {
            @Override
            public double operate(double d1, double d2) {
                return d1 * d2;
            }
        });

        operators.put("/", new Operator() {
            @Override
            public double operate(double d1, double d2) {
                return d1 / d2;
            }
        });
    }

    /**
     * ע������������������ⲿ����
     * 
     * @param op
     * @param operator
     */
    public static void registerOperator(String op, Operator operator) {
        operators.put(op, operator);
    }

    private static boolean isOperator(String op) {
        return operators.containsKey(op);
    }

    /**
     * ���ʽ���ڵ�
     */
    static class ExpressionTreeNode {
        private String op;// ������/������
        private ExpressionTreeNode left;// ������
        private ExpressionTreeNode right;// ������

        private ExpressionTreeNode(String data) {
            this.op = data;
            this.left = this.right = null;
        }

        private ExpressionTreeNode(String data, ExpressionTreeNode left, ExpressionTreeNode right) {
            this.op = data;
            this.left = left;
            this.right = right;
        }

        public double calc() {
            if (left == null || right == null) {
                return Double.valueOf(op);
            }
            Operator operator = operators.get(op);
            return operator.operate(left.calc(), right.calc());
        }
    }

    /**
     * ���ʽ���Ĵ�����
     */
    static class ExpressionTreeBuilder {

        /**
         * @param ops
         *            ���ʽ�ĺ�׺��ʽ
         * @return
         */
        static ExpressionTreeNode buildExpression(String[] ops) {
            Stack<ExpressionTreeNode> stack = new Stack<ExpressionTreeNode>();

            for (String op : ops) {
                ExpressionTreeNode node = new ExpressionTreeNode(op);

                // �����ǰ�ڵ�Ϊ����������popջ�нڵ���Ϊ��ǰ�ڵ����/��������
                if (isOperator(op)) {
                    node.right = stack.pop();// ջ��Ϊ�ڵ��������
                    node.left = stack.pop();
                }

                stack.push(node);
            }

            return stack.peek();
        }
    }

    /**
     * �������������ӿ�
     */
    interface Operator {
        double operate(double v1, double v2);
    }

    public static void main(String[] args) {
        // 1+2*3+(4+5)/6
        String[] array = { "1", "2", "3", "*", "+", "4", "5", "+", "6", "/", "+" };// ��׺���ʽ
        ExpressionTreeNode root = ExpressionTreeBuilder.buildExpression(array);
        System.out.println(root.calc());// 8.5
    }
}
