package leamon.multicast.threadpool;

@FunctionalInterface
public interface Callable<T> {

    T call();

}
