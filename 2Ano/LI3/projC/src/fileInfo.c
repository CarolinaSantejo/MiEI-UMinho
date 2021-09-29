#include <stdlib.h>
#include <glib.h>
#include "fileInfo.h"

struct fileInfo {
    char* productsPath;
    size_t totalProducts;
    size_t validProducts;

    char* clientsPath;
    size_t totalClients;
    size_t validClients;

    char* salesPath;
    size_t totalSales;
    size_t validSales;
};

/**
 * Initializes a new instance of FileInfo.
 */
FileInfo initFileInfo() {
    FileInfo fileInfo = malloc(sizeof(struct fileInfo));
    memset(fileInfo, 0, sizeof(struct fileInfo));
    return fileInfo;
}

/**
 * Frees all memory allocated to an instance of FileInfo.
 */
void destroyFileInfo(FileInfo fileInfo) {
    g_free(fileInfo->productsPath);
    g_free(fileInfo->clientsPath);
    g_free(fileInfo->salesPath);
    free(fileInfo);
}

/**
 * Sets the fields in a FileInfo regarding file paths.
 */
void setPaths(FileInfo fileInfo, char* clientsPath, char* productsPath, char* salesPath) {
    fileInfo->clientsPath = g_strdup(clientsPath);
    fileInfo->productsPath = g_strdup(productsPath);
    fileInfo->salesPath = g_strdup(salesPath);
}

/**
 * Sets the fields in a FileInfo regarding total lines.
 */
void setTotalLines(FileInfo fileInfo, size_t totalClients, size_t totalProducts, size_t totalSales) {
    fileInfo->totalClients = totalClients;
    fileInfo->totalProducts = totalProducts;
    fileInfo->totalSales = totalSales;
}

/**
 * Sets the fields in a FileInfo regarding valid lines.
 */
void setValidLines(FileInfo fileInfo, size_t validClients, size_t validProducts, size_t validSales) {
    fileInfo->validClients = validClients;
    fileInfo->validProducts = validProducts;
    fileInfo->validSales = validSales;
}

/**
 * Gets the products file path from a FileInfo.
 */
char* getProductsPath(FileInfo fileInfo) {
    return g_strdup(fileInfo->productsPath);
}

/**
 * Gets the clients file path from a FileInfo.
 */
char* getClientsPath(FileInfo fileInfo) {
    return g_strdup(fileInfo->clientsPath);
}

/**
 * Gets the sales file path from a FileInfo.
 */
char* getSalesPath(FileInfo fileInfo) {
    return g_strdup(fileInfo->salesPath);
}

/**
 * Gets the total number of clients from a FileInfo.
 */
size_t getTotalClients(FileInfo fileInfo) {
    return fileInfo->totalClients;
}

/**
 * Gets the valid number of clients from a FileInfo.
 */
size_t getValidClients(FileInfo fileInfo) {
    return fileInfo->validClients;
}

/**
 * Gets the total number of products from a FileInfo.
 */
size_t getTotalProducts(FileInfo fileInfo) {
    return fileInfo->totalProducts;
}

/**
 * Gets the valid number of products from a FileInfo.
 */
size_t getValidProducts(FileInfo fileInfo) {
    return fileInfo->validProducts;
}

/**
 * Gets the total number of sales from a FileInfo.
 */
size_t getTotalSales(FileInfo fileInfo) {
    return fileInfo->totalSales;
}

/**
 * Gets the valid number of sales from a FileInfo.
 */
size_t getValidSales(FileInfo fileInfo) {
    return fileInfo->validSales;
}