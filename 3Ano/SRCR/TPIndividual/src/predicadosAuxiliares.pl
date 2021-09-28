

%Extensao do predicado solucoes: F, Q, S -> {V, F}

solucoes(F, Q, S) :- findall(F, Q, S).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Extensao do predicado elementosNaoComuns: L,L1,L2 ->{V,F}
%Lista mais extensa deve estar no primeiro argumento
elementosNaoComuns([],_,[]).
elementosNaoComuns([H|T],S,L):- pertence(H,S),
                                elementosNaoComuns(T,S,L).

elementosNaoComuns([H|T],S,L):- nao(pertence(H,S)),
                                elementosNaoComuns(T,S,Z),
                                L = [H|Z].

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado nao: Termo -> {V, F}

nao(T) :- T, !, fail.
nao(_).

membro(X, [X|_]).
membro(X, [_|Xs]):-
	membro(X, Xs).

inverso(Xs, Ys):-
	inverso(Xs,[], Ys).

inverso([],Xs,Xs).
inverso([X|Xs],Ys,Zs):-
	inverso(Xs,[X|Ys],Zs).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado comprimento: S, N -> {V, F}

comprimento(S, N) :- length(S, N).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado pertence: X, Lista -> {V, F}

pertence(X, [X | _]).
pertence(X, [H | T]) :- X \= H, pertence(X, T).


%----------------------------------------
% Verificar se pelo menos um elemento da primeira lista existe na segunda

pertenceLista([X],L):-pertence(X,L).
pertenceLista([H|_],L):- pertence(H,L).
pertenceLista([_|T],L):- pertenceLista(T,L).

minimo([(P,X)],(P,X)).
minimo([(_,X)|L],(Py,Y)):- minimo(L,(Py,Y)), X>Y.
minimo([(Px,X)|L],(Px,X)):- minimo(L,(_,Y)), X=<Y.

list_sum([], 0).
list_sum([Head | Tail], TotalSum) :-
    list_sum(Tail, Sum1),
    TotalSum = Head + Sum1.

insertAtEnd(X,[ ],[X]).
insertAtEnd(X,[H|T],[H|Z]) :- insertAtEnd(X,T,Z).


seleciona(E,[E|Xs],Xs).
seleciona(E,[X|Xs],[X|Ys]) :- seleciona(E,Xs,Ys).

