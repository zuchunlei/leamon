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
 * ���ݹ�������������
 */
public class DataFilterFactory {

    // ������map�ṹ
    private static Map<String, Operator> operators = new HashMap<String, Operator>();

    /**
     * ע�������������������description�����Ĵ���������
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
     * ��ǰ�����ķ����Ƿ�Ϊע��Ĳ�����
     * 
     * @param str
     * @return
     */
    private static boolean isOperator(String str) {
        return operators.containsKey(str);
    }

    /**
     * ���ݸ����Ĺ�����ʽ���������ݹ���������
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
     * ��ȡ���������ݸ����ַ�������λ���pattern��λ��
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
     * �ָ�����������ı��ʽ���ݲ��������зָ�
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
     * �����ַ��������������ʽ�Լ������������ȼ����н������������׺�ַ���
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

                if (current.isMatch()) {// ƥ���������һ��Ϊ������
                    while (!stack.isEmpty() && !current.match(stack.peek().getDescription())) {
                        suffixList.add(stack.pop().getDescription());
                    }

                    stack.pop();// ����"("
                } else {// ��ͨ������
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
     * ���ݸ����ĺ�׺�ַ����������ݹ���������
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
     * �ַ��������ڵ㣬�ýڵ���Ҫ��¼�����������ڸ����ַ�����λ��
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
