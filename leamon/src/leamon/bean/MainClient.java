package leamon.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import leamon.converter.TypeConverters;
import leamon.converter.impl.DateConverter;

public class MainClient {

    public static void main(String[] args) {
        // ����ת����������Ӧ��ϵͳ����ǰ�������ã����ұ�֤ϵͳ��ֵ���ڸ���ĵ�ʵ��
        // �����WebӦ�ã�������ת����������Ӧ����Servlet�н��У���Servlet��load-on-startup
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