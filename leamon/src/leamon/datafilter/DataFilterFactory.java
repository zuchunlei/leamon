package leamon.datafilter;

import java.util.HashMap;
import java.util.Map;

/**
 * ���ݹ�������������
 */
public class DataFilterFactory {

	// ������map�ṹ
	private static Map<String, Operator> operators = new HashMap<String, Operator>();

	/**
	 * ע�������������������description�����Ĵ���������
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
	 * ���ݸ����Ĺ�����ʽ���������ݹ���������
	 * 
	 * @param express
	 * @return
	 */
	public static DataFilter buildDataFilter(String express) {
		return null;
	}

}
