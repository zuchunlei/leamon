package leamon.multicast.threadpool;

public interface Future<V> {

    boolean isDone();

    V get();

    void addListener(Listener<V> listener);

    void callback(V v, Throwable e);
}
