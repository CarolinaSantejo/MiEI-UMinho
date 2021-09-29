#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>

#include "queries.h"

#define BUFFERSIZE 100

char buffer[BUFFERSIZE];

/**
 * Auxiliary function used to get a line of input from stdin.
 */
void getLine(char* line, size_t N) {
    while(fgets(line, N, stdin) == NULL); 
}

/**
 * Controls the flow of the first query.
 */
void query1(SGV sgv) {
    /* Clients folder, products folder, and sales folder, in this order. */
    char** folders = calloc(3, sizeof(char*));
    while(1) {
        getFolderUI('a');
        getLine(buffer, BUFFERSIZE);
        char const selection = buffer[0];
        if(selection == '1') {
            folders[0] = g_strdup("../db/Clientes.txt");
            folders[1] = g_strdup("../db/Produtos.txt");
            folders[2] = g_strdup("../db/Vendas_1M.txt");
            break;
        }
        else if(selection == '2') {
            folders[0] = g_strdup("../db/Clientes.txt");
            folders[1] = g_strdup("../db/Produtos.txt");
            folders[2] = g_strdup("../db/Vendas_3M.txt");
            break;
        }
        else if(selection == '3') {
            folders[0] = g_strdup("../db/Clientes.txt");
            folders[1] = g_strdup("../db/Produtos.txt");
            folders[2] = g_strdup("../db/Vendas_5M.txt");
            break;
        }
        else if(selection == '4') {
            FILE* tempF;
            while(1) {
                getFolderUI('c');
                getLine(buffer, BUFFERSIZE);
                buffer[strcspn(buffer,"\n\r")] = 0;
                folders[0] = g_strdup_printf("../%s", buffer);
                if((tempF = fopen(folders[0], "r"))) {
                    fclose(tempF);
                    break;
                }
                errorUI('h');
                getLine(buffer, BUFFERSIZE);
            }
            while(1) {
                getFolderUI('p');
                getLine(buffer, BUFFERSIZE);
                buffer[strcspn(buffer,"\n\r")] = 0;
                folders[1] = g_strdup_printf("../%s", buffer);
                if((tempF = fopen(folders[1], "r"))) {
                    fclose(tempF);
                    break;
                }
                errorUI('h');
                getLine(buffer, BUFFERSIZE);
            }
            while(1) {
                getFolderUI('s');
                getLine(buffer, BUFFERSIZE);
                buffer[strcspn(buffer,"\n\r")] = 0;
                folders[2] = g_strdup_printf("../%s", buffer);
                if((tempF = fopen(folders[2], "r"))) {
                    fclose(tempF);
                    break;
                }
                errorUI('h');
                getLine(buffer, BUFFERSIZE);
            }
            break;
        }
    }

    loadingUI();
    clock_t start = clock();
    loadSGVFromFiles(sgv, folders[0], folders[1], folders[2]);
    clock_t end = clock();
    g_free(folders[0]);
    g_free(folders[1]);
    g_free(folders[2]);
    g_free(folders);

    double loadTime = ((double)(end - start) / CLOCKS_PER_SEC);
    query13(sgv, &loadTime);
}

/**
 * Controls the flow of the second query.
 */
void query2(SGV sgv) {
    char letter;
    while(1) {
        query2UI();
        getLine(buffer, BUFFERSIZE);
        letter = *buffer;
        if(isalpha(letter)) break;
    }

    StringArray list = getProductsStartedByLetter(sgv, letter);
    char* title = g_strdup_printf("\tProdutos que começam pela letra '%c'", letter);

    while(1) {
        query2ResultUI(letter, getStringArrayLength(list));
        getLine(buffer, BUFFERSIZE);
        char selection = *buffer;
        if(selection == '0') break;
        else if(selection == '1') navigateStringArray(list, title);
    }

    g_free(title);
    destroyStringArray(list);
}

/**
 * Controls the flow of the third query.
 */
void query3(SGV sgv) {
    char* prodID;
    int month;
    while(1) {
        query3UI('p');
        getLine(buffer, BUFFERSIZE);
        buffer[strcspn(buffer, "\r\n")] = 0;
        prodID = g_strdup(buffer);
        if(is_valid_product(prodID)) break;
        errorUI('p');
        getLine(buffer, BUFFERSIZE);
    }
    while(1) {
        query3UI('m');
        getLine(buffer, BUFFERSIZE);
        if(sscanf(buffer, "%d", &month) == 1)
            if(month >= 1 && month <= 12)
                break;
        errorUI('m');
        getLine(buffer, BUFFERSIZE);
    }
    SalesProfit sp = getProductSalesAndProfit(sgv, prodID, month - 1);
    int branchMode = '1';
    int sales[6] = {getSalesFromSP(sp, 0, 0), getSalesFromSP(sp, 1, 0), getSalesFromSP(sp, 2, 0), 
                    getSalesFromSP(sp, 0, 1), getSalesFromSP(sp, 1, 1), getSalesFromSP(sp, 2, 1)};
    double profit[6] = {getProfitFromSP(sp, 0, 0), getProfitFromSP(sp, 1, 0), getProfitFromSP(sp, 2, 0), 
                        getProfitFromSP(sp, 0, 1), getProfitFromSP(sp, 1, 1), getProfitFromSP(sp, 2, 1)};
    while(1) {
        query3ResultUI(branchMode, prodID, month, sales, profit);
        getLine(buffer, BUFFERSIZE);
        char const selection = *buffer;
        if(selection == '0') break;
        branchMode = selection == '1' || selection == '2' ? selection : branchMode;
    }
    g_free(prodID);
    free(sp);
}

/**
 * Controls the flow of the fourth query.
 */
void query4(SGV sgv) {
    int branchID;
    while(1) {
        query4UI();
        getLine(buffer, BUFFERSIZE);
        if(sscanf(buffer, "%d", &branchID) == 1) break;
    }

    StringArray prodList = getProductsNeverBought(sgv, branchID - 1);
    char* title = NULL;
    if(branchID == 3) title = g_strdup_printf("\tProdutos que ninguém comprou em nenhuma filial");
    else title = g_strdup_printf("\tProdutos que ninguém comprou na filial %d", branchID);

    while(1) {
        query4ResultUI(getStringArrayLength(prodList), branchID);
        getLine(buffer, BUFFERSIZE);
        if(*buffer == '0') break;
        if(*buffer == '1') navigateStringArray(prodList, title);
    }

    g_free(title);
    destroyStringArray(prodList);
}

/**
 * Controls the flow of the fifth query.
 */
void query5(SGV sgv) {
    StringArray list = getClientsOfAllBranches(sgv);
    char* title = g_strdup_printf("\tClientes que compraram em todas as filiais");

    while(1) {
        query5ResultUI(getStringArrayLength(list));
        getLine(buffer, BUFFERSIZE);
        if(*buffer == '0') break;
        if(*buffer == '1') navigateStringArray(list, title);
    }

    g_free(title);
    destroyStringArray(list);
}

/**
 * Controls the flow of the sixth query.
 */
void query6(SGV sgv) {
    int* cp = getClientsAndProductsNeverBoughtCount(sgv);
    query6ResultUI(cp[0], cp[1]);
    getLine(buffer, BUFFERSIZE);
    free(cp);
}

/**
 * Controls the flow of the seventh query.
 */
void query7(SGV sgv) {
    char* clientID;
    while(1) {
        query7UI();
        getLine(buffer, BUFFERSIZE);
        buffer[strcspn(buffer, "\r\n")] = 0;
        clientID = g_strdup(buffer);
        if(is_valid_client(clientID)) break;
        errorUI('c');
        getLine(buffer, BUFFERSIZE);
    }
    ProductTable prodT = getProductsBoughtByClient(sgv, clientID);
    size_t quants[12][4];
    size_t month;
    for(month = 0; month < 12; month++) {
        quants[month][0] = getProductTableQuant(prodT, 0, month);
        quants[month][1] = getProductTableQuant(prodT, 1, month);
        quants[month][2] = getProductTableQuant(prodT, 2, month);
        quants[month][3] = quants[month][0] + quants[month][1] + quants[month][2];
    }
    query7ResultUI(clientID, quants);
    getLine(buffer, BUFFERSIZE);
    g_free(clientID);
    free(prodT);
}

/**
 * Controls the flow of the eigth query.
 */
void query8(SGV sgv) {
    int maxMonth;
    int minMonth;
    while(1) {
        query8UI(0);
        getLine(buffer, BUFFERSIZE);
        if(sscanf(buffer, "%d", &minMonth) == 1)
            if(minMonth >= 1 && minMonth <= 12)
                break;
        errorUI('m');
        getLine(buffer, BUFFERSIZE);
    }
    while(1) {
        query8UI(1);
        getLine(buffer, BUFFERSIZE);
        if(sscanf(buffer, "%d", &maxMonth) == 1)
            if(maxMonth >= 1 && maxMonth <= 12)
                break;
        errorUI('m');
        getLine(buffer, BUFFERSIZE);
    }
    SalesProfit salesProfit = getSalesAndProfit(sgv, minMonth-1, maxMonth-1);
    query8ResultUI(minMonth, maxMonth, getSalesFromSP(salesProfit, 0, 0), getProfitFromSP(salesProfit, 0, 0));
    getLine(buffer, BUFFERSIZE);
    free(salesProfit);
}

/**
 * Controls the flow of the ninth query.
 */
void query9(SGV sgv) {
    char *prodID;
    int branch;
    while (1) {
        query9UI('p');
        getLine(buffer, BUFFERSIZE);
        buffer[strcspn(buffer, "\r\n")] = 0;
        prodID = g_strdup(buffer);
        if (is_valid_product(prodID))
            break;
        errorUI('p');
        getLine(buffer, BUFFERSIZE);
    }
    while (1) {
        query9UI('f');
        getLine(buffer, BUFFERSIZE);
        if(sscanf(buffer, "%d", &branch) == 1)
            if(branch >= 1 && branch <= 3) break;
        errorUI('f');
        getLine(buffer, BUFFERSIZE);
    }
    branch--;
    StringArray* lists = getProductBuyers(sgv, prodID, branch);
    char* title1 = g_strdup_printf("\tClientes que compraram o produto %s em modo normal", prodID);
    char* title2 = g_strdup_printf("\tClientes que compraram o produto %s em promoção", prodID);
    while (1) {
        query9ResultUI(prodID, branch, getStringArrayLength(lists[0]), getStringArrayLength(lists[1]));
        getLine(buffer, BUFFERSIZE);
        if (*buffer == '1') navigateStringArray(lists[0], title1);
        else if (*buffer == '2') navigateStringArray(lists[1], title2);
        else if (*buffer == '0') break;
    }
    g_free(prodID);
    g_free(title1);
    destroyStringArray(lists[0]);
    g_free(title2);
    destroyStringArray(lists[1]);
    free(lists);
}

/**
 * Controls the flow of the tenth query.
 */
void query10(SGV sgv) {
    char* clientID;
    int month;
    while(1) {
        query10UI('c');
        getLine(buffer, BUFFERSIZE);
        buffer[strcspn(buffer, "\r\n")] = 0;
        clientID = g_strdup(buffer);
        if(is_valid_client(clientID)) break;
        errorUI('c');
        getLine(buffer, BUFFERSIZE);
    }
    while(1) {
        query10UI('m');
        getLine(buffer, BUFFERSIZE);
        if(sscanf(buffer, "%d", &month) == 1)
            if(month >= 1 && month <= 12)
                break;
        errorUI('m');
        getLine(buffer, BUFFERSIZE);
    }
    StringArray prodList = getClientFavoriteProducts(sgv, clientID, month - 1);
    char* title = g_strdup_printf("\tProdutos mais comprados pelo cliente %s e quantidade comprada", clientID);
    while(1) {
        query10ResultUI(getStringArrayLength(prodList), clientID, month);
        getLine(buffer, BUFFERSIZE);
        if(*buffer == '0') break;
        if(*buffer == '1') navigateStringArray(prodList, title);
    }
    g_free(title);
    g_free(clientID);
    destroyStringArray(prodList);
}

/**
 * Controls the flow of the eleventh query.
 */
void query11(SGV sgv) {
    int limit;
    while (1) {
        query11UI();
        getLine(buffer, BUFFERSIZE);
        buffer[strcspn(buffer, "\r\n")] = 0;
        if(sscanf(buffer, "%d", &limit) == 1)
            if(limit > 0)
                break;
        errorUI('v');
        getLine(buffer, BUFFERSIZE);
    }
    StringArray list = getTopSelledProducts(sgv, limit);
    char* title = g_strdup_printf("               |         Quantidade         |       Clientes       |\n"
                                  "      ID Prod. | Total | Fil1 | Fil2 | Fil3 | Total | F1 | F2 | F3 |");
    navigateStringArray(list, title);
    g_free(title);
    destroyStringArray(list);
}

/**
 * Controls the flow of the twelfth query.
 */
void query12(SGV sgv) {
    char *clientID;
    int limit;
    while (1) {
        query12UI('c');
        getLine(buffer, BUFFERSIZE);
        buffer[strcspn(buffer, "\r\n")] = 0;
        clientID = g_strdup(buffer);
        if (is_valid_client(clientID))
            break;
        errorUI('c');
        getLine(buffer, BUFFERSIZE);
    }
    while (1) {
        query12UI('l');
        getLine(buffer, BUFFERSIZE);
        buffer[strcspn(buffer, "\r\n")] = 0;
        if(sscanf(buffer, "%d", &limit) == 1)
            if(limit > 0)
                break;
        errorUI('v');
        getLine(buffer, BUFFERSIZE);
    }
    char* title = g_strdup_printf("\tProdutos com que o cliente %s mais gastou dinheiro e dinheiro gasto", clientID);
    StringArray list = getClientTopProfitProducts(sgv, clientID, limit);
    navigateStringArray(list, title);
    g_free(title);
    g_free(clientID);
    destroyStringArray(list);
}

/**
 * Controls the flow of the thirteenth query.
 */
