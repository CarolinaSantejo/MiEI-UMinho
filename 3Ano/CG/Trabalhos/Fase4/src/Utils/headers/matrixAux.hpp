#ifndef FASE3_MATRIXAUX_HPP
#define FASE3_MATRIXAUX_HPP

namespace matrixUtils{
    void multMatrixVector(float*, float*, float*);
    void buildRotMatrix(float*, float*, float*, float*);
    void cross(float *, float *, float *);
    void normalize(float *);
}


#endif //FASE3_MATRIXAUX_HPP