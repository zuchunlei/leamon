#include <stdio.h>

// �ṹ����ڴ�������

struct Person
{
	char sex; // 1���ֽ�
	int number; // 4�ֽ�
	short age; // 2�ֽ�
	char *name; // 4�ֽ�
	double price; // 8�ֽ�
	
}; // ��4�ֽڣ�Ĭ�ϣ����룬�����ͽṹ�����ռ��24�ֽ�


struct Student
{
	char sex;
	short age;
	int number;
	char *name;
	double price;
}; // 20�ֽ�


int main(){

	// ������һ���ṹ�����͵ı���
	struct Person p ; // �����ڴ�
	p.sex = 'w';
	p.number = 1;
	p.age = 28;
	p.name = "zuchunlei";
	p.price = 20.00;

	struct Student s;
	s.sex = 'm';
	s.number = 2;
	s.age = 27;
	s.name = "lidaqi";
	s.price = 200.00;
	
	printf("struct type Person is sizeof :%d\n",sizeof(p));
	printf("struct type Student is sizeof :%d\n",sizeof(s));

}

// ����Ϊ�ṹ���ڴ����ԭ��
// 1�����ڽṹ�ĸ�����Ա����һ����Աλ��ƫ��Ϊ0��λ�ã��Ժ�ÿ�����ݳ�Ա��ƫ����������min(#pragma pack()ָ��������������ݳ�Ա��������) �ı�����
// 2�������ݳ�Ա��ɸ��Զ���֮�󣬽ṹ(������)����ҲҪ���ж��룬���뽫����#pragma packָ������ֵ�ͽṹ(������)������ݳ�Ա�����У��Ƚ�С���Ǹ����С�
