package leamon.datastruct.sort;

/**
 * 排序工具类，内部提供多种排序算法
 */
public class SortUtils {

	/**
	 * 插值排序，基于比较交换进行排序。 选择位置p，将array[p]数值与位于p左区域的数据集进行比较。
	 * 当位置p执行完，则可以保证输入的数组中p左区域已经排好序。
	 * 
	 * @param array
	 */
	public static void insertionSort(int[] array) {

		for (int p = 1; p < array.length; p++) {
			int tmp = array[p];// 临时值
			int j = p;
			for (; j > 0 && array[j - 1] > tmp; j--) {
				// 不是交换，而是移动。所有比p位置值大的数据全部向右移动
				array[j] = array[j - 1];
			}

			array[j] = tmp;
		}
	}

	/**
	 * 堆排序
	 * 
	 * @param array
	 */
	public static void heapSort(int[] array) {
		// build the max heap
		for (int i = (array.length - 1) / 2; i >= 0; i--) {
			percolateDown(array, i, array.length);
		}

		for (int i = array.length - 1; i > 0; i--) {
			swap(array, 0, i);
			percolateDown(array, 0, i);
		}
	}

	/**
	 * 下滤操作
	 * 
	 * @param array
	 *            给定数组
	 * @param hole
	 *            下滤起点
	 * @param size
	 *            当前堆逻辑大小
	 */
	private static void percolateDown(int array[], int hole, int size) {
		int value = array[hole];
		int child = -1;

		for (; leftChild(hole) < size; hole = child) {
			child = leftChild(hole);
			if (child != size - 1 && array[child + 1] > array[child]) {
				child++;
			}

			if (array[child] > value) {
				array[hole] = array[child];
			} else {
				break;
			}
		}

		array[hole] = value;
	}

	private static int leftChild(int hole) {
		return 2 * hole + 1;
	}

	private static void swap(int array[], int src, int dest) {
		if (src >= array.length || dest >= array.length) {
			String message = "position is beyond the array length!";
			throw new RuntimeException(message);
		}
		int value = array[src];
		array[src] = array[dest];
		array[dest] = value;
	}

}
