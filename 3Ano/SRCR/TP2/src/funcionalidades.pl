

:- consult('predicadosAuxiliares.pl').
:- consult('baseDeConhecimento.pl').
:- consult('invariantes.pl').


:- dynamic (-)/1.

:- discontiguous removerImperfeito/1.
:- discontiguous removeExcecoesImpreciso/1.
:- discontiguous incertoI/1.
:- discontiguous imprecisoI/1.
:- discontiguous interditoI/1.
:- discontiguous removerImperfeitoIncerto/1.
:- discontiguous removerImperfeitoImpreciso/1.
:- discontiguous verificaQueExiste/1.
:- discontiguous verificaExcecao/1.
:- discontiguous evolucaoImpreciso/1.
:- discontiguous remove/1.





%FUNCIONALIDADES

%-------------------------------------------------------------------------------------------
%Centros de saude que permitiram vacinacoes indevidas
%Funcionalidade extra 1

cs_Vacinacao_Invalida(L):- solucoes(Utente,vacinacao_Covid(_,Utente,_,_,1),S),
                       utentesVacinadosIndevidamenteAux(S,X1),
               cs_onde_foramVacinados(X1,G),
               sort(0,@<,G,G1),
               nomeCS(G1,L).


cs_onde_foramVacinados([],[]).
cs_onde_foramVacinados([Utente|T],L):- solucoes(Staff,vacinacao_Covid(Staff,Utente,_,_,_),[S|_]),
                                   solucoes(Centro,staff(S,Centro,_,_),[S1|_]),
                       cs_onde_foramVacinados(T,G),
                       L = [S1|G].





%------------------------------------------------------------------------------------------
%Extensão do predicado staffMaisVacinacoes: X,L ->{V,F}
%Lista do(s) elemento(s) do Staff com mais vacinacoes e respetiva frequencia
%Funcionalidade extra 2


staffMaisVacinacoes(Max,IdNomes):- solucoes(IdStaff,vacinacao_Covid(IdStaff,_,_,_,_),L),
                       sort(0,@<,L,Ls),
                       staffMaisVacinacoesAux(Max,Ls,IdMaxL),
                   nomeStaff(IdMaxL,IdNomes).

staffMaisVacinacoesAux(0,[],[]).
staffMaisVacinacoesAux(Max,[IdStaff],[IdStaff]):- staffVacinacoesCount(IdStaff,C),
                              Max = C.

staffMaisVacinacoesAux(Max,[IdStaff|T],L):- staffVacinacoesCount(IdStaff,Count),
                        staffMaisVacinacoesAux(CountMax,T,Ls),
                        (Count > CountMax -> Max = Count, L = [IdStaff];
                         Count == CountMax -> Max = CountMax, L = [IdStaff|Ls]; Max = CountMax, L = Ls).

%--------------------------
%Extensão do predicado staffVacinacoesCount: Id,C ->{V,F}
%Conta o numero de vacinacoes por um elemento do staff

staffVacinacoesCount(IdStaff,X):- solucoes(IdStaff,vacinacao_Covid(IdStaff,_,_,_,_),L),
                              comprimento(L,X).





%-----------------------------------------------------------------------------------------------------
%Extensão do predicado utentesVacinados: L ->{V,F}
%Pessoas vacinadas são as que já tomaram as duas doses.
%Funcionalidade 1


utentesVacinados(L):- solucoes(Utente,vacinacao_Covid(_,Utente,_,_,2),S),
              nomeUtentes(S,X),
              sort(0,@<,X,L).



%--------------------------------------------------------------------------------------------------------
%Extensão do predicado utentesNaoVacinados: L ->{V,F}
%Pessoas não vacinadas são todas as que não têm as duas doses tomadas
%Funcionalidade 2

utentesNaoVacinados(L):- utentesVacinados(Z),
                     solucoes((Utente,Nome),utente(Utente,_,Nome,_,_,_,_,_,_,_),S),
                         elementosNaoComuns(S,Z,X1),
             sort(0,@<,X1,L).



%----------------------------------------------------------------------------------------------
%Extensao do predicado utentesFaltaSegundaDose: L -> {V,F}
%Funcionalidade 5

utentesFaltaSegundaDose(L):- utentesVacinados(S),
                            solucoes(Utente,vacinacao_Covid(_,Utente,_,_,1),S1),
                            nomeUtentes(S1,X),
                            elementosNaoComuns(X,S,X1),
                            sort(0,@<,X1,L).




%------------------------------------------------------------------------------------------------
%Extensão do predicado utentesVacinadosIndevidamente: L -> {V,F}
%Funcionalidade 3

utentesVacinadosIndevidamente(L):- solucoes(Utente,vacinacao_Covid(_,Utente,_,_,1),S),
                               utentesVacinadosIndevidamenteAux(S,X),
                   nomeUtentes(X,X1),
                   sort(0,@<,X1,L).

%-----------------------------------------
%Extensão do predicado utentesVacinadosIndevidamente: L,L1 ->{V,F}

utentesVacinadosIndevidamenteAux([],[]).
utentesVacinadosIndevidamenteAux([H|T],L):- (utenteMalVacinado(H) -> utentesVacinadosIndevidamenteAux(T,Z), L = [H|Z] ;
                                            utentesVacinadosIndevidamenteAux(T,L) ).


