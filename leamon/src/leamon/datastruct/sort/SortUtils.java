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

}
