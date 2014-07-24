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
	 * 希尔排序，递减增量排序，是插值排序的一种高效改进版本。
	 * 
	 * @param array
	 */
	public static void shellSort(int[] array) {
		int size = array.length;

		// 选定增量，gap为以size一半的方式进行增量
		for (int gap = size / 2; gap > 0; gap /= 2) {
			// 从gap到size，对因gap而形成的组分别进行插入排序
			for (int i = gap; i < size; i++) {
				int value = array[i];
				int j = i - gap;
				for (; j >= 0 && value < array[j]; j -= gap) {
					array[j + gap] = array[j];
				}
				array[j + gap] = value;
			}
		}
	}

	/**
	 * 快速排序
	 * 
	 * @param array
	 */
	public static void quickSort(int[] array) {
		qsort(array, 0, array.length - 1);
	}

	private static void qsort(int[] array, int begin, int end) {
		if (end > begin) {
			int index = partition(array, begin, end);
			qsort(array, begin, index - 1);
			qsort(array, index + 1, end);
		}
	}

	/**
	 * 分区算法，选择正确的index，通过分区，保证index左面都是小于pivot，又面都是大于pivot的。
	 * 
	 * @param array
	 * @param begin
	 * @param end
	 * @return
	 */
	private static int partition(int[] array, int begin, int end) {
		int index = (begin + end) / 2;
		int pivot = array[index];
		swap(array, index, end);

		for (int i = index = begin; i < end; ++i) {// ++i，保证了当前pivot不进行比较操作
			if (array[i] < pivot) {
				swap(array, index++, i);
			}
		}
		swap(array, index, end);
		return index;
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

	/**
	 * 归并排序
	 * 
	 * @param array
	 */
	public static void mergeSort(int[] array) {
		int[] tmp = new int[array.length];
		mergeSort(array, tmp, 0, array.length - 1);
	}

	/**
	 * 对给定数组进行分组排序。
	 * 
	 * @param array
	 * @param tmp
	 * @param left
	 * @param right
	 */
	private static void mergeSort(int[] array, int[] tmp, int left, int right) {
		if (left < right) {
			int center = (left + right) / 2;
			mergeSort(array, tmp, left, center);
			mergeSort(array, tmp, center + 1, right);
			merge(array, tmp, left, center + 1, right);
		}
	}

	/**
	 * 归并操作。给定已排序的两个数组进行归并到一个临时数组中。
	 * 
	 * @param array
	 * @param tmp
	 * @param left
	 * @param right
	 * @param end
	 */
	private static void merge(int[] array, int[] tmp, int left, int right,
			int end) {

		int leftPosition = left;
		int leftEnd = right - 1;
		int rightPosition = right;
		int rightEnd = end;
		int tmpPosition = left;

		while (leftPosition <= leftEnd && rightPosition <= rightEnd) {
			if (array[leftPosition] < array[rightPosition]) {
				tmp[tmpPosition++] = array[leftPosition++];
			} else {
				tmp[tmpPosition++] = array[rightPosition++];
			}
		}

		while (leftPosition <= leftEnd) {
			tmp[tmpPosition++] = array[leftPosition++];
		}

		while (rightPosition <= rightEnd) {
			tmp[tmpPosition++] = array[rightPosition++];
		}

		while (left <= end) {
			array[left] = tmp[left];
			left++;
		}
	}

	/**
	 * 冒泡排序，通过每一趟的比较交换，保证位置i到array.length之间的数据有序
	 * 
	 * @param array
	 */
	public static void bubbleSort(int[] array) {
		// 变量i用来对输入的数组进行分区
		for (int i = array.length - 1; i > 0; i--) {
			// 通过比较交换，保证[j--i]之间的数据有序性
			for (int j = 0; j < i; j++) {
				if (array[j + 1] < array[j]) {
					swap(array, j, j + 1);
				}
			}
		}
	}

}
