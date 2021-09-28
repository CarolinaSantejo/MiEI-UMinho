:- consult('baseConhecimento.pl').
:- consult('predicadosAuxiliares.pl').
:- consult('algoritmosNormais.pl').
:- set_prolog_flag(answer_write_options,[max_depth(0)]).


adjacente(Nodo,ProxNodo,C) :-
    arestaRegresso(Nodo,ProxNodo,C).

adjacente(Nodo,ProxNodo,C) :-
    aresta(Nodo,ProxNodo,C).

adjacente(Nodo,ProxNodo,C) :-
    aresta(ProxNodo,Nodo,C).

calculaTempoExec(Predicado, Tempo) :- statistics(runtime,[Start|_]),
                                      Predicado,
                                      statistics(runtime,[Stop|_]),
                                      Tempo is Stop - Start.



% ------------ 1. Circuitos de recolha por territorio(ruas) ----------------------------------------------------------------------------------------

% ------------ Pesquisa em profundidade -------------------------------------





circuitosPorTerritorio(ListaRuas,CaminhoRes,Distancia,Residuo) :- circuitosPorTerritorioSeletivo(ListaRuas,CaminhoRes,Distancia,'qualquer',Residuo).

circuitosPorTerritorioSeletivo(ListaRuas,CaminhoRes,Distancia,TipoResiduo,Residuo) :- 
    deposito(NodoDep,_,_),
    getCircuitosPorTerritorioSeletivo(ListaRuas,NodoDep,Caminho,DistanciaRes,TipoResiduo,Residuo),
    garagem(NodoG,_,_),
    adjacente(NodoDep,NodoG,Custo),
    Distancia is DistanciaRes + Custo,
    CaminhoInv = [NodoG|Caminho],
    inverso(CaminhoInv,CaminhoRes).



getCircuitosPorTerritorioSeletivo(ListaRuas,Nodo,[Nodo|Caminho],Distancia,TipoResiduo,Residuo) :- 
    pontosRecolhaListaRuas(ListaRuas,PontosRecolha),
    garagem(NodoDep,_,_),
    profundidadePrimeiroTerritorioSeletivo(Nodo,[NodoDep|PontosRecolha],[Nodo],Caminho,Distancia,TipoResiduo,Residuo,_).

profundidadePrimeiroTerritorioSeletivo(Nodo,[],_,[],0,_,0,0) :- garagem(Nodo,_,_).

profundidadePrimeiroTerritorioSeletivo(Nodo,PontosRecolha,Historico,[ProxNodo|Caminho],Distancia,TipoResiduo,Residuo,ResiduoCamiao) :- 
    adjacente(Nodo,ProxNodo,C1),
    membro(ProxNodo,PontosRecolha),
    nao(membro(ProxNodo,Historico)),
    delete(PontosRecolha,ProxNodo,PontosRecolhaRes),
    profundidadePrimeiroTerritorioSeletivo(ProxNodo,PontosRecolhaRes,[ProxNodo|Historico],CaminhoRes,C2,TipoResiduo,ResiduoTotal,ResiduoCamiaoRes),
    (TipoResiduo == 'qualquer' -> residuoPontoRecolha(ProxNodo,Res); residuoSeletivoPontoRecolha(ProxNodo,TipoResiduo,Res)),
    Residuo is (Res + ResiduoTotal),
    camiao(CapacidadeCamiao),
    (CapacidadeCamiao > (Res + ResiduoCamiaoRes) ->
            ResiduoCamiao is (Res + ResiduoCamiaoRes),
            Distancia is (C1+C2),
            Caminho = CaminhoRes;
            ResiduoCamiao is 0,
            deposito(NodoDep,_,_),
            adjacente(NodoDep,ProxNodo,CustoDep),
            Caminho1 = [ProxNodo|CaminhoRes],
            Caminho = [NodoDep|Caminho1],
            Distancia is (C1+C2 + CustoDep + CustoDep)).
                                                                                                                                      
% ------------ Pesquisa em profundidade limitada -------------------------------------

circuitosPorTerritorioLimitado(ListaRuas,CaminhoRes,Distancia,Residuo,Depth) :- 
    circuitosPorTerritorioSeletivoLimitado(ListaRuas,CaminhoRes,Distancia,'qualquer',Residuo,Depth).

