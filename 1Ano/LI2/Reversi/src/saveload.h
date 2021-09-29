#include "estado.h"

/**
\file estado.h
\brief Funções responsáveis por guardar e carregar o jogo.
*/


/**
 * Guarda num ficheiro o estado atual do jogo.
 * @param [in] e o estado que se pretende guardar
 * @param [in] fileName o nome do ficheiro a criar
 * @return 0 se a função tiver êxito, 1 caso contrário
 */
int grava(ESTADO e, char* fileName);

/**
 * Carrega um estado a partir de um ficheiro.
 * @param [out] e o estado carregado
 * @param [in] s o nome do ficheiro a carregar
 * @return 0 se a função tiver êxito, 1 caso contrário
 */
int carrega (ESTADO *e, char *s);
