#include <stdio.h>

// 对结构体的声明
struct Person 
{
	long int id;
	char *name;
	int age;
};

// 函数声明，如果函数中第一次调用了函数标识符，则此标识符应在该函数前被声明。
// 否则编译器会对该函数进行默认声明，返回值为int。
// 函数声明，可以不必明确参数名称，只要明确参数类型即可。
void change(struct Person *);


int main(){
	
	// 定义结构体变量p，其类型为struct Person
	// 变量定义，是为此变量分配存储空间。
	// 变量声明，是将变量符号编译到符号表中。
	struct Person p;

	p.id = 100;
	p.name = "zuchunlei";
	p.age = 28;

	change(&p);

	printf("person id is %d\t name is %s\t and age is %d\n",p.id,p.name,p.age);
}

void change(struct Person *p){
	p->id = 1;
	p->name = "zcl";
	p->age--;
}
