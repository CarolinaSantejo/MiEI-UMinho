#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h> 
#include <string.h>

#define MAX 1000000
#define FILENAME "file_pessoas"

typedef struct Person {
	char name[200];
	int age;
} Person;

int new_person(int f, char* name, int age) {
	Person person;
	strcpy(person.name, name);
	person.age = age;
	int res = write(f, &person, sizeof(struct Person));
	if (res<0) {
		perror("Falha a escrever para ficheiro");
		return -1;
	}
	return res;
}

int person_change_age(int f, char* name, int age) {
	int res = 0;
	Person p;
	while ((res = read(f, &p, sizeof(Person))) > 0) {
		if (strcmp(p.name,name) == 0) {
			p.age = age;
			f = lseek(f, -(sizeof(Person)), SEEK_CUR);
			res = write(f, &p, sizeof(Person));
			if (res<0) {
				perror("Falha a escrever para ficheiro");
				return -1;
			}
			break;
		}
	}
	return res;
}

void main(int argc, char const *argv[]) {
	int i = 0;
	int f = open(FILENAME,  O_CREAT | O_RDWR | O_APPEND ,0600,0700);
/*	for (;i<MAX; i++) {
		if (new_person(f,"nos",45) > 0) printf("Pessoa adicionada com sucesso.\n");
	}*/
	if (person_change_age(f,"nos",34) > 0) printf("Idade alterada com sucesso.\n");
	else {
		printf("Pessoa nao encontrada.\n");
	}
}

/*
int person_change_age_v2(long pos, int age);*/