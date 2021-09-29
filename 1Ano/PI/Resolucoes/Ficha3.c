#include <stdio.h>

/*	1

(b)
int main () {
	int i, j, *a, *b;
	i=3; j=5;
	a = &i; b = &j; // a = endereço de i
	i++;
	j = i + *b;	// *b <- valor no endereço b
	b = a;
	j = j + *b;
	printf ("%d\n", j);
	return 0;
}

	2

void swapM (int *x, int *y) {
	int aux;
	aux = *x;
	*x = *y;
	*y = aux;
}

int main () {
	int x = 3, y = 5;
	swapM (&x, &y);
	printf ("%d %d\n", x, y);
	return 0;
}
	3
void swap (int v[], int i, int j) {
	int aux;
	aux = v[i];
	v[i] = v[j];
	v[j] = aux;

}


int main () {
	int v[5000]; int i,j,c;
	printf("Indique o comprimento do conjunto:\n");
	scanf ("%d", &c);
	printf("Indique um conjunto de números inteiros:\n");
	for (int a = 0; a < c ; a++) scanf ("%d", &v[a]);
	printf("Indique as posições a trocar:\n");
	scanf ("%d %d", &i,&j);
	swap (v, i, j);
	printf("O novo conjunto é:");
	for (int a = 0; a < c ; a++) printf("%d", v[a]);
	return 0;
} 
	4
int soma (int v[], int N) {
	int soma = 0;
	for (int i = 0; i<N; i++) soma += v [i];
	return soma; 


}

int main () {
	int n;
	int v [5000];
	printf("Indique o comprimento do conjunto:\n");
	scanf ("%d", &n);
	printf("Indique um conjunto de números inteiros:\n");
	for (int a = 0; a < n ; a++) scanf ("%d", &v[a]);
	printf("A soma de todos os números é: %d\n", soma (v,n));
	return 0;
	}

	5
int maximum (int v[], int N, int *m) {
	for (int i = 0; i<N; i++) {
		if (v [i] > *m) *m = v[i];
	}
	return (*m);
}

int main () {
	int v [5000];
	int N;
	int m;
	printf("Indique o comprimento do conjunto:\n");
	scanf ("%d", &N);
	printf("Indique um conjunto de números inteiros:\n");
	for (int a = 0; a < N ; a++) scanf ("%d", &v[a]);
	m = v [0];
	printf("O máximo é: %d\n", maximum (v,N,&m));
	return 0;
}

	6
*/
void quadrados (int q[], int N) {
	for (int i = 0; i<N; i++) {
		q[i] = (i+1)*(i+1);
	}
	printf("Os quadrados são: ");
	for (int i = 0; i<N; i++) printf("%d ", q[i]);
}

int main () {
	int N;
	printf("Indique o comprimento do conjunto:\n");
	scanf ("%d", &N);
	int q[N];
	quadrados (q,N);
	return 0;

}
