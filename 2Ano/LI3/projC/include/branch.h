#ifndef BRANCH_H
#define BRANCH_H

#include "sale.h"
#include "auxStructs.h"

/**
 * \brief Structure that stores all relations between the clients and products of a branch.
 * 
 * It uses two hash tables to store information, one matches a product to all the clients who bought it, and the other matches a client to all the products they bought.
 */
typedef struct branch* Branch;

/**
 * \brief Structure used to store information about a client, in relation to a product that they bought.
 * 
 * More specifically, it stores how many units of a product that client bought, divided by month and by mode, i.e., whether the product was bought at a discount or not.
 */
typedef struct clientInfo* ClientInfo;

/**
 * \brief Structure used to store information about a product, in relation to a client who bought it.
 * 
 * More specifically, it stores how many units of that product were bought by a client, divided by month, and how much money was spent on that product by a client.
 */
typedef struct productInfo* ProductInfo;

Branch initBranch();
void addToBranch(Branch branch, Sale sale);
void destroyBranch(Branch branch);

void destroyClientInfo(void* object);
void destroyProductInfo(void* object);

int* getTotalQuantByClient(Branch branch, char* clientID);
void fillQuantTableOfProdsByClient(Branch branch, char* clientID, int month, GHashTable* quantTable);
void fillQuantTableOfProds(Branch branch, GHashTable* quantTable);

StringArray getClientsWhoBoughtProduct(Branch branch, char * prodID, int np);

void fillTableWithProducts(Branch branch, char *clientID , GHashTable* moneySpentOnProducts);

void fillTopSelledTable(Branch branch, int branchNum, GHashTable* quantTable);

#endif