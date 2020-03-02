package leamon;

import java.util.ArrayList;
import java.util.List;

public class DisplayTest {

    public void sya(String name) {
        System.out.println(name);
    }

    public void doSome(List<String> list) {
        for (String name : list) {
            sya(name);
        }
    }

    public static void main(String[] args) {
        List<String> names = new ArrayList<String>();
        names.add("zuchunlei");
        names.add("maomao");
        names.add("daqiqi");
        names.add("xiaolili");

        DisplayTest test = new DisplayTest();
        test.doSome(names);
    }
}
