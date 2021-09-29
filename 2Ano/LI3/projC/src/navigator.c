#include <stdio.h>
#include <string.h>
#include <glib.h>

#include "navigator.h"
#include "UI.h"

#define BUFFERSIZE 100

char buffer[BUFFERSIZE];

/**
 * Allows the user to navigate a StringArray by page.
 */
void navigateStringArray(StringArray stringArray, char* title) {
    size_t size = getStringArrayLength(stringArray);
    size_t columnsPerPage = 0;
    if(size == 0) {
        errorUI('l');
        fgets(buffer, BUFFERSIZE, stdin);
    }
    else {
        if(!columnsPerPage) {
            char* line = getStringArrayElem(stringArray, 0);
            columnsPerPage = 80 / (strlen(line) + 10);
            g_free(line);
        }
        size_t currentIndex = 0;
        while(1) {
            navigatorUI('t', title, 0);
            size_t i, j;
            for(i = 0; i < COLUMNSIZE; i++) {
                for(j = 0; j < columnsPerPage; j++) {
                    size_t currentPos = currentIndex + i + COLUMNSIZE * j;
                    char* currentString = getStringArrayElem(stringArray, currentPos);
                    if(currentString) navigatorUI('e', currentString, &currentPos);
                    g_free(currentString);
                }
                navigatorUI('n', NULL, 0);
            }

            size_t totalPages = 1 + (size - 1) / (columnsPerPage * COLUMNSIZE);
            size_t currentPage = 1 + (currentIndex * totalPages / size);
            size_t pages[2] = {currentPage, totalPages};
            navigatorUI('p', NULL, pages);

            fgets(buffer, BUFFERSIZE, stdin);
            char option = *buffer;
            if(option == '0') break;
            else if(option == '1') {
                if(currentIndex + columnsPerPage * COLUMNSIZE < size)
                    currentIndex += columnsPerPage * COLUMNSIZE;
                else if(currentPage == totalPages)
                    currentIndex = 0;
            }
            else if(option == '2') {
                if(currentIndex > 0)
                    currentIndex -= columnsPerPage * COLUMNSIZE;
                else if(currentIndex == 0)
                    currentIndex = (totalPages - 1) * columnsPerPage * COLUMNSIZE;
            }
        }
    }
}