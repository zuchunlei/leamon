#include <stdio.h>


int a(void);
int b(void);
int c(void);

/*
	arrayΪָ�����飬��Ԫ��Ϊָ������ָ�롣
*/
int (*array[])(void) = {a,b,c,NULL};

int main(){

	int (**p)(void) = array;

	while(*p!=NULL){
		// ͨ������ָ����ú���������ʹ��p()��Ҳ����ʹ��(*p)()��
		// �Ƽ�ʹ�ú��ߣ���Ϊ������������ָ�롣
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
