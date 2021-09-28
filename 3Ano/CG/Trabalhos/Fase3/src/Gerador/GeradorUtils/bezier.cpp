#include "headers/bezier.hpp"

using namespace matrixUtils;


void bezier::getBezierPoint(float u, float v, float** matrixX, float** matrixY, float** matrixZ, float* pos) {
    float bezierMatrix[4][4] = { { -1.0f, 3.0f, -3.0f, 1.0f },
                               { 3.0f, -6.0f, 3.0f, 0.0f },
                               { -3.0f, 3.0f, 0.0f, 0.0f },
                               { 1.0f,  0.0f, 0.0f, 0.0f } };

    float vetorU[4] = { u * u * u, u * u, u, 1 };
    float vetorV[4] = { v * v * v, v * v, v, 1 };

    float vetorAux[4];
    float px[4];
    float py[4];
    float pz[4];

    float mx[4];
    float my[4];
    float mz[4];

    multMatrixVector((float*)bezierMatrix, vetorV, vetorAux);
    multMatrixVector((float*)matrixX, vetorAux, px);
    multMatrixVector((float*)matrixY, vetorAux, py);
    multMatrixVector((float*)matrixZ, vetorAux, pz);

    multMatrixVector((float*)bezierMatrix, px, mx);
    multMatrixVector((float*)bezierMatrix, py, my);
    multMatrixVector((float*)bezierMatrix, pz, mz);


    pos[0] = (vetorU[0] * mx[0]) + (vetorU[1] * mx[1]) + (vetorU[2] * mx[2]) + (vetorU[3] * mx[3]);
    pos[1] = (vetorU[0] * my[0]) + (vetorU[1] * my[1]) + (vetorU[2] * my[2]) + (vetorU[3] * my[3]);
    pos[2] = (vetorU[0] * mz[0]) + (vetorU[1] * mz[1]) + (vetorU[2] * mz[2]) + (vetorU[3] * mz[3]);

}

figure bezier::generateBezierPatches(figure pVertices, std::vector<size_t> pIndexes, size_t tecelagem) {
    figure pontos;
    float pos[4][3];
    float matrixX[4][4];
    float matrixY[4][4];
    float matrixZ[4][4];

    float u = 0;
    float v = 0;
    float inc = 1 / (float)tecelagem;

    for (size_t p = 0; p < pIndexes.size(); p += 16) {
        for (size_t i = 0; i < tecelagem; i++) {
            for (size_t j = 0; j < tecelagem; j++) {
                u = inc * i;
                v = inc * j;
                float u2 = inc * (i + 1);
                float v2 = inc * (j + 1);


                for (size_t a = 0; a < 4; a++) {
                    for (size_t b = 0; b < 4; b++) {

                        matrixX[a][b] = pVertices.pontos.at(pIndexes.at(p + a * 4 + b)).x;
                        matrixY[a][b] = pVertices.pontos.at(pIndexes.at(p + a * 4 + b)).y;
                        matrixZ[a][b] = pVertices.pontos.at(pIndexes.at(p + a * 4 + b)).z;

                    }
                }

                getBezierPoint(u, v, (float**)matrixX, (float**)matrixY, (float**)matrixZ, pos[0]);
                getBezierPoint(u, v2, (float**)matrixX, (float**)matrixY, (float**)matrixZ, pos[1]);
                getBezierPoint(u2, v, (float**)matrixX, (float**)matrixY, (float**)matrixZ, pos[2]);
                getBezierPoint(u2, v2, (float**)matrixX, (float**)matrixY, (float**)matrixZ, pos[3]);

                pontos.addPoint(pos[3][0], pos[3][1], pos[3][2]);
                pontos.addPoint(pos[2][0], pos[2][1], pos[2][2]);
                pontos.addPoint(pos[0][0], pos[0][1], pos[0][2]);

                pontos.addPoint(pos[0][0], pos[0][1], pos[0][2]);
                pontos.addPoint(pos[1][0], pos[1][1], pos[1][2]);
                pontos.addPoint(pos[3][0], pos[3][1], pos[3][2]);

            }
        }
    }

    return pontos;

}
