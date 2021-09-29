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
	char readln[100];
	pipe(pd);
	if (fork() == 0) {
		close(pd[1]);
		while((n = read(pd[0], &readln, 1000))>0) printf("%s\n", readln);
		close(pd[0]);
		_exit(0);
	}
	else {
		close(pd[0]);
		while((n = read(0,readln,1000)) > 0 ) write(pd[1], readln,n);
		close(pd[1]);
		wait(&status);
		if( WIFEXITED(status) )
            printf("Child process ended with sucess!\n");
        else 
            printf("Error in child process!\n");
	}
	return 0;
}