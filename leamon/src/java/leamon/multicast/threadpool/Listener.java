package leamon.multicast.threadpool;

public interface Listener<T> {

    void handle(T result, Throwable e);

}
