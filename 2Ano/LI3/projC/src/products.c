#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#include "products.h"
/*
struct product {
    char* id;
    GHashTable* clientsTable[3]; Three hash tables (one per branch) that relates each client that bought a product with a type of purchase (N or P)
};*/

/**
 * Initializes a new instance of Products.
 */
Products initProducts() {
    Products products = g_tree_new_full((GCompareDataFunc)strcmp, NULL, g_free, g_free);
    return products;
}

/**
 * Adds a product to an instance of Products.
 */
bool addProduct(Products products, char* prodID) {
    if(is_valid_product(prodID)) {
        bool* branchInfo = calloc(3, sizeof(bool));
        g_tree_insert(products, g_strdup(prodID), branchInfo);
        return true;
    }
    return false;
}

/**
 * Frees all memory allocated to an instance of Products.
 */
void destroyProducts(Products products) {
    g_tree_destroy(products);
}

/**
 * Checks if a product code is valid or not.
 */
bool is_valid_product(char* product) {
    if(strlen(product) != 6) return false;
    if(!isupper(*product) || !isupper(*(product + 1))) return false;
    int num;
    if(sscanf(product + 2, "%d", &num) != EOF) {
        return num > 999 && num < 10000;
    }
    return false;
}

/**
 * Checks if a given product is in an instance of Products.
 */
bool is_in_products(Products products, char* prodID) {
    if(is_valid_product(prodID))
        return g_tree_lookup(products, prodID) != NULL;
    return false;
}

/**
 * Updates the information of a given product in an instance of Products.
 */
void updateProductBranchInfo(Products products, char* prodID, int branch) {
    bool* branchInfo = g_tree_lookup(products, prodID);
    branchInfo[branch] = true;
}

/**
 * Checks whether or not a product was bought in a given branch.
 */
bool productBoughtInBranch(Products products, char* prodID, int branch) {
    bool* branchInfo = g_tree_lookup(products, prodID);
    return branchInfo[branch];
}