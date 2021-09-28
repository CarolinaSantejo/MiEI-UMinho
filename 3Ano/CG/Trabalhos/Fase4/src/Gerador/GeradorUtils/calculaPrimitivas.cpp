#define _USE_MATH_DEFINES
#include <cmath>

#include "headers/calculaPrimitivas.hpp"



figure generate::createPlane(float x, float z) {

    figure f;

    float x1 = x / 2;
    float z1 = z / 2;



    // Face de cima
    
    f.addPoint(x1, 0, z1);
    f.addPoint(x1, 0, -z1);
    f.addPoint(-x1, 0, -z1);
    f.addPoint(-x1, 0, z1);

    f.addTexPoint(1, 0, 0);
    f.addTexPoint(1, 1, 0);
    f.addTexPoint(0, 1, 0);
    f.addTexPoint(0, 0, 0);

    f.addNormalPoint(0, 1, 0);
    f.addNormalPoint(0, 1, 0);
    f.addNormalPoint(0, 1, 0);
    f.addNormalPoint(0, 1, 0);

    //Triângulo 1
    
    f.indices.push_back(0);
    f.indices.push_back(1);
    f.indices.push_back(2);
    
     
    // Triângulo 2

    f.indices.push_back(2);
    f.indices.push_back(3);
    f.indices.push_back(0);
    
    
    // Face de baixo
    
    f.addPoint(-x1, 0, -z1);
    f.addPoint(x1, 0, -z1);
    f.addPoint(x1, 0, z1);
    f.addPoint(-x1, 0, z1);

    f.addTexPoint(1, 1, 0);
    f.addTexPoint(0, 1, 0);
    f.addTexPoint(0, 0, 0);
    f.addTexPoint(1, 0, 0);

    f.addNormalPoint(0, -1, 0);
    f.addNormalPoint(0, -1, 0);
    f.addNormalPoint(0, -1, 0);
    f.addNormalPoint(0, -1, 0);

    //Triângulo 1
    f.indices.push_back(4);
    f.indices.push_back(5);
    f.indices.push_back(6);

    // Triângulo 2
    f.indices.push_back(6);
    f.indices.push_back(7);
    f.indices.push_back(4);
    
    
    return f;
}


