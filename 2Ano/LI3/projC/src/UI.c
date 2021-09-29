#include <stdio.h>
#include <stdlib.h>

#include "UI.h"

/**
 * Displays the main menu.
 */
void mainMenuUI(bool loadedSGV, bool* errorMessage) {
    clearWindow();
    puts(CYAN "-------------------------------------------\n\tSistema de Gestão de Vendas\n-------------------------------------------\n" RESET);
    puts("1) Carregar ficheiros.");
    printf(loadedSGV ? "" : RED_BOLD);
    puts("2) Produtos que começam por uma dada letra.\n"
        "3) Total de lucro e vendas de um dado produto num dado mês.\n"
        "4) Produtos não vendidos na totalidade ou por filial.\n"
        "5) Clientes que compraram em todas as filiais\n"
        "6) Número de clientes que não realizaram compras e de produtos que ninguém comprou.\n"
        "7) Total de produtos comprados por um dado cliente.\n"
        "8) Lucro e nº de vendas totais durante um intervalo de tempo\n"
        "9) Clientes que compraram determinado produto.\n"
        "10) Produtos mais comprados por um dado cliente num dado mês.\n"
        "11) Produtos mais vendidos.\n"
        "12) Produtos com que um dado cliente gastou mais dinheiro.\n"
        "13) Informação dos ficheiros carregados.\n");
    puts(RESET "14) Modo benchmark\n\n0) Sair.\n");
    if(*errorMessage) {
        puts(RED "Opção indisponível - não existem ficheiros carregados.\n" RESET);
        *errorMessage = false;
    }
    printf("Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays a loading screen.
 */
void loadingUI() {
    clearWindow();
    puts("LOADING...");
}

/**
 * Displays info about the loaded files and, optionally, load time.
 */
void loadInfoUI(char mode, char* path, size_t total, size_t valid, double time) {
    switch(mode) {
        case 't':
            clearWindow();
            puts(YELLOW "\t***INFORMAÇÃO DOS FICHEIROS***\n" RESET);
            break;
        case 'p':
            puts(GREEN "Produtos\n" RESET);
            printf("Ficheiro lido = '%s'\n", path);
            printf("Produtos totais = %zu\n", total);
            printf("Produtos válidos = %zu\n\n", valid);
            break;
        case 'c':
            puts(GREEN "Clientes\n" RESET);
            printf("Ficheiro lido = '%s'\n", path);
            printf("Clientes totais = %zu\n", total);
            printf("Clientes válidos = %zu\n\n", valid);
            break;
        case 's':
            puts(GREEN "Vendas\n" RESET);
            printf("Ficheiro lido = '%s'\n", path);
            printf("Vendas totais = %zu\n", total);
            printf("Vendas válidas = %zu\n\n", valid);
            break;
        case 'l':
            printf("Tempo de leitura dos ficheiros: %lfs\n\n", time);
            break;
        case 'e':
            puts("Pressione ENTER para continuar.");
            break;
    }
}

/**
 * Displays the first query's menu.
 */
void getFolderUI(char mode) {
    clearWindow();
    puts(YELLOW "\t***CARREGAMENTO DE FICHEIROS***\n" RESET);
    switch(mode) {
        case 'a':
            puts("1) Carregar ficheiros predefinidos (ficheiros na pasta db, 1 milhão de vendas).\n"
                "2) Carregar ficheiros predefinidos (ficheiros na pasta db, 3 milhões de vendas).\n"
                "3) Carregar ficheiros predefinidos (ficheiros na pasta db, 5 milhões de vendas).\n"
                "4) Usar ficheiros personalizados.\n");
            printf("Escreva o número correspondente à opção pretendida.\n> ");
            break;
        case 'c':
            printf("Introduza o caminho para o ficheiro dos clientes. (caminho a partir da pasta projC)\n> ");
            break;
        case 'p':
            printf("Introduza o caminho para o ficheiro dos produtos. (caminho a partir da pasta projC)\n> ");
            break;
        case 's':
            printf("Introduza o caminho para o ficheiro das vendas. (caminho a partir da pasta projC)\n> ");
            break;
    }
}

/**
 * Used to display a StringArray by the Navigator module.
 */
void navigatorUI(char mode, char* string, size_t* pos) {
    switch(mode) {
        case 't':
            clearWindow();
            puts(string);
            break;
        case 'e':
            printf("%6zu. %s  ", (*pos) + 1, string);
            break;
        case 'n':
            puts("");
            break;
        case 'p':
            printf("\nPágina %lu de %lu.\n", *pos, *(pos+1));

            puts("\n1) Página seguinte.\n"
                "2) Página anterior.\n\n"
                "0) Retroceder.\n");
            printf("Escreva o número correspondente à opção pretendida.\n> ");
    }
}

/**
 * Displays the second query's menu.
 */
void query2UI() {
    clearWindow();
    puts(YELLOW "\t***PROCURA DE PRODUTOS INICIADOS POR UMA DADA LETRA***\n" RESET);
    printf("Introduza uma letra.\n> ");
}

/**
 * Displays the second query's results.
 */
void query2ResultUI(char letter, size_t numP) {
    clearWindow();
    puts(YELLOW "\t***PROCURA DE PRODUTOS INICIADOS POR UMA DADA LETRA***\n" RESET);
    printf("Número total de produtos cujo código começa por %c: " MAGENTA "%zu\n\n" RESET, letter, numP);
    puts("1) Ver lista de produtos.\n\n"
        "0) Retroceder.\n");
    printf("Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays the third query's menu.
 */
void query3UI(char mode) {
    clearWindow();
    puts(YELLOW "\t***TOTAL DE VENDAS E LUCRO DE UM PRODUTO***\n" RESET);
    if(mode == 'p') printf("Introduza um código de produto.\n> ");
    else if(mode == 'm') printf("Introduza um mês (número entre 1 e 12).\n> ");
}

/**
 * Displays the third query's results.
 */
void query3ResultUI(char branchMode, char* prodID, int month, int* sales, double* profit) {
    clearWindow();
    puts(YELLOW "\t***TOTAL DE VENDAS E LUCRO DE UM PRODUTO***\n" RESET);
    printf(GREEN "Produto %s, mês %d\n", prodID, month);
    if(branchMode == '2') {
        puts("Todas as filiais\n\n" RESET);
        printf("Vendas totais do produto em modo N: " MAGENTA "%10d\n" RESET, sales[0] + sales[1] + sales[2]);
        printf("Vendas totais do produto em modo P: " MAGENTA "%10d\n" RESET, sales[3] + sales[4] + sales[5]);
        printf("Lucro total do produto em modo N:   " MAGENTA "%10.2lf\n" RESET, profit[0] + profit[1] + profit[2]);
        printf("Lucro total do produto em modo P:   " MAGENTA "%10.2lf\n\n" RESET, profit[3] + profit[4] + profit[5]);
    }
    else {
        puts("Filial a filial\n" RESET);
        puts("Filial                                       1          2          3");
        printf("Vendas totais do produto em modo N: " MAGENTA "%10d %10d %10d\n" RESET, sales[0], sales[1], sales[2]);
        printf("Vendas totais do produto em modo P: " MAGENTA "%10d %10d %10d\n" RESET, sales[3], sales[4], sales[5]);
        printf("Lucro total do produto em modo N:   " MAGENTA "%10.2lf %10.2lf %10.2lf\n" RESET, profit[0], profit[1], profit[2]);
        printf("Lucro total do produto em modo P:   " MAGENTA "%10.2lf %10.2lf %10.2lf\n\n" RESET, profit[3], profit[4], profit[5]);
    }
    puts("1) Ver resultados filial a filial.\n"
        "2) Ver resultados para todas as filiais.\n\n"
        "0) Retroceder.\n");
    printf("Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays the fourth query's menu.
 */
void query4UI() {
    clearWindow();
    puts(YELLOW "\t***PRODUTOS NÃO COMPRADOS***\n" RESET);
    puts("1) Ver produtos não comprados na filial 1.\n"
        "2) Ver produtos não comprados na filial 2.\n"
        "3) Ver produtos não comprados na filial 3.\n"
        "4) Ver produtos não comprados em nenhuma filial.\n");
    printf("Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays the fourth query's results.
 */
void query4ResultUI(int numP, int branchID) {
    clearWindow();
    puts(YELLOW "\t***PRODUTOS NÃO COMPRADOS***\n" RESET);
    if(branchID != 4) printf("Número total de produtos não comprados na filial %d: " MAGENTA "%d\n\n" RESET, branchID, numP);
    else printf("Número total de produtos não comprados em nenhuma filial: " MAGENTA "%d\n\n" RESET, numP);
    puts("1) Ver lista de produtos.\n\n"
        "0) Retroceder.\n");
    printf("Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays the fifth query's results.
 */
void query5ResultUI(int numC) {
    clearWindow();
    puts(YELLOW "\t***Clientes que compraram em todas as filiais***\n" RESET);
    if (numC == 0) {
        printf("Não existem clientes.\n\n");
        puts("0) Retroceder.\n");
        printf("Escreva o número correspondente à opção pretendida.\n> "); 
    }
    else {
    printf("Número total de clientes que compraram em todas as filiais: " MAGENTA "%d\n\n" RESET, numC);
    puts("1) Ver lista de clientes.\n\n"
        "0) Retroceder.\n");
    printf("Escreva o número correspondente à opção pretendida.\n> "); 
    }
}

/**
 * Displays the sixth query's results.
 */
void query6ResultUI(int numC, int numP) {
    clearWindow();
    puts(YELLOW "\t***NÚMERO DE CLIENTES QUE NÃO COMPRARAM E DE PRODUTOS NÃO COMPRADOS***\n" RESET);
    printf("Número total de clientes que não realizaram compras: " MAGENTA "%d\n\n" RESET, numC);
    printf("Número total de produtos que não foram comprados: " MAGENTA "%d\n\n" RESET, numP);
    puts("Pressione ENTER para continuar.");
}

/**
 * Displays the seventh query's menu.
 */
void query7UI() {
    clearWindow();
    puts(YELLOW "\t***TOTAL DE PRODUTOS COMPRADOS POR UM CLIENTE***\n" RESET);
    printf("Introduza um código de cliente.\n> ");
}

/**
 * Displays the seventh query's results.
 */
void query7ResultUI(char* clientID, size_t quants[12][4]) {
    clearWindow();
    puts(YELLOW "\t***TOTAL DE PRODUTOS COMPRADOS POR UM CLIENTE***\n" RESET);
    printf(GREEN "Cliente %s\n" RESET, clientID);
    puts("--------------------------------------------------------------\n"
        "|   mês  |  filial 1  |  filial 2  |  filial 3  |     total  |");
    size_t month;
    for(month = 0; month < 12; month++) {
        puts("--------------------------------------------------------------");
        printf("|    %02zu  |  %8zu  |  %8zu  |  %8zu  |  %8zu  |\n", month + 1, *(quants[month]), *(quants[month]+1), *(quants[month]+2), *(quants[month]+3));
    }
    printf("--------------------------------------------------------------");
}

/**
 * Displays the eighth query's menu.
 */
void query8UI(int month) {
    if (month == 1) printf("\nIntroduza o mês final (número entre 1 e 12).\n> ");
    else {
    clearWindow();
    puts(YELLOW "\t***Lucro e nº de vendas totais durante um intervalo de tempo***\n" RESET);
    printf("Introduza o mês inicial (número entre 1 e 12).\n> ");
    }
}

/**
 * Displays the eighth query's results.
 */
void query8ResultUI(int minMonth, int maxMonth, int sales, double profit) {
    clearWindow();
    puts(YELLOW "\t***Lucro e nº de vendas totais durante um intervalo de tempo***\n" RESET);
    printf("Entre os meses %d e %d foram obtidos os seguintes resultados:\n\n",  minMonth, maxMonth);
    printf("Nº de vendas efetuadas: " MAGENTA "%d" RESET"\n\n", sales);
    printf("Faturação total: "MAGENTA "%.2lf" RESET"\n\n", profit);
    puts("Pressione ENTER para continuar.");
}

/**
 * Displays the ninth query's menu.
 */
void query9UI(char mode) {
    clearWindow();
    puts(YELLOW "\t***CLIENTES QUE COMPRARAM DETERMINADO PRODUTO***\n" RESET);
    if(mode == 'p') printf("Introduza um código de produto.\n> ");
    else if(mode == 'f') printf("Introduza uma filial (valor entre 1 e 3).\n> ");
}

/**
 * Displays the ninth query's results.
 */
void query9ResultUI(char *prodID, int branch, size_t totalN, size_t totalP) {
    clearWindow();
    puts(YELLOW "\t***CLIENTES QUE COMPRARAM DETERMINADO PRODUTO***\n" RESET);
    printf("Nº total de clientes que compraram o produto %s na filial %d sem promoção: %ld\n", prodID, branch + 1, totalN);
    printf("Nº total de clientes que compraram o produto %s na filial %d com promoção: %ld\n\n", prodID, branch + 1, totalP);
    puts("1) Ver lista de clientes que compraram sem promoção.");
    puts("2) Ver lista de clientes que compraram com promoção.\n");
    puts("0) Retroceder.\n");
    printf("Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays the tenth query's menu.
 */
void query10UI(char mode) {
    clearWindow();
    puts(YELLOW "\t***PRODUTOS MAIS COMPRADOS POR UM CLIENTE***\n" RESET);
    if(mode == 'c') printf("Introduza um código de cliente.\n> ");
    else if(mode == 'm') printf("Introduza um mês (número entre 1 e 12).\n> ");
}

/**
 * Displays the tenth query's results.
 */
void query10ResultUI(int numP, char* clientID, int month) {
    clearWindow();
    puts(YELLOW "\t***PRODUTOS MAIS COMPRADOS POR UM CLIENTE***\n" RESET);
    printf("O cliente %s comprou " MAGENTA "%d" RESET " produtos diferentes no mês %d.\n\n", clientID, numP, month);
    puts("1) Ver lista de produtos mais comprados por ordem decrescente.\n\n"
        "0) Retroceder.\n");
    printf("Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays the eleventh query's menu.
 */
void query11UI () {
    clearWindow();
    puts(YELLOW "\t***PRODUTOS MAIS VENDIDOS***\n" RESET);
    printf("Quantos produtos pretende encontrar? "); 
}

/**
 * Displays the twelfth query's menu.
 */
void query12UI(char mode) {
    clearWindow();
    puts(YELLOW "\t***PRODUTOS COM OS QUAIS O CLIENTE GASTOU MAIS DINHEIRO***\n" RESET);
    if(mode == 'c') printf("Introduza um código de cliente.\n> ");
    else if(mode == 'l') printf("Quantos produtos pretende encontrar? ");
}

/**
 * Displays the benchmark's menu.
 */
void benchmarkUI() {
    clearWindow();
    puts(YELLOW "\t***BENCHMARK***\n" RESET);
    printf("1) Benchmark com 1 milhão de vendas.\n"
    "2) Benchmark com 3 milhões de vendas.\n"
    "3) Benchmark com 5 milhões de vendas.\n"
    "4) Benchmark com 1, 3 e 5 milhões de vendas.\n\n"
    RED "AVISO: ESTE PROCESSO PODE DEMORAR ALGUM TEMPO.\n\n" RESET
    "Escreva o número correspondente à opção pretendida.\n> ");
}

/**
 * Displays the benchmark's results.
 */
void benchmarkResultUI(double** results) {
    clearWindow();
    puts(YELLOW "\t***BENCHMARK***\n" RESET);
    puts(" query | 1M  vendas | 3M  vendas | 5M  vendas |\n"
         "-----------------------------------------------");
    size_t i;
    for(i = 0; i < 12; i++) {
        printf(" %5zu | %9.6lfs | %9.6lfs | %9.6lfs |\n", i + 1, results[0] ? results[0][i] : 0, results[1] ? results[1][i] : 0, results[2] ? results[2][i] : 0);
        puts("-----------------------------------------------");
    }
}

/**
 * Used to display an error message.
 */
void errorUI(char errorType) {
    switch(errorType) {
        case 'i':
            puts(RED "Erro a ler input!" RESET);
            break;
        case 'p':
            puts(RED "\nERRO - produto inválido!\nPrima ENTER para continuar." RESET);
            break;
        case 'c':
            puts(RED "\nERRO - cliente inválido!\nPrima ENTER para continuar." RESET);
            break;
        case 'm':
            puts(RED "\nERRO - mês inválido!\nPrima ENTER para continuar." RESET);
            break;
        case 'f': 
            puts(RED "\nERRO - filial inválida!\nPrima ENTER para continuar." RESET);
            break;        
        case 'v': 
            puts(RED "\nERRO - valor inválido!\nPrima ENTER para continuar." RESET);
            break;
        case 'h':
            puts(RED "\nERRO - caminho inválido!\nPrima ENTER para continuar." RESET);
            break;
        case 'l':
            puts(RED "\nERRO - lista vazia!\nPrima ENTER para continuar." RESET);
            break;
    }
}

/**
 * Auxiliar function used to clear the terminal.
 */
void clearWindow() {
    system("clear");
}