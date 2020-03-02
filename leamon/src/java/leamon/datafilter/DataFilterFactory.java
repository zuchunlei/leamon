package leamon.datafilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import leamon.datafilter.filters.BasicDataFilter;
import leamon.datafilter.filters.LogicDataFilter;

/**
 * 数据过滤器构建工厂
 */
public class DataFilterFactory {

    // 操作符map结构
    private static Map<String, Operator> operators = new HashMap<String, Operator>();

    /**
     * 注册操作符处理器，并将description关联的处理器返回
     * 
     * @param description
     * @param operator
     * @return
     */
    public static Operator registerDataFilter(String description, Operator operator) {
        Operator result = operators.get(description);
        operators.put(description, operator);
        return result;
    }

    /**
     * 当前给定的符号是否为注册的操作符
     * 
     * @param str
     * @return
     */
    private static boolean isOperator(String str) {
        return operators.containsKey(str);
    }

    /**
     * 根据给定的规则表达式，构建数据过滤器对象
     * 
     * @param express
     * @return
     */
    public static DataFilter buildDataFilter(String express) {
        List<ParseNode> list = new ArrayList<ParseNode>();

        for (String operator : operators.keySet()) {
            extract(express, operator, 0, list);
        }

        List<String> infixList = split(express, list);
        List<String> suffixList = parse(infixList);
        return build(suffixList);
    }

    /**
     * 抽取方法，根据给定字符串，定位相关pattern的位置
     * 
     * @param express
     * @param pattern
     * @param start
     * @param list
     */
    private static void extract(String express, String pattern, int start, List<ParseNode> list) {

        int length = pattern.length();
        int index = express.indexOf(pattern, start);

        while (index != -1) {
            ParseNode node = new ParseNode(index, length);
            list.add(node);

            start = index + length;
            index = express.indexOf(pattern, start);
        }
    }

    /**
     * 分割方法，将给定的表达式根据操作符进行分割
     * 
     * @param express
     * @param list
     * @return
     */
    private static List<String> split(String express, List<ParseNode> list) {
        Collections.sort(list);

        List<String> result = new ArrayList<String>();

        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
        set.add(0);
        for (int i = 0, size = list.size(); i < size; i++) {
            ParseNode node = list.get(i);
            int index = node.index;
            int end = index + node.length;
            set.add(index);
            set.add(end);
        }
        set.add(express.length());

        Object[] array = set.toArray();

        for (int i = 0, length = array.length; i < length - 1; i++) {
            int begin = (Integer) array[i];
            int end = (Integer) array[i + 1];
            result.add(express.substring(begin, end));
        }

        return result;
    }

    /**
     * 根据字符串的中序遍历形式以及操作符的优先级进行解析，返回其后缀字符串
     * 
     * @param infixList
     * @return
     */
    private static List<String> parse(List<String> infixList) {
        List<String> suffixList = new ArrayList<String>();
        Stack<Operator> stack = new Stack<Operator>();

        for (String str : infixList) {
            if (isOperator(str)) {
                Operator current = operators.get(str);

                if (current.isMatch()) {// 匹配操作符，一般为右括号
                    while (!stack.isEmpty() && !current.match(stack.peek().getDescription())) {
                        suffixList.add(stack.pop().getDescription());
                    }

                    stack.pop();// 弹出"("
                } else {// 普通操作符
                    while (!stack.isEmpty() && current.underPriority(stack.peek())) {
                        suffixList.add(stack.pop().getDescription());
                    }

                    stack.push(current);
                }
            } else {
                suffixList.add(str);
            }
        }

        while (!stack.isEmpty()) {
            suffixList.add(stack.pop().getDescription());
        }

        return suffixList;
    }

    /**
     * 根据给定的后缀字符串构建数据过滤器对象
     * 
     * @param suffixList
     * @return
     */
    private static DataFilter build(List<String> suffixList) {
        Stack<DataFilter> stack = new Stack<DataFilter>();

        for (String str : suffixList) {
            if (isOperator(str)) {
                Operator op = operators.get(str);
                LogicDataFilter node = new LogicDataFilter(op);
                node.setRight(stack.pop());
                node.setLeft(stack.pop());
                stack.push(node);
            } else {
                BasicDataFilter node = new BasicDataFilter(str);
                stack.push(node);
            }
        }

        return stack.pop();
    }

    /**
     * 字符串解析节点，该节点主要记录操作符所处于给定字符串的位置
     */
    private static class ParseNode implements Comparable<ParseNode> {
        private int index;
        private int length;

        public ParseNode(int index, int length) {
            this.index = index;
            this.length = length;
        }

        @Override
        public int compareTo(ParseNode o) {
            return index - o.index;
        }
    }

}
