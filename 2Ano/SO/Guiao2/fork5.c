#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

int main() {
	pid_t pidf; 
	int status;
	for (int i = 0; i < 10; i++){
		if (fork() == 0) {
			printf("[Filho] PID: %d\n", getpid());
			printf("[Filho] Pai: %d\n\n", getppid());
			sleep(1);
		}
		else {
			wait(0);
			_exit(i);
		}
	}
	return 0;
}