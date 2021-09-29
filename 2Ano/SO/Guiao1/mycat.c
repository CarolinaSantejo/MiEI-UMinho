#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

#define MAX 1024

int main(int argc, char const *argv[])
{
	int res = 0;
	//int f = open("File.txt", O_RDONLY, S_IRUSR);
	char* buf[MAX];
	while((res = read(0,buf,MAX))>0){
		write(1, buf, res);
	}
	//close(f);
	return 0;
}