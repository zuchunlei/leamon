package leamon.datastruct.heap;

/**
 * 最小堆
 */
public class MinimumHeap {

	private static final int DEFAULT_CAPACITY = 11;

	private int size;

	private int[] array;

	public MinimumHeap() {
		this(DEFAULT_CAPACITY);
	}

	public MinimumHeap(int capacity) {
		this.size = 0;
		this.array = new int[capacity];
	}

	public MinimumHeap(int[] array) {
		this.size = array.length;
		this.array = new int[array.length + 1];
		System.arraycopy(array, 0, this.array, 1, array.length);

		buildHeap();
	}

	private void buildHeap() {
		for (int i = size / 2; i > 0; i--) {
			percolateDown(i);
		}
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void makeEmpty() {
		this.size = 0;
	}

	/**
	 * 添加元素
	 * 
	 * @param value
	 */
	public void insert(int value) {
		if (size == array.length - 1) {
			enlargeArray(2 * array.length + 1);
		}

		array[++size] = value;
		percolateUp(size);
	}

	/**
	 * 上滤
	 * 
	 * @param hole
	 */
	private void percolateUp(int hole) {
		int value = array[hole];
		for (; hole > 1 && value < array[hole / 2]; hole /= 2) {
			array[hole] = array[hole / 2];
		}

		array[hole] = value;
	}

	/**
	 * 删除最小值
	 * 
	 * @return 当前堆中的最小值
	 */
	public int deleteMin() {
		int min = findMin();
		array[1] = array[size--];

		// 下滤，保证堆序性
		percolateDown(1);

		return min;
	}

	/**
	 * 下滤
	 * 
	 * @param hole
	 */
	private void percolateDown(int hole) {
		int child = 0;
		int value = array[hole];

		for (; hole * 2 <= size; hole = child) {
			child = hole * 2;
			// 对左/右孩子进行比较，获得child的位置
			if (child != size && array[child + 1] < array[child]) {
				child++;
			}

			if (value > array[child]) {
				array[hole] = array[child];
			} else {
				break;
			}
		}
		array[hole] = value;
	}

	public int findMin() {
		if (isEmpty()) {
			String message = "current heap is empty!";
			throw new RuntimeException(message);
		}
		return array[1];
	}

	public int size() {
		return this.size;
	}

	private void enlargeArray(int capacity) {
		int[] dest = new int[capacity];
		System.arraycopy(array, 0, dest, 0, array.length);
		this.array = dest;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 1; i <= size; i++) {
			buffer.append(array[i]);
			if (i != size) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		int[] array = { 9, 10, 7, 6, 3, 1, 8, 4 };

		MinimumHeap heap = new MinimumHeap(array);
		System.out.println(heap);

		heap.makeEmpty();
		for (int value : array) {
			heap.insert(value);
		}
		System.out.println(heap);

		// System.out.println(heap.deleteMin());
		//
		// heap.insert(12);
		//
		// System.out.println(heap);
	}

}
