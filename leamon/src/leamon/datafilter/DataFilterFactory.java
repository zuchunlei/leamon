package leamon.datafilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
	public static Operator registerDataFilter(String description,
			Operator operator) {
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
		ParseNodeHeap heap = new ParseNodeHeap();

		for (String operator : operators.keySet()) {
			extract(express, operator, 0, heap);
		}

		List<String> infixList = split(express, heap);
		List<String> suffixList = parse(infixList);
		return null;
	}

	/**
	 * 抽取方法，根据给定字符串，定位相关pattern的位置
	 * 
	 * @param express
	 * @param pattern
	 * @param start
	 * @param heap
	 */
	private static void extract(String express, String pattern, int start,
			ParseNodeHeap heap) {

		int length = pattern.length();
		int index = express.indexOf(pattern, start);

		while (index != -1) {
			ParseNode node = new ParseNode(index, length);
			heap.add(node);

			start = index + length;
			index = express.indexOf(pattern, start);
		}
	}

	/**
	 * 分割方法，将给定的表达式根据操作符进行分割
	 * 
	 * @param express
	 * @param heap
	 * @return
	 */
	private static List<String> split(String express, ParseNodeHeap heap) {
		List<String> result = new ArrayList<String>();

		LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();
		set.add(0);
		for (int i = 0, size = heap.size(); i < size; i++) {
			ParseNode node = heap.deleteMin();
			int index = node.index;
			int end = index + node.length;
			set.add(index);
			set.add(end);
		}
		set.add(express.length());

		Object[] array = set.toArray();

		int i = 0;
		while (i < array.length - 1) {
			int begin = (Integer) array[i];
			int end = (Integer) array[i + 1];
			result.add(express.substring(begin, end));
			i++;
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
					while (!stack.isEmpty()
							&& !current.match(stack.peek().getDescription())) {
						suffixList.add(stack.pop().getDescription());
					}

					stack.pop();// 弹出"("
				} else {// 普通操作符
					while (!stack.isEmpty()
							&& current.underPriority(stack.peek())) {
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

	/**
	 * ParseNode的最小堆实现
	 */
	private static class ParseNodeHeap {
		private static final int DEFAULT_CAPACITY = 10;

		private ParseNode[] array;
		private int size;

		public ParseNodeHeap() {
			this(DEFAULT_CAPACITY);
		}

		public ParseNodeHeap(int capacity) {
			this.size = 0;
			this.array = new ParseNode[capacity];
		}

		private void enlarge(int capacity) {
			ParseNode[] tmp = new ParseNode[capacity];
			System.arraycopy(array, 0, tmp, 0, size - 1);
			array = tmp;
		}

		public int size() {
			return size;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public ParseNode findMin() {
			if (isEmpty()) {
				String message = "current heap is empty;";
				throw new RuntimeException(message);
			}

			return array[0];
		}

		public void add(ParseNode node) {
			if (size == array.length) {
				enlarge(2 * size);
			}
			array[size] = node;
			percolateUp(size++);
		}

		private void percolateUp(int hole) {
			ParseNode node = array[hole];
			for (; hole > 0 && array[(hole - 1) / 2].compareTo(node) > 0; hole = (hole - 1) / 2) {
				array[hole] = array[(hole - 1) / 2];
			}
			array[hole] = node;
		}

		public ParseNode deleteMin() {
			ParseNode node = findMin();

			array[0] = array[--size];
			percolateDown(0);
			return node;
		}

		private void percolateDown(int hole) {
			ParseNode node = array[hole];
			int child = -1;
			for (; leftChild(hole) < size; hole = child) {
				child = leftChild(hole);

				if (child != size - 1
						&& array[child + 1].compareTo(array[child]) < 0) {
					child++;
				}

				if (array[child].compareTo(node) < 0) {
					array[hole] = array[child];
				} else {
					break;
				}

			}
			array[hole] = node;

		}

		private int leftChild(int hole) {
			return 2 * hole + 1;
		}
	}
}
