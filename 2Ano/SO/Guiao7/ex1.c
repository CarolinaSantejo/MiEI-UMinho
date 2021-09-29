#include <signal.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

typedef void (*sighandler_t)(int);

int time, count;

void handlerInt(int signum) {
	printf("Sinal %d recebido\n", signum);
	printf("Tempo percorrido: %d\n", time);
	count++;
}

void handlerQuit(int signum) {
	printf("Sinal %d recebido\n", signum);
	printf("Contador (CTRL+C): %d\n", count);
	exit(0);
}

void handlerAlarm(int signum) {
	time++;
	alarm(1);
}

int main(int argc, char const *argv[]) {
	signal(SIGINT,handlerInt);
	signal(SIGQUIT,handlerQuit);
	signal(SIGALRM,handlerAlarm);
	time,count = 0;
	alarm(1);
	while(1) {
		pause();
		printf("A executar...\n");
	}
	return 0;
}