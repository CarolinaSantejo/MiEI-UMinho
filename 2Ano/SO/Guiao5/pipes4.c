#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <sys/wait.h>
#include <string.h>

int main(int argc, char const *argv[])
{
	int pd[2];
	int status, n;
	pipe(pd);
	if (fork() == 0) {
		close(pd[1]);
		dup2(pd[0], 0);
		close(pd[0]);
		execlp("wc","wc","-l", NULL);
		_exit(0);
	}
	else {
		close(pd[0]);
		dup2(pd[1],1);
		close(pd[1]);
		execlp("ls","ls","/etc", NULL);
		wait(&status);
		if( WIFEXITED(status) )
            printf("Child process ended with sucess!\n");
        else 
            printf("Error in child process!\n");
	}
	return 0;
}