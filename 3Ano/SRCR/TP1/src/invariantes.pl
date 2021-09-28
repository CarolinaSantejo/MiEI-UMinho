% Invariantes

%--------------------------------- - - - - - - - - - -  -  -  -  -   -
% Definição de invariante

:-op(900,xfy,'::').



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
        comprimento(S, N),
        N == 1,
        solucoes(SegurancaSocial, utente(_,SegurancaSocial,_,_,_,_,_,_,_,_), S1),
        comprimento(S1, N1),
        N1 == 1,
        solucoes(IdCentroSaude, centro_saude(IdCentroSaude, _, _, _,_), S2),
        comprimento(S2, N2),
        N2 == 1
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
        comprimento(S,N),
        N==1
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
        comprimento(S,N),
        N==1,
        solucoes(IdCentroSaude,centro_saude(IdCentroSaude,_,_,_,_),S1),
        comprimento(S1,N1),
        N1==1
 ).

-staff(Id,_,_,_) :: (
        solucoes(Id,vacinacao_Covid(Id,_,_,_,_),S),
        comprimento(S,N),
        N==0
    ).

% ----Para adicionar uma segunda vacina, a pessoa tem de ter tomado a
% primeira dose!
+vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma) :: (
        Toma == 2,
        integer(IdStaff),
        integer(IdUtente),
        dataValida(Data),
        atom(Vacina),
        solucoes(IdStaff,staff(IdStaff,_,_,_),S),
        comprimento(S,N),
        N==1,
        solucoes(IdUtente,utente(IdUtente,_,_,_,_,_,_,_,_,_),S1),
        comprimento(S1,N1),
        N1==1,
        solucoes((IdStaff,IdUtente,Data,Vacina,Toma),vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma),S2),
        comprimento(S2,N2),
        N2==1,
        nao(naoRecebeuPrimeiraVacina(IdUtente)),
        solucoes(NomeVacina,vacinacao_Covid(_,IdUtente,_,NomeVacina,1),[H|_]),
        H == Vacina
    ).

+vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma) :: (
        Toma == 1,
        integer(IdStaff),
        integer(IdUtente),
        dataValida(Data),
        atom(Vacina),
        solucoes(IdStaff,staff(IdStaff,_,_,_),S),
        comprimento(S,N),
        N==1,
        solucoes(IdUtente,utente(IdUtente,_,_,_,_,_,_,_,_,_),S1),
        comprimento(S1,N1),
        N1==1,
        solucoes((IdStaff,IdUtente,Data,Vacina,Toma),vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma),S2),
        comprimento(S2,N2),
        N2==1
        ).



-vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma)::(
        solucoes((IdStaff,IdUtente,Data,Vacina,Toma),vacinacao_Covid(IdStaff,IdUtente,Data,Vacina,Toma),S),
        comprimento(S,N),
        N==0
).
