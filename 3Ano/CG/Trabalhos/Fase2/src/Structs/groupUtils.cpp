#include "groupUtils.hpp"

using namespace groupUtils;

void group::addTransform(transform t) {
    transformacoes.push_back(t);
}
void group::addFigure(figure f) {
    figuras.push_back(f);
}
void group::addGroup(group g) {
    filhos.push_back(g);
}

void group::deleteG() {
    for (size_t i = 0; i < figuras.size(); i++) {
        figuras.pop_back();
    }
    for (size_t i = 0; i < transformacoes.size(); i++) {
        transformacoes.pop_back();
    }
    for (size_t i = 0; i < filhos.size(); i++) {
        filhos.pop_back();
    }
    printf("lll: %d %d %d\n",figuras.size(),transformacoes.size(), figuras.size());
}

bool group::isEmptyG() {
    return figuras.size() == 0 && transformacoes.size() == 0 && figuras.size() == 0;
}


std::vector<figure> group::getFiguras() {
    return figuras;
}
std::vector<transform> group::getTransforms() {
    return transformacoes;
}
std::vector<group> group::getFilhos() {
    return filhos;
}