figure generate::createBox(float x, float y, float z, int camadas) {
    figure f;

    float x1 = x / 2;
    float z1 = z / 2;
    float camadaX = x / camadas;
    float camadaY = y / camadas;
    float camadaZ = z / camadas;
    int i, j;
    int indexCount = 0;

    float hor = 1.0f / 3.0f;
    float ver = 1.0f / 2.0f;

    float textH = hor / (float)camadas;
    float textV = ver / (float)camadas;

    float baseH = 0;
    float baseV = 0;

    // Desenhar a base
    float auxX = -x1;
    float auxZ = -z1;
    for (i = 0; i < camadas; i++) {
        for (j = 0; j < camadas; j++) {

            f.addPoint(auxX, 0, auxZ);
            f.addPoint(auxX + camadaX, 0, auxZ);
            f.addPoint(auxX + camadaX, 0, auxZ + camadaZ);
            f.addPoint(auxX, 0, auxZ + camadaZ);

            f.addTexPoint(baseH, baseV, 0);
            f.addTexPoint(baseH + textH, baseV, 0);
            f.addTexPoint(baseH + textH, baseV + textV, 0);
            f.addTexPoint(baseH, baseV + textV, 0);
                   
            f.addNormalPoint(0, -1, 0);
            f.addNormalPoint(0, -1, 0);
            f.addNormalPoint(0, -1, 0);
            f.addNormalPoint(0, -1, 0);

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount+1);
            f.indices.push_back(indexCount+2);

                       

            f.indices.push_back(indexCount+2);
            f.indices.push_back(indexCount+3);
            f.indices.push_back(indexCount);
            


            indexCount += 4;
            auxZ += camadaZ;
            baseV += textV;
            
        }
        auxZ = -z1;
        auxX += camadaX;
        baseH += textH;
        baseV = 0;
    }

    // Desenhar o teto
    auxX = -x1;
    auxZ = -z1;
    float auxY = y;
    float tetoH = hor; 
    float tetoV = ver - textV;
    for (i = 0; i < camadas; i++) {
        for (j = 0; j < camadas; j++) {

            f.addPoint(auxX + camadaX, auxY, auxZ + camadaZ);
            f.addPoint(auxX + camadaX, auxY, auxZ);
            f.addPoint(auxX, auxY, auxZ);
            f.addPoint(auxX, auxY, auxZ + camadaZ);

            f.addTexPoint(tetoH + textH, tetoV, 0);
            f.addTexPoint(tetoH + textH, tetoV + textV, 0);
            f.addTexPoint(tetoH, tetoV + textV, 0);
            f.addTexPoint(tetoH, tetoV, 0);
            
            f.addNormalPoint(0, 1, 0);
            f.addNormalPoint(0, 1, 0);
            f.addNormalPoint(0, 1, 0);
            f.addNormalPoint(0, 1, 0);

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);

            
            f.indices.push_back(indexCount + 2);
            f.indices.push_back(indexCount + 3);
            f.indices.push_back(indexCount);

            indexCount += 4;
            auxZ += camadaZ;
            tetoV -= textV;
        }
        auxZ = -z1;
        auxX += camadaX;
        
        tetoH += textH;
        tetoV = ver - textV;
    }

    // Desenhar o lado direito
    auxX = x1;
    auxZ = z1;
    auxY = y;
    float dirH = hor*2;
    float dirV = ver - textV;;
    for (i = 0; i < camadas; i++) {
        for (j = 0; j < camadas; j++) {
            f.addPoint(auxX, auxY, auxZ);
            f.addPoint(auxX, auxY - camadaY, auxZ);
            f.addPoint(auxX, auxY - camadaY, auxZ - camadaZ);
            f.addPoint(auxX, auxY, auxZ - camadaZ);

                    
            f.addTexPoint(dirH, dirV + textV, 0);
            f.addTexPoint(dirH, dirV, 0);
            f.addTexPoint(dirH + textH, dirV, 0);
            f.addTexPoint(dirH + textH, dirV + textV, 0);
            
            f.addNormalPoint(1, 0, 0);
            f.addNormalPoint(1, 0, 0);
            f.addNormalPoint(1, 0, 0);
            f.addNormalPoint(1, 0, 0);

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);                  

            f.indices.push_back(indexCount + 2);
            f.indices.push_back(indexCount + 3);
            f.indices.push_back(indexCount);


            indexCount += 4;
            auxZ -= camadaZ;
            dirH += textH;
        }
        auxZ = z1;
        auxY -= camadaY;
        dirV -= textV;
        dirH = hor*2;
    }

    // Desenhar o lado esquerdo
    auxX = -x1;
    auxZ = -z1;
    auxY = y;
    float esqH = 0;
    float esqV = 1-textV;
    for (i = 0; i < camadas; i++) {
        for (j = 0; j < camadas; j++) {
            f.addPoint(auxX, auxY, auxZ);
            f.addPoint(auxX, auxY - camadaY, auxZ);
            f.addPoint(auxX, auxY - camadaY, auxZ + camadaZ);
            f.addPoint(auxX, auxY, auxZ + camadaZ);

            f.addTexPoint(esqH, esqV + textV, 0);
            f.addTexPoint(esqH, esqV, 0);
            f.addTexPoint(esqH + textH, esqV, 0);
            f.addTexPoint(esqH + textH, esqV + textV, 0);
            
            f.addNormalPoint(-1, 0, 0);
            f.addNormalPoint(-1, 0, 0);
            f.addNormalPoint(-1, 0, 0);
            f.addNormalPoint(-1, 0, 0);

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);
                        

            f.indices.push_back(indexCount + 2);
            f.indices.push_back(indexCount + 3);
            f.indices.push_back(indexCount);

            
            
            indexCount += 4;
            auxZ += camadaZ;
            esqH += textH;
        }
        auxZ = -z1;
        auxY -= camadaY;
        esqV -= textV;
        esqH = 0;
    }

    // Desenhar frente
    auxX = -x1;
    auxZ = z1;
    auxY = y;
    float frenteH = hor;
    float frenteV = 1-textV;
    for (i = 0; i < camadas; i++) {
        for (j = 0; j < camadas; j++) {
            f.addPoint(auxX, auxY, auxZ);
            f.addPoint(auxX, auxY - camadaY, auxZ);
            f.addPoint(auxX + camadaX, auxY - camadaY, auxZ);
            f.addPoint(auxX + camadaX, auxY, auxZ);

            f.addTexPoint(frenteH, frenteV + textV, 0);
            f.addTexPoint(frenteH, frenteV, 0);
            f.addTexPoint(frenteH + textH, frenteV, 0);
            f.addTexPoint(frenteH + textH, frenteV + textV, 0);
            
            f.addNormalPoint(0, 0, 1);
            f.addNormalPoint(0, 0, 1);
            f.addNormalPoint(0, 0, 1);
            f.addNormalPoint(0, 0, 1);

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);            

            f.indices.push_back(indexCount + 2);
            f.indices.push_back(indexCount + 3);
            f.indices.push_back(indexCount);


            indexCount += 4;
            auxX += camadaX;
            frenteH += textH;
        }
        auxX = -x1;
        auxY -= camadaY;
        frenteV -= textV;
        frenteH = hor;
    }

    // Desenhar trás
    auxX = x1;
    auxZ = -z1;
    auxY = y;
    float trasH = hor * 2;
    float trasV = 1-textV;
    for (i = 0; i < camadas; i++) {
        for (j = 0; j < camadas; j++) {
            f.addPoint(auxX, auxY, auxZ);
            f.addPoint(auxX, auxY - camadaY, auxZ);
            f.addPoint(auxX - camadaX, auxY - camadaY, auxZ);
            f.addPoint(auxX - camadaX, auxY, auxZ);

            f.addTexPoint(trasH, trasV + textV, 0);
            f.addTexPoint(trasH, trasV, 0);
            f.addTexPoint(trasH + textH, trasV, 0);
            f.addTexPoint(trasH + textH, trasV + textV, 0);

            f.addNormalPoint(0, 0, -1);
            f.addNormalPoint(0, 0, -1);
            f.addNormalPoint(0, 0, -1);
            f.addNormalPoint(0, 0, -1);

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);            
            
            f.indices.push_back(indexCount + 2);
            f.indices.push_back(indexCount + 3);
            f.indices.push_back(indexCount);

            

            indexCount += 4;
            auxX -= camadaX;
            trasH += textH;
        }
        auxX = x1;
        auxY -= camadaY;
        trasV -= textV;
        trasH = hor * 2;
    }
    return f;
}

