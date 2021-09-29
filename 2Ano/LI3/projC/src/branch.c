#include <stdlib.h>

#include "branch.h"

struct branch {
    /* Matches a product to a hash table containing the clients who bought it. */
    GHashTable* clientsWhoBought;
    /* Matches a client to a hash table containing the products bought by them. */
    GHashTable* productsBoughtBy;
};

struct clientInfo {
    /* Quantity of a product bought by a given client by month and by mode (normal or in sale) */
    int quantity[12][2];
};

struct productInfo {
    /* Quantity of a given product bought by a client by month */
    int quantity[12];
    /* Money spent on a given product by a client */
    double moneySpent;
};

/**
 * Initializes a new instance of Branch.
 */
Branch initBranch() {
    Branch branch = malloc(sizeof(struct branch));
    branch->clientsWhoBought = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, (GDestroyNotify) g_hash_table_destroy);
    branch->productsBoughtBy = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, (GDestroyNotify) g_hash_table_destroy);
    return branch;
}

/**
 * Adds the info of a sale to a given Branch.
 * 
 * This function is responsible for initializing all ClientInfo and ProductInfo instances found in a Branch.
 */
void addToBranch(Branch branch, Sale sale) {
    char* prodID = getSaleProduct(sale);
    char* clientID = getSaleClient(sale);
    int month = getSaleMonth(sale) - 1;
    int quant = getSaleQuantity(sale);

    GHashTable* clientsWhoBoughtProduct = g_hash_table_lookup(branch->clientsWhoBought, prodID);
    if(!clientsWhoBoughtProduct) {
        clientsWhoBoughtProduct = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, destroyClientInfo);
        g_hash_table_insert(branch->clientsWhoBought, g_strdup(prodID), clientsWhoBoughtProduct);
    }
    ClientInfo clientInfo = g_hash_table_lookup(clientsWhoBoughtProduct, clientID);
    if(!clientInfo) {
        clientInfo = calloc(1, sizeof(struct clientInfo));
        g_hash_table_insert(clientsWhoBoughtProduct, g_strdup(clientID), clientInfo);
    }
    clientInfo->quantity[month][getSaleNPInt(sale)] += quant;

    GHashTable* productsBoughtByClient = g_hash_table_lookup(branch->productsBoughtBy, clientID);
    if(!productsBoughtByClient) {
        productsBoughtByClient = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, destroyProductInfo);
        g_hash_table_insert(branch->productsBoughtBy, g_strdup(clientID), productsBoughtByClient);
    }
    ProductInfo productInfo = g_hash_table_lookup(productsBoughtByClient, prodID);
    if(!productInfo) {
        productInfo = calloc(1, sizeof(struct productInfo));
        g_hash_table_insert(productsBoughtByClient, g_strdup(prodID), productInfo);
    }
    productInfo->quantity[month] += quant;
    productInfo->moneySpent += getSaleTotalPrice(sale);

    g_free(prodID);
    g_free(clientID);
}

/**
 * Frees all memory allocated to an instance of Branch.
 */
void destroyBranch(Branch branch) {
    g_hash_table_destroy(branch->clientsWhoBought);
    g_hash_table_destroy(branch->productsBoughtBy);
    free(branch);
}

/**
 * Frees all memory allocated to an instance of ClientInfo.
 */
void destroyClientInfo(void* object) {
    ClientInfo clientInfo = (ClientInfo) object;
    free(clientInfo);
}

/**
 * Frees all memory allocated to an instance of ProductInfo.
 */
void destroyProductInfo(void* object) {
    ProductInfo productInfo = (ProductInfo) object;
    free(productInfo);
}

/**
 * Auxiliary function used to navigate a hash table containing instances of ProductInfo and get the quantity of that product bought by month.
 */
gpointer quantSumByMonth(gpointer key, gpointer value, gpointer data) {
    int* totalQuantByMonth = (int*) data;
    ProductInfo prodInfo = (ProductInfo) value;
    size_t month;
    for(month = 0; month < 12; month++)
        totalQuantByMonth[month] += prodInfo->quantity[month];
    return FALSE;
}

/**
 * Gets the total quantity of products bought by a given client in a Branch, divided by month.
 */
int* getTotalQuantByClient(Branch branch, char* clientID) {
    GHashTable* productsBoughtByClient = g_hash_table_lookup(branch->productsBoughtBy, clientID);
    int* totalQuantByMonth = calloc(12, sizeof(int));
    g_hash_table_foreach(productsBoughtByClient, (GHFunc) quantSumByMonth, totalQuantByMonth);
    return totalQuantByMonth;
}

