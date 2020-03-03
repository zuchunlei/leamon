package network;

import java.io.IOException;
import java.nio.channels.Pipe;

public class PipeTester {

    public static void main(String[] args) {
        try {
            Pipe pipe = Pipe.open();
            System.out.println(pipe.source().validOps());
            System.out.println(pipe.sink().validOps());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
