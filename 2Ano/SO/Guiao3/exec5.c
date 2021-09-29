#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char*argv[])
{
	int ret, status;
	for (int i = 1; i<argc; i++){
		if (fork() == 0) {
			ret = execl(argv[i], argv[i], NULL);
			if (ret == -1) perror("Comando nao executado");
			_exit(ret);
		}
	}
	for (int i = 1; i < argc; i++) {
		wait(&status);
		if (WIFEXITED(status)) printf("Filho terminado com sucesso\n");
	}
	return 0;
}