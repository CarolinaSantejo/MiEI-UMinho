//
// Created by pja on 28/02/2019.
//
#include <stdio.h>
#include <stdlib.h>
#include "estado.h"
#include "funcsjogo.h"

void newBoard(ESTADO* e, VALOR peca, char modo) {
    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 8; j++) {
            e->grelha[i][j] = VAZIA;
        }
    }
    
    e->grelha[3][4] = VALOR_X;
    e->grelha[4][3] = VALOR_X;
    e->grelha[3][3] = VALOR_O;
    e->grelha[4][4] = VALOR_O;

    e->peca = peca;

    e->modo = modo;
}

// exemplo de uma função para imprimir o estado (Tabuleiro)
void printa(ESTADO e, int nPos, POSICAO pos[], POSICAO * dica) {
    char c = ' ';
    printf("\nModo %s\n ",e.modo == 0 ? "manual" : "automático");
    for(int i = 0; i < 8; ++i) printf(" %d",i + 1);
    putchar('\n');
    for(int i = 0; i < 8; ++i) {
        printf("%d ",i + 1);
        for(int j = 0; j < 8; ++j) {
            switch (e.grelha[i][j]) {
                case VALOR_O: {
                    c = 'O';
                    break;
                }
                case VALOR_X: {
                    c = 'X';
                    break;
                }
                case VAZIA: {
                    c = '-';
                    break;
                }
            }
            for(int k = 0; k < nPos; k++)
                if(i == pos[k].lin && j == pos[k].col)
                    c = '.';
            if(dica && dica->lin == i && dica->col == j)
                c = '?';
            printf("%c ", c);
        }
        putchar('\n');
    }
    printf("X: %2d       O: %2d\n\n",score(e,VALOR_X),score(e,VALOR_O));
}

// void limpaHist(ESTADOSH* hist) {
//     while((*hist) != NULL) {
//         ESTADOSH temp = *hist;
//         *hist = (*hist)->prox;
//         free(temp);
//     }
// }