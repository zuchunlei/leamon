package leamon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Entity implements Serializable {
    private static final long serialVersionUID = 8042628005046964131L;

    private String name;
    private int age;

    public Entity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private Object writeReplace() {
        this.age = -1;
        return this;
    }

    // private Object readResolve() {
    // return new Boolean(true);
    // }

    public static void main(String[] args) throws Exception {
        Entity e = new Entity("1", 28);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("C:\\java.dump"));
        os.writeObject(e);

        ObjectInputStream is = new ObjectInputStream(new FileInputStream("C:\\java.dump"));
        Object o = is.readObject();
        System.out.println(o);

        os.close();
        is.close();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
