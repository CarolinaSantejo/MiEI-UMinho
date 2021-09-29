#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct entrada{
	char *palavra;
	int ocorr;
	struct entrada *prox;
} *Palavras;

typedef Palavras Dicionario;

void initDic (Dicionario *d) {
	*d = NULL;
}

Dicionario cons (Dicionario d, char *p) {
	Palavras nd = (Palavras) malloc(sizeof(struct entrada));
	nd -> palavra = strdup(p);
	nd -> ocorr = 1;
	nd -> prox = d;
	return nd;
}


int acrescenta (Dicionario *d, char *pal) {
	Dicionario ant, act;
	if (*d == NULL || strcmp(pal,(*d)->palavra) < 0) {
		*d = cons(*d,pal);
		return 1;
	}
	ant = *d;
	act = (*d) -> prox;
	while (act != NULL && strcmp(pal,act->palavra)>0) {
		ant = act;
		ant = act -> prox;
	}
	if (ant != NULL && strcmp(ant->palavra,pal) == 0) {
		ant -> ocorr++;
		return ant -> ocorr++;
	}
	if (act != NULL && strcmp(act->palavra, pal) == 0) {
		act->ocorr++;
		return act->ocorr++;
	}
	ant->prox = cons(act,pal);
	return 1;
}

char* maisFreq (Dicionario d, int *c) {
	int r = -1;
	char* rp;
	while (d != NULL) {
		if (d->ocorr > r) {
			r = d->ocorr;
			rp = d->palavra;
		}
		d = d->prox;
	}
	*c = r;
	return rp;
}






int main (){
	Dicionario d;
	int c = 0;
	initDic(&d);
	acrescenta (&d,"nddai");
	acrescenta (&d,"a");
	acrescenta (&d,"a");
	acrescenta (&d,"a");
	acrescenta (&d,"b");
	acrescenta (&d,"b");
	printf("%s\n",maisFreq (d,&c)); 
	return 0;
}