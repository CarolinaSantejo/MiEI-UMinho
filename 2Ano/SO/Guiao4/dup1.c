#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

#define MAX 5

ssize_t readln_v2(int fildes, char* line, size_t size){
	ssize_t n = read(fildes,line,size);
	int i = 0;
	if( n > 0 ){
		for( ; i < n && line[i] != '\n'; i++);
		lseek(fildes,-(n - i - 1), SEEK_CUR);
        i++;
	}	
	return i;
}

int main(int argc, char const *argv[])
{
	char line[MAX];
	int n;
	int f1 = open("/etc/passwd", O_RDONLY, 00400);
	int f2 = open("saida.txt", O_CREAT | O_RDWR | O_TRUNC ,0600,0700);
	int f3 = open("erros.txt", O_CREAT | O_RDWR | O_TRUNC ,0600,0700);
	dup2(f1, 0);
	dup2(f2, 1);
	dup2(f3, 2);
	close(f1);
	close(f2);
	close(f3);

	n = readln_v2(0, line, MAX);
	write(1, line, n);
	write(2, line, n);
	return 0;
}