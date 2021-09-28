:-set_prolog_flag(discontiguous_warnings,off).
:-set_prolog_flag(single_var_warnings,off).
:-set_prolog_flag(answer_write_options,[max_depth(0)]).




:- dynamic (utente/10).
:- dynamic (centro_saude/5).
:- dynamic (staff/4).
:- dynamic (vacinacao_Covid/5).



%BASE DO CONHECIMENTO

%------------------Utentes-------------
utente(1,123456,'Etelvyna',dataValida(26,1,1958),'etelvyna@gmail.com',912342352,'Rua de Cima','Medico',['Insuficiência cardíaca'],1).
utente(2,123457,'Filipa',dataValida(7,7,2000),'filipa@gmail.com',926775817,'Rua de Cima','Estudante',[],2).
utente(3,123458,'Carolina',dataValida(14,12,1947),'nao Tem',25334141,'Rua de Baixo','Reformado',['Doença coronária'],3).
utente(4,123459,'Raquel',dataValida(13,6,2001),'kelinhaMeGusta@gmail.com',926775814,'Rua da Esquerda','Vendedor',['Colesterol elevado'],4).
utente(5,123467,'Sara',dataValida(29,1,1998),'sarocaLaroca@gmail.com',916759213,'Rua da Direita','SuperModel',[],5).

utente(6,1234564,'Mario',dataValida(12,9,1979),'mario@gmail.com',922342354,'Rua da Manteiga','Enfermeiro',[],5).
utente(7,1234574,'Possidonio',dataValida(1,1,1930),'nao tem',936775117,'Rua da Direita','Reformado',['Hipertensão arterial','Diabetes'],4).
utente(8,1234584,'Páscoa',dataValida(14,11,1947),'coelhinha_pascoa@gmail.com',25324541,'Rua Professora Doutora Etelvina','Reformado',['Diabetes'],3).
utente(9,1234594,'Anastácia',dataValida(3,3,2007),'anastacia@gmail.com',916755834,'Rua 26 de Abril','Estudante',[],1).
utente(10,1234674,'Justino',dataValida(9,10,1925),'nao tem',916648913,'Rua da Cabreira','Reformado',['Diabetes','DPOC','Insuficiência cardíaca'],2).
utente(11,1234594,'Domingos',dataValida(13,6,1959),'domingos_Adora_Segundas@gmail.com',911771814,'Rua Imaculado Jesus','Auxiliar de Saude',[],1).
utente(12,12346754,'Aurora',dataValida(5,8,1990),'aurora@gmail.com',92664913,'Rua Azul','Policial',['Obesidade'],1).

utente(13,132346754,'Paulo',dataValida(6,9,1969),'nos_bosques@gmail.com',92664913,'Rua Ativa','Guarda Florestal',[],1).


%-----------------Centros de Saúde------------------
centro_saude(1,'Santa Maria','Rua da Estrela',253214998,'santamaria@gmail.com').
centro_saude(2,'Centro Regional de Monção','Rua da Estrela',253214998,'monçãoCS@gmail.com').
centro_saude(3,'Imaculado Cristo','Rua da Estrela',253214998,'iCristo@gmail.com').
centro_saude(4,'Centro Professor Doutor Afonso Henriques','Rua dos Mouros',252234595,'afonso_MataTudo@gmail.com').
centro_saude(5,'Estrela Brilhante','Rua da Estrela',251214990,'estrela_Brilhante@gmail.com').

%-------------------Staff-------------------------
staff(1,1,'Antonio','antonio@gmail.com').
staff(2,2,'Maria','maria@gmail.com').
staff(3,3,'Fátima','fatima@gmail.com').
staff(4,4,'Josefa','josefa@gmail.com').
staff(5,5,'Francisco','francisco@gmail.com').
staff(6,5,'Domingos','domingos@gmail.com').
staff(7,1,'Manuel','manuel@gmail.com').

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