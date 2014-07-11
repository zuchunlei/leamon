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

}
