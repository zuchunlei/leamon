package com.bes.common.aop;

import java.lang.reflect.Method;

import com.bes.common.aop.anno.AfterAdvice;
import com.bes.common.aop.anno.BeforeAdvice;
import com.bes.common.aop.anno.ExceptionAdvice;
import com.bes.common.aop.anno.ReturnAdvice;
import com.bes.common.aop.biz.BizHandler;

public class Client {

	public static void main(String[] args) throws Exception {
		Class clazz = BizHandler.class;
		Method method = clazz.getMethod("handle", new Class[] {});

		BeforeAdvice before = method.getAnnotation(BeforeAdvice.class);
		AfterAdvice after = method.getAnnotation(AfterAdvice.class);
		ExceptionAdvice ex = method.getAnnotation(ExceptionAdvice.class);
		ReturnAdvice ret = method.getAnnotation(ReturnAdvice.class);

		try {
			if (before != null) {
				System.out.println(before.advice());
			}
			method.invoke(new BizHandler(), new Object[] {});
			if (after != null) {
				System.out.println(after.advice());
			}
		} catch (Exception e) {
			if (ex != null) {
				System.out.println(ex.advice());
			}
		} finally {
			if (ret != null) {
				System.out.println(ret.advice());
			}
		}
	}
}
