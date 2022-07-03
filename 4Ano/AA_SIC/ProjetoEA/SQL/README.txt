
Para correrem os scripts na console do IntelliJ:

1º Correr do ficheiro 'inserts.sql'

2º Correr do ficheiro 'procedures.sql'
	-> Se der este erro " 'CREATE/ALTER PROCEDURE' must be the first statement in a query batch ", tem de se correr cada procedure à vez na consola, porque o procedure tem de começar na primeira linha (correr um procedure, apagar, correr outro procedure, apagar, etc)


Nota: A ordem tem de ser essa por causa das dependências entre tabelas!