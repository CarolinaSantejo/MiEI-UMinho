%:- consult('predicadosAuxiliares.pl').
%:- consult('baseConhecimento.pl').
%:- consult('main.pl').

% ------- Pesquisa em profundidade

resolve_pp_c(Nodo,[Nodo|Caminho],C) :-
    profundidadePrimeiro(Nodo,[Nodo],Caminho,C).

profundidadePrimeiro(Nodo,_,[],0):-garagem(Nodo,_,_).

profundidadePrimeiro(Nodo,Historico,[ProxNodo|Caminho],C):-
            adjacente(Nodo,ProxNodo,C1),
            nao(membro(ProxNodo,Historico)),
            profundidadePrimeiro(ProxNodo,[ProxNodo|Historico],Caminho,C2),
            C is C1+C2.

% ------- Pesquisa em profundidade limitada

resolve_pp_c_Limitada(Nodo,[Nodo|Caminho],C,Depth) :-
    profundidadePrimeiroLimitada(Nodo,[Nodo],Caminho,C,Depth).

profundidadePrimeiroLimitada(Nodo,_,[],0,_):-garagem(Nodo,_,_).

profundidadePrimeiroLimitada(Nodo,Historico,[ProxNodo|Caminho],C,Depth):-
            Depth>0,
            adjacente(Nodo,ProxNodo,C1),
            nao(membro(ProxNodo,Historico)),
            profundidadePrimeiroLimitada(ProxNodo,[ProxNodo|Historico],Caminho,C2,Depth-1),
            C is C1+C2.

% ------- Pesquisa em largura

bfs(Orig,Dest,Cam):-bfs2(Dest,[[Orig]],Cam). %condicao final: destino = nó à cabeça do caminho actual
bfs2(Dest,[[Dest|T]|_],Cam):- %caminho actual está invertido 
                              reverse([Dest|T],Cam).
bfs2(Dest,[LA|Outros],Cam):- LA=[Act|_],
                             % calcular todos os nós adjacentes não visitados e
                             % gerar um caminho novo c/ cada nó e caminho actual
                             findall([X|LA], (Dest\==Act,adjacente(Act,X,_),nao(membro(X,LA))), Novos),
                             %novos caminhos são colocados no final da lista p/ posterior exploração
                             append(Outros,Novos,Todos),
                             %chamada recursiva
                             bfs2(Dest,Todos,Cam).


goal(2).
estima(Nodo,C) :- goal(Nodo2),
                adjacente(Nodo,Nodo2,C).
                

% ------- Pesquisa gulosa
% resolve_gulosa(s,C).

resolve_gulosa(Nodo,Caminho/Custo) :- 
    estima(Nodo,Estima),
    agulosa([[Nodo]/0/Estima],InvCaminho/Custo/_),
    inverso(InvCaminho,Caminho).

agulosa(Caminhos, Caminho) :- 
    obtem_melhor_g(Caminhos,Caminho),
    Caminho = [Nodo|_]/_/_,goal(Nodo).

agulosa(Caminhos,SolucaoCaminho) :-
    obtem_melhor_g(Caminhos,MelhorCaminho),
    seleciona(MelhorCaminho,Caminhos,OutrosCaminhos),
    expande_gulosa(MelhorCaminho,ExpCaminhos),
    append(OutrosCaminhos,ExpCaminhos,NovoCaminhos),
    agulosa(NovoCaminhos,SolucaoCaminho).

obtem_melhor_g([Caminho], Caminho) :- !.

obtem_melhor_g([Caminho1/Custo1/Est1,_/_/Est2|Caminhos], MelhorCaminho) :-
    Est1 =< Est2, !,
    obtem_melhor_g([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).

obtem_melhor_g([_|Caminhos], MelhorCaminho) :-
    obtem_melhor_g(Caminhos,MelhorCaminho).

expande_gulosa(Caminho, ExpCaminhos) :- 
    findall(NovoCaminho,adjacente3(Caminho,NovoCaminho),ExpCaminhos).

% ------- Pesquisa a estrela
% Para o A* funcionar temos que alterar a regra obtem_melhor_g
% resolve_aestrela(s,C).

resolve_aestrela(Nodo,Caminho/Custo) :- estima(Nodo,Estima),
									    aestrela([[Nodo]/0/Estima], InvCaminho/Custo/_),
									    inverso(InvCaminho,Caminho).

aestrela(Caminhos,Caminho) :- obtem_melhor(Caminhos,Caminho),
    						  Caminho = [Nodo|_]/_/_,goal(Nodo).

aestrela(Caminhos,SolucaoCaminho) :- obtem_melhor(Caminhos,MelhorCaminho),
								     seleciona(MelhorCaminho,Caminhos,OutrosCaminhos),
								     expande_aestrela(MelhorCaminho,ExpCaminhos),
								     append(OutrosCaminhos,ExpCaminhos,NovoCaminhos),
								     aestrela(NovoCaminhos,SolucaoCaminho).

obtem_melhor([Caminho], Caminho) :- !.

obtem_melhor([Caminho1/Custo1/Est1,_/Custo2/Est2|Caminhos], MelhorCaminho) :- Custo1 + Est1 =< Custo2 + Est2, !,
    																		  obtem_melhor([Caminho1/Custo1/Est1|Caminhos], MelhorCaminho).

obtem_melhor([_|Caminhos], MelhorCaminho) :- obtem_melhor(Caminhos,MelhorCaminho).

expande_aestrela(Caminho,ExpCaminhos) :- findall(NovoCaminho,adjacente3(Caminho,NovoCaminho), ExpCaminhos).

% Definir um novo método adjacente que vai ter em conta a estimativa e o custo
% Temos que garantir que não estamos a visitar mais de uma vez um determinado nó

adjacente3([Nodo|Caminho]/Custo/_,[ProxNodo,Nodo|Caminho]/NovoCusto/Est) :-
    adjacente(Nodo,ProxNodo,PassoCusto),
    nao(membro(ProxNodo,Caminho)),
    NovoCusto is Custo + PassoCusto,
    estima(ProxNodo,Est).