void query13(SGV sgv, double* loadTime) {
    clearWindow();

    FileInfo fileInfo = getCurrentFilesInfo(sgv);

    loadInfoUI('t', NULL, 0, 0, 0);
    char* productsPath = getProductsPath(fileInfo);
    loadInfoUI('p', productsPath, getTotalProducts(fileInfo), getValidProducts(fileInfo), 0);
    g_free(productsPath);
    char* clientsPath = getClientsPath(fileInfo);
    loadInfoUI('c', clientsPath, getTotalClients(fileInfo), getValidClients(fileInfo), 0);
    g_free(clientsPath);
    char* salesPath = getSalesPath(fileInfo);
    loadInfoUI('s', salesPath, getTotalSales(fileInfo), getValidSales(fileInfo), 0);
    g_free(salesPath);

    if(loadTime) loadInfoUI('l', NULL, 0, 0, *loadTime);
    loadInfoUI('e', NULL, 0, 0, 0);

    getLine(buffer, BUFFERSIZE);
}

#define TEXEC(start, end) ((double)(end - start) / CLOCKS_PER_SEC)

/**
 * Allows the user to measure the performance of the program.
 */
double** benchmark(char mode) {
    double** results = calloc(3, sizeof(double*));
    clock_t start = 0, end = 0;
    StringArray tempSA = NULL;
    StringArray* tempSAPtr = NULL;
    SalesProfit tempSP = NULL;
    ProductTable tempPT = NULL;

    size_t i;
    for(i = 0; i < 3; i++) {
        double* resultsT = calloc(12, sizeof(double));
        SGV sgv = initSGV();

        char* clientsPath = "../db/Clientes.txt";
        char* productsPath = "../db/Produtos.txt";
        char* salesPath = NULL;
        if(mode == '1' || (mode == '4' && i == 0)) salesPath = "../db/Vendas_1M.txt";
        else if(mode == '2' || (mode == '4' && i == 1)) salesPath = "../db/Vendas_3M.txt";
        else if(mode == '3' || (mode == '4' && i == 2)) salesPath = "../db/Vendas_5M.txt";

        start = clock();
        loadSGVFromFiles(sgv, clientsPath, productsPath, salesPath);
        end = clock();
        resultsT[0] = TEXEC(start, end);

        start = clock();
        tempSA = getProductsStartedByLetter(sgv, 'A');
        end = clock();
        resultsT[1] = TEXEC(start, end);
        destroyStringArray(tempSA);

        start = clock();
        tempSP = getProductSalesAndProfit(sgv, "CE1958", 2);
        end = clock();
        resultsT[2] = TEXEC(start, end);
        destroySalesProfit(tempSP);

        start = clock();
        tempSA = getProductsNeverBought(sgv, 0);
        end = clock();
        resultsT[3] = TEXEC(start, end);
        destroyStringArray(tempSA);
        
        start = clock();
        tempSA = getClientsOfAllBranches(sgv);
        end = clock();
        resultsT[4] = TEXEC(start, end);
        destroyStringArray(tempSA);

        start = clock();
        int* tempIntPtr = getClientsAndProductsNeverBoughtCount(sgv);
        end = clock();
        resultsT[5] = TEXEC(start, end);
        free(tempIntPtr);

        start = clock();
        tempPT = getProductsBoughtByClient(sgv, "N4779");
        end = clock();
        resultsT[6] = TEXEC(start, end);
        destroyProductTable(tempPT);

        start = clock();
        tempSP = getSalesAndProfit(sgv, 3, 6);
        end = clock();
        resultsT[7] = TEXEC(start, end);
        destroySalesProfit(tempSP);

        start = clock();
        tempSAPtr = getProductBuyers(sgv, "CE1958", 0);
        end = clock();
        resultsT[8] = TEXEC(start, end);
        destroyStringArray(tempSAPtr[0]);
        destroyStringArray(tempSAPtr[1]);
        free(tempSAPtr);

        start = clock();
        tempSA = getClientFavoriteProducts(sgv, "N4779", 1);
        end = clock();
        resultsT[9] = TEXEC(start, end);
        destroyStringArray(tempSA);

        start = clock();
        tempSA = getTopSelledProducts(sgv, 10);
        end = clock();
        resultsT[10] = TEXEC(start, end);
        destroyStringArray(tempSA);

        start = clock();
        tempSA = getClientTopProfitProducts(sgv, "N4779", 10);
        end = clock();
        resultsT[11] = TEXEC(start, end);

        destroyStringArray(tempSA);
        destroySGV(sgv);
        if(mode == '1') {
            results[0] = resultsT;
            break;
        }
        if(mode == '2') {
            results[1] = resultsT;
            break;
        }
        if(mode == '3') {
            results[2] = resultsT;
            break;
        }
        results[i] = resultsT;
    }
    return results;
}