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
        std::vector<int> indices;
        std::vector<point> normal;
        std::vector<point> texCoord;
        void addPoint(float, float, float);
        void addNormalPoint(float, float, float);
        void addTexPoint(float, float, float);
    };


    std::string getPath();

}

#endif