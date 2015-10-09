package leamon.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import leamon.converter.TypeConverters;

/**
 * 将xml文件转化为对象实例的工具类
 */
public class Beans {

    /**
     * @param bean
     *            目标bean对象
     * @param prop
     *            xml格式文档，key为bean的属性名称，value为文档text
     */
    public static void populate(Object bean, Map<String, String> prop) {
        Class<?> clazz = bean.getClass();

        try {
            // 获取类型的Bean信息对象
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            // 获取该beanInfo对象的属性描述器数组
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor pd : pds) {
                // 获取bean的属性名称
                String property = pd.getName();
                String text = prop.get(property);
                if (text == null) {
                    continue;
                }
                // 获得属性的write方法
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
