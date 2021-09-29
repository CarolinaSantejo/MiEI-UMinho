#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <stdio.h>

#include "argus.h"

int main(int argc, char const *argv[]) {
    char string[MESSAGESIZE];

    if(argc < 2) {
        int bytesRead = 0;
        while((bytesRead = read(STDIN_FILENO, string, MESSAGESIZE)) > 0) {
            if(string[bytesRead - 1] == '\n') string[--bytesRead] = 0;

            int client_server_fifo = open("client_server_fifo", O_WRONLY);
            int server_client_fifo = open("server_client_fifo", O_RDONLY);
            write(client_server_fifo, string, bytesRead);
            close(client_server_fifo);

            while((bytesRead = read(server_client_fifo, string, MESSAGESIZE)) > 0)
                write(STDOUT_FILENO, string, bytesRead);
            close(server_client_fifo);
        }
    }
    else {
        if(*argv[1] == '-') {
            switch(argv[1][1]) {
                case 'h':
                    strcpy(string, "ajuda");
                    break;
                case 'r':
                    strcpy(string, "historico");
                    break;
                case 't':
                    sprintf(string, "terminar %s", argv[2]);
                    break;
                case 'l':
                    strcpy(string, "listar");
                    break;
                case 'e':
                    sprintf(string, "executar \"%s\"", argv[2]);
                    break;
                case 'm':
                    sprintf(string, "tempo-execucao %s", argv[2]);
                    break;
                case 'i':
                    sprintf(string, "tempo-inactividade %s", argv[2]);
                    break;
                case 'o':
                    sprintf(string, "output %s", argv[2]);
                    break;
            }
            int client_server_fifo = open("client_server_fifo", O_WRONLY);
            write(client_server_fifo, string, strlen(string));
            close(client_server_fifo);
        }
        int bytesRead = 0;
        int server_client_fifo = open("server_client_fifo", O_RDONLY);
        while((bytesRead = read(server_client_fifo, string, 1024)) > 0)
            write(STDOUT_FILENO, string, bytesRead);
        close(server_client_fifo);
    }
    return 0;
}