package leamon.multicast.threadpool;

public interface Future<V> {

    boolean isDone();

    V get();

}
