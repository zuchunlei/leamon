#include <stdio.h>

// �������䣬�������

// ȫ�ֱ�����δ��ʼ��ȫ�ֱ�������̬���䡣�洢��.bss���ݶ��С�
int number;
short age;

// �Գ�ʼ��ȫ�ֱ�������̬���䡣�洢��.data���ݶ��С�
int size =10;
short sex = 2;


int main(){
	
	// �����ľֲ�����/��������̬���䡣�洢��ջ�ڴ��С�
	int num = 20;

	static int si;// δ��ʼ���ľ�̬�ֲ�������������.bss��
	static int sii = 100;//�ѳ�ʼ���ľ�̬�ֲ�������������.data��


	printf("function main var num is %d\n",num);

}

