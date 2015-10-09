package leamon.enhance;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 传输对象，测试transient关键字对java对象序列化的影响
 */
public class TransportObject implements Serializable {

    private static final long serialVersionUID = -8278501218669575448L;

    private int age;

    private transient String name;

    private transient long id;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 成员内部类（非静态）
     */
    class CommonInnerObject implements Serializable {
        private static final long serialVersionUID = 1031648876487968082L;

        private long version;
        private String description;

        public CommonInnerObject(long version, String description) {
            this.version = version;
            this.description = description;
        }

        public long getVersion() {
            return version;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * 静态内部类
     */
    static class StaticInnerObject implements Serializable {
        private static final long serialVersionUID = -3773312243815260813L;

        private int age;
        private String name;

        public StaticInnerObject(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws Exception {
        TransportObject obj = new TransportObject();
        obj.setId(100);
        obj.setAge(29);
        obj.setName("zuchunlei");

        // 成员内部类对象的创建需要外围类的对象实例
        CommonInnerObject commonInnerObject = obj.new CommonInnerObject(1, "this is a test");

        // 静态内部类对象的创建不需要外围类的对象实例
        StaticInnerObject staticInnerObject = new StaticInnerObject(29, "panda");

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:/3.txt"));

        out.writeObject(commonInnerObject);
        out.writeObject(staticInnerObject);

        // 在对commonInnerObject与staticInnerObject对象writeObject后，修改其对象的内部状态
        commonInnerObject.description = "test is a test";
        commonInnerObject.version = 2;
        staticInnerObject.age = 30;
        staticInnerObject.name = "foxli";

        // 再对commonInnerObject与staticInnerObject对象进行writeObject调用进行序列化操作
        out.writeObject(commonInnerObject);
        out.writeObject(staticInnerObject);

        out.close();
    }
}
