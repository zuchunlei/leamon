package com.bes.training;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ReflectClass {
	private String className; // 类名
	private String clazzName; // 类全名
	private int id; // 属性id
	private String name; // 属性name

	public ReflectClass() {

	}

	/**
	 * xml解析，解析获得所需要的属性信息
	 * 
	 * @throws Exception
	 */
	public void xmlParser() throws Exception {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new File("Reflect.xml"));
		Element root = (Element) doc.getRootElement();
		List<?> list = root.getChildren("bean");
		Iterator<?> iter = list.iterator();
		while (iter.hasNext()) {
			Element e = (Element) iter.next();
			className = e.getAttributeValue("id");
			clazzName = e.getAttributeValue("class");
			id = Integer.parseInt(e.getChildText("id"));
			name = e.getChildText(name);
		}
	}

	/**
	 * 创建Person类的实例
	 * 
	 * @throws Exception
	 */
	public void createClass() throws Exception {
		Class<?> clazz = Class.forName(clazzName);
		Constructor<?> con = clazz.getDeclaredConstructor(new Class[] {
				String.class, int.class });
		Person p = (Person) con.newInstance(name, id);
		System.out.println(p.printInf());
	}

	public static void main(String[] args) throws Exception {
		ReflectClass r = new ReflectClass();
		r.xmlParser();
		r.createClass();
	}
}
