package leamon.condition;

public interface TypeHandler<T> {

    public boolean equal(T base, T value);

    public boolean greater(T base, T value);

    public boolean less(T base, T value);
}