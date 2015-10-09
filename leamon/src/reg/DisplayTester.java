package reg;

import java.util.HashMap;
import java.util.Map;

public class DisplayTester {

    public static void main(String[] args) {
        String name = "app/host-instance";
        String[] array = name.split("/|-");

        Map<String, String> header = new HashMap<String, String>();
        header.put("app", "blog");
        header.put("host", "localhost");
        header.put("instance", "tomcat-1");

        for (String s : array) {
            String value = header.get(s);
            if (null == value) {
                value = "null";
            }
            name = name.replace(s, value);
        }

    }
}
