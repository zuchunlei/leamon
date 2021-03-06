#include <stdio.h>

float func(int);

int main(){
	
	float param = 10;

	float number = func(param);//当前func函数没有原型，则编译器会对其参数和返回值进行类型的隐式转换。

	printf("number is %f\n",number);

}

/*
如果没有关于调用函数的特定信息，编译器便假定在这个函数的调用时参数的类型和数量是正确的。
它同时会假设函数将返回一个整型值。对于那些返回值并非整型的函数而言，这种隐式认定会导致错误。
*/

