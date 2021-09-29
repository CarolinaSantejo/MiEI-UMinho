#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main(int argc, char const *argv[])
{
	pid_t pidf;
	int status;
	int x = 0;
	int m[3][40];
	int valor = 6;
	for (int i = 0; i < 3; ++i)
		for (int j = 0; j < 40; ++j) {
			m[i][j] = x;
			x++;
		}

	for (int i = 0; i < 3; ++i) {
		if (fork() == 0) {
			for (int j = 0; j < 40; ++j)
				if (m[i][j] == valor) {
					_exit(1);
				}
			_exit(0);
		}
	}

	for (int i = 0; i < 3; ++i) {
		pidf = wait(&status);
		if (WEXITSTATUS(status) == 1) {
			printf("Valor encontrado pelo pid %d.\n", pidf);
		}
		else printf("Valor nao encontrado pelo pid %d.\n", pidf);
	}
	return 0;
}