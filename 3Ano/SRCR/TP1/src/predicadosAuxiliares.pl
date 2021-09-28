%Predicados Auxiliares
%--------------------------------- - - - - - - - - - -  -  -  -  -   -
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
%Extensao do predicado dataValida: D, M, A -> {V, F}
%Verificamos para fevereiro se o ano é bisexto

dataValida(D, M, A) :-
    A >= 1900,
    pertence(M, [1,3,5,7,8,10,12]),
    D >= 1,
    D =< 31.
dataValida(D, M, A) :-
    A >= 1900,
    pertence(M, [4,6,9,11]),
    D >= 1,
    D =< 30.
dataValida(D, 2, A) :-
    A >= 1900,
    A mod 4 =\= 0,
    D >= 1,
    D =< 28.
dataValida(D, 2, A) :-
        A >= 1900,
    A mod 4 =:= 0,
    D >= 1,
    D =< 29.
dataValida(dataValida(D, M, A)) :- dataValida(D, M, A).




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Extensão do predicado nomeUtentes: L1,L2 -> {V,F}

nomeUtentes([],[]).
nomeUtentes([H|T],L):-solucoes((H,Nome),utente(H,_,Nome,_,_,_,_,_,_,_),[S|_]),
                  nomeUtentes(T,Z),
               L = [S|Z].


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
%Extensão do predicado nomeCS: L1,L2 -> {V,F}

nomeCS([],[]).
nomeCS([H|T],L):-solucoes((H,Nome),centro_saude(H,Nome,_,_,_),[S|_]),
             nomeCS(T,Z),
         L = [S|Z].



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado nao: Termo -> {V, F}

nao(T) :- T, !, fail.
nao(_).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado comprimento: S, N -> {V, F}

comprimento(S, N) :- length(S, N).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado pertence: X, Lista -> {V, F}

pertence(X, [X | _]).
pertence(X, [H | T]) :- X \= H, pertence(X, T).


%------------------------
%Extensao do predicado listaAtoms L ->{V,F}
%Devolve true se a lista for toda de atoms

listaAtoms([]).
listaAtoms([H|T]):- atom(H),
                    listaAtoms(T).





