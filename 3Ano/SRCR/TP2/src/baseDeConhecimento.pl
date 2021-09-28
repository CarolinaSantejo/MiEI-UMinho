:-set_prolog_flag(discontiguous_warnings,off).
:-set_prolog_flag(single_var_warnings,off).
:-set_prolog_flag(answer_write_options,[max_depth(0)]).






:- dynamic (utente/10).
:- dynamic (centro_saude/5).
:- dynamic (staff/4).
:- dynamic (vacinacao_Covid/5).
:- dynamic (fase/6).
:- dynamic excecao/1.
:- dynamic nulo/1.
:- dynamic perfeito/1.
:- dynamic impreciso/1.
:- dynamic incerto/1.
:- dynamic interdito/1.







%BASE DO CONHECIMENTO

nulo(int01).

%------------------Utentes-------------
utente(1,111111,'Etelvyna',dataValida(26,1,1958),'etelvyna@gmail.com',912342352,'Rua de Cima','Medico',['Insuficiência cardíaca'],1).
utente(2,222222,'Filipa',dataValida(7,7,2000),'filipa@gmail.com',926775817,'Rua de Cima','Estudante',[],2).
utente(3,333333,'Carolina',dataValida(14,12,1947),'nao Tem',25334141,'Rua de Baixo','Reformado',['Doença coronária'],3).
utente(4,444444,'Raquel',dataValida(13,6,2001),'kelinhaMeGusta@gmail.com',926775814,'Rua da Esquerda','Vendedor',['Colesterol elevado'],4).
utente(5,123467,'Sara',dataValida(29,1,1998),'sarocaLaroca@gmail.com',916759213,'Rua da Direita','SuperModel',[],5).

utente(6,1234564,'Mario',dataValida(12,9,1979),'mario@gmail.com',922342354,'Rua da Manteiga','Enfermeiro',[],5).
utente(7,1234574,'Possidonio',dataValida(1,1,1930),'nao tem',936775117,'Rua da Direita','Reformado',['Hipertensão arterial','Diabetes'],4).
utente(8,1234584,'Páscoa',dataValida(14,11,1947),'coelhinha_pascoa@gmail.com',25324541,'Rua Professora Doutora Etelvina','Reformado',['Diabetes'],3).
utente(9,1234594,'Anastácia',dataValida(3,3,2007),'anastacia@gmail.com',916755834,'Rua 26 de Abril','Estudante',[],1).
utente(10,1234674,'Justino',dataValida(9,10,1925),'nao tem',916648913,'Rua da Cabreira','Reformado',['Diabetes','DPOC','Insuficiência cardíaca'],2).
utente(11,1234594,'Domingos',dataValida(13,6,1959),'domingos_Adora_Segundas@gmail.com',911771814,'Rua Imaculado Jesus','Auxiliar de Saude',[],1).
utente(12,12346754,'Aurora',dataValida(5,8,1990),'aurora@gmail.com',92664913,'Rua Azul','Policial',['Obesidade'],1).

utente(13,132346754,'Paulo',dataValida(6,9,1969),'nos_bosques@gmail.com',926649133,'Rua Ativa','Guarda Florestal',[],1).
utente(120,1333222111,'Jessica',dataValida(1,5,1978),'nao tem',9191919191,'Rua Deserta','Contabilista',[],3).

%utente incerto
utente(17,13316467121,inc01,dataValida(25,4,1974),'guida@gmail.com',92664113,'Avenida da Liberdade','Atriz',[],2).

%utente interdito
utente(18,1331645114,'Zeca',dataValida(1,12,1967),'zeca@gmail.com',926641131,int01,'Motorista',[],1).




%-----------------Centros de Saude------------------
centro_saude(1,'Santa Maria','Rua da Estrela',253214998,'santamaria@gmail.com').
centro_saude(2,'Centro Regional de Monção','Rua da Estrela',253214998,'monçãoCS@gmail.com').
centro_saude(3,'Imaculado Cristo','Rua da Estrela',253214998,'iCristo@gmail.com').
centro_saude(4,'Centro Professor Doutor Afonso Henriques','Rua dos Mouros',252234595,'afonso_MataTudo@gmail.com').
centro_saude(5,'Estrela Brilhante','Rua da Estrela',251214990,'estrela_Brilhante@gmail.com').

centro_saude(8,'Santa Amora',inc01,111333444,'amoraHospital@gmail.com').



