package com.bes.com.bes.common.io;


public interface IoFilter {

    void doFilter(IoFilterChain filterChain);

    interface IoFilterChain {
        void doFilter();
    }
}



