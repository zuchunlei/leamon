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

}
