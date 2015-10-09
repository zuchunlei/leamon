package leamon.datafilter.operators;

import leamon.datafilter.Operator;

/**
 * ƥ�����������Ҫ����")"
 */
public class MatchOperator implements Operator {

    private String desc;// ����������
    private String matcher;// ��ƥ�������
    private int priority;// ���ȼ�

    public MatchOperator(String desc, String matcher, int priority) {
        this.desc = desc;
        this.matcher = matcher;
        this.priority = priority;
    }

    @Override
    public boolean underPriority(Operator op) {
        return priority <= op.getPriority();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public final boolean isMatch() {
        return true;
    }

    @Override
    public final boolean match(String str) {
        return matcher.equals(str);
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public boolean doFilter(boolean left, boolean right) {// not support
        return false;
    }

}
