#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <signal.h>

#include "argus.h"

char* processes[2048];
int exitStatus[2048];
int execTimes[2048];
char commTimes[2048];
int monitors[2048];
int pids[2048][32];
int numPids[2048];
int lastProcess = 0;
int tExec = -1;
int tInac = -1;

void strcpyandtrim(char* dest, char* src);
void terminate(int procNum);

void sigchld_handler(int sig) {
    int status;
    pid_t pid;
    while((pid = waitpid(-1, &status, WNOHANG)) > 0) {
        for(size_t i = 0; i <= lastProcess; i++) {
            for(size_t j = 0; j < numPids[i]; j++) {
                if(pids[i][j] == pid && WEXITSTATUS(status) == ERROR) {
                    terminate(i);
                    exitStatus[i] = ERROR;
                    break;
                }
            }
            if(pids[i][numPids[i] - 1] == pid) {
                if(!WIFSIGNALED(status) && exitStatus[i] == EXECUTING) {
                    int log = open("log", O_RDONLY);
                    int logidx = open("log.idx", O_WRONLY | O_APPEND);
                    exitStatus[i] = FINISHED;
                    kill(monitors[i], SIGTERM);
                    off_t end = lseek(log, 0, SEEK_END);
                    char message[64];
                    sprintf(message, "%020zu,%020ld;", i+1, end);
                    write(logidx, message, strlen(message));
                    break;
                }
            }
        }
    }
}

void sigalrm_handler(int sig) {
    for(size_t procNum = 0; procNum < lastProcess; procNum++) {
        if(exitStatus[procNum] == EXECUTING) {
            execTimes[procNum]++;
            commTimes[procNum]++;
            if(tExec > 0 && execTimes[procNum] >= tExec) {
                terminate(procNum);
                exitStatus[procNum] = TERMTEXEC;
            }
            else if(tInac > 0 && commTimes[procNum] >= tInac) {
                terminate(procNum);
                exitStatus[procNum] = TERMINACTIVE;
            }
        }
    }
    alarm(1);
}

void sigusr1_handler(int sig, siginfo_t * siginfo, void * c) {
    int monitorpid = siginfo->si_pid;
    for(int i = 0; i <= lastProcess; i++) {
        if(monitors[i] == monitorpid) {
            commTimes[i] = 0;
        }
    }
}

void sigio_handler(int sig) {
    kill(getppid(), SIGUSR1);
}

