package com.bes.common.aop.biz;

import com.bes.common.aop.anno.AfterAdvice;
import com.bes.common.aop.anno.BeforeAdvice;
import com.bes.common.aop.anno.ExceptionAdvice;
import com.bes.common.aop.anno.ReturnAdvice;

public class BizHandler {
	@BeforeAdvice(advice = "前置通知")
	@AfterAdvice(advice = "后置通知")
	@ExceptionAdvice(advice = "异常通知")
	@ReturnAdvice(advice = "返回通知")
	public void handle() throws Exception {
		System.out.println("我要执行！");
	}
}
