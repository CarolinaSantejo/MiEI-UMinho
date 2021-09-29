#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <string.h> 
#include <unistd.h> 
#include <fcntl.h> 

char buff[1000];

int main(int argc, char const *argv[])
{
	int f = open("fifo", O_RDONLY);
	int n;
	while((n = read(f,buff,100)) > 0) {
		write(1, buff, n);
	}
	close(f);
	return 0;
}