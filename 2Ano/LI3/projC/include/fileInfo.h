#ifndef FILEINFO_H
#define FILEINFO_H

#include <stddef.h>

/**
 * \brief Structure that keeps information about the files used to load the SGV.
 * 
 * It stores the files' paths, their total number of lines and number of valid lines.
 */
typedef struct fileInfo* FileInfo;

FileInfo initFileInfo();
void destroyFileInfo(FileInfo fileInfo);

void setPaths(FileInfo fileInfo, char* clientsPath, char* productsPath, char* salesPath);
void setTotalLines(FileInfo fileInfo, size_t totalClients, size_t totalProducts, size_t totalSales);
void setValidLines(FileInfo fileInfo, size_t validClients, size_t validProducts, size_t validSales);

char* getProductsPath(FileInfo fileInfo);
char* getClientsPath(FileInfo fileInfo);
char* getSalesPath(FileInfo fileInfo);

size_t getTotalClients(FileInfo fileInfo);
size_t getValidClients(FileInfo fileInfo);
size_t getTotalProducts(FileInfo fileInfo);
size_t getValidProducts(FileInfo fileInfo);
size_t getTotalSales(FileInfo fileInfo);
size_t getValidSales(FileInfo fileInfo);


#endif