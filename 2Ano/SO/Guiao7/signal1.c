#include <signal.h>
#include <sys/types.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

typedef void (*sighandler_t)(int);

int seconds = 0;
int count = 0;

void handle_sigint (int signum) {
	count++;
	printf("Sinal %d recebido.\n", signum);
	printf("Tempo percorrido: %d\n", seconds);
}

void handle_sigquit (int signum) {
	printf("Sinal %d recebido.\n", signum);
	printf("Contador (CTRL+C): %d\n", count);
	exit(0);
}

void handle_sigalrm(int signum) {
	seconds++;
	alarm(1);
}

int main(int argc, char const *argv[])
{
	signal(SIGINT,handle_sigint);
	signal(SIGQUIT,handle_sigquit);
	signal(SIGALRM, handle_sigalrm);
	alarm(1);
	while (1) {
		pause();
		printf("Teste\n");
	}

	return 0;
}