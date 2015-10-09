package leamon.datafilter.operators;

import leamon.datafilter.Operator;

/**
 * 匹配操作符，主要是类")"
 */
public class MatchOperator implements Operator {

    private String desc;// 操作符描述
    private String matcher;// 被匹配操作符
    private int priority;// 优先级

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
