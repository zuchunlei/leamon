package leamon.condition;

import java.util.HashMap;
import java.util.Map;

/**
 * 类型处理器工厂
 */
public class TypeHandlerFactory {

    private static final Map<Class<?>, TypeHandler<?>> map = new HashMap<>();

    static {
        registTypeHandler(String.class, new StringTypeHandler());
    }

    public static TypeHandler<?> getTypeHandler(Class<?> type) {
        if (map.containsKey(type)) {
            return map.get(type);
        }

        for (Map.Entry<Class<?>, TypeHandler<?>> entry : map.entrySet()) {
            Class<?> key = entry.getKey();
            if (type.isAssignableFrom(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public static TypeHandler<?> registTypeHandler(Class<?> type, TypeHandler<?> handler) {
        return map.put(type, handler);
    }

    /**
     * String类型处理器
     */
    static class StringTypeHandler implements TypeHandler<String> {
        @Override
        public boolean equal(String base, String value) {
            return base.equals(value);
        }

        @Override
        public boolean greater(String base, String value) {
            return base.compareTo(value) > 0;
        }

        @Override
        public boolean less(String base, String value) {
            return base.compareTo(value) < 0;
        }
    }
}