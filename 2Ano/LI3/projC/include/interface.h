#ifndef INTERFACE_H
#define INTERFACE_H

#include "auxStructs.h"
#include "fileInfo.h"
#include "bills.h"
#include "branch.h"

/**
 * \brief Structure that holds all information about a Sale Management System (SGV)
 * 
 * It contains a client catalog, a product catalog, a collection of bills and information about the sales in each branch.
 * It also stores information about the files read.
 */
typedef struct sgv* SGV;

SGV initSGV();
void destroySGV(SGV sgv);
void loadSGVFromFiles(SGV sgv,
  char* clientsFilePath,
  char* productsFilePath,
  char* salesFilePath);

FileInfo getCurrentFilesInfo(SGV sgv);

StringArray getProductsStartedByLetter(SGV sgv, char letter);
SalesProfit getProductSalesAndProfit(SGV sgv, char* productID, int month);
StringArray getProductsNeverBought(SGV sgv, int branchID);
StringArray getClientsOfAllBranches(SGV sgv);
int* getClientsAndProductsNeverBoughtCount(SGV sgv);
StringArray* getProductBuyers(SGV sgv, char* prodID, int branch);
ProductTable getProductsBoughtByClient(SGV sgv, char* clientID);
SalesProfit getSalesAndProfit(SGV sgv, int minMonth, int maxMonth);
StringArray getClientFavoriteProducts(SGV sgv, char* clientID, int month);
StringArray getTopSelledProducts (SGV sgv, int limit);
StringArray getClientTopProfitProducts (SGV sgv, char * clientID , int limit);

#endif