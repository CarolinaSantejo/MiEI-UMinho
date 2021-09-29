#include <stdio.h>

/*
   1
1
int main () {
   int x , y ;
   x = 3;
   y = x +1;
   x = x*y; y = x + y;
   printf ( "%d %d \n " , x , y ) ;
   return 0;
}

2
int main () {
    int x , y ;
    x = 0; 
    printf ( "%d %d \n " , x , y );
    return 0;
}

3
int main () {
    char a , b , c ;
    a = 'A'; b = ' '; c = '0';
    printf ( "%c %d \n " , a , a ) ;
    a = a +1; c = c +2;
    printf ( "%c %d %c %d \n " , a , a , c , c ) ;
    c = a + b;
    printf ( "%c %d \n " , c , c ) ;
    return 0;
}

5
int main () {
    int x , y ;
    x = 100; y = 28;
    x += y ; // x=x+y
    y -= x ; // y=y-x
    printf ( "%d %d \n " , x++ , ++y ) ;
    printf ( "%d %d \n " , x , y ) ;
    return 0;
}

  2
1
(d)
int main () {
    int i ;
	for ( i =0; (i <20) ; i ++)
	if ( i%2 == 0) putchar ( '_') ;
	else putchar ('#') ;
	return 0;
}
(e)
int main () {
	char i , j ;
	for ( i = 'a'; ( i != 'h') ; i ++) {
		for ( j = i ; ( j != 'h') ; j ++)
			putchar ( j ) ;
		putchar ( '\n' ) ; }
	return 0;
}

2
int main () {
	int l,b,a ;
	scanf ("%d", &l);
	b=l;
	a=l;
	while (a > 0) {
		while (b > 0) {
			putchar ('#');
			b=b-1; } 
        putchar ('\n');
        a = a-1;
		b = l;
		}
	return 0;
}

3
int main () {
	int l,b,a ;
	scanf ("%d", &l);
	b=l;
	a=l;
	while (a > 0) {
	if (a%2 != 0) { 
		while (b > 0) { if (b%2 != 0) { putchar ('#'); b=b-1; } 
						else { putchar ('_'); b=b-1; }
		} }
	else {
		while (b > 0) { if (b%2 == 0) { putchar ('#'); b=b-1; } 
							else { putchar ('_'); b=b-1; }
			} }  
        	putchar ('\n');
        	a = a-1;
			b = l;
			}
	return 0;
}

4
*/


void triangulo ( int n );

int main () {
	triangulo (5) ;
	return 0;
}