package mbean;

public class HelloMBean implements Hello {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void hello() {
        System.out.println("hello :" + name);
    }

    public void printHello(String any) {
        System.out.println("hello :" + any);
    }

}
