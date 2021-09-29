#ifndef QUERIES_H
#define QUERIES_H

#include "interface.h"
#include "UI.h"
#include "navigator.h"

void getLine(char* line, size_t N);

void query1(SGV sgv);
void query2(SGV sgv);
void query3(SGV sgv);
void query4(SGV sgv);
void query5(SGV sgv);
void query6(SGV sgv);
void query7(SGV sgv);
void query8(SGV sgv);
void query9(SGV sgv);
void query10(SGV sgv);
void query11(SGV sgv);
void query12(SGV sgv);
void query13(SGV sgv, double* loadTime);

double** benchmark(char mode);


#endif