#include "headers/groupStruct.hpp"

using namespace groupUtils;

void group::addTransform(transform t) {
    transformacoes.push_back(t);
}

void group::addGroup(group g) {
    filhos.push_back(g);
}
void group::addCurva(transfTime t) {
    curvas.push_back(t);
}

void group::addModel(modelsUtils::model m) {
    models.push_back(m);
}


//void group::deleteG() {
//    fSizes = 0;
//    for (size_t i = 0; i < transformacoes.size(); i++) {
//        transformacoes.pop_back();
//    }
//    for (size_t i = 0; i < filhos.size(); i++) {
//        filhos.pop_back();
//    }
//    
//}

//bool group::isEmptyG() {
//    return fSizes == 0 && transformacoes.size() == 0 && curvas.size() == 0;
//}


std::vector<transform> group::getTransforms() {
    return transformacoes;
}
std::vector<transfTime> group::getCurvas() {
    return curvas;
}
std::vector<modelsUtils::model> group::getModels() {
    return models;
}
std::vector<group> group::getFilhos() {
    return filhos;
}

