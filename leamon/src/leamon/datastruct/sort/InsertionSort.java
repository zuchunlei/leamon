package leamon.datastruct.sort;

/**
 * 插值排序
 */
public class InsertionSort {

	/**
	 * 插值排序，基于比较交换进行排序。 选择位置p，将array[p]数值与位于p左区域的数据集进行比较。
	 * 当位置p执行完，则可以保证输入的数组中p左区域已经排好序。
	 * 
	 * @param array
	 */
	public static void sort(int[] array) {

		for (int p = 1; p < array.length; p++) {
			int tmp = array[p];// 临时值
			int j = p;
			for (; j > 0 && array[j - 1] > tmp; j--) {
				// 交换操作
				array[j] = array[j - 1];
			}

			array[j] = tmp;
		}
	}

}
