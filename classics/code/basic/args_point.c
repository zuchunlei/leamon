#include <stdio.h>

void assignment(char *);


int main(){

	char *name;// ����һ��ָ�����͵ı�������ָ��δָ�����Ч�洢��Ԫ��

	assignment(name);

	printf("name is %s\n",name);

}


// ��ָ�븳ֵ����
void assignment(char *name){
	
	char buf[20];
	
	scanf("%s",buf);
	
	name = buf;// ֱ�Ӷ�ָ��������и�ֵ�������ı��ֻ�ǵ�ǰ����ջ֡��name������ֵ��������������û���κ�Ӱ�졣
}
