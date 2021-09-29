#include <stdio.h>
#include <string.h>
#include "estado.h"

int grava(ESTADO e, char *fileName) { // grava o estado atual do jogo num ficheiro .txt
    FILE *f;
    if((f = fopen(fileName, "w")) == NULL) return 1;
    else {

        if (e.modo == 0) fprintf(f, "M ");
        else             fprintf(f, "A ");

        if (e.peca == VALOR_X) fprintf(f, "X");
        else                   fprintf(f, "O");

        if(e.modo == 1) fprintf(f, " %d", e.dif);

        fprintf(f, "\n");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (e.grelha[i][j] == VAZIA)        fprintf(f, "_ ");
                else if (e.grelha[i][j] == VALOR_X) fprintf(f, "X ");
                else                                fprintf(f, "O ");
            }
            fprintf(f, "\n");
        }
        fclose(f);
        return 0;
    }
}

int carrega (ESTADO *e, char *s) { // carrega um estado de jogo a partir de um ficheiro .txt
    FILE *f;
    char a,b,c;
    f=fopen(s, "r");
    if (f==NULL) return 1;
    else {
        fscanf(f,"%c %c",&a,&b);
        if (a=='M') {
            e->modo = 0;
            fscanf(f,"\n");
        }
        else {
            e->modo = 1;
            fscanf(f," %c\n",&c);
            e->dif = c - '0';
        }
        if (b=='X') e->peca = VALOR_X;
        else        e->peca = VALOR_O;

        for (int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                fscanf(f,"%c ",&c);
                if      (c == 'X') e->grelha[i][j] = VALOR_X;
                else if (c == 'O') e->grelha[i][j] = VALOR_O;
                else               e->grelha[i][j] = VAZIA;
            }
        }
    }
    fclose(f);
    return 0;
}