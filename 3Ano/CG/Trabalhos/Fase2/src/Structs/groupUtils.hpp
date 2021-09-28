#include "../Utils/utils.hpp"
#include "transUtils.hpp"

using namespace utils;
using namespace transUtils;

namespace groupUtils {
    class group {
        std::vector<figure> figuras;
        std::vector<transform> transformacoes;
        std::vector<group> filhos;

    public:
        void addTransform(transform);
        void addFigure(figure);
        void addGroup(group);
        void deleteG();
        bool isEmptyG();
        std::vector<figure> getFiguras();
        std::vector<transform> getTransforms();
        std::vector<group> getFilhos();
        
    };
}