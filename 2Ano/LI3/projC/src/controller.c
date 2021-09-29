#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "controller.h"

#define BUFFERSIZE 100

void controller() {
    char buffer[BUFFERSIZE];
    SGV sgv = NULL;
    bool errorMessage = false;
    bool running = true;

    while(running) {
        mainMenuUI(sgv != NULL, &errorMessage);
        getLine(buffer, BUFFERSIZE);
        int option; 
        if(sscanf(buffer, "%d", &option) != 1) continue;
        switch(option) {
            case 1:
                if(sgv) destroySGV(sgv);
                sgv = initSGV();
                query1(sgv);
                break;
            case 2:
                if(sgv) query2(sgv);
                else errorMessage = true;
                break;
            case 3:
                if(sgv) query3(sgv);
                else errorMessage = true;
                break;
            case 4:
                if(sgv) query4(sgv);
                else errorMessage = true;
                break;
            case 5:
                if(sgv) query5(sgv);
                else errorMessage = true;
                break;
            case 6:
                if(sgv) query6(sgv);
                else errorMessage = true;
                break;
            case 7:
                if(sgv) query7(sgv);
                else errorMessage = true;
                break;
            case 8:
                if(sgv) query8(sgv);
                else errorMessage = true;
                break;
            case 9:
                if (sgv) query9(sgv);
                else errorMessage = true;
                break;
            case 10:
                if(sgv) query10(sgv);
                else errorMessage = true;
                break;
            case 11:
                if(sgv) query11(sgv);
                else errorMessage = true;
                break;
            case 12:
                if (sgv) query12(sgv);
                else errorMessage = true;
                break;
            case 13:
                if(sgv) query13(sgv, NULL);
                else errorMessage = true;
                break;
            case 14:
                if(sgv) {
                    destroySGV(sgv);
                    sgv = NULL;
                }
                char mode;
                while(1) {
                    benchmarkUI();
                    getLine(buffer, BUFFERSIZE);
                    mode = buffer[0];
                    if(mode >= '1' && mode <= '4') break;
                }
                clearWindow();
                loadingUI();
                double** results = benchmark(mode);
                benchmarkResultUI(results);
                getLine(buffer, BUFFERSIZE);
                size_t i;
                for(i = 0; i < 3; i++)
                    if(results[i]) free(results[i]);
                free(results);
                break;
            case 0:
                if(sgv) destroySGV(sgv);
                running = false;
                break;
        }
    }
}