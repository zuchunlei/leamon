package com.bes.common.io.filter;

import java.util.ArrayList;
import java.util.List;

public class DefaultIoService implements IoService {

    private List<IoFilter> chain;
    private IoHandler handler;

    public DefaultIoService() {
        this.chain = new ArrayList<IoFilter>();
        this.handler = new DefaultIoHandler();
    }

    @Override
    public void addFilter(IoFilter filter) {
        chain.add(filter);
    }

    @Override
    public void removeFilter(IoFilter filter) {
        chain.remove(filter);
    }

    @Override
    public void handleIO() {
        IoFilter.IoFilterChain header = new Node(0);
        header.doFilter();
    }

    static class DefaultIoHandler implements IoHandler {
        @Override
        public void handle() {
            System.out.println("IoHandler is invoking !");
        }
    }

    static class DefaultIoFilter implements IoFilter {
        private int number;//当前Filter对象的标识

        DefaultIoFilter(int number) {
            this.number = number;
        }

        @Override
        public void doFilter(IoFilterChain filterChain) {
            System.out.println("IoFilter Object number :" + number + " is invoking !");
            filterChain.doFilter();
        }
    }

    class Node implements IoFilter.IoFilterChain {
        private int index;
        private IoFilter filter;
        private Node next;

        private Node(int index) {
            this.index = index;
            if (this.index < chain.size()) {
                this.filter = chain.get(index);
                this.next = new Node(++index);
            }
        }

        @Override
        public void doFilter() {
            if (filter != null) {
                filter.doFilter(next);
                return;
            }
            handler.handle();
        }
    }

    public static void main(String[] args) {
        IoService service = new DefaultIoService();
        for (int i = 0; i < 10; i++) {
            service.addFilter(new DefaultIoFilter(i));
        }
        service.handleIO();
    }
}
