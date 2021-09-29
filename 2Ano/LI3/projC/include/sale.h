#ifndef SALE_H
#define SALE_H

#include "products.h"
#include "clients.h"

/**
 * \brief Structure that holds information about a sale.
 * 
 * This information is the same as the information for each sale in the sales file.
 */
typedef struct sale* Sale;

Sale initSale(char* line);
void destroySale(Sale sale);

char* getSaleProduct(Sale s);
char* getSaleClient(Sale s);
double getSaleUnitPrice(Sale s);
int getSaleQuantity(Sale s);
char getSaleNP(Sale s);
int getSaleNPInt(Sale s);
int getSaleMonth(Sale s);
int getSaleBranch(Sale s);
double getSaleTotalPrice(Sale s);

bool is_valid_sale(Sale sale, Products products, Clients clients);

#endif