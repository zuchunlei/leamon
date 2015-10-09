package leamon.converter.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import leamon.converter.Converter;

/**
 * ��������ת������֧���ض���ʽ
 */
public class DateConverter extends Converter<Date> {

    private SimpleDateFormat formater;// ���ڸ�ʽ����

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
