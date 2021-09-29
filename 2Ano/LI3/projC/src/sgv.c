#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "interface.h"

#define NUM_BRANCHES 3
#define BUFFERSIZE 100

struct sgv {
    Products products;
    Clients clients;
    Bills bills;
    Branch branches[NUM_BRANCHES];
    FileInfo fileInfo;
};

/**
 * Initializes a new instance of SGV.
 */
SGV initSGV() {
    SGV sgv = malloc(sizeof(struct sgv));
    sgv->clients = initClients();
    sgv->products = initProducts();
    sgv->bills = initBills();
    size_t i;
    for(i = 0; i < NUM_BRANCHES; i++)
        sgv->branches[i] = initBranch();
    sgv->fileInfo = initFileInfo();
    return sgv;
}

/**
 * Loads information to an SGV from the given files.
 * This function answers the first query.
 */
void loadSGVFromFiles(SGV sgv, char* clientsFilePath, char* productsFilePath, char* salesFilePath) {
    setPaths(sgv->fileInfo, clientsFilePath, productsFilePath, salesFilePath);

    size_t totalP = 0, validP = 0, totalC = 0, validC = 0, totalS = 0, validS = 0;

    char buffer[BUFFERSIZE];
    FILE* productsFile = fopen(productsFilePath, "r");
    while(fgets(buffer, BUFFERSIZE, productsFile)) {
        buffer[strcspn(buffer,"\r\n")] = 0;
        if(addProduct(sgv->products, buffer)) validP++;
        totalP++;
    }
    fclose(productsFile);

    FILE* clientsFile = fopen(clientsFilePath, "r");
    while(fgets(buffer, BUFFERSIZE, clientsFile)) {
        buffer[strcspn(buffer,"\r\n")] = 0;
        if(addClient(sgv->clients, buffer)) validC++;
        totalC++;
    }
    fclose(clientsFile);

    FILE* salesFile = fopen(salesFilePath, "r");
    while(fgets(buffer, BUFFERSIZE, salesFile)) {
        buffer[strcspn(buffer,"\r\n")] = 0;
        Sale sale = initSale(buffer);
        totalS++;
        if(is_valid_sale(sale, sgv->products, sgv->clients)) {
            int branch = getSaleBranch(sale) - 1;
            validS++;
            addBill(sgv->bills, sale);
            addToBranch(sgv->branches[branch], sale);
            char* prodID = getSaleProduct(sale);
            updateProductBranchInfo(sgv->products, prodID, branch);
            char* clientID = getSaleClient(sale);
            updateClientBranchInfo(sgv->clients, clientID, branch);
            g_free(prodID);
            g_free(clientID);
        }
        destroySale(sale);
    }
    fclose(salesFile);

    setTotalLines(sgv->fileInfo, totalC, totalP, totalS);
    setValidLines(sgv->fileInfo, validC, validP, validS);
}

/**
 * Frees all memory allocated to an instance of SGV.
 */
void destroySGV(SGV sgv) {
    destroyProducts(sgv->products);
    destroyClients(sgv->clients);
    destroyBills(sgv->bills);
    int i;
    for(i = 0; i < NUM_BRANCHES; i++)
        destroyBranch(sgv->branches[i]);
    destroyFileInfo(sgv->fileInfo);
    free(sgv);
}

/* Query 2 */

/**
 * Auxiliary function for the second query.
 */
gboolean productStartsWith(gpointer key, gpointer value, gpointer data) {
    StringArray stringArray = (StringArray) data;
    int dataSA = getStringArrayData(stringArray);
    char* id = (char*) key;
    if(*id == dataSA) addToStringArray(stringArray, id);
    else if(*id > dataSA) return TRUE;
    return FALSE;
}

/**
 * Function that answers the second query. 
 */
StringArray getProductsStartedByLetter(SGV sgv, char letter) {
    letter = toupper(letter);
    StringArray stringArray = initStringArrayWithData(getValidProducts(getCurrentFilesInfo(sgv)), letter);
    g_tree_foreach(sgv->products, productStartsWith, stringArray);
    resizeStringArrayToN(stringArray);
    return stringArray;
}

/* Query 3 */

/**
 * Function that answers the third query. 
 */
SalesProfit getProductSalesAndProfit(SGV sgv, char *productID, int month) {
    Bill bill = g_hash_table_lookup(sgv->bills, productID);
    SalesProfit sp = initSalesProfit();
    if (bill) {
        size_t branch, np;
        for (branch = 0; branch < 3; branch++) {
            for(np = 0; np < 2; np++) {
                addToSales(sp, branch, np, getBillSales(bill, branch, month, np));
                addToProfit(sp, branch, np, getBillProfit(bill, branch, month, np));
            }
        }
    }
    return sp;
}

/* Query 4 */

/**
 * Auxiliary function for the fourth query.
 */
