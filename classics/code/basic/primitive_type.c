#include <stdio.h>

// C���ԵĻ������ͣ������ࣺ��ֵ�������ַ�����
// ��ֵ���ͣ������븡����
// ���ͣ�short��int��long  �����ͣ�float��double
// �ַ����ͣ�char


// ����������ռ�õ��ڴ�ռ� 
// short int --> 2�ֽڣ�int --> 4�ֽڣ�long int --> 4�ֽڡ�
// float --> 4�ֽڣ�double --> 8�ֽڡ�
// char --> 1�ֽڡ�

int main(){
	
	short age = 28;
	int sum = 50000;
	long id = 200000000;
	float price = 12500.00;
	double race = 1.25671;
	
	printf("primitive type [short int] alloc %d byte space.\n",sizeof(age));
	printf("primitive type [int] alloc %d byte space.\n",sizeof(sum));
	printf("primitive type [long int] alloc %d byte space.\n",sizeof(id));
	printf("primitive type [float] alloc %d byte space.\n",sizeof(price));
	printf("primitive type [double] alloc %d byte space.\n",sizeof(race));
}

