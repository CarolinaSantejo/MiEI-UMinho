#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char const *argv[])
{
	int status;
	int x = 0;
	int m[3][40];
	int valor = 6;
	int linha[3];
	memset(linha,0,3);
	// Gera matriz com valores aleatorios
	for (int i = 0; i < 3; ++i)
		for (int j = 0; j < 40; ++j) {
			m[i][j] = x;
			x++;
		}
	// Procura na matriz
	for (int i = 0; i < 3; ++i) {
		if (fork() == 0) {
			for (int j = 0; j < 40; ++j)
				if (m[i][j] == valor) {
					_exit(i);
				}
			_exit(-1);
		}
	}

	for (int i = 0; i < 3; ++i) {
		wait(&status);
		if (WEXITSTATUS(status) >= 0) {
			linha[WEXITSTATUS(status)] = 1;
		}
	}

	for (int i = 0; i < 3; ++i) {
		if (linha[i]) printf("Valor encontrado na linha %d\n", i);
		else printf("Valor nao encontrado na linha %d\n", i);
	}
	return 0;
}