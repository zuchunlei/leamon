package network;

public class Client {

    public static void main(String[] args) throws Exception {
        // Socket socket = new Socket("localhost", 7788);
        // OutputStream out = socket.getOutputStream();
        // out.write(257);
        // out.close();
        int data = 209000125;
        int d1 = data << 1;
        int d2 = data >> 1;
        int d3 = data >>> 1;

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
    }

}
