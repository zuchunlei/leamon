package leamon.datastruct.sort;

/**
 * ���򹤾��࣬�ڲ��ṩ���������㷨
 */
public class SortUtils {

	/**
	 * ��ֵ���򣬻��ڱȽϽ����������� ѡ��λ��p����array[p]��ֵ��λ��p����������ݼ����бȽϡ�
	 * ��λ��pִ���꣬����Ա�֤�����������p�������Ѿ��ź���
	 * 
	 * @param array
	 */
	public static void insertionSort(int[] array) {

		for (int p = 1; p < array.length; p++) {
			int tmp = array[p];// ��ʱֵ
			int j = p;
			for (; j > 0 && array[j - 1] > tmp; j--) {
				// ���ǽ����������ƶ������б�pλ��ֵ�������ȫ�������ƶ�
				array[j] = array[j - 1];
			}

			array[j] = tmp;
		}
	}

	/**
	 * ϣ�����򣬵ݼ����������ǲ�ֵ�����һ�ָ�Ч�Ľ��汾��
	 * 
	 * @param array
	 */
	public static void shellSort(int[] array) {
		int size = array.length;

		// ѡ��������gapΪ��sizeһ��ķ�ʽ��������
		for (int gap = size / 2; gap > 0; gap /= 2) {
			// ��gap��size������gap���γɵ���ֱ���в�������
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
	 * ��������
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
	 * �����㷨��ѡ����ȷ��index��ͨ����������֤index���涼��С��pivot�����涼�Ǵ���pivot�ġ�
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

		for (int i = index = begin; i < end; ++i) {// ++i����֤�˵�ǰpivot�����бȽϲ���
			if (array[i] < pivot) {
				swap(array, index++, i);
			}
		}
		swap(array, index, end);
		return index;
	}

	/**
	 * ������
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
	 * ���˲���
	 * 
	 * @param array
	 *            ��������
	 * @param hole
	 *            �������
	 * @param size
	 *            ��ǰ���߼���С
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
	 * �鲢����
	 * 
	 * @param array
	 */
	public static void mergeSort(int[] array) {
		int[] tmp = new int[array.length];
		mergeSort(array, tmp, 0, array.length - 1);
	}

	/**
	 * �Ը���������з�������
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
	 * �鲢���������������������������й鲢��һ����ʱ�����С�
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
	 * ð������ͨ��ÿһ�˵ıȽϽ�������֤λ��i��array.length֮�����������
	 * 
	 * @param array
	 */
	public static void bubbleSort(int[] array) {
		// ����i�����������������з���
		for (int i = array.length - 1; i > 0; i--) {
			// ͨ���ȽϽ�������֤[j--i]֮�������������
			for (int j = 0; j < i; j++) {
				if (array[j + 1] < array[j]) {
					swap(array, j, j + 1);
				}
			}
		}
	}

}
