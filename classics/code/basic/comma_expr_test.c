#include <stdio.h>

int main(){

	int number = 0;
	int result = 0;
	int express = 0;

	result = (number=3*5,number*4),number+15,number;

	printf("number is %d\n",number);// 15
	printf("result is %d\n",result);// 60

	express = ((number=3*5,number*4),number+15,number);
	printf("express is %d\n",express);// 15

	// ���ű��ʽ����ֵ����Ϊ�����ң���������ʽ��ֵ��������
	// �Ҳ����������ͺ�ֵ���ǽ�������ͺ�ֵ��
	// �ڿ�ʼ�����Ҳ�������ǰ���������������漰���ĸ����õļ��㡣

	// ������������ȼ�Ϊ��ͣ��ȸ�ֵ�������Ҫ�͡�
	// result = (number=3*5,number*4),number+15,number;ʵ��Ϊ
	// result=(number=3*5,number*4),number+15,number;
}

