package leamon.datafilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据过滤器构建工厂
 */
public class DataFilterFactory {

	// 操作符map结构
	private static Map<String, Operator> operators = new HashMap<String, Operator>();

	/**
	 * 注册操作符处理器，并将description关联的处理器返回
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
	 * 根据给定的规则表达式，构建数据过滤器对象
	 * 
	 * @param express
	 * @return
	 */
	public static DataFilter buildDataFilter(String express) {
		return null;
	}

}
