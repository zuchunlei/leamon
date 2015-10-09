package leamon.datastruct.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 表达式树
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
     * 注册操作符处理器，供外部调用
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
     * 表达式树节点
     */
    static class ExpressionTreeNode {
        private String op;// 操作数/操作符
        private ExpressionTreeNode left;// 左子树
        private ExpressionTreeNode right;// 右子树

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
     * 表达式树的创建者
     */
    static class ExpressionTreeBuilder {

        /**
         * @param ops
         *            表达式的后缀形式
         * @return
         */
        static ExpressionTreeNode buildExpression(String[] ops) {
            Stack<ExpressionTreeNode> stack = new Stack<ExpressionTreeNode>();

            for (String op : ops) {
                ExpressionTreeNode node = new ExpressionTreeNode(op);

                // 如果当前节点为操作符，则pop栈中节点作为当前节点的右/左子树。
                if (isOperator(op)) {
                    node.right = stack.pop();// 栈顶为节点的右子树
                    node.left = stack.pop();
                }

                stack.push(node);
            }

            return stack.peek();
        }
    }

    /**
     * 操作符处理器接口
     */
    interface Operator {
        double operate(double v1, double v2);
    }

    public static void main(String[] args) {
        // 1+2*3+(4+5)/6
        String[] array = { "1", "2", "3", "*", "+", "4", "5", "+", "6", "/", "+" };// 后缀表达式
        ExpressionTreeNode root = ExpressionTreeBuilder.buildExpression(array);
        System.out.println(root.calc());// 8.5
    }
}
