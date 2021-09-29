#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include "estado.h"
#include "funcsjogo.h"
#include "saveload.h"
#include "bot.h"

static char nometorneio[50] = {0};

void torneio(char *);

int main() {
    ESTADO e = {0};
    ESTADOSH historico = malloc(sizeof(struct estadosh));
    historico = NULL;

    BOT bot = {0};

    int quit = 0;
    int inGame = 0;
    char linha[100];
    char c1;
    int x, y, jp = 0, jogInv = 0;
    int ajudaPos = 0;
    POSICAO posDica = {0};
    POSICAO * dica = NULL;
    POSICAO jogadasP[60] = {0};

    while(!quit) {
        jp = jogadasPossiveis(e,e.peca,jogadasP);
        if(jp == 0 && inGame) {
            if(gameOver(e)) {
                printf("\nNão há mais jogadas possíveis! GAME OVER\n");
                int sx = score(e,VALOR_X);
                int so = score(e,VALOR_O);
                if(sx==so) printf("EMPATE!\n");
                else printf("VENCEDOR: %c\n",sx > so ? 'X' : 'O');
                inGame = 0;
            }
            else {
                printf("\nJogador %c não pode jogar, tem de passar a vez.\n\n",e.peca == VALOR_X ? 'X' : 'O');
                e.peca = e.peca == VALOR_O ? VALOR_X : VALOR_O;
            }
        }
        else {
            if(!inGame) printf("> ");
            else printf("%c> ",e.peca == VALOR_X ? 'X' : 'O');
            fgets(linha,50,stdin);
            strtok(linha,"\n");
            switch(toupper(linha[0])) {
                case 'N':
                    e.modo = 0;
                    sscanf(linha + 1," %c",&c1);
                    newBoard(&e,toupper(c1) == 'X' ? VALOR_X : VALOR_O,0);
                    inGame = 1;
                    //limpaHist(&historico);
                    break;
                case 'J':
                    sscanf(linha + 1,"%d %d",&y,&x);
                    y--;
                    x--;
                    jogInv = 1;
                    ESTADOSH new = malloc(sizeof(struct estadosh));
                    new->e = e;
                    new->prox = historico;
                    historico = new;
                    for(int i = 0; i < jp; i++) {
                        if(jogadasP[i].lin == y && jogadasP[i].col == x) {
                            jogar(&e,y,x);
                            e.peca = e.peca == VALOR_O ? VALOR_X : VALOR_O;
                            jogInv = 0;
                            break;
                        }
                    }
                    if(jogInv) printf("\nJogada inválida! Tente novamente.\n\n");
                    break;
                case 'S':
                    ajudaPos = 1;
                    break;
                case 'E': {
                    if(grava(e,strcat(linha + 2,".txt"))) printf("\nErro ao guardar ficheiro.\n\n");
                    else printf("\nFicheiro guardado com sucesso!\n\n");
                    break; }
                case 'L':
                    if(!carrega(&e,strcat(linha + 2,".txt"))) {
                        if(e.modo == 1) {
                            bot.peca = e.peca == VALOR_X ? VALOR_O : VALOR_X;
                            bot.dif = e.dif;
                        }
                        printf("\nJogo carregado com sucesso!\n\n");
                        inGame = 1;
                    }
                    else printf("\nErro ao carregar jogo, ficheiro não encontrado.\n\n");
                    //limpaHist(&historico);
                    break;
                case 'H': {
                    BOT botTemp = { .dif = 2, .peca = e.peca};
                    posDica = jogadaBot(&botTemp,&e);
                    dica = &posDica;
                    break; }
                case 'U':
                    if(historico) {
                        e = historico->e;
                        ESTADOSH temp = historico;
                        historico = historico->prox;
                        free(temp);
                    }
                    else printf("\nImpossível anular jogada.\n\n");
                    inGame = 1;
                    break;
                case 'C':
                    if(!isalpha(linha[2]) && nometorneio[0]) {
                        strcpy(linha + 2, nometorneio);
                    }
                    else {
                        strcpy(nometorneio,linha + 2);
                    }
                    torneio(linha + 2);
                    inGame = 0;
                    break;
                case 'A':
                    sscanf(linha + 1," %c %d",&c1,&x);
                    bot.peca = toupper(c1) == 'X' ? VALOR_X : VALOR_O;
                    newBoard(&e,VALOR_X,1);
                    while(x < 1 || x > 3) {
                        printf("\n\nDificuldade inválida - introduza um valor entre 1 e 3!\n\n> ");
                        scanf("%d",&x);
                    }
                    //limpaHist(&historico);
                    e.dif = bot.dif = x;
                    inGame = 1;
                    break;
                case 'Q':
                    quit = 1;
                    break;

            }
            if(inGame && !quit)
                printa(e, ajudaPos ? jp : 0, jogadasP, dica);
        }
        if(e.modo == 1 && e.peca == bot.peca && !gameOver(e)) {
            jp = jogadasPossiveis(e,e.peca,jogadasP);
            if(jp == 0) {
                printf("\nBot não pode jogar, tem de passar a vez.\n\n");
                e.peca = e.peca == VALOR_O ? VALOR_X : VALOR_O;
            }
            else {
                POSICAO jogada = jogadaBot(&bot,&e);
                jogar(&e,jogada.lin,jogada.col);
                printf("Jogada do bot:\n");
                printa(e, ajudaPos ? jp : 0, jogadasP, dica);
                e.peca = e.peca == VALOR_O ? VALOR_X : VALOR_O;
            }
        }
        jogInv = ajudaPos = 0;
        dica = NULL;
    }
    return 0;
}

void torneio(char *filepath) {
    POSICAO posJ[60];
    ESTADO e = {0};
    strcat(filepath,".txt");
    int erro = carrega(&e,filepath);
    if(erro) {
        printf("Novo campeonato\n");
        newBoard(&e,VALOR_X,1);
        e.dif = 3;
        if(!grava(e, filepath)) {
            printf("\nFicheiro guardado com sucesso!\n\n");
            printa(e,0,NULL,NULL);
        }
        else
            printf("Erro ao criar o ficheiro %s.txt. Certifique-se que tem permissões.", filepath);
    }
    else {
        printf("Jogada para o campeonato: tabuleiro recebido:\n");
        printa(e,0,NULL,NULL);
        BOT botC = { .dif = 3, .peca = e.peca };
        int jp = jogadasPossiveis(e,e.peca,posJ);
        if(jp) {
            POSICAO jogada = jogadaBot(&botC, &e);
            jogar(&e, jogada.lin, jogada.col);
        }
        e.peca = e.peca == VALOR_O ? VALOR_X : VALOR_O;
        printf("Jogada para o campeonato: tabuleiro enviado:\n");
        printa(e,0,NULL,NULL);
        if(gameOver(e)) {
            if(score(e,VALOR_X) > score(e,VALOR_O))
                grava(e,strcat(filepath,".gX"));
            else if(score(e,VALOR_X) < score(e,VALOR_O))
                grava(e,strcat(filepath,".gO"));
            else grava(e,strcat(filepath,".g-"));
        }
        else grava(e,filepath);
    }
}