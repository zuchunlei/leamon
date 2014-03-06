package com.bes.training.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class BeanUtil {

	public static void buildBean(Object o, Map<String, Object> map)
			throws Exception {
		BeanInfo bean = Introspector.getBeanInfo(o.getClass(), Object.class);
		PropertyDescriptor[] pd = bean.getPropertyDescriptors();
		for (int i = 0; i < pd.length; i++) {
			String name = pd[i].getName();
			if (map.containsKey(name)) {
				Object value = map.get(name);
				pd[i].getWriteMethod().invoke(o, value);
			}
		}
	}

}
