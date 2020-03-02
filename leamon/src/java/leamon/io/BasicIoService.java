package leamon.io;

import java.util.ArrayList;
import java.util.List;

public class BasicIoService implements IoService {

    private List<IoFilter> chain;
    private IoHandler handler;

    public BasicIoService() {
        this(null);
    }

    public BasicIoService(IoHandler handler) {
        this.chain = new ArrayList<IoFilter>();
        this.handler = handler;
    }

    public IoHandler getHandler() {
        return handler;
    }

    public void setHandler(IoHandler handler) {
        this.handler = handler;
    }

    @Override
    public void read() {
        new Node().doFilter();
    }

    @Override
    public void write() {
        new Node().doFilter();
    }

    @Override
    public void addFilter(IoFilter filter) {
        chain.add(filter);
    }

    @Override
    public void removeFilter(IoFilter filter) {
        chain.remove(filter);
    }

    private class Node implements IoFilterChain {
        private IoFilter filter;
        private Node next;
        private int index;

        private Node() {
            this(0);
        }

        private Node(int index) {
            this.index = index;
            if (this.index < chain.size()) {
                this.filter = chain.get(index);
                this.next = new Node(++index);// 链式构造对象
            }
        }

        @Override
        public void doFilter() {
            if (filter != null) {
                filter.doFilter(next);
                return;
            }
            // 调用handler的方法
            handler.handle();
        }
    }
}
