#include <stdio.h>

/*
	1

float multInt (int n, float m) {
	float r = 0;

	for (int i = 0; i<n ; i++) 
			r += m;
	return r;

}
	2
float multInt2 (int n, float m) {
	float r = 0;
	while (n > 0) {
		if (n%2 != 0) r += m;
		n /= 2;
		m *= 2;
	}
	return r;

}

int main () {
	int n; float m;
	printf("Indique o número a multiplicar\n");
	scanf ("%f",&m);
	printf("Indique o número de vezes a multiplicar\n");
	scanf ("%d",&n);
	printf("Resultado = %f \n", multInt2 (n,m));
	return 0;

}

	3
int mdc (int a, int b) {
	int r = 0; 
	int i;
	if (a>b) (mdc (b,a));
	for (i=a ; i>0 && (a%i != 0 || b%i != 0); i--);
	r = i;  
	return r;
}

*/
int mdc2 (int a, int b) {
	if (a>b) mdc2 (a-b,b);
	if (a<b) mdc2 (a,b-a);
	return a;
}



int main () {
	int a, b;
	printf("Indique um número\n");
	scanf ("%d", &a);
	printf("Indique um número\n");
	scanf ("%d", &b);
	printf("O mdc é: %d \n", mdc2 (a,b));
	return 0;

}






