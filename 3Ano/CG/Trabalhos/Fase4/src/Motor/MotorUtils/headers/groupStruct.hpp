#include "../../../Utils/headers/pointFigure.hpp"
#include "transforms.hpp"
#include "modelsUtils.hpp"


using namespace utils;
using namespace transUtils;


namespace groupUtils {
    class group {
        std::vector<transform> transformacoes;
        std::vector<transfTime> curvas;
       
    public:
        std::vector<modelsUtils::model> models;
        std::vector<group> filhos;
    public:
        void addTransform(transform);
        void addCurva(transfTime);
        void addModel(modelsUtils::model);
        void addGroup(group);
        //void deleteG();
        //bool isEmptyG();
        std::vector<transform> getTransforms();
        std::vector<transfTime> getCurvas();
        std::vector<modelsUtils::model> getModels();
        std::vector<group> getFilhos();
                
        
    };
}