#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>


int main(int argc, char const *argv[])
{
	int ret;
	int status;
	if (fork() == 0) {
		ret = execl("/bin/ls", "ls", "-l", NULL);
		_exit(0);
	}
	else {
		wait(&status);
		if (WIFEXITED(status)) printf("Comando executado com sucesso.\n");
		else perror("Erro na execucao.");
	}
	return 0;
}