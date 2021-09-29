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
	int n;
	while((n = read(0,buff,100)) > 0) {
		write(f, buff, n);
	}
	close(f);
	return 0;
}