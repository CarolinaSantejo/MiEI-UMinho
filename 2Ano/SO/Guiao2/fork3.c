#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

void main() {
	pid_t pidf; 
	int status;
	for (int i = 0; i < 10; i++){
		if (fork() == 0) {
			printf("[Filho] PID: %d\n", getpid());
			printf("[Filho] Pai: %d\n", getppid());
			_exit(i);
		}
		else {
			pidf = wait(&status);
			printf("[Pai] Filho %d terminado com valor %d\n", pidf, WEXITSTATUS(status));
		}
	}

}