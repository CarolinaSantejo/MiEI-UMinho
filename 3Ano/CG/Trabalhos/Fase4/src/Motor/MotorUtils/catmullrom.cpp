#include <cmath>

#include "../../Utils/headers/pointFigure.hpp"
#include "../../Utils/headers/matrixAux.hpp"
#include "headers/catmullrom.hpp"


void catmull::getCatmullRomPoint(float t, utils::point p[], float* pos, float* deriv) {

    // catmull-rom matrix
    float m[4][4] = { {-0.5f,  1.5f, -1.5f,  0.5f},
                      { 1.0f, -2.5f,  2.0f, -0.5f},
                      {-0.5f,  0.0f,  0.5f,  0.0f},
                      { 0.0f,  1.0f,  0.0f,  0.0f} };
    
    float x[4], y[4], z[4];
    for (size_t i = 0; i < 4; i++) {
        x[i] = p[i].x;
        y[i] = p[i].y;
        z[i] = p[i].z;
    }

    float* pt[3] = { x,y,z };
    float a[4];
    for (size_t i = 0; i < 3; i++) {
        // Compute A = M * P
        matrixUtils::multMatrixVector(*m, pt[i], a);
        // Compute pos = T * A
        pos[i] = a[0] * t*t*t + a[1] * t*t + a[2] * t + a[3];
        // compute deriv = T' * A
        deriv[i] = a[0] * 3*t*t + a[1] * 2*t + a[2];
    }

}


// given global t, returns the point in the curve
void catmull::getGlobalCatmullRomPoint(transUtils::transfTime* transf, float gt, float* pos, float* deriv) {
    size_t size = transf->getPoints().size();
    float t = gt * size; // this is the real global t
    int index = floor(t);  // which segment
    t = t - index; // where within  the segment

    // indices stores the points
    int indices[4];
    indices[0] = (index + size - 1) % size;
    indices[1] = (indices[0] + 1) % size;
    indices[2] = (indices[1] + 1) % size;
    indices[3] = (indices[2] + 1) % size;
    utils::point pontos[4] = { transf->getPoints()[indices[0]], transf->getPoints()[indices[1]], transf->getPoints()[indices[2]], transf->getPoints()[indices[3]] };
    getCatmullRomPoint(t, pontos, pos, deriv);
}