%--------------------------------------------------------------------------------------------------
%Extensão do predicado uutentesNaoVacinadosCandidatos : L,Fase ->{V,F}       
% Fase representa a fase em que nos encontramos, e todos os candidatos
% sao aqueles nao foram vacinados nas fases anteriores e aqueles que
% podem ser vacinados na fase atual
%Funcionalidade 4

 utentesNaoVacinadosCandidatos(L,Fase):- solucoes(Utente,utente(Utente,_,_,_,_,_,_,_,_,_),S),
                                         solucoes(U,vacinacao_Covid(_,U,_,_,2),S1),
                                         elementosNaoComuns(S,S1,Z),!,
                                         verificaSeCandidato(Z,Fase,K),!,
                                         nomeUtentes(K,K1),
                                         sort(0,@<,K1,L).                   


%-----------------
verificaSeCandidato([],_,[]).

verificaSeCandidato([H|T],Fase,L):- utenteMalVacinado(H),
                    faseDeveSerVacinado(H,Y),
                        faseAnterior(Fase,ListaFases),
                    (pertence(Y,ListaFases) -> verificaSeCandidato(T,Fase,X), L = [H|X]; verificaSeCandidato(T,Fase,L)).


verificaSeCandidato([H|T],Fase,L):- nao(naoRecebeuPrimeiraVacina(H)),
                                verificaSeCandidato(T,Fase,X),
                                    L = [H|X].


verificaSeCandidato([H|T],Fase,L):- faseAnterior(Fase,ListaFases),
                                    faseDeveSerVacinado(H,Y),
                                    (pertence(Y,ListaFases) -> verificaSeCandidato(T,Fase,X),L = [H|X];verificaSeCandidato(T,Fase,L) ).


%-----------------------------------------------------------------------------
%Extensão do predicado utenteSemQualquerDose L -> {V,F}
%Predicado que indica as pessoas que não levaram nenhuma vacina
%Funcionalidade extra 3

utenteSemQualquerDose(L):- solucoes(Utente,utente(Utente,_,_,_,_,_,_,_,_,_),S),
                           solucoes(U,vacinacao_Covid(_,U,_,_,1),S1),
                           elementosNaoComuns(S,S1,Z),
               nomeUtentes(Z,X1),
               sort(0,@<,X1,L).

%------------------------------
%%Funcionalidade extra 4
mesesVacinacoes(L):- solucoes((M,A),vacinacao_Covid(_,_,dataValida(_,M,A),_,_),S),
                 sort(0,@<,S,M1),
             transformeIntoMonth(S,S1),
             transformeIntoMonth(M1,M),
             tuplosComOcur(M,S1,L).


tuplosComOcur([],_,[]).
tuplosComOcur([(M,A)|T],Y,L):- contadorDeOcur(Y,(M,A),Aux),
                               tuplosComOcur(T,Y,D),
                               L = [(M,A,Aux)|D].


%-----------------------------

transformeIntoMonth([],[]).

transformeIntoMonth([(H,A)|T],L):- H == 1,
                               transformeIntoMonth(T,X),
                               L = [("Janeiro",A)|X].

transformeIntoMonth([(H,A)|T],L):- H == 2,
                               transformeIntoMonth(T,X),
                               L = [("Fevereiro",A)|X].

