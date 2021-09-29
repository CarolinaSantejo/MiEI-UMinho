#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>


int main (int argc, char const *argv[])
{
	int f1 = open("/etc/passwd", O_RDONLY, 00400);
	int f2 = open("saida.txt", O_CREAT | O_RDWR | O_TRUNC ,0600,0700);
	int f3 = open("erros.txt", O_CREAT | O_RDWR | O_TRUNC ,0600,0700);
	dup2(f1, 0);
	dup2(f2, 1);
	dup2(f3, 2);
	close(f1);
	close(f2);
	close(f3);

	execlp("wc","wc", NULL);
	return 0;
}