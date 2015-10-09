package leamon.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import leamon.converter.TypeConverters;

/**
 * ��xml�ļ�ת��Ϊ����ʵ���Ĺ�����
 */
public class Beans {

    /**
     * @param bean
     *            Ŀ��bean����
     * @param prop
     *            xml��ʽ�ĵ���keyΪbean���������ƣ�valueΪ�ĵ�text
     */
    public static void populate(Object bean, Map<String, String> prop) {
        Class<?> clazz = bean.getClass();

        try {
            // ��ȡ���͵�Bean��Ϣ����
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            // ��ȡ��beanInfo�������������������
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : pds) {
                // ��ȡbean����������
                String property = pd.getName();
                String text = prop.get(property);
                if (text == null) {
                    continue;
                }
                // ������Ե�write����
                Method writeMethod = pd.getWriteMethod();
                if (writeMethod == null) {
                    continue;
                }

                Class<?> type = pd.getPropertyType();
                writeMethod.invoke(bean, TypeConverters.converte(type, text));
            }

        } catch (Exception e) {
            // ignore
        }
    }

}
