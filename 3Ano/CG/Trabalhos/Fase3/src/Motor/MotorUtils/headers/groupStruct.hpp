#include "../../../Utils/headers/pointFigure.hpp"
#include "transforms.hpp"

using namespace utils;
using namespace transUtils;

namespace groupUtils {
    class group {
        size_t fSizes = 0; //soma do numero de pontos(3 floats) de todas as figuras
        std::vector<transform> transformacoes;
        std::vector<transfTime> curvas;
        std::vector<group> filhos;

    public:
        void addTransform(transform);
        void addCurva(transfTime);
        void addFSize(size_t);
        void addGroup(group);
        void deleteG();
        bool isEmptyG();
        size_t getFSizes();
        std::vector<transform> getTransforms();
        std::vector<transfTime> getCurvas();
        std::vector<group> getFilhos();
        
    };
}