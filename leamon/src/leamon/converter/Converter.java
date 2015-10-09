package leamon.converter;

/**
 * ����ת���������ַ�������ת��ΪĿ�����
 */
public abstract class Converter<T> {

    private Converter<T> next;

    /**
     * ת�����������ַ�������ת��ΪĿ����󣬷��أ� �����ǰ�����޷���ȷ���ת�������ƽ���next���д���
     * 
     * @param value
     * @return
     */
    public final T converte(String value) {
        T result = doConverte(value);
        if (result == null && hasNext()) {
            result = next.converte(value);
        }

        return result;
    }

    /**
     * ��converter����Ϊ��ǰת�����ĺ��
     * 
     * @param converter
     * @return
     */
    public Converter<T> addNext(Converter<T> converter) {
        Converter<T> c = next;
        next = converter;
        return c;
    }

    protected abstract T doConverte(String value);

    /**
     * ��ǰת�����Ƿ���ں��
     * 
     * @return
     */
    public boolean hasNext() {
        return next != null;
    }

}
