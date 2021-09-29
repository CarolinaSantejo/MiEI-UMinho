#ifndef QUERIESUI_H
#define QUERIESUI_H

#include <stdbool.h>

#define RED         "\x1b[0;31m"
#define RED_BOLD    "\x1b[1;31m"
#define GREEN       "\x1b[32m"
#define YELLOW      "\x1b[33m"
#define BLUE        "\x1b[34m"
#define MAGENTA     "\x1b[35m"
#define CYAN        "\x1b[36m"
#define RESET       "\x1b[0m"

void mainMenuUI(bool loadedSGV, bool* errorMessage);
void getFolderUI(char mode);
void loadingUI();
void loadInfoUI(char mode, char* path, size_t total, size_t valid, double time);

void navigatorUI(char mode, char* string, size_t* pos);

void query2UI();
void query2ResultUI(char letter, size_t numP);

void query3UI(char mode);
void query3ResultUI(char branchMode, char* prodID, int month, int* sales, double* profit);

void query4UI();
void query4ResultUI(int numP, int branchID);

void query5ResultUI(int numC);

void query6ResultUI(int numC, int numP);

void query7UI();
void query7ResultUI(char* clientID, size_t quants[12][4]);

void query8UI(int month);
void query8ResultUI(int minMonth, int maxMonth, int sales, double profit);

void query9UI(char mode);
void query9ResultUI(char *prodID, int branch, size_t totalN, size_t totalP);

void query10UI(char mode);
void query10ResultUI(int numP, char* clientID, int month);

void query11UI();

void query12UI(char mode);

void errorUI(char errorType);
void clearWindow();

void benchmarkUI();
void benchmarkResultUI(double** results);

#endif