figure generate::createSphere(float radius, int slices, int stacks){
    figure f;

    float delta1 = M_PI / stacks;
    float delta2 = 2 * M_PI / slices;
    float theta1 = -M_PI / 2;
    float theta2 = 0.0f;
    int indexCount = 0;
    float textH = 1.0f / (float)slices;
    float textV = 1.0f / (float)stacks;

    for (int i = 0; i < stacks; i++) {

        for (int j = 0; j < slices; j++) {
            theta2 = j * delta2;
            f.addPoint(cos(theta1 + delta1) * sin(theta2) * radius, sin(theta1 + delta1) * radius, cos(theta1 + delta1) * cos(theta2) * radius);
            f.addPoint(cos(theta1) * sin(theta2) * radius, sin(theta1) * radius, cos(theta1) * cos(theta2) * radius);
            f.addPoint(cos(theta1) * sin(theta2 + delta2) * radius, sin(theta1) * radius, cos(theta1) * cos(theta2 + delta2) * radius);
            f.addPoint(cos(theta1 + delta1) * sin(theta2 + delta2) * radius, sin(theta1 + delta1) * radius, cos(theta1 + delta1) * cos(theta2 + delta2) * radius);

            f.addNormalPoint(cos(theta1 + delta1) * sin(theta2), sin(theta1 + delta1), cos(theta1 + delta1) * cos(theta2));
            f.addNormalPoint(cos(theta1) * sin(theta2), sin(theta1), cos(theta1) * cos(theta2));
            f.addNormalPoint(cos(theta1) * sin(theta2 + delta2), sin(theta1), cos(theta1) * cos(theta2 + delta2));
            f.addNormalPoint(cos(theta1 + delta1) * sin(theta2 + delta2), sin(theta1 + delta1), cos(theta1 + delta1) * cos(theta2 + delta2));
            
            /* -Para desenhar o sol
            f.addNormalPoint(-(cos(theta1 + delta1) * sin(theta2)), -sin(theta1 + delta1), -(cos(theta1 + delta1) * cos(theta2)));
            f.addNormalPoint(-(cos(theta1) * sin(theta2)), -sin(theta1), -(cos(theta1) * cos(theta2)));
            f.addNormalPoint(-(cos(theta1) * sin(theta2 + delta2)), -sin(theta1), -(cos(theta1) * cos(theta2 + delta2)));
            f.addNormalPoint(-(cos(theta1 + delta1) * sin(theta2 + delta2)), -sin(theta1 + delta1), -(cos(theta1 + delta1) * cos(theta2 + delta2)));*/

            f.addTexPoint(j * textH, i * textV + textV, 0);
            f.addTexPoint(j * textH, i * textV, 0);
            f.addTexPoint(j * textH + textH, i * textV, 0);
            f.addTexPoint(j * textH + textH, i * textV + textV, 0);
            

            //Triângulo 1
            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);
                                 

            //Triângulo 2
            f.indices.push_back(indexCount );
            f.indices.push_back(indexCount + 2);
            f.indices.push_back(indexCount + 3);



            indexCount += 4;
        }
        theta1 += delta1;
    }
    
    return f;
}

