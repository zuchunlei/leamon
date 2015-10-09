package leamon.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * ������������Ϣ����
 */
public class OperatorSetting {

    private static Map<Character, Operator> operators = new HashMap<Character, Operator>();

    /**
     * ע�������
     * 
     * @param c
     * @param o
     * @return
     */
    public static Operator setOperator(Character c, Operator o) {
        Operator op = operators.get(c);
        operators.put(c, o);
        return op;
    }

    /**
     * ����������Ϣ��ò���������
     * 
     * @param c
     * @return
     */
    public static Operator getOperator(Character c) {
        return operators.get(c);
    }

    /**
     * �жϸ�����key�Ƿ�Ϊ��ע��Ĳ�����
     * 
     * @param c
     * @return
     */
    public static boolean isOp(Character c) {
        return operators.containsKey(c);
    }
}