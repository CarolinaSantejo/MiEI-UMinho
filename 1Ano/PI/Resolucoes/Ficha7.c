#include <stdio.h>
#include <stdlib.h>


typedef struct slist *LInt;

typedef struct slist {
	int valor;
	LInt prox;
} Nodo;

LInt exa () {
	LInt l, n1, n2, n3;
	n1 = (LInt) malloc(sizeof(Nodo));
	n1->valor = 10;
	n2 = (LInt) malloc(sizeof(Nodo));
	n2->valor = 5;
	n3 = (LInt) malloc(sizeof(Nodo));
	n3->valor = 15;
	n1->prox = n2;
	n2->prox = n3;
	n3->prox = NULL;
	l = n1;
	return l;
}

LInt cons (LInt l,int x) {
	LInt n = (LInt) malloc(sizeof(Nodo));
	n->valor = x;
	n->prox = l;
	return n;
}

void printL (LInt l) {
	printf("[");
	while (l != NULL) {
		printf("%d",l->valor);
		if (l->prox != NULL) printf(",");
		l = l->prox;
	}
	printf("]\n");
}

int isEmpty (LInt l) {
	return (l == NULL);
}


LInt tail(LInt l) {
	LInt aux = l;
	if(!isEmpty(l)) { // (!isEmpty(l)) -> Enquanto lista nao é vazia
		aux = l->prox;
		free(l);
	}
		return aux;
}

int length (LInt l) {
	int n = 0;
	while (!isEmpty(l)) {
		n++;
		l = l->prox;
	}
	return n;
}

LInt addAll (LInt l, int a[],int n) {
	for (int i = n-1; i>=0; i--) {
		l = cons(l,a[i]);
	}
	return l;

}

int last(LInt l) {
	while (l->prox != NULL) l = l->prox;
	return (l->valor);
}

void freelista (LInt l) {
	free (l);
}

LInt take (LInt l,int n) {
	LInt h = l;
	while (n>1 && h != NULL) {
		h = h->prox;
		n--;
	}
	if (h != NULL){
		freelista(h->prox);
		h->prox = NULL;
	}
	return l;
}
/*
LInt reverse (LInt l) {
	LInt nl = malloc(sizeof(Nodo));
	while (!isEmpty(l)) {
		nl = cons(nl,l->valor);
		l = l->prox;
	}

	return nl;
}
*/
LInt reverse (LInt l) {
	LInt ant, tmp;
	if(isEmpty(l)) return l;
	ant = l;
	l = l->prox;
	ant->prox = NULL;
	while (l!=NULL) {
		tmp = l->prox;
		l->prox = ant;
		ant = l;
		l = tmp;
	}
	return ant;
}

LInt insOrd (LInt l, int x) {
	LInt ant, act;
	if (isEmpty(l) || x<l->valor) return cons(l,x);
	ant = l;
	act = l->prox;
	while (act != NULL && x > act->valor) {
		ant = act;
		act = act -> prox;
	}
	ant -> prox = cons(act,x);
	return l;
}


int main () {
	LInt l;
	int a[3] = {1,2,3};
	l = exa();
	l = cons(l,3);
	l = tail(l);
	l = addAll(l,a,3);
	l = take (l,6);
	l = reverse(l);
	printL(l);	
	printf("Length = %d\nUltimo: %d\n",length(l), last(l));
	return 0;
}