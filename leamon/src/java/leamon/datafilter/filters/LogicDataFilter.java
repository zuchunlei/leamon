package leamon.datafilter.filters;

import java.util.Map;

import leamon.datafilter.DataFilter;
import leamon.datafilter.Operator;

/**
 * �߼����ݹ�����
 */
public class LogicDataFilter implements DataFilter {

    private Operator operator;// �߼�������

    private DataFilter left;
    private DataFilter right;

    public LogicDataFilter(Operator op) {
        this.operator = op;
    }

    public void setLeft(DataFilter left) {
        this.left = left;
    }

    public void setRight(DataFilter right) {
        this.right = right;
    }

    @Override
    public boolean doFilter(Map<String, Object> dataMap) {
        boolean leftValue = left.doFilter(dataMap);
        boolean rightValue = right.doFilter(dataMap);

        return operator.doFilter(leftValue, rightValue);
    }

}
