package leamon.converter;

/**
 * 类型转换器，将字符串输入转换为目标对象
 */
public abstract class Converter<T> {

    private Converter<T> next;

    /**
     * 转换操作，将字符串输入转化为目标对象，返回； 如果当前对象无法正确完成转换，则移交给next进行处理。
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
     * 将converter设置为当前转换器的后继
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
     * 当前转换器是否存在后继
     * 
     * @return
     */
    public boolean hasNext() {
        return next != null;
    }

}
