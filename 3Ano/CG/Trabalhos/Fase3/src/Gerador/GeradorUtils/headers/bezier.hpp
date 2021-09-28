#ifndef FASE3_BEZIER_HPP
#define FASE3_BEZIER_HPP

#include "../../../Utils/headers/pointFigure.hpp"
#include "../../../Utils/headers/matrixAux.hpp"

using namespace utils;

namespace bezier{
    void getBezierPoint(float, float, float**, float**, float**, float*);
    figure generateBezierPatches(figure, std::vector<size_t>, size_t);
}











#endif //FASE3_BEZIER_HPP
