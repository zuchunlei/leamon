#define NAME "zuchunlei"
#define NUMBER 100

// #define 符号常量 替换文本
// 注意，#define命令没有；


int main(){
	
	int num = NUMBER;
	char *name = NAME;

}

// define 定义的字符常量，在预编译期完全替换字符常量声明的替换文本，再交与编译器进行编译处理。
// 所以编译器不会看到define定义的符号常量，在程序运行其，符号常量更不会存在。
// define 定义的字符常量与替换文本，其后面没有；进行分割。

// 以下是gcc编译预编译后的文本。
/*
# 1 "define_test.c"
# 1 "<built-in>"
# 1 "<command line>"
# 1 "define_test.c"

int main(){

 int num = 100;
 char *name = "zuchunlei";

}
*/




