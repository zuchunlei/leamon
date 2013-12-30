package com.bes.common.io.filter;


public interface IoFilter {

    void doFilter(IoFilterChain filterChain);

    interface IoFilterChain {
        void doFilter();
    }
}



