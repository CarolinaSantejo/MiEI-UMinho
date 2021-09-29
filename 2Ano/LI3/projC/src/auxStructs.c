#include <stdio.h>
#include <stdlib.h>
#include <glib.h>
#include <math.h>

#include "auxStructs.h"
#include "UI.h"

#define BUFFERSIZE 100

struct stringArray {
  char **array;
  size_t N;
  int data;
};

struct salesProfit {
  int sales[3][2];
  double profit[3][2];
};

struct productTable {
  int totalQuant[3][12];
};

/**
 * Initializes a new instance of StringArray with a given size.
 */
StringArray initStringArray(size_t size) {
    StringArray stringArray = malloc(sizeof(struct stringArray));
    stringArray->array = calloc(size + 1, sizeof(char*));
    stringArray->array[size] = NULL;
    stringArray->N = 0;
    stringArray->data = 0;
    return stringArray;
}

/**
 * Initializes a new instance of StringArray with a given size and **data** value.
 */
StringArray initStringArrayWithData(size_t size, int data) {
    StringArray stringArray = malloc(sizeof(struct stringArray));
    stringArray->array = calloc(size + 1, sizeof(char*));
    stringArray->array[size] = NULL;
    stringArray->N = 0;
    stringArray->data = data;
    return stringArray;
}

/**
 * Frees all memory allocated to an instance of StringArray.
 */
void destroyStringArray(StringArray stringArray) {
    g_strfreev(stringArray->array);
    free(stringArray);
}

/**
 * Adds a new element to a StringArray.
 */
void addToStringArray(StringArray stringArray, char* string) {
    stringArray->array[stringArray->N++] = g_strdup(string);
}

/**
 * Adds a new element to a StringArray only if the array's limit (stored in the **data** variable) hasn't been reached.
 */
bool addToStringArrayIf(StringArray stringArray, char* string) {
    if(!stringArray->data || stringArray->N < stringArray->data) {
        stringArray->array[stringArray->N++] = g_strdup(string);
        return true;
    }
    else return false;
}

/**
 * Deallocates unused pointers in a StringArray.
 */
void resizeStringArrayToN(StringArray stringArray) {
    stringArray->array = realloc(stringArray->array, (stringArray->N + 1) * sizeof(char*));
    stringArray->array[stringArray->N] = NULL;
}

/**
 * Gets the **data** variable from a StringArray.
 */
int getStringArrayData(StringArray stringArray) {return stringArray->data; }

/**
 * Gets the length of a StringArray, i.e., how many elements it has.
 */
size_t getStringArrayLength(StringArray stringArray) {return stringArray->N; }

/**
 * Gets the element of a given index of a StringArray.
 */
char* getStringArrayElem(StringArray stringArray, size_t index) {return stringArray->N > index ? g_strdup(stringArray->array[index]) : NULL; }

/**
 * Initializes a new instance of SalesProfit.
 */
SalesProfit initSalesProfit() {
    SalesProfit salesProfit = malloc(sizeof(struct salesProfit));
    memset(salesProfit, 0, sizeof(struct salesProfit));
    return salesProfit;
}

/**
 * Frees all memory allocated to an instance of SalesProfit.
 */
void destroySalesProfit(SalesProfit salesProfit) {
    free(salesProfit);
}

/**
 * Adds an x amount to the sales of a given branch and a given mode.
 */
void addToSales(SalesProfit salesProfit, int branch, int np, int sale) {
    salesProfit->sales[branch][np] += sale;
}

/**
 * Adds an x amount to the profit of a given branch and a given mode.
 */
void addToProfit(SalesProfit salesProfit, int branch, int np, double profit) {
    salesProfit->profit[branch][np] += profit;
}

/**
 * Gets the total sales of a SalesProfit, given a branch and a mode.
 */
int getSalesFromSP(SalesProfit salesProfit, int branch, int np) {
    return salesProfit->sales[branch][np];
}

/**
 * Gets the total profit of a SalesProfit, given a branch and a mode.
 */
double getProfitFromSP(SalesProfit salesProfit, int branch, int np) {
    return salesProfit->profit[branch][np];
}

/**
 * Initializes a new instance of a ProductTable.
 */
ProductTable initProductTable() {
    ProductTable productTable = malloc(sizeof(struct productTable));
    memset(productTable, 0, sizeof(struct productTable));
    return productTable;
}

/**
 * Frees all memory allocated to an instance of ProductTable.
 */
void destroyProductTable(ProductTable productTable) {
    free(productTable);
}

/**
 * Sets all the values of a ProductTable of a given branch from an array of 12 elements.
 */
void setProductTableYear(ProductTable productTable, int branch, int* quantArray) {
    memcpy(productTable->totalQuant[branch], quantArray, 12 * sizeof(int));
}

/**
 * Gets the quantity sold from a ProductTable given a branch and a month.
 */
int getProductTableQuant(ProductTable productTable, int branch, int month) {
    return productTable->totalQuant[branch][month];
}