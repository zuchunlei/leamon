#include <stdio.h>

void assignment(char *);


int main(){

	char *name;// 定义一个指针类型的变量，该指针未指向的有效存储单元。

	assignment(name);

	printf("name is %s\n",name);

}


// 给指针赋值操作
void assignment(char *name){
	
	char buf[20];
	
	scanf("%s",buf);
	
	name = buf;// 直接对指针变量进行赋值操作，改变的只是当前函数栈帧中name变量的值，对主调函数中没有任何影响。
}
