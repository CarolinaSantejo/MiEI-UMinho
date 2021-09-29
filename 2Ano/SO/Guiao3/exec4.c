#include <unistd.h>
#include <stdio.h>


int main(int argc, char*argv[])
{
	int ret;
	//ret = execl("./ex3","ex3" ,"blabla", "ola", "xau", NULL);
	ret = execv("./ex3", argv);
	if (ret == -1) perror("Comando nao executado"); 
	return 0;
}