transformeIntoMonth([(H,A)|T],L):- H == 3,
                               transformeIntoMonth(T,X),
                               L = [("Março",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 4,
                               transformeIntoMonth(T,X),
                               L = [("Abril",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 5,
                               transformeIntoMonth(T,X),
                               L = [("Maio",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 6,
                               transformeIntoMonth(T,X),
                               L = [("Junho",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 7,
                               transformeIntoMonth(T,X),
                               L = [("Julho",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 8,
                               transformeIntoMonth(T,X),
                               L = [("Agosto",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 9,
                               transformeIntoMonth(T,X),
                               L = [("Setembro",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 10,
                               transformeIntoMonth(T,X),
                               L = [("Outubro",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 11,
                               transformeIntoMonth(T,X),
                               L = [("Novembro",A)|X].
transformeIntoMonth([(H,A)|T],L):- H == 12,
                               transformeIntoMonth(T,X),
                               L = [("Dezembro",A)|X].



%--------------------
%Conta o numero de ocorrencias de um elemento  numa lista
contadorDeOcur([],_,0).
contadorDeOcur([X|T],X,Y):- contadorDeOcur(T,X,Z), Y is 1+Z.
contadorDeOcur([X1|T],X,Z):- X1\=X,contadorDeOcur(T,X,Z).


%%----------------------------------
%Extensão do predicado utenteMalVacinado: idUtente ->{V,F}
%Devolve true se o utente em questão foi vacinado indevidamente
%Pessoas nao vacinadas totalmente não sao indevidamente vacinadas
%Pessoas que nao levaram a primeira vacina neste predicado
%Devolve sempre falso


utenteMalVacinado(IdUtente):- recebeuSegundaVacina(IdUtente),
                              faseEmQueFoiVacinadoDose2(IdUtente,X),
                              faseDeveSerVacinado(IdUtente,Y),!,
                               X \== Y.

utenteMalVacinado(IdUtente):- recebeuSegundaVacina(IdUtente),
                              faseEmQueFoiVacinadoDose1(IdUtente,X1),
                              faseDeveSerVacinado(IdUtente,Y1),!,
                              X1 \== Y1.


utenteMalVacinado(IdUtente):- nao(recebeuSegundaVacina(IdUtente)),
                              faseEmQueFoiVacinadoDose1(IdUtente,X),
                              faseDeveSerVacinado(IdUtente,Y),!,
                              X \== Y.


%--------------------
% Prossupoem-se que o utente deve levar a primeira e a segunda dose na
% mesma fase
% Partindo de todas as fases válidas de um utente escolhe-se a mais antiga

faseDeveSerVacinado(IdUtente,Y):- solucoes((Data,Prof,Doencas),utente(IdUtente,_,_,Data,_,_,_,Prof,Doencas,_),[H1|_]),
                                  faseCorretaVacinacao(H1,Y).



%---------------

faseCorretaVacinacao(H,X):- solucoes((Fase,DataI),fase(Fase,DataI,_,_,_,_),S),
                            getFasesValidas(H,S,L),
                            getDataMaisAntiga(L,(X,_)).

%----------------------------------------------------------------------
%Devolve lista com todas as fases válidas de um utente
getFasesValidas(_,[],[]).
getFasesValidas((Data,Profissao,Doencas),[(H,DataI)|T],L):- validarFase((Data,Profissao,Doencas),H),
                                                            getFasesValidas((Data,Profissao,Doencas),T,Y),
                                                            L = [(H,DataI)|Y].

getFasesValidas((Data,Profissao,Doencas),[_|T],L):- getFasesValidas((Data,Profissao,Doencas),T,L).


%----------------------------
% Devolve true se o utente tem condicoes para ser vacinado na fase F.
% Ter condicoes nao significa que seja a sua fase. Por exemplo um utente
% da fase 1 é valido tmb na fase 2
%
validarFase((Data,_,_),F):- solucoes(Idade,fase(F,_,_,Idade,_,_),[H|_]),
                            idadePrioritaria(Data,H).



validarFase((_,Profissao,_),F):- solucoes(ProfRisco,fase(F,_,_,_,_,ProfRisco),[H|_]),
                                 pertence(Profissao,H).


validarFase((Data,_,Doencas),F):- solucoes(DoencasRisco,fase(F,_,_,_,DoencasRisco,_),[(I,LD)|_]),
                                  idadePrioritaria(Data,I),
                                  pertenceLista(Doencas,LD).


%----------------------------------
%Extensão do predicado naoRecebeuPrimeiraVacina : Id -> {V,F}
%Devolve true se a pessoa ainda nao tiver recebido a primeira dose

naoRecebeuPrimeiraVacina(IdUtente):- solucoes(Data,vacinacao_Covid(_,IdUtente,Data,_,1),S),
                                 comprimento(S,N),
                     N == 0.
%--------------------------------
%Verifica se pessoa recebeu segunda dose
recebeuSegundaVacina(IdUtente):- solucoes(Data,vacinacao_Covid(_,IdUtente,Data,_,2),S),
                                 comprimento(S,N),
                     N == 1.

%-------------------------------
%Extensão do predicado faseEmQueFoiVacinado : idUtente, X ->{V,F}
%Verifica em que fase é o utente tomou a primeira vacina
faseEmQueFoiVacinadoDose1(IdUtente,X):- solucoes(Data,vacinacao_Covid(_,IdUtente,Data,_,1),[H|_]),
                                       faseVacinacao(H,X).

faseEmQueFoiVacinadoDose2(IdUtente,X):- solucoes(Data,vacinacao_Covid(_,IdUtente,Data,_,2),[H|_]),
                                       faseVacinacao(H,X).


%------------------
%Extensão do predicado faseVacinacao: Data,X ->{V,F}
%Verifica se uma data é relativa a uma determinada fase

faseVacinacao(Data,X):- solucoes((Fase,DataInicio,DataFim),fase(Fase,DataInicio,DataFim,_,_,_),S),
                        faseVacinacaoAux(Data,S,X).

faseVacinacaoAux(_,[],_).
faseVacinacaoAux(Data,[(Fase,I,F)|_],X):- dataDepoisIgual(Data,I),
                                          dataAntesIgual(Data,F),
                                          X = Fase.

faseVacinacaoAux(Data,[_|T],X):- faseVacinacaoAux(Data,T,X).




%----------------------
%Extensão do predicado faseAnterior : F,L ->{V,F}
%Verifica se todas as fases em L são anteriores ou iguais a Fase 


faseAnterior(Fase,L):- solucoes(DataInicio,fase(Fase,DataInicio,_,_,_,_),[D|_]),
                       solucoes((F,DataI),fase(F,DataI,_,_,_,_),S),
                       faseAnteriorAux(D,S,L).


faseAnteriorAux(_,[],[]).

faseAnteriorAux(Data,[(F,DataFim)|T],L):- dataAntesIgual(DataFim,Data),
                                          faseAnteriorAux(Data,T,Y),
                                          L = [F|Y].

faseAnteriorAux(Data,[_|T],L):- faseAnteriorAux(Data,T,L).




%-----------------------
%Extensão do predicado nomeStaff: L1,L2 -> {V,F}

nomeStaff([],[]).
nomeStaff([H|T],L):-solucoes((H,Nome),staff(H,_,Nome,_),[S|_]),
                  nomeStaff(T,Z),
               L = [S|Z].



%-----------------
%Extensao do predicado semDoencas: idUtente -> {V,F}
%Devolve true se o utente nao tiver qualquer tipo de problema de saude
%
semDoencas(IdUtente):- solucoes(IdUtente,utente(IdUtente,_,_,_,_,_,_,_,[],_),S),
                   comprimento(S,N),
                   N == 1.

%-----------------
%Extensao do predicado idadePrioritaria:IdUtente,X -> {V,F}
% Devolve true se o utente tiver X ou mais anos (feitos ou a fazer em
% 2021)

idadePrioritaria(DataNasc,X):-  getAno(DataNasc,Ano),
                diferenca(2021,Ano,Dif),
                Dif >= X.


%----------------------
%Extensao do predicado getAno: data,A ->{V,F}
getAno(dataValida(_,_,Ano),A):- A is Ano.


%-----------------
diferenca(X,Y,Z):- Z is X - Y.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -

imperfeito(Termo):- incertoI(Termo).
imperfeito(Termo):- imprecisoI(Termo).
imperfeito(Termo):- interditoI(Termo).

%--------------------------------- - - - - - - - - - -  -  -  -  -   -

incertoI(utente(Id,_,_,_,_,_,_,_,_,_)) :- incerto(utente(Id)).
imprecisoI(utente(Id,_,_,_,_,_,_,_,_,_)) :- impreciso(utente(Id)).
interditoI(utente(Id,_,_,_,_,_,_,_,_,_)) :- interdito(utente(Id)).

  
%--------------------------------- - - - - - - - - - -  -  -  -  -   -

  
incertoI(centro_saude(Id,_,_,_,_)) :- incerto(centro_saude(Id)).
imprecisoI(centro_saude(Id,_,_,_,_)) :- impreciso(centro_saude(Id)).
interditoI(centro_saude(Id,_,_,_,_)) :- interdito(centro_saude(Id)).

  
  
%--------------------------------- - - - - - - - - - -  -  -  -  -   -


incertoI(staff(IdStaff,_,_,_)) :- incerto(staff(IdStaff)).
imprecisoI(staff(IdStaff,_,_,_)) :- impreciso(staff(IdStaff)).
interditoI(staff(IdStaff,_,_,_)) :- interdito(staff(IdStaff)).


  
%--------------------------------- - - - - - - - - - -  -  -  -  -   -

incertoI(vacinacao_Covid(IdStaff,IdUtente,_,_,Toma)) :- incerto(vacinacao_Covid(IdStaff,IdUtente,Toma)).
imprecisoI(vacinacao_Covid(IdStaff,IdUtente,_,_,Toma)) :- impreciso(vacinacao_Covid(IdStaff,IdUtente,Toma)).
interditoI(vacinacao_Covid(IdStaff,IdUtente,_,_,Toma)) :- interdito(vacinacao_Covid(IdStaff,IdUtente,Toma)).

  
%--------------------------------- - - - - - - - - - -  -  -  -  -   -

incertoI(fase(Fase,_,_,_,_,_)) :- incerto(fase(Fase)).
imprecisoI(fase(Fase,_,_,_,_,_)) :- impreciso(fase(Fase)).
interditoI(fase(Fase,_,_,_,_,_)) :- interdito(fase(Fase)).


  
  

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado evolucao: Termo -> {V, F}


evolucao(Termo) :- solucoes(Invariante, +Termo::Invariante, Lista),
                   insere(Termo),
                   teste(Lista).
        
              



evolucao(-Termo) :- solucoes(Invariante, +(-Termo)::Invariante, Lista),
                    insere(-Termo),
                    teste(Lista).



                   
%------------------------------------------
%Evolucao de conhecimento imperfeito 

%incerto

evolucao_Incerto(Termo):- evolucaoIncerto(Termo).
evolucao_Incerto(-Termo):- evolucaoIncerto(Termo).


%utente
evolucaoIncerto(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- nao(impreciso(utente(Id))),
                                                                                                                     evolucao(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)),
                                                                                                                     insere(incerto(utente(Id))),
                                                                                                                     checkExcecao(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)).

%centro_saude
evolucaoIncerto(centro_saude(Id,Nome,Morada,Telefone,Email)):- nao(impreciso(centro_saude(Id))),
                                                               evolucao(centro_saude(Id,Nome,Morada,Telefone,Email)),
                                                               insere(incerto(centro_saude(Id))),
                                                               checkExcecao(centro_saude(Id,Nome,Morada,Telefone,Email)).


%staff
evolucaoIncerto(staff(Id,IdCentroSaude,Nome,Email)):- nao(impreciso(staff(Id))),
                                                      evolucao(staff(Id,IdCentroSaude,Nome,Email)),
                                                      insere(incerto(staff(Id))),
                                                      checkExcecao(staff(Id,IdCentroSaude,Nome,Email)).

%vacinacao
evolucaoIncerto(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)):- nao(impreciso(vacinacao_Covid(IdStaff,IdUtente,_,_,Toma))),
                                                                      evolucao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)),
                                                                      insere(incerto(vacinacao_Covid(IdStaff,IdUtente,Toma))),
                                                                      checkExcecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)).


%fase-ligeiramente diferente por causa dos inteiros
evolucaoIncerto(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)):- nao(impreciso(fase(Fase))),
                                                                      evolucaoIncertoFaseAux(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)),
                                                                      insere(incerto(fase(Fase))),
                                                                      checkExcecao(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)).




evolucaoIncertoFaseAux(Termo) :- solucoes(Invariante, +Termo:-:Invariante, Lista),
                                 insere(Termo),
                                 teste(Lista).



%impreciso


evolucao_Impreciso(Termo):- evolucaoImpreciso(Termo).
evolucao_Impreciso(-Termo):- evolucaoImpreciso(Termo).


%----------utente-----------
evolucaoImpreciso(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- removerImperfeitoIncerto(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)),
                                                                                                                       insere(impreciso(utente(Id))),
                                                                                                                       insere(excecao(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude))).

evolucaoImpreciso(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- nao(perfeito(utente(Id))),
                                                                                                                       nao(interdito(utente(Id))), 
                                                                                                                       verificaQueExiste(utente(Id)),
                                                                                                                       solucoes(Invariante, +excecao(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude))::Invariante, Lista),
                                                                                                                       teste(Lista).
                                                                                                                       

verificaQueExiste(utente(Id)):- nao(impreciso(utente(Id))),
                                insere(impreciso(utente(Id))).

verificaQueExiste(utente(_)).    


%-----------centro_saude----------------------------
evolucaoImpreciso(centro_saude(Id,Nome,Morada,Telefone,Email)):- removerImperfeitoIncerto(centro_saude(Id,Nome,Morada,Telefone,Email)),
                                                                 insere(impreciso(centro_saude(Id))),
                                                                 insere(excecao(centro_saude(Id,Nome,Morada,Telefone,Email))).

evolucaoImpreciso(centro_saude(Id,Nome,Morada,Telefone,Email)):- nao(perfeito(centro_saude(Id))),
                                                                 nao(interdito(centro_saude(Id))), 
                                                                 verificaQueExiste(centro_saude(Id)),
                                                                 solucoes(Invariante, +excecao(centro_saude(Id,Nome,Morada,Telefone,Email))::Invariante, Lista),
                                                                 teste(Lista).




verificaQueExiste(centro_saude(Id)):- nao(impreciso(centro_saude(Id))),
                                      insere(impreciso(centro_saude(Id))).

verificaQueExiste(centro_saude(_)).    





%staff
evolucaoImpreciso(staff(Id,IdCentroSaude,Nome,Email)):- removerImperfeitoIncerto(staff(Id,IdCentroSaude,Nome,Email)),
                                                        insere(impreciso(staff(Id))),
                                                        insere(excecao(staff(Id,IdCentroSaude,Nome,Email))).

evolucaoImpreciso(staff(Id,IdCentroSaude,Nome,Email)):- nao(perfeito(staff(Id))),
                                                        nao(interdito(staff(Id))), 
                                                        verificaQueExiste(staff(Id)),
                                                        solucoes(Invariante, +excecao(staff(Id,IdCentroSaude,Nome,Email))::Invariante, Lista),
                                                        teste(Lista).




verificaQueExiste(staff(Id)):- nao(impreciso(staff(Id))),
                               insere(impreciso(staff(Id))).

verificaQueExiste(staff(_)).    



%vacinacao
evolucaoImpreciso(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)):- removerImperfeitoIncerto(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)),
                                                                        insere(impreciso(vacinacao_Covid(IdStaff,IdUtente,Toma))),
                                                                        insere(excecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma))).

evolucaoImpreciso(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)):- nao(perfeito(vacinacao_Covid(IdStaff,IdUtente,Toma))),
                                                                        nao(interdito(vacinacao_Covid(IdStaff,IdUtente,Toma))),
                                                                        verificaQueExiste(vacinacao_Covid(IdStaff,IdUtente,Toma)),
                                                                        solucoes(Invariante, +excecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma))::Invariante, Lista),
                                                                        teste(Lista).