int main(int argc, char const *argv[]) {
    mkfifo("client_server_fifo", 0644);
    mkfifo("server_client_fifo", 0644);

    signal(SIGCHLD, sigchld_handler);
    signal(SIGALRM, sigalrm_handler);
    signal(SIGIO, sigio_handler);

    struct sigaction * psa = calloc(1, sizeof(struct sigaction));
    psa->sa_sigaction = sigusr1_handler;
    psa->sa_flags = SA_SIGINFO | SA_RESTART;
    sigaction(SIGUSR1, psa, NULL);

    int log = open("log", O_RDWR | O_CREAT | O_TRUNC, 0644);
    int logidx = open("log.idx", O_RDWR | O_CREAT | O_TRUNC, 0644);

    alarm(1);

    while(1) {
        char* buffer = calloc(MESSAGESIZE, sizeof(char));
        int client_server_fifo = open("client_server_fifo", O_RDONLY);
        int server_client_fifo = open("server_client_fifo", O_WRONLY);
        read(client_server_fifo, buffer, MESSAGESIZE);

        if(strncmp(buffer, "ajuda", 5) == 0) {
            char helpMessage[] = "\nCOMANDOS DISPONÍVEIS (modo linha de comando entre parênteses)\n\n"
                                "$ tempo-inactividade TEMPO (-i TEMPO)\n"
                                "Define um tempo máximo de inatividade (em segundos) de comunicação nos pipes de uma tarefa.\n\n"
                                "$ tempo-execucao TEMPO (-m TEMPO)\n"
                                "Define um tempo máximo de execução (em segundos) de uma tarefa.\n\n"
                                "$ executar \"COMANDO_1[ | COMANDO_2 ... | COMANDO_N]\" (-e \"C1[ | C2 ... | CN]\")\n"
                                "Executa uma tarefa, que consiste em um ou mais comandos encadeados.\n\n"
                                "$ listar (-l)\n"
                                "Lista todas as tarefas em execução.\n\n"
                                "$ terminar NUM_TAREFA (-t NUM_TAREFA)\n"
                                "Termina uma tarefa em execução.\n\n"
                                "$ historico (-r)\n"
                                "Lista o registo histórico de tarefas terminadas.\n\n"
                                "$ ajuda (-h)\n"
                                "Apresenta este texto de ajuda.\n\n"
                                "$ output NUM_TAREFA (-o NUM_TAREFA)\n"
                                "Permite consultar o standard output produzido por uma tarefa já executada.\n\n";
            write(server_client_fifo, helpMessage, strlen(helpMessage));
        }
        else if(strncmp(buffer, "executar", 8) == 0) {
            char command[MESSAGESIZE];
            strcpyandtrim(command, buffer + 9);

            processes[lastProcess] = strdup(command);
            exitStatus[lastProcess] = EXECUTING;

            char message[64];
            sprintf(message, "\nnova tarefa #%d\n\n", lastProcess + 1);
            write(server_client_fifo, message, strlen(message));
            close(server_client_fifo);

            int pid;
            int pipes[32][2];
            int currPipe = 0;

            char* token;
            char* args[1000];
            int i = 0;
            char* rest = command;

            if((monitors[lastProcess] = fork()) == 0) {
                monitors[lastProcess] = getpid();
                while(1);
            }
            while((token = strtok_r(rest, " ", &rest))) {
                if(i == 0) {
                    args[0] = strdup(token);
                    i++;
                }

                if(*token == '|') {
                    pipe(pipes[currPipe]);
                    fcntl(pipes[currPipe][0], __F_SETOWN, monitors[lastProcess]);
                    fcntl(pipes[currPipe][0], F_SETFL, O_ASYNC | O_RDONLY);

                    args[i] = NULL;
                    if((pid = fork()) == 0) {
                        close(pipes[currPipe][0]);
                        if(currPipe != 0) {
                            dup2(pipes[currPipe - 1][0], STDIN_FILENO);
                            close(pipes[currPipe - 1][0]);
                        }
                        dup2(pipes[currPipe][1], STDOUT_FILENO);
                        close(pipes[currPipe][1]);
                        execvp(args[0], args + 1);
                        exit(ERROR);
                    }
                    else {
                        pids[lastProcess][currPipe] = pid;
                        for(size_t j = 0; j < i; j++) free(args[j]);
                        i = 0;
                    }
                    close(pipes[currPipe][1]);
                    if(currPipe != 0) close(pipes[currPipe - 1][0]);
                    currPipe++;
                }
                else {
                    args[i] = strdup(token);
                    i++;
                }
            }

            args[i] = NULL;
            if((pid = fork()) == 0) {
                dup2(log, STDOUT_FILENO);
                close(log);
                if(currPipe != 0) {
                    dup2(pipes[currPipe - 1][0], STDIN_FILENO);
                    close(pipes[currPipe - 1][0]);
                }
                execvp(args[0], args + 1);
                exit(ERROR);
            }
            else pids[lastProcess][currPipe] = pid;
            numPids[lastProcess] = currPipe + 1;

            lastProcess++;

            for(size_t j = 0; j < i; j++) free(args[j]);
        }
        else if(strncmp(buffer, "terminar", 8) == 0) {
            long num = strtol(buffer + 9, NULL, 10);
            if(num > 0) {
                num--;
                if(num < lastProcess && exitStatus[num] == EXECUTING) {
                    terminate(num);
                    exitStatus[num] = TERMINATED;
                    char message[] = "\nTarefa terminada com sucesso.\n\n";
                    write(server_client_fifo, message, strlen(message));
                }
                else {
                    char message[] = "\nTarefa não está em execução.\n\n";
                    write(server_client_fifo, message, strlen(message));
                }
            }
            else write(server_client_fifo, "\nInput inválido.\n\n", 19);
        }
        /*
        else if(strncmp(buffer, "sair", 4) == 0) {
            free(buffer);
            break;
        }
        */
        else if(strncmp(buffer, "tempo-execucao", 14) == 0) {
            long time = strtol(buffer + 15, NULL, 10);
            if(time > 0) {
                tExec = time;
                char message[] = "\nTempo definido com sucesso.\n\n";
                write(server_client_fifo, message, strlen(message));
            }
            else {
                char message[] = "\nTempo inválido.\n\n";
                write(server_client_fifo, message, strlen(message));
            }
        }
        else if(strncmp(buffer, "tempo-inactividade", 18) == 0) {
            long time = strtol(buffer + 19, NULL, 10);
            if(time > 0) {
                tInac = time;
                char message[] = "\nTempo definido com sucesso.\n\n";
                write(server_client_fifo, message, strlen(message));
            }
            else {
                char message[] = "\nTempo inválido.\n\n";
                write(server_client_fifo, message, strlen(message));
            }
        }
        else if(strncmp(buffer, "listar", 6) == 0) {
            char message[1024];
            int empty = 1;
            strcpy(message, "\nTAREFAS EM EXECUÇÃO:\n");
            write(server_client_fifo, message, strlen(message));
            for(size_t i = 0; i < lastProcess; i++) {
                if(exitStatus[i] == EXECUTING) {
                    empty = 0;
                    sprintf(message, "#%zu: %s\n", i+1, processes[i]);
                    write(server_client_fifo, message, strlen(message));
                }
            }
            write(server_client_fifo, "\n", 1);
            if(empty) {
                strcpy(message, "Não há tarefas em execução.\n\n");
                write(server_client_fifo, message, strlen(message));
            }
        }
        else if(strncmp(buffer, "historico", 9) == 0) {
            char message[1024];
            strcpy(message, "\nTAREFAS CONCLUÍDAS:\n");
            write(server_client_fifo, message, strlen(message));
            for(size_t i = 0; i < lastProcess; i++) {
                if(exitStatus[i] != EXECUTING) {
                    char* status;
                    switch(exitStatus[i]) {
                        case FINISHED:
                            status = strdup("concluída");
                            break;
                        case TERMINATED:
                            status = strdup("terminada");
                            break;
                        case TERMINACTIVE:
                            status = strdup("max inatividade");
                            break;
                        case TERMTEXEC:
                            status = strdup("max execução");
                            break;
                    default:
                            status = strdup("erro");
                            break;
                    }
                    sprintf(message, "#%zu, %s: %s\n", i+1, status, processes[i]);
                    write(server_client_fifo, message, strlen(message));
                    free(status);
                }
            }
            write(server_client_fifo, "\n", 1);
            if(lastProcess == 0) {
                strcpy(message, "Histórico vazio.\n\n");
                write(server_client_fifo, message, strlen(message));
            }
        }
        else if(strncmp(buffer, "output", 6) == 0) {
            long num = strtol(buffer + 7, NULL, 10);
            char buf[64];

            if(num > 0) {
                if(num <= lastProcess) {
                    lseek(logidx, 0, SEEK_SET);

                    long start = 0, procNum = 0, end = 0;
                    int procExists = 0;

                    while(read(logidx, buf, 42) > 0) {
                        sscanf(buf, "%ld,%ld;", &procNum, &end);
                        if(procNum == num) {
                            procExists = 1;
                            long N = end - start;
                            if(N == 0) write(server_client_fifo, "\nTarefa não produziu output.\n\n", 31);
                            else {
                                char output[N];
                                lseek(log, start, SEEK_SET);
                                read(log, output, N);
                                write(server_client_fifo, "\n", 1);
                                write(server_client_fifo, output, N);
                                write(server_client_fifo, "\n", 1);
                            }
                            break;
                        }
                        start = end;
                    }
                    if(!procExists) {
                        strcpy(buf, "\nTarefa não produziu output.\n\n");
                        write(server_client_fifo, buf, strlen(buf));       
                    }
                    lseek(log, 0, SEEK_END);
                }
                else {
                    strcpy(buf, "\nTarefa não encontrada.\n\n");
                    write(server_client_fifo, buf, strlen(buf));  
                }
            }
            else {
                strcpy(buf, "\nInput inválido.\n\n");
                write(server_client_fifo, buf, strlen(buf));  
            }
        }
        else {
            char buf[32];
            strcpy(buf, "\nComando inválido.\n\n");
            write(server_client_fifo, buf, strlen(buf));  
        }
        free(buffer);
        close(server_client_fifo);
        close(client_server_fifo);
    }
    for(size_t i = 0; i < lastProcess; i++)
        free(processes[i]);
    return 0;
}

void strcpyandtrim(char* dest, char* src) {
    char* start = src;
    while(*start == ' ') start++;
    char quoteType = *start;
    while(*start == quoteType) start++;
    strcpy(dest, start);
    int lastIndex = strlen(dest) - 1;
    while(dest[lastIndex] == quoteType || dest[lastIndex] == ' ') dest[lastIndex--] = '\0';
}

void terminate(int procNum) {
    for(size_t i = 0; i < numPids[procNum]; i++)
        kill(pids[procNum][i], SIGTERM);
    kill(monitors[procNum], SIGTERM);
}