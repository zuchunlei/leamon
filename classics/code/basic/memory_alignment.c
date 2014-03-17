#include <stdio.h>

#pragma pack(2) // 指定程序为指定字节对齐，此例中结构体数据以2字节对齐。Linux下默认为4字节

// 结构体的内存对齐测试

struct Person
{
	char sex; // 1个字节     2 
	int number; // 4字节     4
	short age; // 2字节      2
	char *name; // 4字节     4
	double price; // 8字节   8
	
}; // 以2字节对齐，该类型结构体变量占用20字节


struct Student
{
	char sex;
	short age;
	int number;
	char *name;
	double price;
}; // 20字节


int main(){

	// 定义了一个结构体类型的变量
	struct Person p ; // 分配内存
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

// 以下为结构体内存对齐原则：
// 1、对于结构的各个成员，第一个成员位于偏移为0的位置，以后每个数据成员的偏移量必须是min(#pragma pack()指定的数，这个数据成员的自身长度) 的倍数。
// 2、在数据成员完成各自对齐之后，结构(或联合)本身也要进行对齐，对齐将按照#pragma pack指定的数值和结构(或联合)最大数据成员长度中，比较小的那个进行。
