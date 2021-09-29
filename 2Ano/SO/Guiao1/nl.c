#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <string.h>

#define MAX 500

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

void main () {
	int f = open("File.txt", O_RDONLY, S_IRUSR);
	char line[MAX];
	char res[MAX+6];
	ssize_t n;
	int i = 1;
	while ((n = readln_v2(f, line,MAX)) > 0) {
		line[n] = 0;
		sprintf(res,"\t%d  %s",i++,line);
		write(1, res, strlen(res));
	}
	
}