gboolean checkIfNotBought(gpointer key, gpointer value, gpointer data) {
    StringArray stringArray = (StringArray) data;
    char* prodID = (char*) key;
    bool* branchInfo = (bool*) value;
    int dataSA = getStringArrayData(stringArray);
    if(dataSA == 0 && !branchInfo[0]) addToStringArray(stringArray, prodID);
    else if(dataSA == 1 && !branchInfo[1]) addToStringArray(stringArray, prodID);
    else if(dataSA == 2 && !branchInfo[2]) addToStringArray(stringArray, prodID);
    else if(dataSA == 3 && !branchInfo[0] && !branchInfo[1] && !branchInfo[2]) addToStringArray(stringArray, prodID);
    return FALSE;
}

/**
 * Function that answers the fourth query. 
 */
StringArray getProductsNeverBought(SGV sgv, int branchID) {
    StringArray stringArray = initStringArrayWithData(getValidProducts(getCurrentFilesInfo(sgv)), branchID);
    g_tree_foreach(sgv->products, checkIfNotBought, stringArray);
    resizeStringArrayToN(stringArray);
    return stringArray;
}

/* Query 5*/

/**
 * Auxiliary function for the fifth query.
 */
gboolean ClientsOfAllBranches (gpointer key, gpointer value, gpointer data) {
    StringArray stringArray = (StringArray) data;
    char* clientID = (char*) key;
    bool* branchInfo = (bool*) value;
    if (branchInfo[0] && branchInfo[1] && branchInfo[2]) addToStringArray(stringArray, clientID);
    return FALSE;
}

/**
 * Function that answers the fifth query. 
 */
StringArray getClientsOfAllBranches (SGV sgv) {    
    StringArray stringArray = initStringArray(getValidClients(getCurrentFilesInfo(sgv)));
    g_tree_foreach(sgv->clients, ClientsOfAllBranches, stringArray);
    resizeStringArrayToN(stringArray);
    return stringArray;
}

/* Query 6*/

/**
 * Auxiliary function for the sixth query.
 */
gboolean prodCount(gpointer key, gpointer value, gpointer data) {
    int* counter = (int*) data;
    bool* branchInfo = (bool*) value;
    if (!branchInfo[0] && !branchInfo[1] && !branchInfo[2]) (*counter)++;
    return FALSE;
}

/**
 * Auxiliary function for the sixth query.
 */
gboolean clientCount(gpointer key, gpointer value, gpointer data) {
    int* counter = (int*) data;
    bool* branchInfo = (bool*) value;
    if (!branchInfo[0] && !branchInfo[1] && !branchInfo[2]) (*counter)++;
    return FALSE;
}

/**
 * Function that answers the sixth query. 
 */
int* getClientsAndProductsNeverBoughtCount(SGV sgv) {
    int* clientProd = calloc(2, sizeof(int));
    g_tree_foreach(sgv->clients, clientCount, clientProd);
    g_tree_foreach(sgv->products, prodCount, clientProd + 1);
    return clientProd;
}

/* Query 7 */

/**
 * Function that answers the seventh query. 
 */
ProductTable getProductsBoughtByClient(SGV sgv, char* clientID) {
    ProductTable prodT = initProductTable();
    size_t branch;
    for(branch = 0; branch < 3; branch++) {
        if(!clientBoughtInBranch(sgv->clients, clientID, branch)) continue;
        int* totalQuantByMonth = getTotalQuantByClient(sgv->branches[branch], clientID);
        setProductTableYear(prodT, branch, totalQuantByMonth);
        free(totalQuantByMonth);
    }
    return prodT;
}

/* Query 8*/

/**
 * Function that answers the eighth query. 
 */
SalesProfit getSalesAndProfit (SGV sgv, int minMonth, int maxMonth) {
    return getSalesAndProfitFromBills(sgv->bills, minMonth, maxMonth);
}

/* Query 9 */

/**
 * Function that answers the ninth query. 
 */
StringArray* getProductBuyers(SGV sgv, char *prodID, int branch) {
    StringArray* lists = calloc(2,sizeof(StringArray));
    if(productBoughtInBranch(sgv->products, prodID, branch)) {
        lists[0] = getClientsWhoBoughtProduct(sgv->branches[branch], prodID, 0);
        lists[1] = getClientsWhoBoughtProduct(sgv->branches[branch], prodID, 1);
        resizeStringArrayToN(lists[0]);
        resizeStringArrayToN(lists[1]);
    }
    else {
        lists[0] = initStringArray(0);
        lists[1] = initStringArray(0);
    }
    return lists;
}

/* Query 10 */

/**
 * Auxiliary function for the tenth, eleventh and twelfth queries.
 */
gboolean treeToList(gpointer key, gpointer value, gpointer data) {
    StringArray stringArray = (StringArray) data;
    char* id = (char*) key;
    char* datastr = (char*) value;

    char* line = g_strdup_printf("%s %s", id, datastr);
    bool success = addToStringArrayIf(stringArray, line);
    g_free(line);
    if(!success) return TRUE;
    return FALSE;
}

/**
 * Auxiliary function for the tenth query.
 */
