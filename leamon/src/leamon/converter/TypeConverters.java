package leamon.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * ����ת����������
 */
public class TypeConverters {

	private static final Map<Class<?>, Converter<?>> map = new HashMap<Class<?>, Converter<?>>();

	/**
	 * ���ض����ͣ����ת��������
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
	 * ����ת������
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
