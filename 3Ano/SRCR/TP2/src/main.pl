%MENU


:- consult('funcionalidades.pl').



main :-
    repeat,
    nl,nl,
    write('--------------------------------MENU-----------------------------------'), nl,
    write('1. Consultar utentes vacinados'), nl,
    write('2. Consultar utentes não vacinados, com 0 ou 1 toma da vacina'), nl,
    write('3. Consultar utentes vacinados indevidamente'), nl,
    write('4. Consultar utentes não vacinados e que são candidatos à vacinação'), nl,
    write('5. Consultar utentes a quem falta a segunda toma da vacina'), nl,
    write('6. Consultar utentes que não tomaram qualquer dose da vacina'), nl,
    write('7. Determinar o nº de vacinações em cada mês do ano'), nl,
    write('8. Determinar centros de saúde que permitiram vacinações indevidas'), nl,
    write('9. Consultar o membro do staff com mais vacinas aplicadas'), nl,
    write('0. Exit'), nl,nl,
    write('Choose : '),
    read(Z),
    ( Z = 0 -> !, fail ; true ),
    nl,nl,
    action_for(Z),
    fail.

%Funcionalidades
action_for(1) :-
    utentesVacinados(L),
    write(L), nl.

action_for(2) :-
    utentesNaoVacinados(L),
    write(L), nl.

action_for(3) :-
    utentesVacinadosIndevidamente(L),
    write(L), nl.

action_for(4) :-
    write('Indique a fase ("1A". ou "1B". ou "2". ou "3".)'),!,nl,
    read(Fase),
    utentesNaoVacinadosCandidatos(L,Fase),
    write(L), nl.

action_for(5) :-
    utentesFaltaSegundaDose(L),
    write(L),nl.

%Funcionalidades extra

action_for(6) :-
    utenteSemQualquerDose(L),
    write(L),nl.

action_for(7) :-
    mesesVacinacoes(L),
    write(L),nl.

action_for(8) :-
    cs_Vacinacao_Invalida(L),
    write(L),nl.

action_for(9) :-
    staffMaisVacinacoes(Max,IdNomes),!,
    write('Membro do staff: '),
    write(IdNomes),nl,
    write('Nº de vacinas: '),
write(Max),nl.