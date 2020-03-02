package leamon.io;

/**
 * 接口隔离，不要让客户被迫依赖它不用的接口。
 */
public interface OperabilityFilterChain extends IoFilterChain {
    void addFilter(IoFilter filter);

    void removeFilter(IoFilter filter);
}
