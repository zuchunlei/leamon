#include <stdio.h>

void assignment(char *);


int main(){

	char name[20];// 定义一个字符数组，存在存储空间。

	assignment(name);// 以指针类型参数传递给assignment函数。

	printf("name is %s\n",name);

}


// 给指针赋值操作
void assignment(char *name){
	
	scanf("%s",name);// 直接操作name所指的存储空间，等同于执行 *name = XXX
	
}
