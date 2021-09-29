#include <stdio.h>
#include <stdlib.h>

/*
#define MAX 100

typedef struct stack {
	int sp;
	int valores [MAX];
	} STACK;

void initStack (STACK *s) {
	s->sp = 0;
}

int isEmptyS (STACK *s) {
	return (s->sp == 0);
}

int push (STACK *s, int x) {
	if (s->sp < MAX) {
		s->valores [s->sp++] = x;
		return 0;
	}
	else return 1;
}

int pop (STACK *s, int *x) {
	if (isEmptyS(s)) return 1;
	else *x = s->valores[s-> sp-1];
}

void printStack (STACK *s) {
	for (int i = (s->sp)-1; i>=0; i--) printf("%d\n", s->valores[i]);
}
// ??? 
int top (STACK *s, int *x) {
	if (isEmptyS(&s)) return 1;
	else {
		*x = valores[s->sp-1]
	}
}
int main () {
	STACK st;
	initStack (&st);
	printf("Stack Empty? %s\n", isEmptyS(&st)? "True":"False");
	push (&st, 3);
	push (&st, 2);
	printStack (&st);
}
*/
#define MAXG 3

typedef struct stack {
	int size; // guarda o tamanho do array valores
	int sp;
	int *valores;
} STACKG;

void initStackG (STACKG *q) {
	q -> sp = 0;
	q -> size = MAXG;
	q -> valores = malloc (sizeof (int)*MAXG);
}
int isEmptySG (STACKG *q) {
	return (q->sp == 0);
}
void push (STACKG *q,int x) {
	if (q->sp< q-> size) *(q->valores+q -> sp++) = x;
	else {
		int *ns = (int *) malloc(sizeof(int) * q->size*2);
		for (int i = 0; i< q->sp; i++) *(ns+i) = *(q->valores+i);
		*(ns + q->sp) = x;
		++q->sp;
		free(q->valores);
		q->valores = ns;
		q->size *= 2;
	}
}

int main () {
	STACKG q;
	initStackG (&q);
	printf("Stack Empty? %s\n", isEmptySG(&q)? "True":"False");
	push (&q, 2);
	push (&q, 3);
	return 0;
}