verificaQueExiste(vacinacao_Covid(IdStaff,IdUtente,Toma)):- nao(impreciso(vacinacao_Covid(IdStaff,IdUtente,Toma))),
                                                            insere(impreciso(vacinacao_Covid(IdStaff,IdUtente,Toma))).

verificaQueExiste(vacinacao_Covid(_,_,_)).    






%fase
evolucaoImpreciso(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)):- removerImperfeitoIncerto(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)),
                                                                        insere(impreciso(fase(Fase))),
                                                                        insere(excecao(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof))).


evolucaoImpreciso(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)):- nao(perfeito(fase(Fase))),
                                                                        nao(interdito(fase(Fase))), 
                                                                        verificaQueExiste(fase(Fase)),
                                                                        solucoes(Invariante, +excecao(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof))::Invariante, Lista),
                                                                        teste(Lista).




verificaQueExiste(fase(Fase)):- nao(impreciso(fase(Fase))),
                               insere(impreciso(fase(Fase))).

verificaQueExiste(fase(_)).    



                                                      





%interdito   

evolucao_Interdito(Termo):- evolucaoImpreciso(Termo).
evolucao_Interdito(-Termo):- evolucaoImpreciso(Termo).     


%utente
evolucaoInterdito(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- nao(impreciso(utente(Id))),
                                                                                                                       evolucao(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)),
                                                                                                                       insere(interdito(utente(Id))),
                                                                                                                       checkExcecaoInterdito(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)).



