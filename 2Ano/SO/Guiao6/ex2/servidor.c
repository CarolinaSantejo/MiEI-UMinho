#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <string.h> 
#include <unistd.h> 
#include <fcntl.h> 

char buff[1000];

int main(int argc, char const *argv[])
{
	int n,f;
	int fd_file = open("log.txt", O_CREAT | O_APPEND | O_WRONLY, 0666);
	while(1){
		f = open("fifo", O_RDONLY);
		printf("Novo cliente.\n");
		while((n = read(f,buff,1000)) > 0) {
			write(fd_file, buff, n);
		}
	}
	close(f);
	close(fd_file);
	return 0;
}