#include <stdio.h>
#include <ctype.h>

/*
	1
int minusculas (char s[]) {
	int x = 0;
	for (int i = 0; s[i] != '\0'; ++i)
	{
		if (s[i] > 'A' && s[i]<'Z') {
			x++;
			s[i] += 32;
		}
	}
	printf("%s\n", s);
	printf("%d\n", x);
	return 0;
}

int main () {
	char s[500];
	printf("Introduza uma string:\n");
	gets(s);
	minusculas (s);
}

	2
int contalinhas (char s[]) {
	int x = 1;
	for (int i = 0; s[i] != '\0'; ++i)
	{
		if (s[i] == '\n') x++;
	}
	printf("%d\n", x);
}

int main () {
	char s[500];
	printf("Introduza uma string:\n");
	gets(s);
	contalinhas (s);
	return 0;
}

	3


int contaPal (char s[]) {
	int x = 0;
	for (int i = 0; s[i] != '\0'; ++i)
	{
		if (isspace(s[i]) < isspace(s[i+1]) || (isspace(s[i]) == 0 && s[i+1] == '\0')) x++;
	}
	return x;
}

int main () {
	char s[500];
	printf("Introduza uma string:\n");
	gets(s);
	printf ("%d\n",contaPal(s));
	return 0;
}

	4
*/

int procura (char *p, char *ps[], int N) {


}

int main() {
	char s[500]; char [500] [500];
	int N; 
	printf("Introduza uma string:\n");
	gets(s);
	printf("Introduza o número de strings para colocar num array:\n");
	scanf ("%d",N);
	for (i=0; i<N; i++)
	return 0;
}