package leamon.expression.operator;

import leamon.expression.Operator;

/**
 * 匹配操作符，主要是类")"
 */
public class MatchOperator implements Operator {

    private char op;// 操作符号
    private int priority;// 优先级
    private char matcher;// 被匹配操作符

    public MatchOperator(char op, int priority, char matcher) {
        this.op = op;
        this.priority = priority;
        this.matcher = matcher;
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
    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean isMatch() {
        return true;
    }

    @Override
    public boolean match(char c) {
        return c == matcher;
    }

    @Override
    public char getDescription() {
        return this.op;
    }

}
