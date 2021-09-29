#include <unistd.h>
#include <sys/wait.h>
#include <stdio.h>

void main()
{
	int * status;
	pid_t fpid;
	if (fork()==0) {
		printf("[Filho] PID: %d\n", getpid());
		printf("[Filho] Pai: %d\n", getppid());
	}
	else {
		fpid = wait(status);
		printf("[Pai] Filho: %d\n", fpid);
		printf("[Pai] Pai: %d\n", getppid());
		printf("[Pai] PID: %d\n", getpid());
	}

}