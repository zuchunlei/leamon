package leamon.expression.operator;

import leamon.expression.Operator;

/**
 * ƥ�����������Ҫ����")"
 */
public class MatchOperator implements Operator {

    private char op;// ��������
    private int priority;// ���ȼ�
    private char matcher;// ��ƥ�������

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
