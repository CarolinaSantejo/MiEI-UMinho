#ifndef FASE3_TRANSFORMS_HPP
#define FASE3_TRANSFORMS_HPP
#include "../../../Utils/headers/pointFigure.hpp"


namespace transUtils {
    enum class transtype { none, translate, rotate, scale, color };

    class transform {
        float x;
        float y;
        float z;
        float angle;
        transtype type;

    public:
        void setTransform();
        void setTransform(float, float, float, float, transtype);
        float getX();
        float getY();
        float getZ();
        float getAngle();
        transtype getType();
    };

    class transfTime {
        transtype type;
        int time;
        float x;
        float y;
        float z;
        std::vector<utils::point> points;
        std::vector<utils::point> curvePoints;

    public:
        void setTransfTime();
        void setTranslateTime(int, std::vector<utils::point>);
        void setRotateTime(int, float, float, float);
        float getX();
        float getY();
        float getZ();
        std::vector<utils::point> getPoints();
        std::vector<utils::point> getCurvePoints();
        transtype getType();
        void setCurvePoints();
        int getTime();

    };


}

#endif