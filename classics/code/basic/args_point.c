#include <stdio.h>

void assignment(char *);


int main(){

	char name[20];// ����һ���ַ����飬���ڴ洢�ռ䡣

	assignment(name);// ��ָ�����Ͳ������ݸ�assignment������

	printf("name is %s\n",name);

}


// ��ָ�븳ֵ����
void assignment(char *name){
	
	scanf("%s",name);// ֱ�Ӳ���name��ָ�Ĵ洢�ռ䣬��ͬ��ִ�� *name = XXX
	
}
