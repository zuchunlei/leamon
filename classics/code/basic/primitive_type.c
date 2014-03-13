#include <stdio.h>

// C语言的基本类型，两大类：数值类型与字符类型
// 数值类型：整型与浮点型
// 整型：short、int、long  浮点型：float、double
// 字符类型：char


// 基本类型所占用的内存空间 
// short int --> 2字节，int --> 4字节，long int --> 4字节。
// float --> 4字节，double --> 8字节。
// char --> 1字节。

int main(){
	
	short age = 28;
	int sum = 50000;
	long id = 200000000;
	float price = 12500.00;
	double race = 1.25671;
	
	printf("primitive type [short int] alloc %d byte space.\n",sizeof(age));
	printf("primitive type [int] alloc %d byte space.\n",sizeof(sum));
	printf("primitive type [long int] alloc %d byte space.\n",sizeof(id));
	printf("primitive type [float] alloc %d byte space.\n",sizeof(price));
	printf("primitive type [double] alloc %d byte space.\n",sizeof(race));
}

