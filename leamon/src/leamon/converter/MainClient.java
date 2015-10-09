package leamon.converter;

import java.util.Date;

import leamon.converter.impl.DateConverter;

/**
 * 类型转换器测试主类
 */
public class MainClient {

    public static void main(String[] args) {
        TypeConverters.addConverter(Date.class, new DateConverter("yyyy-MM-dd"));
        TypeConverters.addConverter(Date.class, new DateConverter("yyyy/MM/dd"));
        TypeConverters.addConverter(Date.class, new DateConverter("yyyy~MM~dd"));

        Date result;

        result = TypeConverters.converte(Date.class, "1986-10-24");
        System.out.println(result);

        result = TypeConverters.converte(Date.class, "1997/9/1");
        System.out.println(result);

        result = TypeConverters.converte(Date.class, "2008~9~10");
        System.out.println(result);
    }

}
