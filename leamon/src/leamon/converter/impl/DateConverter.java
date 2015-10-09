package leamon.converter.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import leamon.converter.Converter;

/**
 * 日期类型转换器，支持特定格式
 */
public class DateConverter extends Converter<Date> {

    private SimpleDateFormat formater;// 日期格式化器

    public DateConverter(String pattern) {
        this.formater = new SimpleDateFormat(pattern);
    }

    @Override
    protected Date doConverte(String value) {
        Date result = null;
        try {
            result = formater.parse(value);
        } catch (Exception e) {
            // ignore
        }
        return result;
    }

}
