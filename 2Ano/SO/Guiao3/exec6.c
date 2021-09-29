#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <stdio.h>
#include <string.h>


int mysystem(char* command) {
	char *args[20];
	char* token;
	int i,ret = 0;
	int n, status, fork_ret, wait_ret;
	token = strtok(command, " ");
	while(token != NULL && i<20 ) {
     	args[i] = token;
     	token = strtok(NULL, " ");
     	i++;
   	}
   	args[i] = NULL;
   	fork_ret = fork();
	if(fork_ret==0) {
   		ret = execvp(args[0], args);
		if (ret == -1) perror("Comando nao executado");
		_exit(ret);
	}
	else {
		printf("Pai a espera\n");
		wait_ret = waitpid(fork_ret, &status, 0);
		
		if(WIFEXITED(status)) ret = WEXITSTATUS(status);
		else ret = -1;
	}
	return ret;
}


int main(int argc, char*argv[])
{
	char comando1[] = "ls -l -a -h";
	char comando2[] = "sleep 10";
	char comando3[] = "ps";
	int ret;

	printf("A executar mysystem para %s\n", comando1);
	ret = mysystem(comando1);
	printf("ret: %d\n",ret);
	
	printf("A executar mysystem para %s\n", comando2);
	ret = mysystem(comando2);

	printf("A executar mysystem para %s\n", comando3);
	ret = mysystem(comando3);

	return 0;
}