#include <stdio.h>

#define SIZE 1024

/* º¯ÊıÉùÃ÷ */
void string_append(char *dest,const char *src);

char array[SIZE];

int main(int argc,char *argv[]){
	char **ptr = argv;
	
	while(ptr!=&argv[argc]){
		string_append(array,*ptr++);
	}

	printf("current array is %s\n",array);

}

void string_append(char *dest,const char *src){
	while(*dest!='\0'){
		dest++;
	}

	while(*src!='\0'){
		*dest++ = *src++;
	}
	
	*dest = '\0';
}
