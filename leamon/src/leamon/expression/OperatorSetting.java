package leamon.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作符配置信息管理
 */
public class OperatorSetting {

    private static Map<Character, Operator> operators = new HashMap<Character, Operator>();

    /**
     * 注册操作符
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
     * 根据描述信息获得操作符对象
     * 
     * @param c
     * @return
     */
    public static Operator getOperator(Character c) {
        return operators.get(c);
    }

    /**
     * 判断给定的key是否为已注册的操作符
     * 
     * @param c
     * @return
     */
    public static boolean isOp(Character c) {
        return operators.containsKey(c);
    }
}