%staff
evolucaoInterdito(staff(Id,IdCentroSaude,Nome,Email)):- nao(impreciso(staff(Id))),
                                                        evolucao(staff(Id,IdCentroSaude,Nome,Email)),
                                                        insere(interdito(staff(Id))),
                                                        checkExcecaoInterdito(staff(Id,IdCentroSaude,Nome,Email)).

%-----------------------------
checkExcecaoInterdito(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- excecao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)).

checkExcecaoInterdito(utente(_,_,_,_,_,_,_,_,_,_)):- insere(excecao(utente(A,B,C,D,E,F,G,H,I,J)) :- utente(A,B,C,D,E,F,int01,H,I,J)).


checkExcecaoInterdito(staff(Id,IdCentroSaude,Nome,Email)):- excecao(staff(Id,IdCentroSaude,Nome,Email)).

checkExcecaoInterdito(staff(_,_,_,_)):- insere(excecao(centro_saude(I,N,M,T,E)):-centro_saude(I,N,M,T,int01)).



%-----------------------------
%Verifica se é preciso adicionar uma excecao. Verifica se nao estamos a adicionar excecoes do incerto repetidas
checkExcecao(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- excecao(utente(Id,SegurancaSocial,Nome_Inc,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)).

checkExcecao(utente(_,_,_,_,_,_,_,_,_,_)):- insere(excecao(utente(A,B,C,D,E,F,G,H,I,J)) :- utente(A,B,inc01,D,E,F,G,H,I,J)).



checkExcecao(centro_saude(Id,Nome,Morada,Telefone,Email)):- excecao(centro_saude(Id,Nome,Morada,Telefone,Email)).

checkExcecao(centro_saude(_,_,_,_,_)):- insere(excecao(centro_saude(I,N,M,T,E)):-centro_saude(I,N,inc01,T,E)).



checkExcecao(staff(Id,IdCentroSaude,Nome,Email)):- excecao(staff(Id,IdCentroSaude,Nome,Email)).

checkExcecao(staff(_,_,_,_)):- insere(excecao(centro_saude(I,N,M,T,E)):-centro_saude(I,N,inc01,T,E)).


checkExcecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)):- excecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)).

