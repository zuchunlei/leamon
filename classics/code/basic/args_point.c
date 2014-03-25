#include <stdio.h>

void assignment(char *);


int main(){

	char *name;// 定义一个指针类型的变量，该指针未指向的有效存储单元。

	assignment(name);

	printf("name is %s\n",name);

}


// 给指针赋值操作
void assignment(char *name){
		
	scanf("%s",name);// 将输入的值，存储到name指针指定的存储单元中。
	
}
