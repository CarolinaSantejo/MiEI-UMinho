#include <stdlib.h>

#include "bills.h"

struct bill {
    double totalProfit[3][12][2];
    int totalSales[3][12][2];
};

/**
 * Initializes a new instance of Bills.
 */
Bills initBills() {
    Bills bills = g_hash_table_new_full(g_str_hash, g_str_equal, g_free, g_free);
    return bills;
}

/**
 * Adds a Bill (which the function creates from a Sale it receives) to the given Bills.
 */
void addBill(Bills bills, Sale sale) {
    char* prodID = getSaleProduct(sale);
    int month = getSaleMonth(sale) - 1;
    int branch = getSaleBranch(sale) - 1;
    int np = getSaleNP(sale) == 'N' ? 0 : 1;
    Bill bill = g_hash_table_lookup(bills, prodID);
    if(!bill) {
        bill = initBill();
        g_hash_table_insert(bills, prodID, bill);
    }
    else g_free(prodID);
    bill->totalSales[branch][month][np]++;
    bill->totalProfit[branch][month][np] += getSaleTotalPrice(sale);
}

/**
 * Frees the memory allocated to an instance of Bills.
 */
void destroyBills(Bills bills) {
    g_hash_table_destroy(bills);
}

/**
 * Initializes a new instance of Bill.
 */
Bill initBill() {
    Bill bill = malloc(sizeof(struct bill));
    memset(bill, 0, sizeof(struct bill));
    return bill;
}

/**
 * Gets the profit of a product from its bill, given a branch, month and whether the profit came from regular purchases or discounted purchases.
 */
double getBillProfit(Bill bill, int branch, int month, int np) {
    return bill->totalProfit[branch][month][np];
}

/**
 * Gets the number of sales of a product from its bill, given a branch, month and whether the sales were from regular purchases or discounted purchases.
 */
int getBillSales(Bill bill, int branch, int month, int np) {
    return bill->totalSales[branch][month][np];
}

/**
 * Gets the total sales of a product and the profit a product made in a given time interval.
 */
SalesProfit getSalesAndProfitFromBills(Bills bills, int minMonth, int maxMonth) {
    SalesProfit sp = initSalesProfit();
    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, bills);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        Bill bill = (Bill) value;
        size_t branch, month, np;
        for(branch = 0; branch < 3; branch++)
            for(month = minMonth; month <= maxMonth; month++)
                for(np = 0; np < 2; np++) {
                    addToSales(sp, 0, 0, getBillSales(bill, branch, month, np));
                    addToProfit(sp, 0, 0, getBillProfit(bill, branch, month, np));
                }
    }
    return sp;
}

/**
 * Fills a Hash Table with the total sales and profit of all products stored in an instance of Bills. These totals are from a given time interval.
 */
void fillQuantTableOfProdsSales(int minMonth, int maxMonth, GHashTable* quantTable, Bills bills) {
    GHashTableIter iterator;
    g_hash_table_iter_init(&iterator, bills);
    gpointer key, value;
    while(g_hash_table_iter_next(&iterator, &key, &value)) {
        int * sales = malloc(sizeof(int));
        *sales = 0;
        char* prodID = (char*) key;
        Bill bill = (Bill) value;
        size_t branch, month, np;
        for(branch = 0; branch < 3; branch++)
            for(month = minMonth; month <= maxMonth; month++)
                for(np = 0; np < 2; np++) 
                    *sales += getBillSales(bill, branch, month, np);
        g_hash_table_insert(quantTable, prodID, sales);
    }
}