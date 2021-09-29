#include <signal.h>
#include <sys/types.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>

typedef void (*sighandler_t)(int);

int spawn_grep(char* word, char* file, int i) {
	int pid, ret;
	if (pid = fork() == 0) {
			printf("Process %d with pid %d exec(grep) for file %s\n", i ,getpid(), file);
			ret = execlp("grep", "grep", word, file, NULL);
			if (ret == -1) perror("Comando nao executado");
			_exit(ret);
	}
	return pid;
}

int main(int argc, char *argv[])
{
	if (argc<3) {
		printf("Usage multigrep <word> file1 file2 ...\n");
		return 1;
	}
	int files_count = argc-2;
	int pids[files_count];
	char** files = argv+2;
	for (int i = 0; i < files_count; i++) {
		pids[i] = spawn_grep(argv[i], files[i], i);
	}
	int found = 0;
	int status = 0;
	int pid;
	int pid_found = -1;
	while(!found && (pid = wait(&status)) > 0) {
		if (WIFEXITED(status)) {
			switch(WEXITSTATUS(status)) {
				case 0:
					printf("Process %d found whe word.\n", pid);
					found = 1;
					pid_found = pid;
					break;
				case 1:
					printf("Process %d did not find whe word.\n", pid);
					break;
			}

		}
	}
	if (found == 1) {
		for (int i = 0; i < files_count; i++)
		{
			if (pids[i] != pid_found) {
				printf("Killing process %d\n", pids[i]);

				// evitar possibilidade de um kill -1
				if (pids[i]>0) {
					kill(pids[i], SIGKILL);
				}

				// mostra que processo foi interrompido
				waitpid(pids[i], &status, 0);
				if (!WIFEXITED(status)) {
					printf("Process %d was interrupted\n", pids[i]);
				}
				else {
					printf("Process %d ended correctly already\n", pids[i]);
				}
			}
		}
	}
	return !found;
}