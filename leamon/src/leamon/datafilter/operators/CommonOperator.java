package leamon.datafilter.operators;

import leamon.datafilter.Operator;

public abstract class CommonOperator implements Operator {

    private String desc;// 操作符描述
    private int priority;// 优先级

    public CommonOperator(String desc, int priority) {
        this.desc = desc;
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
    public final boolean isMatch() {// not support
        return false;
    }

    @Override
    public final boolean match(String str) {// not support
        return false;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public abstract boolean doFilter(boolean left, boolean right);

}
