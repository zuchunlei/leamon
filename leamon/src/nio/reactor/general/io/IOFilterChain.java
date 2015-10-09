package nio.reactor.general.io;

import nio.reactor.general.data.DataPacket;

/**
 * IO数据过滤器
 */
public interface IOFilterChain {

    /**
     * 网络数据读完成，内核态----->用户态 （完成）
     * 
     * @param packet
     */
    void onReadComplete(DataPacket packet);

    /**
     * 应用数据写就绪，用户态----->内核态 （就绪）
     * 
     * @param packet
     * @return
     */
    DataPacket onWriteReady(DataPacket packet);

}
