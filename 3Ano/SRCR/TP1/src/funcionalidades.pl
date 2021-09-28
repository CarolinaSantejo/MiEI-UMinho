

:- consult('predicadosAuxiliares.pl').
:- consult('baseDeConhecimento.pl').
:- consult('invariantes.pl').


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
                                        elementosNaoComuns(S,S1,Z),
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

faseDeveSerVacinado(IdUtente,Y):- solucoes((IdUtente,Data,Prof,Doencas),utente(IdUtente,_,_,Data,_,_,_,Prof,Doencas,_),[H1|_]),
                  faseCorretaVacinacao(H1,Y).
%---------------

faseCorretaVacinacao((_,_,Prof,_),"1A"):- Prof == 'Medico'.
faseCorretaVacinacao((_,_,Prof,_),"1A"):- Prof == 'Enfermeiro'.
faseCorretaVacinacao((_,_,Prof,_),"1A"):- Prof == 'Policial'.
faseCorretaVacinacao((_,_,Prof,_),"1A"):- Prof == 'Bombeiro'.
faseCorretaVacinacao((_,_,Prof,_),"1A"):- Prof == 'Auxiliar de Saude'.




faseCorretaVacinacao((_,DataNasc,_,_),"1B"):- idadePrioritaria(DataNasc,80).

faseCorretaVacinacao((_,DataNasc,_,L),"1B"):- idadePrioritaria(DataNasc,50), pertence('Insuficiência cardíaca',L).
faseCorretaVacinacao((_,DataNasc,_,L),"1B"):- idadePrioritaria(DataNasc,50), pertence('Doença coronária',L).
faseCorretaVacinacao((_,DataNasc,_,L),"1B"):- idadePrioritaria(DataNasc,50), pertence('Insuficiência renal',L).
faseCorretaVacinacao((_,DataNasc,_,L),"1B"):- idadePrioritaria(DataNasc,50), pertence('DPOC',L).


faseCorretaVacinacao((_,DataNasc,_,_),"2"):- idadePrioritaria(DataNasc,65).

faseCorretaVacinacao((Id,DataNasc,_,_),"2"):- idadePrioritaria(DataNasc,50),
                              nao(idadePrioritaria(DataNasc,65)),
                              nao(semDoencas(Id)).

faseCorretaVacinacao(_,"3").


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
%Verifica se uma data é relativa auma determinada fase

faseVacinacao(dataValida(_,M,A),"1A"):- A = 2020, M = 12.
faseVacinacao(dataValida(_,M,A),"1A"):- A = 2021, M = 1.

faseVacinacao(dataValida(_,M,A),"1B"):- A = 2021 , M = 2.
faseVacinacao(dataValida(_,M,A),"1B"):- A = 2021 , M = 3.

faseVacinacao(dataValida(_,M,A),"2") :- A = 2021 , M = 4.
faseVacinacao(dataValida(_,M,A),"2") :- A = 2021 , M = 5.
faseVacinacao(dataValida(_,M,A),"2") :- A = 2021 , M = 6.

faseVacinacao(dataValida(_,M,A),"3"):- A >= 2021 , M > 6.


%----------------------
faseAnterior("3",X):- X = ["1A","1B","2","3"].
faseAnterior("2",X):- X = ["1A","1B","2"].
faseAnterior("1B",X):- X = ["1A","1B"].
faseAnterior("1A",X):- X = ["1A"].





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
% Extensao do predicado evolucao: Termo -> {V, F}

evolucao(Termo) :- solucoes(Inv, +Termo::Inv, S),
                   insere(Termo),
                   teste(S).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado involucao: Termo -> {V, F}

involucao(Termo) :- Termo,
            solucoes(Inv, -Termo::Inv, S),
                    remove(Termo),
                    teste(S).


%---------------------------------------
% Extensao do predicado teste: Lista -> {V, F}

teste([]).
teste([H | T]) :- H, teste(T).


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado insere: Termo -> {V, F}

insere(Termo) :- assert(Termo).
insere(Termo) :- retract(Termo), !, fail.


%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Extensao do predicado remove: Termo -> {V, F}

remove(Termo) :- retract(Termo).
remove(Termo) :- assert(Termo), !, fail.

%--------------------------------------------
% Extensao do predicado demo: Questao, Flag -> {V, F, D}

demo( Questao,verdadeiro ) :- Questao.
demo( Questao, falso ) :-  nao(Questao).
%demo( Questao,desconhecido ) :- nao( Questao ),
%                                nao( -Questao ).

