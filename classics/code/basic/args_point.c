#include <stdio.h>

void assignment(char *);


int main(){

	char *name;// ����һ��ָ�����͵ı�������ָ��δָ�����Ч�洢��Ԫ��

	assignment(name);

	printf("name is %s\n",name);

}


// ��ָ�븳ֵ����
void assignment(char *name){
		
	scanf("%s",name);// �������ֵ���洢��nameָ��ָ���Ĵ洢��Ԫ�С�
	
}