%-------------------Staff-------------------------
staff(1,1,'Antonio','antonio@gmail.com').
staff(2,2,'Maria','maria@gmail.com').
staff(3,3,'Fátima','fatima@gmail.com').
staff(4,4,'Josefa','josefa@gmail.com').
staff(5,5,'Francisco','francisco@gmail.com').
staff(6,5,'Domingos','domingos@gmail.com').
staff(7,1,'Manuel','manuel@gmail.com').


% staff incerto
staff(11,1,inc01,'antonio@gmail.com').

%staff interdito
staff(12,1,'Rosa',int01).






%--------------------Vacinações------------------------
vacinacao_Covid(1,10,dataValida(17,2,2021),'Astrazeneca',1).
vacinacao_Covid(5,10,dataValida(20,2,2021),'Astrazeneca',2).

vacinacao_Covid(2,1,dataValida(4,12,2020),'Pfizer',1).
vacinacao_Covid(4,1,dataValida(28,12,2020),'Pfizer',2).

vacinacao_Covid(4,11,dataValida(22,12,2020),'Pfizer',1).
vacinacao_Covid(5,11,dataValida(1,1,2021),'Pfizer',2).

vacinacao_Covid(2,8,dataValida(2,4,2021),'Pfizer',1).

vacinacao_Covid(6,3,dataValida(1,2,2021),'Astrazeneca',1).

vacinacao_Covid(7,6,dataValida(21,12,2020),'Pfizer',1).
vacinacao_Covid(2,6,dataValida(30,12,2020),'Pfizer',2).

vacinacao_Covid(5,12,dataValida(2,3,2021),'Pfizer',1). %Vacinada tarde demais
vacinacao_Covid(1,7,dataValida(1,4,2021),'Astrazeneca',1). %Vacinada tarde demais
vacinacao_Covid(3,2,dataValida(21,2,2021),'Pfizer',1). %Vacina  Cedo demais


vacinacao_Covid(3,8,dataValida(15,4,2021),inc01,2).







%---------------------Fase---------------------------------
fase('1A',dataValida(1,12,2020),dataValida(31,1,2021),126,(126,[]),['Medico','Enfermeiro','Policial','Bombeiro','Auxiliar de Saude']).
fase('1B',dataValida(1,2,2021),dataValida(31,3,2021),80,(50,['Insuficiência cardíaca','Doença coronária','Insuficiência renal','DPOC']),[]).
fase('2',dataValida(1,4,2021),dataValida(31,6,2021),65,(50,['Diabetes','Neoplasia maligna ativa','Doença renal crónica','Insuficiência hepática','Hipertensão arterial','Obesidade']),[]).
fase('3',dataValida(1,7,2021),dataValida(31,12,2021),0,(0,[]),[]).



%------------------

perfeito(utente(1)).
perfeito(utente(2)).
perfeito(utente(3)).
perfeito(utente(4)).
perfeito(utente(5)).
perfeito(utente(6)).
perfeito(utente(7)).
perfeito(utente(8)).
perfeito(utente(9)).
perfeito(utente(10)).
perfeito(utente(11)).
perfeito(utente(12)).
perfeito(utente(13)).

perfeito(utente(14)).
perfeito(utente(15)).
perfeito(utente(100)).
perfeito(utente(120)).


perfeito(centro_saude(1)).
perfeito(centro_saude(2)).
perfeito(centro_saude(3)).
perfeito(centro_saude(4)).
perfeito(centro_saude(5)).

perfeito(centro_saude(6)).
perfeito(centro_saude(7)).

perfeito(staff(1)).
perfeito(staff(2)).
perfeito(staff(3)).
perfeito(staff(4)).
perfeito(staff(5)).
perfeito(staff(6)).
perfeito(staff(7)).

perfeito(staff(8)).
perfeito(staff(9)).
perfeito(staff(10)).

perfeito(vacinacao_Covid(1,10,1)).
perfeito(vacinacao_Covid(5,10,2)).
perfeito(vacinacao_Covid(2,1,1)).
perfeito(vacinacao_Covid(4,1,2)).
perfeito(vacinacao_Covid(4,11,1)).
perfeito(vacinacao_Covid(5,11,2)).
perfeito(vacinacao_Covid(2,8,1)).
perfeito(vacinacao_Covid(6,3,1)).
perfeito(vacinacao_Covid(7,6,1)).
perfeito(vacinacao_Covid(2,6,2)).
perfeito(vacinacao_Covid(5,12,1)).
perfeito(vacinacao_Covid(1,7,1)).
perfeito(vacinacao_Covid(3,2,1)).
perfeito(vacinacao_Covid(4,3,1)).
perfeito(vacinacao_Covid(5,4,1)).
perfeito(vacinacao_Covid(2,7,1)).
perfeito(vacinacao_Covid(4,8,1)).


