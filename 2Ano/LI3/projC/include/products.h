#ifndef PRODUCTS_H
#define PRODUCTS_H

#include <stdbool.h>
#include <glib.h>

/**
 * \brief Structure that contains a product catalog.
 * 
 * This structure is a balanced binary tree that matches a product with a list that tells us if that product was purchased in a given branch.
 */
typedef GTree* Products;

Products initProducts();
bool addProduct(Products products, char* prodID);
void destroyProducts(Products products);

bool is_valid_product(char* product);
bool is_in_products(Products products, char* prodID);

void updateProductBranchInfo(Products products, char* prodID, int branch);
bool productBoughtInBranch(Products products, char* prodID, int branch);

#endif