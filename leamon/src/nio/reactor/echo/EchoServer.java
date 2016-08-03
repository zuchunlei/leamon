package nio.reactor.echo;

import java.io.IOException;
import java.nio.ByteBuffer;

import nio.reactor.general.io.IOListener;
import nio.reactor.general.server.IOSession;
import nio.reactor.general.server.Server;

/**
 * EchoServerÊÂÀý´úÂë
 */
public class EchoServer {

    public static void main(String[] args) {
        Server server = new Server("192.168.17.1", 20000);
        server.setListener(new EchoIOListener());
        server.start();
    }

    static class EchoIOListener implements IOListener {
        @Override
        public void attach(IOSession session) {
            session.setAttribute("buffer", ByteBuffer.allocateDirect(1024));
            session.interestRead();
        }

        @Override
        public void read(IOSession session) {
            final ByteBuffer buffer = (ByteBuffer) session.getAttribute("buffer");
            try {
                final int count = session.getChannel().read(buffer);
                if (count < 0) {
                    session.close();
                } else {
                    if (buffer.position() > 0) {
                        if (buffer.hasRemaining()) {
                            session.interestReadWrite();
                        } else {
                            // no space to read, set to only write mode
                            session.uninterestRead().interestWrite();
                        }
                    }
                }
            } catch (final IOException e) {
                session.close();
            }
        }

        @Override
        public void write(IOSession session) {
            final ByteBuffer buffer = (ByteBuffer) session.getAttribute("buffer");
            try {
                buffer.flip();
                session.getChannel().write(buffer);
                if (!buffer.hasRemaining()) {
                    // nothing to write, set to read mode
                    session.uninterestWrite().interestRead();
                }
                buffer.compact();
            } catch (final IOException ex) {
                session.close();
            }
        }

        @Override
        public void detach(IOSession session) {
            System.out.println("go");
        }
    }
}
