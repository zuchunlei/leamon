package network;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.LinkedList;
import java.util.Queue;

public class NioSelector {

    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        System.out.println(selector);
        IOProcessor processor = new IOProcessor();
        System.out.println(processor.hashCode());
    }

}

class IOProcessor {
    Queue<Selector> pool;

    public IOProcessor() {
        pool = new LinkedList<Selector>();
        int number = Runtime.getRuntime().availableProcessors() + 1;
        for (int i = 0; i < number; i++) {
            try {
                pool.add(Selector.open());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}