#include <stdio.h>

void change(char *,char);

int main(){

	char *ptr = "zuchunlei";
	// ��ǰ��"zuchunlei"Ϊ�ַ���������Ҳ��Ϊ�ַ�����������
	// �����͵��ַ��������������ڽ��̵����ݶε�.rodata�У�����������ִ��ʱ���������ǲ����޸ĵġ�
	// ����������ַ��������������޸ĵĻ�����������δ���

	char name[] = "zuchunlei";
	// ��char name[] = "zuchunlei";��ʽ�������ַ����飬���ַ����洢��name����Ŀռ��У����Խ����޸ġ�

	change(ptr,'a');

}


void change(char *name,char c){
	while(*name != '\0'){
		*name = c;// ��ֵ���������δ���
		name++;
	}

}
