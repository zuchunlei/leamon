package com.bes.common.aop.biz;

import com.bes.common.aop.anno.AfterAdvice;
import com.bes.common.aop.anno.BeforeAdvice;
import com.bes.common.aop.anno.ExceptionAdvice;
import com.bes.common.aop.anno.ReturnAdvice;

public class BizHandler {
	@BeforeAdvice(advice = "ǰ��֪ͨ")
	@AfterAdvice(advice = "����֪ͨ")
	@ExceptionAdvice(advice = "�쳣֪ͨ")
	@ReturnAdvice(advice = "����֪ͨ")
	public void handle() throws Exception {
		System.out.println("��Ҫִ�У�");
	}
}
