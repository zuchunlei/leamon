package nio.reactor.general.io;

import nio.reactor.general.server.IOSession;

/**
 * IO的处理器接口，实现该接口的对象实例不需要保存IO过程中的状态对象，只作为工具方法使用。<br/>
 * 具体的IO过程中的状态信息均由IOSession对象维护。
 */
public interface IOListener {

    /**
     * session的初始化操作，使用setAttribute方法设置IO处理中相关的资源，典型的是ByteBuffer。
     * 
     * @param session
     */
    void attach(IOSession session);

    /**
     * 读操作，数据从内核态到用户态流动
     * 
     * @param session
     */
    void read(IOSession session);

    /**
     * 写操作，数据从用户态到内核态流动
     * 
     * @param session
     */
    void write(IOSession session);

    /**
     * 注销session资源等
     * 
     * @param session
     */
    void detach(IOSession session);

}
