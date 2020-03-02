package nio.reactor.general.io;

import nio.reactor.general.data.DataPacket;

/**
 * IOFilter接口
 */
public interface IOFilter {

    /**
     * 网络数据读完成，内核态----->用户态 （完成）
     * 
     * @param packet
     * @param chain
     */
    void onReadComplete(DataPacket packet, IOFilterChain chain);

    /**
     * 应用数据写就绪，用户态----->内核态 （就绪）
     * 
     * @param packet
     * @param chain
     * @return
     */
    DataPacket onWriteReady(DataPacket packet, IOFilterChain chain);
}
