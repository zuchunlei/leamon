package leamon.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型转换器工具类
 */
public class TypeConverters {

	private static final Map<Class<?>, Converter<?>> map = new HashMap<Class<?>, Converter<?>>();

	/**
	 * 对特定类型，添加转换器对象。
	 * 
	 * @param clazz
	 * @param converter
	 */
	@SuppressWarnings("unchecked")
	public static void addConverter(Class<?> clazz, Converter<?> converter) {
		@SuppressWarnings("rawtypes")
		Converter c = map.get(clazz);
		if (c != null) {
			converter.addNext(c);
		}
		map.put(clazz, converter);
	}

	/**
	 * 类型转换操作
	 * 
	 * @param clazz
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converte(Class<T> clazz, String value) {
		T result = null;
		Converter<T> converter = (Converter<T>) map.get(clazz);
		if (converter != null) {
			result = converter.converte(value);
		}
		return result;
	}
}
