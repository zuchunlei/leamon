package leamon.expression.operator;

import leamon.expression.Operator;

/**
 * 操作符
 */
public class CommonOperator implements Operator {

    private char op;// 操作符号
    private int priority;// 优先级

    public CommonOperator(char op, int priority) {
        this.op = op;
        this.priority = priority;
    }

    @Override
    public double calculate(double leftValue, double rightValue) {
        return 0;
    }

    @Override
    public boolean underPriority(Operator op) {
        return this.priority <= op.getPriority();
    }

    @Override
    public boolean isMatch() {
        return false;
    }

    @Override
    public boolean match(char c) {
        return false;
    }

    @Override
    public char getDescription() {
        return this.op;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

}
