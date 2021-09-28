#ifndef UTILS_H
#define UTILS_H


#include <cstdio>
#include <cstdlib>
#include <vector>
#include <string>
#include <iostream>
#include <fstream>
#include <sstream>
#include <map>


namespace utils{
    struct point {
        float x;
        float y;
        float z;
    };

    class figure {

    public:
        std::vector<point> pontos;
        void addPoint(float, float, float);
    };


    std::string getPath();

}

#endif