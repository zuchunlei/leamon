package nio.reactor.general.io;

import nio.reactor.general.data.DataPacket;

/**
 * IO数据处理器
 */
public interface IOHandler {

    /**
     * 应用层数据处理
     * 
     * @param packet
     * @return
     */
    DataPacket handle(DataPacket packet);

}