perfeito(fase('1A')).
perfeito(fase('1B')).
perfeito(fase('2')).
perfeito(fase('3')).
perfeito(fase('4')).






%------------------Conhecimento Negativo-------------------



-utente(14,111111,'Marilia',dataValida(2,12,1988),'Marilia@gmail.com',585662688,'Rua do Cruzeiro','Vendedor',[],4).
-utente(15,222222,'Miguel',dataValida(1,7,2002),'Miguel@gmail.com',585662689,'Rua do Quintal','Estudante',[],2).
-utente(100,333333,'Henrique',dataValida(13,11,1973),'Henrique@gmail.com',585662610,'Rua da Forja','Professor',['Doença coronária'],5).





-centro_saude(6,'São José','Rua do Pomar',253219983,'saoJose@gmail.com').
-centro_saude(7,'Lusíadas','Avenida dos Mouros',211765234,'lusiadas@gmail.com').




-staff(8,4,'Josefa','josefa@gmail.com').
-staff(9,5,'Daniela','dani@gmail.com').
-staff(15,1,'Gabriel','gabriel@gmail.com').



-vacinacao_Covid(4,3,dataValida(20,12,2020),'Pfizer',1).
-vacinacao_Covid(5,4,dataValida(1,5,2021),'Pfizer',1).


-fase('4',dataValida(1,1,2022),dataValida(31,3,2022),20,(0,[]),[]).






%-----------------------Excecoes--------------
excecao(utente(Id,SegurancaSocial,_,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude)) :- utente(Id,SegurancaSocial,inc01,DataNasc,Email,Telefone,Morada,Profissao,ListaD,IdCentroSaude).

excecao(utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,_,Profissao,ListaD,IdCentroSaude)) :- utente(Id,SegurancaSocial,Nome,DataNasc,Email,Telefone,int01,Profissao,ListaD,IdCentroSaude).

excecao(utente(16,1331646711,'Vasco',dataValida(1,1,1980),'vasco@gmail.com',92664113,'Rua da Ponte','Empregado',[],3)).
excecao(utente(16,1331646711,'Vasco',dataValida(1,1,1980),'vasco@gmail.com',92664113,'Rua Filipa Pereira','Empregado',[],3)).


excecao(centro_saude(Id,Nome,_,Telefone,Email)):-centro_saude(Id,Nome,inc01,Telefone,Email) .

excecao(centro_saude(7,'Gil Vicente','Rua da vida',111777888,'gv@gmail.com')).
excecao(centro_saude(7,'Gil Vicente','Rua da vida',111777888,'gilVicente@gmail.com')).



excecao(staff(IdStaff,IdCentroSaude,_,Email)) :- staff(IdStaff,IdCentroSaude,inc01,Email).
excecao(staff(IdStaff,IdCentroSaude,Nome,_)) :- staff(IdStaff,IdCentroSaude,Nome,int01).


excecao(staff(10,4,'Josefina','josefina@gmail.com')).
excecao(staff(10,4,'Josefina','josefina@hotmail.com')).



excecao(vacinacao_Covid(IdStaff,IdUtente,Data,_,Toma)) :- vacinacao_Covid(IdStaff,IdUtente,Data,inc01,Toma).

excecao(vacinacao_Covid(2,120,dataValida(3,12,2020),'Pfizer',1)).
excecao(vacinacao_Covid(2,120,dataValida(4,12,2020),'Pfizer',1)).




excecao(fase('Aux',dataValida(1,10,2022),dataValida(31,10,2022),40,(0,[]),[])).
excecao(fase('Aux',dataValida(1,10,2022),dataValida(31,10,2022),41,(0,[]),[])).



%---------------Conhecimento Imperfeito------------------------



incerto(utente(17)).
incerto(centro_saude(8)).
incerto(staff(11)).
incerto(vacinacao_Covid(3,8,2)).


interdito(utente(18)).
interdito(staff(12)).




impreciso(utente(16)).
impreciso(centro_saude(7)).
impreciso(staff(10)).
impreciso(vacinacao_Covid(2,120,1)).
impreciso(fase('Aux')).

