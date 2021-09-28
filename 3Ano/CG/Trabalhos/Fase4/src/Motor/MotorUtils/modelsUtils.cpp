#include "headers/modelsUtils.hpp"
#include <IL/il.h>

#ifdef __unix__
static const std::string slash = "/";
#else
static const std::string slash = "\\";
#endif

using namespace modelsUtils;


void model::addCorDifusa(float r, float g, float b) {
    cores[0][0] = r;
    cores[0][1] = g;
    cores[0][2] = b;
}

void model::addCorAmbiente(float r, float g, float b) {
    cores[1][0] = r;
    cores[1][1] = g;
    cores[1][2] = b;
}

void model::addCorEspecular(float r, float g, float b) {
    cores[2][0] = r;
    cores[2][1] = g;
    cores[2][2] = b;
}
void model::addCorEmissiva(float r, float g, float b) {
    cores[3][0] = r;
    cores[3][1] = g;
    cores[3][2] = b;
}

float* model::getCorDifusa() {
    return cores[0];
}

float* model::getCorAmbiente() {
    return cores[1];
}

float* model::getCorEspecular() {
    return cores[2];
}

float* model::getCorEmissiva() {
    return cores[3];
}

bool model::temDifusa() {
    return cores[0][0] >= 0.0f && cores[0][1] >= 0.0f && cores[0][2] >= 0.0f;
}

bool model::temAmbiente() {
    return cores[1][0] >= 0.0f && cores[1][1] >= 0.0f && cores[1][2] >= 0.0f;
}

bool model::temEspecular() {
    return cores[2][0] >= 0.0f && cores[2][1] >= 0.0f && cores[2][2] >= 0.0f;
}

bool model::temEmissiva() {
    return cores[3][0] >= 0.0f && cores[3][1] >= 0.0f && cores[3][2] >= 0.0f;
}

void model::generateVBOs() {
    glGenBuffers(1, &indicesVBO);
    glGenBuffers(1, &verticesVBO);
    glGenBuffers(1, &normaisVBO);
    glGenBuffers(1, &texPontos);

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesVBO);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.size() * sizeof(size_t), indices.data(), GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, verticesVBO);
    glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(float), vertices.data(), GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, normaisVBO);
    glBufferData(GL_ARRAY_BUFFER, normal.size() * sizeof(float), normal.data(), GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, texPontos);
    glBufferData(GL_ARRAY_BUFFER, texCoords.size() * sizeof(float), texCoords.data(), GL_STATIC_DRAW);

    if (texName.compare("") != 0) loadTexture();

    vboGerados = 1;
}

void model::loadTexture() {
    unsigned int t, tw, th;
    unsigned char* texData;
    //unsigned int texID;
    ilInit();
    ilEnable(IL_ORIGIN_SET);
    ilOriginFunc(IL_ORIGIN_LOWER_LEFT);
    ilGenImages(1, &t);
    ilBindImage(t);
    ilLoadImage((ILstring)texName.c_str());
    tw = ilGetInteger(IL_IMAGE_WIDTH);
    th = ilGetInteger(IL_IMAGE_HEIGHT);
    ilConvertImage(IL_RGBA, IL_UNSIGNED_BYTE);
    texData = ilGetData();

    glGenTextures(1, &texImagem);

    glBindTexture(GL_TEXTURE_2D, texImagem);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(
        GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexImage2D(
        GL_TEXTURE_2D,
        0,
        GL_RGBA,
        tw,
        th,
        0,
        GL_RGBA,
        GL_UNSIGNED_BYTE,
        texData);
    glGenerateMipmap(GL_TEXTURE_2D);

    glBindTexture(GL_TEXTURE_2D, 0);
}