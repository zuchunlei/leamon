package leamon;

import java.io.UnsupportedEncodingException;

public class IntChain {

    private int[] chain;
    private int counter;

    public IntChain() {
        chain = new int[10];
        counter = 0;
    }

    public void addInt(int number) {
        if (counter == chain.length) {
            int[] newChain = new int[chain.length << 1];
            System.arraycopy(chain, 0, newChain, 0, chain.length);
            chain = newChain;
        }
        chain[counter++] = number;
    }

    public void removeInt(int number) {
        int[] newChain = new int[chain.length];
        for (int i = 0, j = 0, len = counter; i < len; i++) {
            if (chain[i] == number) {
                counter--;
            } else {
                newChain[j++] = chain[i];
            }
        }
        chain = newChain;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        IntChain demo = new IntChain();
        for (int i = 0; i < 16; i++) {
            demo.addInt(i);
        }
        demo.removeInt(5);
    }
}