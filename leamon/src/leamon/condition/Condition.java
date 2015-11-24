package leamon.condition;

import java.util.Map;

import leamon.converter.TypeConverters;

/**
 * �������󣬶��ڸ���Map�����������˲���
 */
public enum Condition {

    EQUAL {
        @Override
        protected boolean doTest(TypeHandler<Object> handler, Object baseValue, Object headerValue) {
            return handler.equal(baseValue, headerValue);
        }
    }, // ����

    GREATER {
        @Override
        protected boolean doTest(TypeHandler<Object> handler, Object baseValue, Object headerValue) {
            return handler.greater(baseValue, headerValue);
        }
    }, // ����

    LESS {
        @Override
        protected boolean doTest(TypeHandler<Object> handler, Object baseValue, Object headerValue) {
            return handler.less(baseValue, headerValue);
        }
    };// С��

    protected String key;

    protected String value;

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public boolean test(Map<String, Object> eventHeader) {
        boolean result = false;
        Object headerValue = eventHeader.get(key);
        Class<?> clazz = headerValue.getClass();

        TypeHandler<Object> handler = (TypeHandler<Object>) TypeHandlerFactory.getTypeHandler(clazz);

        if (handler != null) {
            Object baseValue = TypeConverters.converte(clazz, value);
            doTest(handler, baseValue, headerValue);
        }

        return result;
    }

    protected abstract boolean doTest(TypeHandler<Object> handler, Object baseValue, Object headerValue);
}