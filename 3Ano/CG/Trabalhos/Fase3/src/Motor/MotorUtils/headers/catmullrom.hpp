#ifndef FASE3_CATMULLROM_HPP
#define FASE3_CATMULLROM_HPP


#include "transforms.hpp"

namespace catmull{
    void getCatmullRomPoint(float, utils::point[], float*, float*);
    void getGlobalCatmullRomPoint(transUtils::transfTime*, float , float* , float* );
}


#endif //FASE3_CATMULLROM_HPP
