package leamon.interceptor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据包对象
 */
public class MessagePacket implements Serializable {
    private static final long serialVersionUID = 5378422410294437807L;

    private Map<String, Object> header;
    private byte[] body;

    public MessagePacket() {
        this(null);
    }

    public MessagePacket(byte[] body) {
        this.body = body;
        this.header = new HashMap<String, Object>();
    }

    public void addHeader(String key, Object value) {
        header.put(key, value);
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
