#include "estado.h"

/** \mainpage Créditos
 * 
 * Projeto realizado por Ana Filipa Pereira, Carolina Santejo e Sofia Santos, alunas do 1º ano do MIEI na Universidade
 * do Minho, no âmbito da cadeira de Laboratórios de Informática II.
 * 
 */

/**
\file bot.h
\brief Definição do bot e das função relativas a este.
*/


/**
 * \brief Estrutura que armazena as características de um 'bot', i.e., um jogador controlado pelo computador.
 * 
 * Um bot é caracterizado pela sua dificuldade e pela peça com que joga. A partir destes dois dados, podemos criar um jogador autónomo, que usa algoritmos para calcular onde deve jogar.
 */
typedef struct bot {
    char dif; ///< a dificuldade do bot
    VALOR peca; ///< a peça com que o bot joga
} BOT;


/**
 * Calcula a posição onde o bot deve jogar, com base na sua dificuldade .
 * @param [in] bot bot que irá jogar
 * @param [in] e estado que contém o tabuleiro onde o bot irá jogar
 * @return posição onde o bot deve jogar
 */
POSICAO jogadaBot(BOT* bot, ESTADO* e);

/**
 * Calcula o valor de um tabuleiro, isto é, o quão bom esse tabuleiro é para determinado jogador, com base na colocação das peças. 
 * @param [in] e estado que contém o tabuleiro
 * @param [in] peca peça do jogador para o qual vamos calcular o valor
 * @return o valor do tabuleiro para o jogador dado
 */
int valor(ESTADO e, VALOR peca);

/**
 * Algoritmo que determina o valor de uma jogada com base nas possíveis jogadas futuras (descrição mais detalhada no relatório).
 * @param node o estado cujo valor queremos calcular.
 * @param depth o nível da recursão, quando maior, mais tempo a função vai demorar a ser executada. Se for 0, a função apenas calcula o valor atual do estado (node).
 * @param alpha valor auxiliar para tornar a função mais eficiente
 * @param beta valor auxiliar para tornar a função mais eficiente
 * @param maximizingPlayer a peça do jogador que "chamou" esta função
 * @return o valor do estado node, para ser comparado com os outros nodos
 */
int minimax(ESTADO node, int depth, int alpha, int beta, VALOR maximizingPlayer);

/**
 * Calcula o máximo de dois números.
 */
int max(int a, int b);
/**
 * Calcula o mínimo de dois números.
 */
int min(int a, int b);