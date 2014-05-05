#include <stdio.h>


int a(void);
int b(void);
int c(void);

/*
	array为指针数组，其元素为指向函数的指针。
*/
int (*array[])(void) = {a,b,c,NULL};

int main(){

	int (**p)(void) = array;

	while(*p!=NULL){
		// 通过函数指针调用函数，可以使用p()，也可以使用(*p)()；
		// 推荐使用后者，因为这样更能体现指针。
		(*p++)();
	}

	return 0;
}

int a(){
	printf("current function named is a\n");
	return 'a';
}

int b(){
	printf("current function named is b\n");
	return 'b';
}

int c(){
	printf("current function named is c\n");
	return 'c';
}