circuitosPorTerritorioSeletivoLimitado(ListaRuas,CaminhoRes,Distancia,TipoResiduo,Residuo,Depth) :- 
    deposito(NodoDep,_,_),
    getCircuitosPorTerritorioSeletivoLimitado(ListaRuas,NodoDep,Caminho,DistanciaRes,TipoResiduo,Residuo,Depth),
    garagem(NodoG,_,_),
    adjacente(NodoDep,NodoG,Custo),
    Distancia is DistanciaRes + Custo,
    CaminhoInv = [NodoG|Caminho],
    inverso(CaminhoInv,CaminhoRes).



getCircuitosPorTerritorioSeletivoLimitado(ListaRuas,Nodo,[Nodo|Caminho],Distancia,TipoResiduo,Residuo,Depth) :- 
    pontosRecolhaListaRuas(ListaRuas,PontosRecolha),
    garagem(NodoDep,_,_),
    profundidadePrimeiroTerritorioSeletivoLimitado(Nodo,[NodoDep|PontosRecolha],[Nodo],Caminho,Distancia,TipoResiduo,Residuo,_,Depth).


profundidadePrimeiroTerritorioSeletivoLimitado(Nodo,[],_,[],0,_,0,0,_) :- garagem(Nodo,_,_).


profundidadePrimeiroTerritorioSeletivoLimitado(Nodo,PontosRecolha,Historico,[ProxNodo|Caminho],Distancia,TipoResiduo,Residuo,ResiduoCamiao,Depth) :- 
    adjacente(Nodo,ProxNodo,C1),
    membro(ProxNodo,PontosRecolha),
    nao(membro(ProxNodo,Historico)),
    delete(PontosRecolha,ProxNodo,PontosRecolhaRes),
    profundidadePrimeiroTerritorioSeletivoLimitado(ProxNodo,PontosRecolhaRes,[ProxNodo|Historico],CaminhoRes,C2,TipoResiduo,ResiduoTotal,ResiduoCamiaoRes,DepthRes),
    (TipoResiduo == 'qualquer' -> residuoPontoRecolha(ProxNodo,Res); residuoSeletivoPontoRecolha(ProxNodo,TipoResiduo,Res)),
    (DepthRes =< 0 -> ProxNodo is 1; Depth is (DepthRes-1)),
    Residuo is (Res + ResiduoTotal),
    camiao(CapacidadeCamiao),
    (CapacidadeCamiao > (Res + ResiduoCamiaoRes) ->
        ResiduoCamiao is (Res + ResiduoCamiaoRes),
        Distancia is (C1+C2),
        Caminho = CaminhoRes;
        ResiduoCamiao is 0,
        deposito(NodoDep,_,_),
        adjacente(NodoDep,ProxNodo,CustoDep),
        Caminho1 = [ProxNodo|CaminhoRes],
        Caminho = [NodoDep|Caminho1],
        Distancia is (C1+C2 + CustoDep + CustoDep)).


pontosRecolhaListaRuas([],[]).
pontosRecolhaListaRuas([Rua|ListaRuas],PontosRecolha) :- pontosRecolhaRua(Rua,L),
                                                         pontosRecolhaListaRuas(ListaRuas,Pr),
                                                         append(L,Pr,PontosRecolha).
pontosRecolhaRua(Rua,L) :- rua(_,Rua,L).

% ------------ Pesquisa em largura -------------------------------------

getcircuitosPorTerritorioLargura(Caminho,Distancia,ListaRuas,TipoResiduo,Residuo,ResiduoCamiao):- 
    pontosRecolhaListaRuas(ListaRuas,[P|PontosRecolha]),
    deposito(NodoDep,_,_),
    getcircuitosPorTerritorioLarguraAux(NodoDep,[[P]], CaminhoRes,[P,NodoDep|PontosRecolha]),
    calcula_residuo_caminho(CaminhoRes,TipoResiduo,Residuo),
    Residuo < 15000,
    garagem(NodoG,_,_),
    Caminho1 = [NodoG|CaminhoRes],
    insertAtEnd(NodoG,Caminho1,Caminho),
    calcula_distancia_caminho(Caminho,Distancia).


getcircuitosPorTerritorioLarguraAux(Destino,[[Destino|Visitados]|_], Path,_) :- Visitados = [Start|_], 
                                                                                adjacente(Start,Destino,_),
                                                                                reverse([Destino|Visitados], Path).