/**
 * Fills a hash table with the products bought by a given client in a given month in a Branch.
 * 
 * The table matches a product (key) with how many units of it were bought (value).
 */
void fillQuantTableOfProdsByClient(Branch branch, char* clientID, int month, GHashTable* quantTable) {
    GHashTable* productsBoughtByClient = g_hash_table_lookup(branch->productsBoughtBy, clientID);
    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, productsBoughtByClient);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        char* prodID = (char*) key;
        ProductInfo prodInfo = (ProductInfo) value;
        int* quant = g_hash_table_lookup(quantTable, prodID);
        if(!quant) {
            quant = calloc(1, sizeof(int));
            g_hash_table_insert(quantTable, prodID, quant);
        }
        *quant += prodInfo->quantity[month];
    }
}


/**
 * Auxiliary function used to navigate a hash table containing instances of ClientInfo and to add that client to a StringArray if they made a purchase.
 */
void addToListNorP(gpointer key, gpointer value, gpointer data) {
    StringArray stringArray = (StringArray)data;
    char* clientID = (char*)key;
    ClientInfo clientInfo = (ClientInfo)value;
    int np = getStringArrayData(stringArray);
    size_t month;
    for (month = 0; month < 12; month++) {
        if (clientInfo->quantity[month][np] > 0) {
            addToStringArray(stringArray, clientID);
            break;
        }
    }
}

/**
 * Returns a StringArray containing all the clients who bought a given product, either at a discount or at full price.
 */
StringArray getClientsWhoBoughtProduct(Branch branch, char * prodID, int np) {
    GHashTable* clientsWhoBoughtProduct = g_hash_table_lookup(branch->clientsWhoBought, prodID);
    StringArray stringArray = initStringArrayWithData(g_hash_table_size(clientsWhoBoughtProduct), np);
    g_hash_table_foreach(clientsWhoBoughtProduct, addToListNorP, stringArray);
    return stringArray;
}

/**
 * Auxiliary function used to navigate a hash table containing instances of ProductInfo and to add that product and how much money was
 * spent on it to another hash table if money was spent on that product.
 */
void productsAndMoneySpent(gpointer key, gpointer value, gpointer data) {
    GHashTable *moneySpentTable = (GHashTable *)data;
    char *prodID = (char *)key;
    ProductInfo prodInfo = (ProductInfo)value;
    double *moneySpent = g_hash_table_lookup(moneySpentTable, prodID);
    if(!moneySpent) {
        moneySpent = calloc(1, sizeof(double));
        g_hash_table_insert(moneySpentTable, prodID, moneySpent);
    }
    *moneySpent += prodInfo->moneySpent;
}

/**
 * Fills a hash table that matches the products bought by a given client (key) with the amount spent on that product by that client (value).
 */
void fillTableWithProducts(Branch branch, char *clientID, GHashTable *moneySpentOnProducts) {
    GHashTable *productsBoughtByClient = g_hash_table_lookup(branch->productsBoughtBy, clientID);
    g_hash_table_foreach(productsBoughtByClient, productsAndMoneySpent, moneySpentOnProducts);
}

/**
 * Auxiliary function used to navigate a hash table containing instances of ClientInfo and to fill an array with how many units of a product that client bought.
 */
void fillTopSelledTableProd(GHashTable* clientsWhoBoughtProduct, int branchNum, int* data) {
    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, clientsWhoBoughtProduct);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        ClientInfo clientInfo = (ClientInfo) value;
        size_t month, np;
        for (month = 0; month < 12; month++)
            for (np = 0; np < 2; np++) {
                data[branchNum + 1] += clientInfo->quantity[month][np];
                *data += clientInfo->quantity[month][np];
            }
    }
}

/**
 * Fills a hash table that matches a product (key) with the number of clients who bought it and how many units of it were sold, by branch (value).
 */
void fillTopSelledTable(Branch branch, int branchNum, GHashTable* quantTable) {
    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, branch->clientsWhoBought);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        char* prodID = (char*) key;
        GHashTable* clientsWhoBoughtProduct = (GHashTable*) value;
        int* data = g_hash_table_lookup(quantTable, prodID);
        if(data == NULL) {
            data = calloc(8, sizeof(int));
            g_hash_table_insert(quantTable, prodID, data);
        }
        size_t cliNum = g_hash_table_size(clientsWhoBoughtProduct);
        data[4] += cliNum;
        data[branchNum + 5] += cliNum;
        fillTopSelledTableProd(clientsWhoBoughtProduct, branchNum, data);
    }
}