figure generate::createCone(float radius, float height, int slices, int stacks) {
    figure f;

    float theta = 0;
    float nextTheta = 0;
    float delta = (2 * M_PI) / slices;
    float raio = radius / stacks;
    float alturas = height / stacks;
    int i, j;
    int indexCount = 0;

    float textH = 1.0f / slices;
    float textV = 1.0f / stacks;

    float var = (2 * radius * M_PI) / slices;
    //fazer o circulo da base
    for (i = 0; i < slices; i++) {

        nextTheta = theta + delta;

        f.addPoint(0, 0, 0);
        f.addPoint(radius * sin(nextTheta), 0, radius * cos(nextTheta));
        f.addPoint(radius * sin(theta), 0, radius * cos(theta));

        f.indices.push_back(indexCount);
        f.indices.push_back(indexCount + 1);
        f.indices.push_back(indexCount + 2);

        f.addNormalPoint(0, -1, 0);
        f.addNormalPoint(0, -1, 0);
        f.addNormalPoint(0, -1, 0);

        f.addTexPoint(var / 2, 1, 0);
        f.addTexPoint(i * var, 0, 0);
        f.addTexPoint((i + 1) * var, 0, 0);
        

        theta = nextTheta;
        indexCount += 3;
    }

    // Fazer as laterais
    float r1 = radius;
    float r2 = radius - raio;
    float alt1 = 0;
    float alt2 = alturas;
    theta = 0;
    nextTheta = 0;
    float ny = cos(atan(height / radius));

    for (i = 0; i < slices; i++) {

        nextTheta += delta;

        for (j = 0; j < stacks; j++) {
            f.addPoint(r1 * sin(nextTheta), alt1, r1 * cos(nextTheta));
            f.addPoint(r2 * sin(nextTheta), alt2, r2 * cos(nextTheta));
            f.addPoint(r1 * sin(theta), alt1, r1 * cos(theta));
            f.addPoint(r2 * sin(theta), alt2, r2 * cos(theta));

            f.addTexPoint((i + 1) * textH, j * textV, 0);
            f.addTexPoint((i + 1) * textH, (j + 1) * textV, 0);
            f.addTexPoint(i * textH, j * textV, 0);
            f.addTexPoint(i * textH, (j + 1) * textV, 0);

            f.addNormalPoint(sin(nextTheta), ny, cos(nextTheta));
            f.addNormalPoint(sin(nextTheta), ny, cos(nextTheta));
            f.addNormalPoint(sin(theta), ny, cos(theta));
            f.addNormalPoint(sin(theta), ny, cos(theta));

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);

            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 3);
            f.indices.push_back(indexCount + 2);


            r1 -= raio;
            r2 -= raio;
            alt1 += alturas;
            alt2 += alturas;
            indexCount+=4;
        }
        r1 = radius;
        r2 = radius - raio;
        alt1 = 0;
        alt2 = alturas;
        theta = nextTheta;
    }
    return f;
}