getcircuitosPorTerritorioLarguraAux(Destino,[Visitados|Restantes], Path, PontosRecolha) :- 
    Visitados = [Start|_],
    membro(Start,PontosRecolha),
    findall([X|Visitados], (adjacente(Start,X,_),membro(X,PontosRecolha),nao(membro(X,Visitados))), Novos),
    append(Restantes,Novos,Todos),
    getcircuitosPorTerritorioLarguraAux(Destino,Todos, Path, PontosRecolha).




calcula_distancia_caminho([Gid1,Gid2|T],Distancia):-
    adjacente(Gid1,Gid2,DistanciaLigacao),
    calcula_distancia_caminho([Gid2|T],DistanciaAcumulada),
    Distancia is DistanciaLigacao + DistanciaAcumulada.
  
calcula_distancia_caminho([_],0).
calcula_distancia_caminho([],0).

calcula_residuo_caminho([],_,0).
calcula_residuo_caminho([P|PontosRecolha],TipoResiduo,Res) :- (TipoResiduo == 'qualquer' -> residuoPontoRecolha(P,Res2); residuoSeletivoPontoRecolha(P,TipoResiduo,Res2)),
                                                              calcula_residuo_caminho(PontosRecolha,TipoResiduo,ResTotal),
                                                              Res is ResTotal + Res2.

adiciona_queue( A, B, [B|A]).





% ------------ 2. Circuitos com mais pontos de recolha ----------------------------------------------------------------------------------------

% ------------ Pesquisa em profundidade -------------------------------------

circuitosMaisPontosRecolha(CaminhoMax,Custo,ResiduoTotal,NrPontos) :- circuitosMaisPontosRecolhaSeletivo('qualquer',CaminhoMax,Custo,ResiduoTotal,NrPontos).

circuitosMaisPontosRecolhaSeletivo(TipoResiduo,CaminhoMax,Custo,ResiduoTotal,NrPontos) :- 
    findnsols(20,(Caminho,C,ResiduoCamiao),circuito(Caminho,C,TipoResiduo,ResiduoCamiao),Caminhos),
    getCaminhoMaisLongo(Caminhos,(CaminhoMax,Custo,ResiduoTotal),NrPontos).


circuito([Nodo|Caminho],C,TipoResiduo,ResiduoCamiao) :- camiao(CapacidadeCamiao),
                                                        garagem(Nodo,_,_),
                                                        circuitoAux(Nodo,[Nodo],Caminho,C,TipoResiduo,CapacidadeCamiao,ResiduoCamiao),
                                                        ResiduoCamiao > 14980.

circuitoAux(Nodo,_,[],0,_,_,0) :- deposito(Nodo,_,_).

circuitoAux(Nodo,Historico,[ProxNodo|Caminho],C,TipoResiduo,Residuo,ResiduoCamiao):- Residuo > 0,
                                                                                     adjacente(Nodo,ProxNodo,C1),
                                                                                     nao(membro(ProxNodo,Historico)),
                                                                                     (TipoResiduo == 'qualquer' -> residuoPontoRecolha(ProxNodo,Res); residuoSeletivoPontoRecolha(ProxNodo,TipoResiduo,Res)),
                                                                                     circuitoAux(ProxNodo,[ProxNodo|Historico],Caminho,C2,TipoResiduo,Residuo - Res,ResiduoCamiao2),
                                                                                     C is C1+C2,
                                                                                     ResiduoCamiao is ResiduoCamiao2 + Res.


getCaminhoMaisLongo([(Caminho,Custo,Res)],(Caminho,Custo,Res),Len) :- length(Caminho,Len).
getCaminhoMaisLongo([(Caminho,Custo,Res)|Caminhos],CMax,Len) :- getCaminhoMaisLongo(Caminhos,CMaxRes,L2),
                                                                length(Caminho,L1),
                                                                    (L1 < L2 -> 
                                                                          CMax = CMaxRes,
                                                                          Len is L2; 
                                                                          CMax = (Caminho,Custo,Res),
                                                                          Len is L1).


% ------------ 3. Comparar circuitos de recolha tendo em conta os indicadores de produtividade ----------------------------------------------------------------------------------------

% ------------ Pesquisa em profundidade -------------------------------------

