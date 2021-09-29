#ifndef AUX_STRUCTS_H
#define AUX_STRUCTS_H

#include <stdbool.h>
#include <stddef.h>

#define COLUMNSIZE 16

/**
 * \brief Structure used to hold an array of strings.
 * 
 * This structure also has an extra "data" variable, used to hold information like the maximum number of elements allowed on the array.
 * This variable is used by some of the queries.
 */
typedef struct stringArray * StringArray;

/**
 * \brief Structure used to hold information about the total sales and profit of one or more products.
 * 
 * This structure stores its information by branch and by mode, i.e., whether the product/s was/were bought at full price or at a discount.
 */
typedef struct salesProfit * SalesProfit;

/**
 * \brief Structure used to hold information about the total quantity of a product bought by month and by mode, i.e., whether it was bought at full price or at a discount.
 */
typedef struct productTable * ProductTable;

StringArray initStringArray(size_t size);
StringArray initStringArrayWithData(size_t size, int data);
void destroyStringArray(StringArray stringArray);
void addToStringArray(StringArray stringArray, char* string);
bool addToStringArrayIf(StringArray stringArray, char* string);
void resizeStringArrayToN(StringArray stringArray);
size_t getStringArrayLength(StringArray stringArray);
int getStringArrayData(StringArray stringArray);
char* getStringArrayElem(StringArray stringArray, size_t index);

SalesProfit initSalesProfit();
void destroySalesProfit(SalesProfit salesProfit);
void addToSales(SalesProfit salesProfit, int branch, int np, int sale);
void addToProfit(SalesProfit salesProfit, int branch, int np, double profit);
int getSalesFromSP(SalesProfit salesProfit, int branch, int np);
double getProfitFromSP(SalesProfit salesProfit, int branch, int np);

ProductTable initProductTable();
void destroyProductTable(ProductTable productTable);
void setProductTableYear(ProductTable productTable, int branch, int* quantArray);
int getProductTableQuant(ProductTable productTable, int branch, int month);

#endif