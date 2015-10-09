package leamon.io;

public interface IoService {

    void read();

    void write();

    void addFilter(IoFilter filter);

    void removeFilter(IoFilter filter);
}
