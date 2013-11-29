package com.bes.training.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectInf {

	public static void reflect(Object a) throws Exception {
		// 反射获得对象所属的类
		Class<?> clazz = a.getClass();

		// 获得该类的声明的成员变量和类变量
		Field[] fields = clazz.getDeclaredFields();
		System.out.println("属性信息如下：");
		for (int i = 0; i < fields.length; i++) {
			System.out.println(printFieldInf(fields[i], a));
		}

		// 获得该类的声明的方法
		Method[] methods = clazz.getDeclaredMethods();
		System.out.println("方法信息如下:");
		for (int i = 0; i < methods.length; i++) {
			System.out.println(printMethodInf(methods[i], a));
		}
	}

	/**
	 * 模仿field的toString方法
	 * 
	 * @param field
	 * @param a
	 * @return
	 * @throws Exception
	 */
	private static String printFieldInf(Field field, Object a) throws Exception {
		field.setAccessible(true);
		Object obj = field.get(a);
		int mod = field.getModifiers();
		return (((mod == 0) ? "" : (Modifier.toString(mod) + "----"))
				+ field.getType().getSimpleName() + "----" + field.getName()
				+ "----" + obj.toString());
	}

	/**
	 * 模仿method的toString方法
	 * 
	 * @param method
	 * @param a
	 * @return
	 */
	private static String printMethodInf(Method method, Object a) {
		method.setAccessible(true);
		Class<?>[] clazzs = method.getParameterTypes();

		// 今天学到的一点，不同于StringBuffer strbuff = null；
		StringBuffer strbuff = new StringBuffer();
		for (int i = 0; i < clazzs.length; i++) {
			strbuff = strbuff.append(clazzs[i].getSimpleName().toString());
			if (i < clazzs.length - 1) {
				strbuff = strbuff.append(",");
			}
		}
		int mod = method.getModifiers();
		return (((mod == 0) ? "" : (Modifier.toString(mod) + "----"))
				+ method.getReturnType().getSimpleName() + "----"
				+ method.getName() + "(" + strbuff.toString() + ")");
	}

	public static void main(String[] args) throws Exception {
		ReflectInf.reflect(new Object());
	}

}
