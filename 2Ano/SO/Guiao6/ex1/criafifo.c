#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{
	int ret = mkfifo("fifo", 0666);
	if (ret==0) printf("Fifo criado com sucesso!\n");
	else perror("Fifo nao criado!");
	return ret;
}