circuitoIndicadoresProfundidade([Garagem|Caminho],Media,TipoResiduo,ResiduoCamiao) :- circuito([Garagem|Caminho],_,TipoResiduo,ResiduoCamiao),
                                                                                      deposito(NodoD,_,_),
                                                                                      delete(Caminho,NodoD,CaminhoRes),
                                                                                      distanciaTotalPontos(CaminhoRes,Dist),
                                                                                      length(CaminhoRes,L),
                                                                                      Media is Dist/(L-1).

distanciaTotalPontos([],0).
distanciaTotalPontos([_],0).
distanciaTotalPontos([N1,N2|Cam],D) :- distanciaTotalPontos([N2|Cam],D2),
                                       adjacente(N1,N2,D1),
                                       D is D1+D2.

% ------------ Pesquisa em largura      -------------------------------------

circuitoIndicadoresLargura([Garagem|Caminho],Media,ResiduoCamiao) :- garagem(NodoG,_,_),
                                                                     deposito(NodoD,_,_),
                                                                     bfs(NodoG,NodoD,[Garagem|Caminho]),
                                                                     delete(Caminho,NodoD,CaminhoRes),
                                                                     distanciaTotalPontos(CaminhoRes,Dist),
                                                                     length(CaminhoRes,L),
                                                                     (L > 1 -> Media is Dist/(L-1); Media is 0),
                                                                     residuoListaPontoRecolha(CaminhoRes,ResiduoCamiao).

residuoListaPontoRecolha([],0).
residuoListaPontoRecolha([P|Pontos],Residuo) :- residuoListaPontoRecolha(Pontos,ResiduoRes),
                                                residuoPontoRecolha(P,Res),
                                                Residuo is Res + ResiduoRes.

residuoPontoRecolha(Ponto,Res):- solucoes(Residuo,contentor(_,_,Residuo,Ponto),L),
                                 sum_list(L, Res).

residuoSeletivoPontoRecolha(Ponto,TipoResiduo,Res):- solucoes(Residuo,contentor(_,TipoResiduo,Residuo,Ponto),L),
                                                     sum_list(L, Res).

% ------------ 4. Escolher o circuito mais rápido (usando o critério da distância) ----------------------------------------------------------------------------------------

% ------------ Algoritmo A* -------------------------------------

circuitoMaisRapidoAestrela(C) :- garagem(NodoG,_,_),
                                 resolve_aestrela(NodoG,C).

% ------------ Algoritmo Gulosa -------------------------------------

circuitoMaisRapidoGulosa(C) :- garagem(NodoG,_,_),
                               resolve_gulosa(NodoG,C).

% ------------ 5. Escolher o circuito mais eficiente (Mais pontos de recolha e menos distancia) ----------------------------------------------------------------------------------------

circuitoMaisEficiente(TipoResiduo,CaminhoMax,CustoMin,ResiduoTotal,PontosRecolha) :- findnsols(10,(Caminho,C,ResiduoCamiao),circuito(Caminho,C,TipoResiduo,ResiduoCamiao),Caminhos),
                                                                                     getCaminhoMaisEficiente(Caminhos,(CaminhoMax,CustoMin,ResiduoTotal),PontosRecolha).


getCaminhoMaisCurto([(Caminho,Custo,Res)],(Caminho,Custo,Res),Custo).
getCaminhoMaisCurto([(Caminho,Custo,Res)|Caminhos],CMin,Len) :- getCaminhoMaisLongo(Caminhos,CMinRes,L2),
                                                                (Custo > L2 -> 
                                                                      CMin = CMinRes,
                                                                      Len is L2; 
                                                                      CMin = (Caminho,Custo,Res),
                                                                      Len is Custo).

getCaminhoMaisEficiente([(Caminho,Custo,Res)],(Caminho,Custo,Res),Len) :- length(Caminho,Len).
getCaminhoMaisEficiente([(Caminho,Custo,Res)|Caminhos],CMax,Len) :- getCaminhoMaisLongo(Caminhos,(Cam,CMin,ResRes),L2),
                                                                    length(Caminho,L1),
                                                                    (L1 < L2 -> 
                                                                        CMax = (Cam,CMin,ResRes),
                                                                        Len is L2;
                                                                        (L1 = L2 ->
                                                                            (Custo < CMin ->
                                                                                CMax = (Caminho,Custo,Res), Len is L1; 
                                                                                CMax = (Cam,CMin,ResRes), Len is L2
                                                                            );
                                                                            CMax = (Caminho,Custo,Res),Len is L1
                                                                        )
                                                                    ). 

