#include <stdio.h>

// �Խṹ�������
struct Person 
{
	long int id;
	char *name;
	int age;
};

// ������������������е�һ�ε����˺�����ʶ������˱�ʶ��Ӧ�ڸú���ǰ��������
// �����������Ըú�������Ĭ������������ֵΪint��
// �������������Բ�����ȷ�������ƣ�ֻҪ��ȷ�������ͼ��ɡ�
void change(struct Person *);


int main(){
	
	// ����ṹ�����p��������Ϊstruct Person
	// �������壬��Ϊ�˱�������洢�ռ䡣
	// �����������ǽ��������ű��뵽���ű��С�
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
