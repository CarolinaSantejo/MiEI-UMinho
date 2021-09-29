#include "estado.h"
#include "funcsjogo.h"
#include <stdio.h>

int score(ESTADO e, VALOR peca) {
    int n = 0;
    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 8; j++) {
            if(e.grelha[i][j] == peca) n++;
        }
    }
    return n;
}

int jogadasPossiveis(ESTADO e, VALOR peca, POSICAO pos[]) {
    int n = 0;
    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 8; j++) {
            if(e.grelha[i][j] != VAZIA) continue;
            else {
                POSICAO p = { .lin = i, .col = j, .dest = 0};
                int h = jogadaH(e, peca, &p);
                int v = jogadaV(e, peca, &p);
                int d = jogadaD(e, peca, &p);
                if(h | v | d) pos[n++] = p;
            }
        }
    }
    return n;
}

int jogadaH(ESTADO e, VALOR peca, POSICAO* p) {
    VALOR pecaRival = peca == VALOR_X ? VALOR_O : VALOR_X;
    int pecasPraDestruir = 0;
    int valida = 0;
    for(int i = p->col + 1; i < 8; i++) {
        if(e.grelha[p->lin][i] == VAZIA) break;
        else if(e.grelha[p->lin][i] == pecaRival) pecasPraDestruir++;
        else if(e.grelha[p->lin][i] == peca) {
            if(pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }
    }
    pecasPraDestruir = 0;
    for(int i = p->col - 1; i >= 0; i--) {
        if (e.grelha[p->lin][i] == VAZIA) break;
        else if (e.grelha[p->lin][i] == pecaRival) pecasPraDestruir++;
        else if (e.grelha[p->lin][i] == peca) {
            if (pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }
    }
    return valida;
}

int jogadaV(ESTADO e, VALOR peca, POSICAO* p) {
    VALOR pecaRival = peca == VALOR_X ? VALOR_O : VALOR_X;
    int pecasPraDestruir = 0;
    int valida = 0;
    for(int i = p->lin + 1; i < 8; i++) {
        if(e.grelha[i][p->col] == VAZIA) break;
        else if(e.grelha[i][p->col] == pecaRival) pecasPraDestruir++;
        else if(e.grelha[i][p->col] == peca) {
            if(pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }    
    }
    pecasPraDestruir = 0;
    for(int i = p->lin - 1; i >= 0; i--) {
        if(e.grelha[i][p->col] == VAZIA) break;
        else if(e.grelha[i][p->col] == pecaRival) pecasPraDestruir++;
        else if(e.grelha[i][p->col] == peca) {
            if(pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }  
    }
    return valida;
}

int jogadaD(ESTADO e, VALOR peca, POSICAO* p) {
    VALOR pecaRival = peca == VALOR_X ? VALOR_O : VALOR_X;
    int pecasPraDestruir = 0;
    int valida = 0;
    int i = p->lin + 1, j = p->col + 1;
    while(i < 8 && j < 8) {
        if(e.grelha[i][j] == VAZIA) break;
        else if(e.grelha[i][j] == pecaRival) pecasPraDestruir++;
        else if(e.grelha[i][j] == peca) {
            if(pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }
        i++;
        j++;
    }
    pecasPraDestruir = 0;
    i = p->lin - 1, j = p->col - 1;
    while(i >= 0 && j >= 0) {
        if(e.grelha[i][j] == VAZIA) break;
        else if(e.grelha[i][j] == pecaRival) pecasPraDestruir++;
        else if(e.grelha[i][j] == peca) {
            if(pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }
        i--;
        j--;
    }
    pecasPraDestruir = 0;
    i = p->lin + 1, j = p->col - 1;
    while(i < 8 && j >= 0) {
        if(e.grelha[i][j] == VAZIA) break;
        else if(e.grelha[i][j] == pecaRival) pecasPraDestruir++;
        else if(e.grelha[i][j] == peca) {
            if(pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }
        i++;
        j--;
    }
    pecasPraDestruir = 0;
    i = p->lin - 1, j = p->col + 1;
    while(i >= 0 && j < 8) {
        if(e.grelha[i][j] == VAZIA) break;
        else if(e.grelha[i][j] == pecaRival) pecasPraDestruir++;
        else if(e.grelha[i][j] == peca) {
            if(pecasPraDestruir) {
                p->dest += pecasPraDestruir;
                valida = 1;
            }
            else break;
        }
        i--;
        j++;
    }
    return valida;
}

void altera (ESTADO* e, POSICAO peca, POSICAO p1) {
    int j = 0;
    int i = 0;
    VALOR PECA = e->peca;
    if (peca.lin == p1.lin) {
        if (peca.col > p1.col) {
            for (i = p1.col+1; i < peca.col; i++) e->grelha[peca.lin][i] = PECA;
        }
        else {
            for (i = peca.col+1; i < p1.col; i++) e->grelha[peca.lin][i] = PECA;
        }
    }
    else if (peca.col == p1.col) {
        if (peca.lin > p1.lin) {
            for (i = p1.lin+1; i < peca.lin; i++) e->grelha[i][peca.col] = PECA;
        }
        else {
            for (i = peca.lin+1; i < p1.lin; i++) e->grelha[i][peca.col] = PECA;
        }
    }
    else if (peca.lin < p1.lin) {
        if (peca.col < p1.col) {
            i = peca.lin + 1;
            j = peca.col + 1;
            while(i < p1.lin && j < p1.col) e->grelha[i++][j++] = PECA;
        }
        else {
            i = peca.lin + 1;
            j = peca.col - 1;
            while(i < p1.lin && j > p1.col) e->grelha[i++][j--] = PECA;
        }
    }
    else {
        if (peca.col < p1.col) {
            i = peca.lin - 1;
            j = peca.col + 1;
            while(i > p1.lin && j < p1.col) e->grelha[i--][j++] = PECA;
        }
        else {
            i = peca.lin - 1;
            j = peca.col - 1;
            while(i > p1.lin && j > p1.col) e->grelha[i--][j--] = PECA;
        }
    }
}

void jogar (ESTADO* e, int l, int c) {
    POSICAO p1 = {0} , p = { .lin = l, .col = c, .dest = 0};
    e->grelha[l][c] = e->peca;
    VALOR PECA = e->peca;
    int i, j;
    for (i = c - 1; i >= 0; i--) {
        if (e->grelha[l][i] == PECA) {
            p1.lin = l;
            p1.col = i;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[l][i] == VAZIA) break;
    }
    for (i = c + 1; i < 8; i++) {
        if (e->grelha[l][i] == PECA) {
            p1.lin = l;
            p1.col = i;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[l][i] == VAZIA) break;
    }
    for (i = l - 1 ; i >= 0; i--) {
        if (e->grelha[i][c] == PECA) {
            p1.lin = i;
            p1.col = c;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[i][c] == VAZIA) break;
    }
    for (i = l + 1; i < 8; i++) {
        if (e->grelha[i][c] == PECA) {
            p1.lin = i;
            p1.col = c;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[i][c] == VAZIA) break;
    }
    i = l - 1;
    j = c - 1;
    while(i >= 0 && j >= 0) {
        if (e->grelha[i][j] == PECA) {
            p1.lin = i;
            p1.col = j;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[i][j] == VAZIA) break;
        i--;
        j--;
    }
    i = l - 1;
    j = c + 1;
    while(i >= 0 && j < 8) {
        if (e->grelha[i][j] == PECA) {
            p1.lin = i;
            p1.col = j;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[i][j] == VAZIA) break;
        i--;
        j++;
    }
    i = l + 1;
    j = c - 1;
    while(i < 8 && j >= 0) {
        if (e->grelha[i][j] == PECA) {
            p1.lin = i;
            p1.col = j;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[i][j] == VAZIA) break;
        i++;
        j--;
    }
    i = l + 1;
    j = c + 1;
    while(i < 8 && j < 8) {
        if (e->grelha[i][j] == PECA) {
            p1.lin = i;
            p1.col = j;
            altera (e,p,p1);
            break;
        }
        if (e->grelha[i][j] == VAZIA) break;
        i++;
        j++;
    }
}

int gameOver(ESTADO e) {
    POSICAO temp[60] = {0};
    if(jogadasPossiveis(e,VALOR_X,temp) == 0 && jogadasPossiveis(e,VALOR_O,temp) == 0) return 1;
    else return 0; 
}

int proxCanto(POSICAO pos) {
    return (pos.lin < 2 && pos.col < 2 && (pos.lin != 0 || pos.col != 0)) ||
           (pos.lin < 2 && pos.col > 5 && (pos.lin != 0 || pos.col != 0)) ||
           (pos.lin > 5 && pos.col < 2 && (pos.lin != 0 || pos.col != 0)) ||
           (pos.lin > 5 && pos.col > 5 && (pos.lin != 0 || pos.col != 0));
}