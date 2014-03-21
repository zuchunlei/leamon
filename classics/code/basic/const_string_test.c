#include <stdio.h>

void change(char *,char);

int main(){

	char *ptr = "zuchunlei";
	// 当前的"zuchunlei"为字符串常量，也称为字符串字面量。
	// 该类型的字符串字面量分配在进程的数据段的.rodata中，在整个进程执行时，该数据是不可修改的。
	// 如果对这种字符串字面量进行修改的话，则会引发段错误。

	char name[] = "zuchunlei";
	// 以char name[] = "zuchunlei";方式声明的字符数组，其字符串存储在name数组的空间中，可以进行修改。

	change(ptr,'a');

}


void change(char *name,char c){
	while(*name != '\0'){
		*name = c;// 赋值语句会引发段错误。
		name++;
	}

}
