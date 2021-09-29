#include <unistd.h>
#include <stdio.h>


int main(int argc, char const *argv[])
{
	int ret;
	//ret = execl("/bin/ls", "ls", "-l", NULL);
	ret = execlp("ls","ls" ,"-l", NULL);
	printf("Ola\n");
	return 0;
}