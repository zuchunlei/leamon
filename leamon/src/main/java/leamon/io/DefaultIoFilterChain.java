package leamon.io;

public class DefaultIoFilterChain implements OperabilityFilterChain {
    private static final int DEFAULT_CHAIN_SIZE = 10;

    private IoFilter[] chain;
    private IoHandler handler;// io处理器

    private int counter;
    private int index;// 调用时计数器

    public DefaultIoFilterChain() {
        this(DEFAULT_CHAIN_SIZE, null);
    }

    public DefaultIoFilterChain(int size) {
        this(size, null);
    }

    public DefaultIoFilterChain(int size, IoHandler handler) {
        this.chain = new IoFilter[size <= 0 ? DEFAULT_CHAIN_SIZE : size];
        this.handler = handler;
        this.counter = 0;
    }

    public void setHandler(IoHandler handler) {
        this.handler = handler;
    }

    @Override
    public void doFilter() {
        if (index < counter) {
            IoFilter filter = chain[index++];
            // 前置通知
            filter.doFilter(this);
            // 后置通知
            return;
        }
        handler.handle();
    }

    @Override
    public void addFilter(IoFilter filter) {
        if (counter == chain.length) {
            IoFilter[] newChain = new IoFilter[counter + 10];
            System.arraycopy(chain, 0, newChain, 0, counter);
            chain = newChain;
        }
        chain[counter++] = filter;
    }

    @Override
    public void removeFilter(IoFilter filter) {
        IoFilter[] newChain = new IoFilter[chain.length];
        for (int i = 0, j = 0, len = counter; i < len; i++) {
            if (chain[i].equals(filter)) {
                counter--;
            } else {
                newChain[j++] = chain[i];
            }
        }
        chain = newChain;
    }
}
