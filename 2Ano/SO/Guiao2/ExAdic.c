#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

#define lin 3
#define col 40

int main(int argc, char const *argv[]) {
	int m[lin][col];
	int x, found = 0;
	int line[40];
	int valor = 6;
	int status;
	pid_t pidf;
	// Gera matriz com valores aleatorios
		for (int i = 0; i < lin; ++i) {
			for (int j = 0; j < col; ++j) {
				m[i][j] = x;
				x++;
			}
		}
	int f = open("Matriz", O_CREAT | O_RDWR | O_TRUNC ,0600,0700);
	if (write(f, &m, lin*col*sizeof(int)) > 0) printf("Matriz escrita com sucesso.\n");
	close(f);
	f = open("Matriz", O_RDONLY, S_IRUSR);
	for (int i = 0; i<3; i++) {
		f = lseek(f, i*col*sizeof(int),SEEK_SET);
		if (fork() == 0) {
			read(f, &line, 40*sizeof(int));
			for (int j = 0; j<40 && !found; j++)
				if (line[j] == valor) found = 1;
			_exit(found);
		}
	}
	for (int i = 0; i < 3; ++i) {
		pidf = wait(&status);
		if (WEXITSTATUS(status) == 1) {
			printf("Valor encontrado pelo pid %d.\n", pidf);
		}
		else printf("Valor nao encontrado pelo pid %d.\n", pidf);
	}
	close(f);
	return 0;
}