figure generate::createTorus(float raio_in, float raio_out, int slices, int stacks) {
    figure f;

    float delta1 = 2 * M_PI / slices;
    float delta2 = 2 * M_PI / stacks;
    float phi = 0;
    float theta = 0;
    int indexCount = 0;

    float textH = 1.0f / slices;
    float textV = 1.0f / stacks;

    for (int i = 0; i < slices; i++) {
        for (int j = 0; j < stacks; j++) {
            f.addPoint((raio_in + raio_out * cos(phi)) * cos(theta), (raio_in + raio_out * cos(phi)) * sin(theta), raio_out * sin(phi));
            f.addPoint((raio_in + raio_out * cos(phi)) * cos(theta + delta1), (raio_in + raio_out * cos(phi)) * sin(theta + delta1), raio_out * sin(phi));
            f.addPoint((raio_in + raio_out * cos(phi + delta2)) * cos(theta + delta1), (raio_in + raio_out * cos(phi + delta2)) * sin(theta + delta1), raio_out * sin(phi + delta2));
            f.addPoint((raio_in + raio_out * cos(phi + delta2)) * cos(theta), (raio_in + raio_out * cos(phi + delta2)) * sin(theta), raio_out * sin(phi + delta2));

            f.addTexPoint(i * textH, j * textV, 0);
            f.addTexPoint((i + 1) * textH, j * textV, 0);
            f.addTexPoint((i + 1) * textH, (j + 1) * textV, 0);
            f.addTexPoint(i * textH, (j + 1) * textV, 0);

            f.addNormalPoint(cos(phi) * cos(theta), cos(phi) * sin(theta), sin(phi));
            f.addNormalPoint(cos(phi) * cos(theta + delta1), cos(phi) * sin(theta + delta1), sin(phi));
            f.addNormalPoint(cos(phi + delta2) * cos(theta + delta1), cos(phi + delta2) * sin(theta + delta1), sin(phi + delta2));
            f.addNormalPoint(cos(phi + delta2) * cos(theta), cos(phi + delta2) * sin(theta), sin(phi + delta2));

            f.indices.push_back(indexCount);
            f.indices.push_back(indexCount + 1);
            f.indices.push_back(indexCount + 2);
         

            f.indices.push_back(indexCount + 2);
            f.indices.push_back(indexCount + 3);
            f.indices.push_back(indexCount);


            indexCount += 4;
            phi = delta2 * (j + 1);
        }

        theta = delta1 * (i + 1);
    }


    return f;
}