gint compareQuantInv(gconstpointer a, gconstpointer b, gpointer user_data) {
    char* prodIDA = (char*) a;
    char* prodIDB = (char*) b;
    GHashTable* quantTable = (GHashTable*) user_data;
    int* quantA = g_hash_table_lookup(quantTable, prodIDA);
    int* quantB = g_hash_table_lookup(quantTable, prodIDB);
    return *quantB - *quantA ? *quantB - *quantA : strcmp(prodIDA, prodIDB);
}

/**
 * Function that answers the tenth query. 
 */
StringArray getClientFavoriteProducts(SGV sgv, char* clientID, int month) {
    GHashTable* quantTable = g_hash_table_new_full(g_str_hash, g_str_equal, NULL, g_free);
    size_t branch = 0;
    for(;branch < 3; branch++)
        if(clientBoughtInBranch(sgv->clients, clientID, branch))
            fillQuantTableOfProdsByClient(sgv->branches[branch], clientID, month, quantTable);
    GTree* productsTree = g_tree_new_full(compareQuantInv, quantTable, NULL, g_free);

    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, quantTable);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        char* prodID = (char*) key;
        int* quant = (int*) value;
        if(*quant) g_tree_insert(productsTree, prodID, g_strdup_printf("%6d", *quant));
    }
    g_hash_table_destroy(quantTable);
    StringArray stringArray = initStringArray(g_tree_nnodes(productsTree));
    g_tree_foreach(productsTree, treeToList, stringArray);
    g_tree_destroy(productsTree);
    return stringArray;
}

/* Query 11*/

/**
 * Auxiliary function for the eleventh query.
 */
int compareQuantSold(gconstpointer a, gconstpointer b, gpointer user_data) {
    char* prodIDA = (char*)a;
    char* prodIDB = (char*)b;
    GHashTable* topSelledTable = (GHashTable*)user_data;
    int* dataA = g_hash_table_lookup(topSelledTable, prodIDA);
    int* dataB = g_hash_table_lookup(topSelledTable, prodIDB);
    return *dataB - *dataA ? *dataB - *dataA : strcmp(prodIDA, prodIDB);
}

/**
 * Function that answers the eleventh query. 
 */
StringArray getTopSelledProducts (SGV sgv, int limit) {
    GHashTable* topSelledTable = g_hash_table_new_full(g_str_hash, g_str_equal, NULL, g_free);
    size_t branch = 0;
    for(;branch < 3; branch++)
        fillTopSelledTable(sgv->branches[branch], branch, topSelledTable);
 
    GTree* topSelledTree = g_tree_new_full(compareQuantSold, topSelledTable, NULL, g_free);
    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, topSelledTable);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        char* prodID = (char*) key;
        int* data = (int*) value;
        if(*data) g_tree_insert(topSelledTree, prodID, 
            g_strdup_printf("| %5d | %4d | %4d | %4d | %5d | %2d | %2d | %2d |", data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]));
    }
    g_hash_table_destroy(topSelledTable);

    StringArray stringArray = initStringArrayWithData(limit, limit);
    g_tree_foreach(topSelledTree, treeToList, stringArray);
    g_tree_destroy(topSelledTree);
    resizeStringArrayToN(stringArray);
    return stringArray;
}

/* Query 12 */

/**
 * Auxiliary function for the twelfth query.
 */
gint compareMoneySpent(gconstpointer a, gconstpointer b, gpointer user_data) {
    char* ia = (char*) a;
    char* ib = (char*) b;
    GHashTable* hash  = (GHashTable*) user_data;
    double* msA = g_hash_table_lookup(hash, ia);
    double* msB = g_hash_table_lookup(hash, ib);
    return *msB - *msA ? *msB - *msA : strcmp(ia, ib);
}

/**
 * Function that answers the twelfth query. 
 */
StringArray getClientTopProfitProducts (SGV sgv, char * clientID , int limit) {
    GHashTable* moneySpentOnProducts = g_hash_table_new_full(g_str_hash, g_str_equal, NULL, g_free);
    size_t branch = 0;
    for(;branch < 3; branch++)
        if(clientBoughtInBranch(sgv->clients, clientID, branch))
            fillTableWithProducts(sgv->branches[branch], clientID, moneySpentOnProducts);
    GTree* moneySpentTree = g_tree_new_full(compareMoneySpent, moneySpentOnProducts, NULL, g_free); 

    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, moneySpentOnProducts);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        char* clientID = (char*) key;
        double* moneySpent = (double*) value;
        g_tree_insert(moneySpentTree, clientID, g_strdup_printf("%10.2lf", *moneySpent));
    }
    g_hash_table_destroy(moneySpentOnProducts);

    StringArray stringArray = initStringArrayWithData(limit, limit);
    g_tree_foreach(moneySpentTree, treeToList, stringArray);
    g_tree_destroy(moneySpentTree);
    resizeStringArrayToN(stringArray);
    return stringArray;
}

/* Query 13 */

/**
 * Function that answers the thirteenth query. 
 */
FileInfo getCurrentFilesInfo(SGV sgv) {
    return sgv->fileInfo;
}