package leamon.io;

/**
 * �ӿڸ��룬��Ҫ�ÿͻ��������������õĽӿڡ�
 */
public interface OperabilityFilterChain extends IoFilterChain {
    void addFilter(IoFilter filter);

    void removeFilter(IoFilter filter);
}
