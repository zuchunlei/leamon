#include <stdio.h>

int main(){

	int number = 0;
	int result = 0;
	int express = 0;

	result = (number=3*5,number*4),number+15,number;

	printf("number is %d\n",number);// 15
	printf("result is %d\n",result);// 60

	express = ((number=3*5,number*4),number+15,number);
	printf("express is %d\n",express);// 15

	// 逗号表达式的求值次序为从左到右，并且左表达式的值被丢弃。
	// 右操作数的类型和值就是结果的类型和值。
	// 在开始计算右操作数以前，将完成左操作数涉及到的副作用的计算。

	// 逗号运算符优先级为最低，比赋值运算符还要低。
	// result = (number=3*5,number*4),number+15,number;实际为
	// result=(number=3*5,number*4),number+15,number;
}

