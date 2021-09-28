#include "utils.hpp"

#ifdef __unix__
#include <unistd.h>
#define getDir getcwd
static const std::string slash = "/";
#else
#include <direct.h>
#define getDir _getcwd
static const std::string slash = "\\";
#endif

using namespace utils;

// cria um ponto com as dadas coordenadas, e adiciona-o à lista de pontos da figura
void figure::addPoint(float a, float b, float c) {
    point p;
    p.x = a;
    p.y = b;
    p.z = c;
    pontos.push_back(p);
}

std::string utils::getPath(){
    char path[90];
    return getDir(path, 90) + slash + "3dFiles" + slash;
}
