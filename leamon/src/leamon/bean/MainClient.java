package leamon.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import leamon.converter.TypeConverters;
import leamon.converter.impl.DateConverter;

public class MainClient {

    public static void main(String[] args) {
        // 类型转换器的配置应在系统启动前进行配置，并且保证系统内值存在该类的单实例
        // 如果是Web应用，则类型转换器的配置应该在Servlet中进行，且Servlet是load-on-startup
        TypeConverters.addConverter(Date.class, new DateConverter("yyyy-MM-dd"));
        TypeConverters.addConverter(Date.class, new DateConverter("yyyy/MM/dd"));

        Map<String, String> prop = new HashMap<String, String>();
        prop.put("birthday", "1988/01/22");
        prop.put("graduate", "2008-07-03");

        VO vo = new VO();
        Beans.populate(vo, prop);
        System.out.println(vo);
    }

}

class VO implements Serializable {
    private static final long serialVersionUID = -1576543387348936707L;

    private Date birthday;
    private Date graduate;

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setGraduate(Date graduate) {
        this.graduate = graduate;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String toString() {
        return "birthday: " + birthday.toLocaleString() + "\t" + "graduate: " + graduate.toLocaleString();
    }
}