checkExcecao(vacinacao_Covid(_,_,_,_,_)):- insere(excecao(vacinacao_Covid(IdS,IdU,D,_,T)) :- vacinacao_Covid(IdS,IdU,D,inc01,T)).


checkExcecao(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)):- excecao(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)).

checkExcecao(fase(_,_,_,_,_,_)):- insere(excecao(fase(F,DI,DF,I,(_,D),P)):-fase(F,DI,DF,I,(inc01,D),P)).
                                                            




%--------
%Evolução do conhecimento positivo e negativo perfeito

evolucaoConhecimentoPerfeito(Termo):- nao(imperfeito(Termo)),
                                      evolucao(Termo),
                                      addPerfeito(Termo).

evolucaoConhecimentoPerfeito(-Termo):- nao(imperfeito(Termo)),
                                       evolucao(-Termo),
                                       addPerfeito(Termo).


evolucaoConhecimentoPerfeito(Termo):- imperfeito(Termo),
                                      removeImperfeito(Termo),
                                      evolucao(Termo),
                                      addPerfeito(Termo).

evolucaoConhecimentoPerfeito(-Termo):- imperfeito(Termo),
                                       removeImperfeito(Termo),
                                       evolucao(-Termo),
                                       addPerfeito(Termo).

                                       


%---------------REMOVER EXCECOES DO IMPERFEITO------------

removeImperfeito(Termo):- removerImperfeitoIncerto(Termo).
removeImperfeito(Termo):- removerImperfeitoImpreciso(Termo).

removeImperfeito(-Termo):- removerImperfeitoImpreciso(Termo).
removeImperfeito(-Termo):- removerImperfeitoImpreciso(Termo).


%utente
removerImperfeitoIncerto(utente(Id,_,_,_,_,_,_,_,_,_)):- incerto(utente(Id)),
                                                         retract(incerto(utente(Id))),
                                                         retract(utente(Id,_,_,_,_,_,_,_,_,_)).



removerImperfeitoImpreciso(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- impreciso(utente(Id)),
                                                                                                                            excecao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)),
                                                                                                                            removeExcecoesImpreciso(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)).
                                                                                                                   



removeExcecoesImpreciso(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,_,Profissao,ListaD,IdCentroSaude)):- solucoes(excecao(utente(_,_,_,_,_,_,_,_,_,_)),excecao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,_,Profissao,ListaD,IdCentroSaude)),S),
                                                                                                                    retiraTudo(S),
                                                                                                                    remove(impreciso(utente(Id))). 




%centro_saude
removerImperfeitoIncerto(centro_saude(Id,_,_,_,_)):- incerto(centro_saude(Id)),
                                                     retract(incerto(centro_saude(Id))),
                                                     retract(centro_saude(Id,_,_,_,_)).

