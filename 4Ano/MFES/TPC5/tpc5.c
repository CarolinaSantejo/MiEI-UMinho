#include "string.h"
#define MAXVERTICES 10


typedef int Graph[MAXVERTICES][MAXVERTICES];

/*@ requires \valid_read(A+(0..(n*n-1)));
  @ requires \valid(R+(0..(n*n-1)));
  @ requires \separated(A+(0..n*n-1), R+(0..n*n-1));
  @ requires 0<n<=MAXVERTICES;
  @
  @ assigns R[0..n-1][0..n-1];
  @
  @ ensures \forall integer k, l;
  @          0 <= k < n && 0 <= l < n ==>
  @          \at(A[k][l],Here) == \at(A[k][l],Old);
  @*/
void WarshallTC (Graph A, Graph R, int n) {
    int i, j, k;
    /*@ 
      @ loop invariant 0<=i<=n-1;
      @ loop assigns i,j,R[0..n-1][0..n-1];
      @ loop invariant \forall integer i,j;
      @                 0<=i<n && 0<=j<n ==>
      @                 \at(A[i][j],Pre) == \at(A[i][j],Here);
      @ loop invariant \forall integer k,l;
      @                 0<=k<i && 0<=l<n ==>
      @                 (R[k][l] == A[k][l]);
      @ loop variant n-1;
    */
    for (i=0 ; i<n ; i++)
      /*@ 
        @ loop invariant 0<=i<=n-1;
        @ loop assigns j,R[0..n-1][0..n-1];
        @ loop invariant \forall integer k;
        @                 0<=k<j ==>
        @                 (R[i][k] == A[i][k]);
        @ loop variant n-i;
      */
        for (j=0 ; j<n ; j++)
            R[i][j] = A[i][j];

    /*@ assert \forall integer i, j;
      @      0<=i<n && 0<=j<n ==> (A[i][j] == R[i][j]);*/
    
    /*@ loop invariant 0<=k<=n-1;
      @
      @ loop assigns k,i,j,R[0..n-1][0..n-1];
      @ loop invariant \forall integer i,j;0<=i<n && 0<=j<n ==> \at(A[i][j],Pre) == \at(A[i][j],Here);
      @ 
      @ loop invariant \forall integer i,j,t;
      @                 0<=i<n && 0<=j<n && t==k ==>
      @                 ((\at(R[i][j],Here) == 1) ==> (\at(R[i][j],Pre)==1) || ((\at(R[i][t],Here)==1) && (\at(R[t][j],Here)==1)));
      @ loop variant n-k;
    */
    for (k=0 ; k<n; k++)
      /*@
        @ loop invariant 0 <= i < n;
        @ loop assigns R[0..n-1][0..n-1],i,j;         
        @ loop variant n-i;
        @*/
        for (i=0 ; i<n ; i++)
          /*@
            @ loop invariant 0 <= j < n-1;
            @ loop assigns R[0..n-1][0..n-1], j;
            @ loop variant n-j;
            @*/
            for (j=0 ; j<n ; j++)
                if (R[i][k] && R[k][j])
                    R[i][j] = 1;
}