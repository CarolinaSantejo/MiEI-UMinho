#ifndef FASE3_BEZIER_HPP
#define FASE3_BEZIER_HPP

#include "../../../Utils/headers/pointFigure.hpp"
#include "../../../Utils/headers/matrixAux.hpp"

using namespace utils;

namespace bezier{
    void getBezierPoint(float, float, float**, float**, float**, float*);
    void getBezierNormalPoint(float u, float v, float** matrixX, float** matrixY, float** matrixZ, float* pos);
    figure generateBezierPatches(figure, std::vector<size_t>, size_t);
}











#endif //FASE3_BEZIER_HPP
