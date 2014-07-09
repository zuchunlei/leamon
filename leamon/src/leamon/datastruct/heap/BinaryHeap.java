package leamon.datastruct.heap;

/**
 * 二叉堆，内部使用数组来进行存储
 */
public class BinaryHeap {

	private static final int DEFAULT_CAPACITY = 10;// 默认存储空贱大小

	private int[] array;// 采用数组进行存储

	private int size;

	private Compare comparator;// 比较器

	public BinaryHeap() {
		this(DEFAULT_CAPACITY, true);
	}

	public BinaryHeap(int[] array, boolean flag) {
		this.size = array.length;
		this.array = new int[array.length];
		System.arraycopy(array, 0, this.array, 0, array.length);

		if (flag) {
			this.comparator = MinimumComparator.instance();
		} else {
			this.comparator = MaximumComparator.instance();
		}

		percolateDown((size - 1) / 2);
	}

	public BinaryHeap(int capacity) {
		this(capacity, true);
	}

	public BinaryHeap(boolean flag) {
		this(DEFAULT_CAPACITY, flag);
	}

	public BinaryHeap(int capacity, boolean flag) {
		this.size = 0;
		this.array = new int[capacity];
		if (flag) {
			this.comparator = MinimumComparator.instance();
		} else {
			this.comparator = MaximumComparator.instance();
		}
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public void makeEmpty() {
		this.size = 0;
	}

	public int size() {
		return this.size;
	}

	public void insert(int value) {
		if (size == array.length) {
			enlargeArray(2 * array.length);
		}

		array[size++] = value;

		// 上滤
		percolateUp(size - 1);
	}

	private void percolateUp(int hole) {
		int value = array[hole];

		while (hole > 0 && comparator.compare(value, array[(hole - 1) / 2])) {
			int parent = (hole - 1) / 2;
			array[hole] = array[parent];
			hole = parent;
		}

		array[hole] = value;
	}

	public int findMin() {
		if (isEmpty()) {
			String message = "current heap is empty!";
			throw new RuntimeException(message);
		}
		return array[0];
	}

	public int deleteMin() {
		int min = findMin();
		array[0] = array[--size];
		// 下滤
		percolateDown(0);
		return min;
	}

	private void percolateDown(int hole) {
		int child = 0;
		int value = array[hole];

		for (; 2 * hole + 1 <= size; hole = child) {
			child = 2 * hole + 1;
			if (child != size
					&& comparator.compare(array[child + 1], array[child])) {
				child++;
			}

			if (comparator.compare(array[child], value)) {
				array[hole] = array[child];
			} else {
				break;
			}
		}

		array[hole] = value;
	}

	private void enlargeArray(int capacity) {
		int[] dest = new int[capacity];
		System.arraycopy(array, 0, dest, 0, array.length);
		array = dest;
	}

	public static void main(String[] args) {
		int[] array = { 9, 10, 7, 6, 3, 1, 8, 4 };
		BinaryHeap heap = new BinaryHeap(false);

		for (int value : array) {
			heap.insert(value);
		}

		while (heap.size() > 0) {
			System.out.println(heap.deleteMin());
		}

	}

	/**
	 * 小于比较器
	 */
	private static class MinimumComparator implements Compare {
		private static final MinimumComparator instance = new MinimumComparator();

		@Override
		public boolean compare(int v1, int v2) {
			return v1 < v2;
		}

		public static MinimumComparator instance() {
			return instance;
		}
	}

	/**
	 * 大于比较器
	 */
	private static class MaximumComparator implements Compare {
		private static final MaximumComparator instance = new MaximumComparator();;

		@Override
		public boolean compare(int v1, int v2) {
			return v1 > v2;
		}

		public static MaximumComparator instance() {
			return instance;
		}
	}

}

/**
 * 比较器接口
 */
interface Compare {
	boolean compare(int v1, int v2);
}