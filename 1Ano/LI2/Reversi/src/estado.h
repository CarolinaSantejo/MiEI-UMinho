//
// Created by pja on 27/02/2019.
//



#ifndef PROJ_ESTADO_H
#define PROJ_ESTADO_H



/**
\file estado.h
\brief Definição do estado i.e. tabuleiro. Representação matricial do tabuleiro.
*/


/*!
 * \brief Definição de valores possiveis no tabuleiro 
 */
typedef enum {VAZIA   ///< casa vazia
            , VALOR_X ///< casa ocupada por uma peça X
            , VALOR_O ///< casa ocupada por uma peça O
} VALOR;

/*!
 * @brief Estrutura que armazena o estado do jogo
 * 
 * Um estado contém todas as informações do jogo, em especial o tabuleiro. Assim, para guardar o jogo, basta apenas guardar o estado atual.
 */
typedef struct estado {
    VALOR peca; ///< peça do jogador que vai jogar!
    VALOR grelha[8][8]; ///< tabuleiro do jogo
    char modo; ///< tipo de jogo! 0-> manual, 1-> contra o computador
    char dif; ///< dificuldade do jogo (quando em modo automático, contra o computador), entre 1 e 3.
} ESTADO;

/*!
 * \brief Estrutura dinâmica (lista ligada) que armazena o histórico do jogo, i.e., todas as jogadas anteriores.
 * 
 * Nesta lista ligada ficam guardadas todas as jogadas realizadas desde o início do programa. Desta forma, um jogador que se arrependa de uma jogada anterior, 
 * ou que queira recomeçar o jogo a partir de uma certa parte pode percorrer esta lista para chegar a todos os estados de jogos anteriores.
 */
typedef struct estadosh {
    ESTADO e;              ///< estado armazenado na lista
    struct estadosh * prox;///< apontador para o resto da lista
} *ESTADOSH;

/*!
 * \brief Estrutura que armazena uma posição da grelha.
 * 
 * Através desta estrutura, podemos facilmente passar posições entre funções, já que podemos passar a posição em vez de passar a linha e a coluna em separado.
 * Para além disso, cada posição vem com a quantidade de peças adversárias que "come" se for aí colocada uma peça. Podemos, deste modo, avaliar uma posição de 
 * forma quase instantânea.
 */
typedef struct posicao {
    int lin;  ///< linha da posição
    int col;  ///< coluna da posição
    int dest; ///< "poder destrutivo" da posição, i.e., quantas peças rivais uma peça "come" se for colocada nessa posição
} POSICAO;

/**
Imprime no terminal o tabuleiro do jogo, com as peças no seu lugar.
@param [in] e estado onde está guardado o tabuleiro.
@param [in] nPos número de posições válidas, i.e., onde o jogador atual pode jogar.
@param [in] pos lista com as posições válidas para o jogador atual.
@param [in] dica apontador para uma posição favorável para o jogador jogar, pode ser visível ou não no tabuleiro.
 */
void printa(ESTADO e, int nPos, POSICAO pos[], POSICAO * dica);

/*!
 * Inicializa um estado, criando um tabuleiro novo para jogar do início.
 * @param [in,out] e estado que será inicializado
 * @param [in] peca peça do jogador que joga primeiro
 * @param [in] modo pode ser manual(0) ou automático(1)
 */
void newBoard(ESTADO* e, VALOR peca, char modo);

//void limpaHist(ESTADOSH* hist);

#endif //PROJ_ESTADO_H