#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>

#define MAX 1024

int main(int argc, char const *argv[])
{
	int res = 0;
	int f = open("File.txt", O_RDONLY, S_IRUSR);
	int k = open("File1.txt", O_CREAT | O_WRONLY | O_TRUNC,S_IWUSR);
	char* buf[MAX];
	while((res = read(f,buf,MAX))>0){
		write(k, buf, res); 
	}
	close(f);
	close(k);
	return 0;
}