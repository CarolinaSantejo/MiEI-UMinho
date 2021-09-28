% Invariantes

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definição de invariante

:-op(900,xfy,'::').
:-op(900,xfy,:-:).
:-op(900,xfy,:~:).

:- discontiguous (::)/2.
:- discontiguous (:-:)/2.
:- discontiguous (:~:)/2.




% Invariantes
% Nao permitir a insercao de um utente que ja exista (mesmo id)
% Id deve ser inteiro
% Idade deve ser inteira e estar entre os valores [0, 125]
% Centro De Saude deve estar na base de conhecimento

+utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude) :: (
        integer(Id),
        integer(SegurancaSocial),
        integer(Telefone),
        integer(IdCentroSaude),
        dataValida(DataNasc),
        atom(Nome),
        atom(Email),
        atom(Morada),
        atom(Profissao),
        listaAtoms(ListaD),
        solucoes(Id, utente(Id,_,_,_,_,_,_,_,_,_), S),
        comprimento(S, 1),
        solucoes(Id, -utente(Id,_,_,_,_,_,_,_,_,_), SA),
        comprimento(SA, 0),
        solucoes(SegurancaSocial, utente(_,SegurancaSocial,_,_,_,_,_,_,_,_), S1),
        comprimento(S1, 1),
        solucoes(IdCentroSaude, centro_saude(IdCentroSaude, _, _, _,_), S2),
        comprimento(S2, 1)

 ).

+utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude) :: (
        solucoes(Morada, (utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude),nulo(Morada)), S3),
        comprimento(S3, N),
        N >= 0,
        N =< 1

 ).

-utente(Id,_,_,_,_,_,_,_,_,_)::(
        solucoes(Id,vacinacao_Covid(_,Id,_,_,_),S),
        comprimento(S,N),
        N == 0

 ).


+centro_saude(Id,Nome,Morada,Telefone,Email) :: (
        integer(Id),
        integer(Telefone),
        atom(Nome),
        atom(Morada),
        atom(Email),
        solucoes(Id,centro_saude(Id,_,_,_,_),S),
        comprimento(S,1),
        solucoes(Id,-centro_saude(Id,_,_,_,_),SA),
        comprimento(SA,0)
).



-centro_saude(Id,_,_,_,_) :: (
        solucoes(Id,staff(_,Id,_,_),S),
        comprimento(S,N),
        N==0,
        solucoes(Id,utente(_,_,_,_,_,_,_,_,_,Id),S1),
        comprimento(S1,N1),
        N1==0
 ).


+staff(IdStaff,IdCentroSaude,Nome,Email) :: (
        integer(IdStaff),
        integer(IdCentroSaude),
        atom(Nome),
        atom(Email),
        solucoes(IdStaff,staff(IdStaff,_,_,_),S),
        comprimento(S,1),
        solucoes(IdStaff,-staff(IdStaff,_,_,_),SA),
        comprimento(SA,0),
        solucoes(IdCentroSaude,centro_saude(IdCentroSaude,_,_,_,_),S1),
        comprimento(S1,1)
).



+staff(IdStaff,IdCentroSaude,Nome,Email) :: (
        solucoes(Email,(staff(IdStaff,IdCentroSaude,Nome,Email),nulo(Email)),S2),
        comprimento(S2,N),
        N >= 0,
        N =< 1
).



-staff(Id,_,_,_) :: (
        solucoes(Id,vacinacao_Covid(Id,_,_,_,_),S),
        comprimento(S,N),
        N==0
    ).

% ----Para adicionar uma segunda vacina, a pessoa tem de ter tomado a
% primeira dose!
+vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,2) :: (
	    integer(IdStaff),
        integer(IdUtente),
        dataValida(Data),
        atom(Vacina),
        solucoes(IdStaff,staff(IdStaff,_,_,_),S),
        comprimento(S,1),
        solucoes(IdUtente,utente(IdUtente,_,_,_,_,_,_,_,_,_),S1),
        comprimento(S1,1),
        solucoes((IdStaff,IdUtente,Data,Vacina,2),vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,2),S2),
        comprimento(S2,1),
        nao(naoRecebeuPrimeiraVacina(IdUtente)),
        solucoes((IdStaff,IdUtente,2),-vacinacao_Covid(IdStaff,IdUtente,_,_,2),SA),
        comprimento(SA,0)

    ).

+vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,1) :: (
        integer(IdStaff),
        integer(IdUtente),
        dataValida(Data),
        atom(Vacina),
        solucoes(IdStaff,staff(IdStaff,_,_,_),S),
        comprimento(S,1),
        solucoes(IdUtente,utente(IdUtente,_,_,_,_,_,_,_,_,_),S1),
        comprimento(S1,1),
        solucoes((IdStaff,IdUtente,Data,Vacina,1),vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,1),S2),
        comprimento(S2,1),
        solucoes((IdStaff,IdUtente,1),-vacinacao_Covid(IdStaff,IdUtente,_,_,1),SA),
        comprimento(SA,0)
        ).



-vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)::(
        solucoes((IdStaff,IdUtente,Data,Vacina,Toma),vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma),S),
        comprimento(S,N),
        N==0
).


%!!!!!Falta verificar com o grupo sobre se uma fase coincide ou não com outra!!!!!

+fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)::(
    atom(Fase),
    dataValida(DataI),
    dataValida(DataF),
    dataAntesIgual(DataI,DataF),
    integer(Idade),
    integer(IdadeM),
    listaAtoms(Doencas),
    listaAtoms(Prof),
    solucoes(Fase,fase(Fase,_,_,_,_,_),S),
    comprimento(S,1),
    solucoes(Fase,-fase(Fase,_,_,_,_,_),SA),
    comprimento(SA,0)
 ).



+fase(Fase,DataI,DataF,Idade,(_,Doencas),Prof):-:(
    atom(Fase),
    dataValida(DataI),
    dataValida(DataF),
    dataAntesIgual(DataI,DataF),
    integer(Idade),
    listaAtoms(Doencas),
    listaAtoms(Prof),
    solucoes(Fase,fase(Fase,_,_,_,(_,_),_),S),
    comprimento(S,1),
    solucoes(Fase,-fase(Fase,_,_,_,_,_),SA),
    comprimento(SA,0)
 ).





-fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)::(
    solucoes((Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof),
    fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof),S),
    comprimento(S,0)
    )



%--------------INVARIANTES PARA A NEGAÇÃO FORTE--------------------------------


+(-utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)) :: (
        integer(Id),
        integer(SegurancaSocial),
        integer(Telefone),
        integer(IdCentroSaude),
        dataValida(DataNasc),
        atom(Nome),
        atom(Email),
        atom(Morada),
        atom(Profissao),
        listaAtoms(ListaD),
        solucoes(Id, -utente(Id,_,_,_,_,_,_,_,_,_), S),
        comprimento(S, 1),
        solucoes(SegurancaSocial, -utente(_,SegurancaSocial,_,_,_,_,_,_,_,_), S1),
        comprimento(S1, 1)
 ).


+(-utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)) :: (
        solucoes(Morada, (-utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude),nulo(Morada)), S3),
        comprimento(S3, N),
        N >= 0,
        N =< 1

 ).


+(-centro_saude(Id,Nome,Morada,Telefone,Email)) :: (
        integer(Id),
        integer(Telefone),
        atom(Nome),
        atom(Morada),
        atom(Email),
        solucoes(Id,-centro_saude(Id,_,_,_,_),S1),
        comprimento(S1,1)
  ).



+(-staff(IdStaff,IdCentroSaude,Nome,Email)) :: (
        integer(IdStaff),
        integer(IdCentroSaude),
        atom(Nome),
        atom(Email),
        solucoes(IdStaff,-staff(IdStaff,_,_,_),S1),
        comprimento(S1,1)
 ).


%Para conhecimento interdito
+(-staff(IdStaff,_,_,Email)) :: (
        solucoes(Email,((-staff(IdStaff,_,_,Email)),nulo(Email)),S2),
        comprimento(S2,N),
        N >= 0,
        N =< 1
 ).


+(-vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)) :: (
        integer(IdStaff),
        integer(IdUtente),
        dataValida(Data),
        atom(Vacina),
        solucoes((IdStaff,IdUtente,Data,Vacina,Toma),-vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma),S3),
        comprimento(S3,1)
    ).






+(-fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof))::(
    atom(Fase),
    dataValida(DataI),
    dataValida(DataF),
    dataAntesIgual(DataI,DataF),
    integer(Idade),
    integer(IdadeM),
    listaAtoms(Doencas),
    listaAtoms(Prof),
    solucoes(Fase,-fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof),S2),
    comprimento(S2,1)

    ).



+(-fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof)):-:(
    atom(Fase),
    dataValida(DataI),
    dataValida(DataF),
    dataAntesIgual(DataI,DataF),
    integer(Idade),
    listaAtoms(Doencas),
    listaAtoms(Prof),
    solucoes(Fase,-fase(Fase,DataI,DataF,Idade,(IdadeM,Doencas),Prof),S2),
    comprimento(S2,1)

    ).



+(-fase(Fase,DataI,DataF,_,(IdadeM,Doencas),Prof)):~:(
    atom(Fase),
    dataValida(DataI),
    dataValida(DataF),
    dataAntesIgual(DataI,DataF),
    integer(IdadeM),
    listaAtoms(Doencas),
    listaAtoms(Prof),
    solucoes(Fase,-fase(Fase,DataI,DataF,_,(IdadeM,Doencas),Prof),S2),
    comprimento(S2,1)

    ).


%----------------------

%Invariante estrutural: Não permitir  a inserção de conhecimento contraditório

+Termo :: (
    nao(-Termo)
).

+(-Termo) :: (
    nao(Termo)
).




%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Impedir adição de exceções repetidas

+(excecao(Termo)) ::(	
    solucoes(Termo, excecao(Termo), S),
    comprimento(S, 1)
).

