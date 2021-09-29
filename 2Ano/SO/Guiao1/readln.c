#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>

#define MAX 100


ssize_t readln(int fildes, char* line, size_t size){
	ssize_t r = 0, n;
	char c;

	while( r < size && (n = read(fildes,&c,1)) > 0 && c != '\n' )
	   	line[r++] = c;
    line[r++] = '\n';

	return r;
}

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
	int f = open("File.txt", O_RDONLY, S_IRUSR);
	char* line[MAX];
	ssize_t res;
	res = readln_v2 (f,*line, MAX);
	printf("%ld\n", res);
	write(1,*line, res);
	close(f);
	return 0;
}