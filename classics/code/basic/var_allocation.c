#include <stdio.h>

// 变量分配，储存相关

// 全局变量，未初始化全局变量，静态分配。存储在.bss数据段中。
int number;
short age;

// 以初始化全局变量，静态分配。存储在.data数据段中。
int size =10;
short sex = 2;


int main(){
	
	// 函数的局部变量/参数，动态分配。存储在栈内存中。
	int num = 20;

	static int si;// 未初始化的静态局部变量，分配在.bss段
	static int sii = 100;//已初始化的静态局部变量，分配在.data段


	printf("function main var num is %d\n",num);

}

