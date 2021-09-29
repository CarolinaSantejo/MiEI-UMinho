#ifndef SALES_H
#define SALES_H

#include "sale.h"
#include "auxStructs.h"

/**
 * \brief Structure used to hold information about all the transactions in the SGV.
 * 
 * This structure is a hash table that matches a product with its Bill.
 */
typedef GHashTable* Bills;

/**
 * \brief Structure used to hold information about the sales of a particular product.
 * 
 * This structure can be freed with the "free" function, so there's no need for a "destroyBill" function.
 */
typedef struct bill* Bill;

Bills initBills();
void addBill(Bills bills, Sale sale);
void destroyBills(Bills bills);

Bill initBill();

double getBillProfit(Bill bill, int branch, int month, int np);
int getBillSales(Bill bill, int branch, int month, int np);

SalesProfit getSalesAndProfitFromBills(Bills bills, int minMonth, int maxMonth);

#endif