removerImperfeitoImpreciso(centro_saude(Id,Nome,Morada,Telefone,Email)):- impreciso(centro_saude(Id)),
                                                                          excecao(centro_saude(Id,Nome,Morada,Telefone,Email)),
                                                                          removeExcecoesImpreciso(centro_saude(Id,Nome,Morada,Telefone,Email)).
                                                                                                                   



removeExcecoesImpreciso(centro_saude(Id,Nome,Morada,Telefone,_)):- solucoes(excecao(centro_saude(_,_,_,_,_)),excecao(centro_saude(Id,Nome,Morada,Telefone,_)),S),
                                                                       retiraTudo(S),
                                                                       retract(impreciso(centro_saude(Id))). 


%staff




removerImperfeitoIncerto(staff(IdStaff,_,_,_)):- incerto(staff(IdStaff)),
                                                 retract(incerto(staff(IdStaff))),
                                                 retract(staff(IdStaff,_,_,_)).

removerImperfeitoImpreciso(staff(IdStaff,IdCentroSaude,Nome,Email)):- impreciso(staff(IdStaff)),
                                                                      excecao(staff(IdStaff,IdCentroSaude,Nome,Email)),
                                                                      removeExcecoesImpreciso(staff(IdStaff,IdCentroSaude,Nome,Email)).
                                                                                                                   


removeExcecoesImpreciso(staff(IdStaff,IdCentroSaude,Nome,_)):- solucoes(excecao(staff(_,_,_,_)),excecao(staff(IdStaff,IdCentroSaude,Nome,_)),S),
                                                               retiraTudo(S),
                                                               retract(impreciso(staff(IdStaff))). 



%vacinacao_Covid

removerImperfeitoIncerto(vacinacao_Covid(IdStaff,IdUtente,_,_,Toma)):- incerto(vacinacao_Covid(IdStaff,IdUtente,Toma)),
                                                                       retract(incerto(vacinacao_Covid(IdStaff,IdUtente,Toma))),
                                                                       retract(vacinacao_Covid(IdStaff,IdUtente,_,_,Toma)).

removerImperfeitoImpreciso(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)):- impreciso(vacinacao_Covid(IdStaff,IdUtente,Toma)),
                                                                                 excecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)),
                                                                                 removeExcecoesImpreciso(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)).

                                                                                                     

removeExcecoesImpreciso(vacinacao_Covid(IdStaff,IdUtente,_,Vacina,Toma)):- solucoes(excecao(vacinacao_Covid(_,_,_,_,_)),excecao(vacinacao_Covid(IdStaff,IdUtente,_,Vacina,Toma)),S),
                                                                              retiraTudo(S),
                                                                              retract(impreciso(vacinacao_Covid(IdStaff,IdUtente,Toma))). 




%fase
removerImperfeitoIncerto(fase(Fase,_,_,_,_,_)):- incerto(fase(Fase)),
                                          retract(incerto(fase(Fase))),
                                          retract(fase(Fase,_,_,_,_,_)).

removerImperfeitoImpreciso(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)):- impreciso(fase(Fase)),
                                                                        excecao(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)),
                                                                        removeExcecoesImpreciso(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)).
                                                                                                                 

removeExcecoesImpreciso(fase(Fase,DataI,DataF,_,(IdadeM,Doencas),Prof)):- solucoes(excecao(fase(_,_,_,_,_,_)),excecao(fase(Fase,DataI,DataF,_,(IdadeM,Doencas),Prof)),S),
                                                                              retiraTudo(S),
                                                                              retract(impreciso(fase(Fase))). 



%-------------------------------
%Adiciona que um termo é conhecimento perfeito

addPerfeito(utente(Id,_,_,_,_,_,_,_,_,_)):- insere(perfeito(utente(Id))).

addPerfeito(centro_saude(Id,_,_,_,_)):- insere(perfeito(centro_saude(Id))).

addPerfeito(staff(IdStaff,_,_,_)):- insere(perfeito(staff(IdStaff))).

addPerfeito(vacinacao_Covid(IdStaff,IdUtente,_,_,Toma)):- insere(perfeito(vacinacao_Covid(IdStaff,IdUtente,Toma))).

addPerfeito(fase(Fase,_,_,_,_,_)):- insere(perfeito(fase(Fase))).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
involucao( Termo ) :-
    solucoes( Invariante,-Termo::Invariante,Lista ),
    remove( Termo ),
    teste( Lista ).



remove( Termo ) :- retract( Termo ).
remove( Termo ) :- assert( Termo ),!,fail.


%-------------------------------- ----- ---


%incerto
involucaoPerfeito(Termo):- removerPerfeito(Termo).
involucaoPerfeito(-Termo):- removerPerfeito(Termo).





%-----------------------------------------

involucaoIncerto(Termo):- removerImperfeitoIncerto(Termo).
involucaoIncerto(-Termo):- removerImperfeitoIncerto(Termo).

%Impreciso
involucaoImpreciso(Termo):- involucaoImprecisoAux(Termo).
involucaoImpreciso(-Termo):- involucaoImprecisoAux(Termo).

involucaoImprecisoAux(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):-solucoes(excecao(utente(_,_,_,_,_,_,_,_,_,_)),excecao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,_,Profissao,ListaD,IdCentroSaude)),S),
                                                                                                                      comprimento(S,1),
                                                                                                                      retract(excecao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude))),
                                                                                                                      retract(impreciso(utente(Id))).



