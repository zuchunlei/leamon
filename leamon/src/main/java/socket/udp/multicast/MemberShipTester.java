package socket.udp.multicast;

public class MemberShipTester {

    public static void main(String[] args) {
        try {
            new MemberShip().go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
