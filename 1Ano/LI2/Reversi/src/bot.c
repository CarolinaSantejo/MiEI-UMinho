#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "estado.h"
#include "bot.h"
#include "funcsjogo.h"

POSICAO jogadaBot(BOT* bot, ESTADO* e) {
    POSICAO jogadasP[60];
    POSICAO jogada = {0};
    srand(time(NULL));
    int jp = jogadasPossiveis(*e,e->peca,jogadasP);
    switch(bot->dif) {
        case 1: {
            jogada = jogadasP[rand() % (jp - 1)];
            break; }
        case 2: {
            POSICAO aresta[24] = {0};
            POSICAO naoAres[36]= {0};
            int canto = 0;
            int numArestas = 0;
            int numNaoAres = 0;
            for(int i=0;i<jp;i++){
                if ((jogadasP[i].lin==0 && jogadasP[i].col==0)
                 || (jogadasP[i].lin==0 && jogadasP[i].col==7)
                 || (jogadasP[i].lin==7 && jogadasP[i].col==0)
                 || (jogadasP[i].lin==7 && jogadasP[i].col==7)) {
                    canto = 1;
                    jogada = jogadasP[i];
                    break;
                }
                else {
                    if (jogadasP[i].lin == 1
                        || jogadasP[i].col == 1
                        || jogadasP[i].lin == 8
                        || jogadasP[i].col == 8) {
                        aresta[numArestas++] = jogadasP[i];
                    }
                    else naoAres[numNaoAres++]=jogadasP[i];
                }
            }

            if (canto==0) {
                if (numArestas) {
                    jogada = aresta[0];
                    for (int i = 1; i < numArestas; i++) {
                        if (aresta[i].dest > jogada.dest) jogada = aresta[i];
                    }
                } else {
                    jogada = naoAres[0];
                    for (int i = 1; i < numNaoAres; i++) {
                        if (naoAres[i].dest > jogada.dest) jogada = naoAres[i];
                    }
                }
            }
            break; }
        case 3: {
            int valor = -1000000000;
            int n;
            for(int i = 0; i < jp; i++) {
                ESTADO eCpy = *e;
                jogar(&eCpy, jogadasP[i].lin, jogadasP[i].col);
                if(proxCanto(jogadasP[i])) n = 13 - jp;
                else n = max(11 - jp, 5);
                eCpy.peca = eCpy.peca == VALOR_X ? VALOR_O : VALOR_X;
                int x = minimax(eCpy, n, -1000000, 1000000, bot->peca);
                if(x > valor) {
                    jogada = jogadasP[i];
                    valor = x;
                }
            }
            break; }
    }
    return jogada;
}

int max(int a, int b) {return a > b ? a : b; }
int min(int a, int b) {return a > b ? b : a; }

int minimax(ESTADO node, int depth, int alpha, int beta, VALOR maximizingPlayer) {
    POSICAO jogadasP[60];
    int value;
    int jp = jogadasPossiveis(node,node.peca,jogadasP);
    if(depth == 0) {
        return valor(node,maximizingPlayer);
    }
    if(gameOver(node)) {
        if(score(node,maximizingPlayer) >= score(node,maximizingPlayer == VALOR_X ? VALOR_O : VALOR_X)) return 1000000;
        else return -1000000;
    }
    if(node.peca == maximizingPlayer) {
        value = -1000000;
        if(jp == 0 && !gameOver(node)) {
            int offset = node.peca == maximizingPlayer ? -100 : 100;
            node.peca = node.peca == VALOR_X ? VALOR_O : VALOR_X;
            value = offset + valor(node,maximizingPlayer);//minimax(node, depth - 1, alpha, beta, maximizingPlayer);
        }
        for(int i = 0; i < jp; i++) {
            ESTADO nodeCopy = node;
            jogar(&nodeCopy, jogadasP[i].lin, jogadasP[i].col);
            nodeCopy.peca = nodeCopy.peca == VALOR_X ? VALOR_O : VALOR_X;
            value = max(value, minimax(nodeCopy, depth - 1, alpha, beta, maximizingPlayer));
            alpha = max(value,alpha);
            if(alpha >= beta) break;
        }
        return value;
    }
    else {
        value = 1000000;
        if(jp == 0 && !gameOver(node)) {
            int offset = node.peca == maximizingPlayer ? -100 : 100;
            node.peca = node.peca == VALOR_X ? VALOR_O : VALOR_X;
            value = offset + valor(node,maximizingPlayer);//minimax(node, depth - 1, alpha, beta, maximizingPlayer);
        }
        for(int i = 0; i < jp; i++) {
            ESTADO nodeCopy = node;
            jogar(&nodeCopy, jogadasP[i].lin, jogadasP[i].col);
            nodeCopy.peca = nodeCopy.peca == VALOR_X ? VALOR_O : VALOR_X;
            value = min(value, minimax(nodeCopy, depth - 1, alpha, beta, maximizingPlayer));
            beta = min(beta,value);
            if(alpha >= beta) break;
        }
        return value;
    }
}

int valor(ESTADO e, VALOR peca) {
    int soma = 0;
    int valor_pos[8][8]={{ 99, -8,  8,  6,  6,  8, -8, 99},
                         { -8,-24, -4, -3, -3, -4,-24, -8},
                         {  8, -4,  7,  4,  4,  7, -4,  8},
                         {  6, -3,  4,  0,  0,  4, -3,  6},
                         {  6, -3,  4,  0,  0,  4, -3,  6},
                         {  8, -4,  7,  4,  4,  7, -4,  8},
                         { -8,-24, -4, -3, -3, -4,-24, -8},
                         { 99, -8,  8,  6,  6,  8, -8, 99}};
    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 8; j++) {
            if(e.grelha[i][j] == peca) 
                soma += valor_pos[i][j];
            else if(e.grelha[i][j] == (peca == VALOR_X ? VALOR_O : VALOR_X))
                soma -= valor_pos[i][j];
        }
    }
    return soma;
}
