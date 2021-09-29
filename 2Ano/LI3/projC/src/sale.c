#include <stdlib.h>
#include <string.h>

#include "sale.h"

struct sale {
    char* product;
    double unitary_price;
    int units_bought;
    char NP;
    char* client;
    int month_of_purchase;
    int branch;
};

/**
 * Initializes a new instance of Sale.
 */
Sale initSale(char* line) {
    Sale sale = malloc(sizeof(struct sale));
    char* token;

    token = strtok(line, " ");
    sale->product = malloc(7 * sizeof(char));
    memcpy(sale->product, token, 7);

    token = strtok(NULL, " ");
    sale->unitary_price = strtod(token, NULL);

    token = strtok(NULL, " ");
    sale->units_bought = atoi(token);

    token = strtok(NULL, " ");
    sale->NP = *token;

    token = strtok(NULL, " ");
    sale->client = malloc(6 * sizeof(char));
    memcpy(sale->client, token, 6);

    token = strtok(NULL, " ");
    sale->month_of_purchase = atoi(token);

    token = strtok(NULL, " ");
    sale->branch = atoi(token);

    return sale;
}

/**
 * Frees all memory allocated to an instance of Sale.
 */
void destroySale(Sale sale) {
    free(sale->product);
    free(sale->client);
    free(sale);
}

/**
 * Gets the product code from a Sale.
 */
char* getSaleProduct(Sale s) {
    return g_strdup(s->product);
}

/**
 * Gets the client code from a Sale.
 */
char* getSaleClient(Sale s) {
    return g_strdup(s->client);
}

/**
 * Gets the unitary price from a Sale.
 */
double getSaleUnitPrice(Sale s) {
    return s->unitary_price;
}

/**
 * Gets the total price from a Sale.
 */
double getSaleTotalPrice(Sale s) {
    return s->unitary_price * s->units_bought;
}

/**
 * Gets the quantity bought from a Sale.
 */
int getSaleQuantity(Sale s) {
    return s->units_bought;
}

/**
 * Gets the information about whether the sale was made at a discount or not from a Sale.
 */
char getSaleNP(Sale s) {
    return s->NP;
}

/**
 * Gets the information about whether the sale was made at a discount or not as an integer from a Sale.
 * 0 means the sale was normal, 1 means it was discounted.
 */
int getSaleNPInt(Sale s) {
    return s->NP == 'N' ? 0 : 1;
}

/**
 * Gets the month of purchase from a Sale.
 */
int getSaleMonth(Sale s) {
    return s->month_of_purchase;
}

/**
 * Gets the branch where the sale was made from a Sale.
 */
int getSaleBranch(Sale s) {
    return s->branch;
}

/**
 * Checks whether a given Sale is valid or not.
 * It checks the products and clients catalog to see if the product and client involved in the sale are present in the respective catalogs.
 */
bool is_valid_sale(Sale sale, Products products, Clients clients) {
    return (is_in_products(products, sale->product)
    && is_in_clients(clients, sale->client)
    && sale->unitary_price >= 0 && sale->unitary_price < 1000
    && sale->month_of_purchase >= 1 && sale->month_of_purchase <= 12
    && sale->units_bought >= 1 && sale->units_bought <= 200
    && (sale->NP == 'N' || sale->NP == 'P')
    && sale->branch >= 1 && sale->branch <= 3);
}