involucaoImprecisoAux(centro_saude(Id,Nome,Morada,Telefone,Email)):- solucoes(excecao(centro_saude(_,_,_,_,_)),excecao(centro_saude(Id,Nome,Morada,Telefone,_)),S),
                                                                     comprimento(S,1),
                                                                     retract(excecao(centro_saude(Id,Nome,Morada,Telefone,Email))),
                                                                     retract(impreciso(centro_saude(Id))).


involucaoImprecisoAux(staff(IdStaff,IdCentroSaude,Nome,Email)):- solucoes(excecao(staff(_,_,_,_)),excecao(staff(IdStaff,IdCentroSaude,Nome,_)),S),
                                                                 comprimento(S,1),
                                                                 retract(excecao(staff(IdStaff,IdCentroSaude,Nome,Email))),
                                                                 retract(impreciso(staff(IdStaff))).



involucaoImprecisoAux(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)):- solucoes(excecao(vacinacao_Covid(_,_,_,_,_)),excecao(vacinacao_Covid(IdStaff,IdUtente,_,Vacina,Toma)),S),
                                                                            comprimento(S,1),
                                                                            retract(excecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma))),
                                                                            retract(impreciso(vacinacao_Covid(IdStaff,IdUtente,Toma))).


involucaoImprecisoAux(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)):- solucoes(excecao(fase(_,_,_,_,_,_)),excecao(fase(Fase,DataI,DataF,_,(IdadeM,Doencas),Prof)),S),
                                                                            comprimento(S,1),
                                                                            retract(excecao(fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof))),
                                                                            retract(impreciso(fase(Fase))).

involucaoImprecisoAux(Termo):- retract(excecao(Termo)).  


%----------------interdito-------------------------------
involucaoInterdito(Termo):- involucaoInterditoAux(Termo).
involucaoInterdito(-Termo):- involucaoInterditoAux(Termo).


involucaoInterditoAux(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- retract(interdito(utente(Id))),
                                                                                                                       retract(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)).
                                                                                                                    
                        

involucaoInterditoAux(staff(IdStaff,IdCentroSaude,Nome,Email)):- retract(interdito(staff(IdStaff))),
                                                                 retract(staff(IdStaff,IdCentroSaude,Nome,Email)).
                                                              




%---------------------------------------
% Extensao do predicado teste: Lista -> {V, F}

teste([]).
teste([H | T]) :- H, teste(T).


%---------------\----------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado insere: Termo -> {V, F}

insere(Termo) :- assert(Termo).
insere(Termo) :- retract(Termo), !, fail.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado remove: Termo -> {V, F}

remove(Termo) :- retract(Termo).
remove(Termo) :- assert(Termo), !, fail.

%--------------------------------------------
% Extensao do predicado demo: Questao, Flag -> {V, F, D}

demo(Questao, verdadeiro) :- Questao.
demo(Questao, falso) :- -Questao.
demo(Questao, desconhecido) :- nao(Questao), nao(-Questao).



%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% NEGACAO FORTE

-utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude) :-
    nao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)),
    nao(excecao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude))).

-centro_saude(Id,Nome,Morada,Telefone,Email) :-
    nao(centro_saude(Id,Nome,Morada,Telefone,Email)),
    nao(excecao(centro_saude(Id,Nome,Morada,Telefone,Email))).

-staff(IdStaff,IdCentroSaude,Nome,Email) :-
    nao(staff(IdStaff,IdCentroSaude,Nome,Email)),
    nao(excecao(staff(IdStaff,IdCentroSaude,Nome,Email))).

-vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma) :-
    nao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)),
    nao(excecao(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma))).
    
-fase(Fase,DataI, DataF,Idade,(Idade1,Doencas),Prof) :-
    nao(fase(Fase,DataI, DataF,Idade,(Idade1,Doencas),Prof)),
    nao(excecao(fase(Fase,DataI, DataF,Idade,(Idade1,Doencas),Prof))).

%-----------------------------------------------------------


removerPerfeito(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)):- perfeito(utente(Id)),
                                                                                                                 retract(perfeito(utente(Id))),
                                                                                                                 retract(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)).

removerPerfeito(centro_saude(Id,Nome,Morada,Telefone,Email)):- perfeito(centro_saude(Id)),
                                                               retract(perfeito(centro_saude(Id))),
                                                               retract(centro_saude(Id,Nome,Morada,Telefone,Email)).

removerPerfeito(staff(Id,IdCentroSaude,Nome,Email)):- perfeito(staff(Id)),
                                                      retract(perfeito(staff(Id))),
                                                      retract(staff(Id,IdCentroSaude,Nome,Email)).

removerPerfeito(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)):- perfeito(vacinacao_Covid(IdStaff,IdUtente,Toma)),
                                                                      retract(perfeito(vacinacao_Covid(IdStaff,IdUtente,Toma))),
                                                                      retract(vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)).
        
removerPerfeito(fase(Fase,DataI, DataF,Idade,(Idade1,Doencas),Prof)):- perfeito(fase(Fase)),
                                                                       retract(perfeito(fase(Fase))),
                                                                       retract(fase(Fase,DataI, DataF,Idade,(Idade1,Doencas),Prof)).                                            