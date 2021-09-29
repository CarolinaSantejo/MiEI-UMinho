#include "estado.h"

#ifndef PROJ_FUNCSJOGO_H
#define PROJ_FUNCSJOGO_H

/**
\file funcsjogo.h
\brief Funções relativas ao funcionamento do jogo.
*/


/**
 * Calcula a pontuação de um jogador, isto é, quantas peças tem no tabuleiro.
 * @param [in] e o estado que contém o tabuleiro
 * @param [in] peca a peça do jogador cuja pontuação se quer calcular
 * @return a pontuação do dado jogador
 */
int score(ESTADO e, VALOR peca);

/**
 * Determina as posições onde o jogador pode jogar no momento.
 * @param [in] e o estado que contém o tabuleiro
 * @param [in] peca a peca do jogador cujas posições jogáveis se quer saber
 * @param [out] pos lista com as posições onde o jogador pode jogar
 * @return o número de posições onde o jogador pode jogar, por outras palavras, o comprimento da lista pos
 */
int jogadasPossiveis(ESTADO e, VALOR peca, POSICAO pos[]);
int jogadaH(ESTADO e, VALOR peca, POSICAO* p);
int jogadaV(ESTADO e, VALOR peca, POSICAO* p);
int jogadaD(ESTADO e, VALOR peca, POSICAO* p);

/**
 * Efetua uma jogada no tabuleiro.
 * @param [in,out] e o estado onde se pretende jogar
 * @param [in] c a coluna da jogada
 * @param [in] l a linha da jogada
 */
void jogar (ESTADO* e, int c,int l);

/**
 * Verifica se o jogo acabou.
 * @param [in] e o estado que contém o tabuleiro
 * @return 1 se o jogo acabou, 0 caso contrário
 */
int gameOver(ESTADO e);

/**
 * Verifica se uma dada posição está próxima a um canto.
 * @param [in] pos a posição dada
 * @return 1 se a posição estiver próxima de um canto, 0 caso contrário
 */
int proxCanto(POSICAO pos);

#endif // PROJ_FUNCSJOGO_H