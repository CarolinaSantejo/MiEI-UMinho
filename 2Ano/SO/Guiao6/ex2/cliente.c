#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <string.h> 
#include <unistd.h> 
#include <fcntl.h> 

char buff[1000];

int main(int argc, char const *argv[])
{
	int f = open("fifo", O_WRONLY);
	printf("Cliente processo [%d]\n", getpid());
	char buffid[1024];
	int r = sprintf(buffid, "[%d] ", getpid());
	int n;
	while((n = read(0,buff,1000)) > 0) {
		strcpy(buffid+r,buff);
		write(f, buffid, n+r);
	}
	close(f);
	return 0;
}