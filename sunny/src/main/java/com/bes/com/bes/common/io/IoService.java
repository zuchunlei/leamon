package com.bes.com.bes.common.io;

public interface IoService {

    void addFilter(IoFilter filter);

    void removeFilter(IoFilter filter);

    void handleIO();
}
