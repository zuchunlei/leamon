package com.bes.common.io.filter;

public interface IoService {

    void addFilter(IoFilter filter);

    void removeFilter(IoFilter filter);

    void handleIO();
}
