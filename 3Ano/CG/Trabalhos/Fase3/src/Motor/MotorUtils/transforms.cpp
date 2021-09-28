#include <cmath>
#include "headers/transforms.hpp"
#include "headers/catmullrom.hpp"

using namespace transUtils;


void transform::setTransform() {
    x = 0;
    y = 0;
    z = 0;
    angle = 0;
    type = transtype::none;
}

void transform::setTransform(float xr, float yr, float zr, float ar, transtype t) {
    x = xr;
    y = yr;
    z = zr;
    angle = ar;
    type = t;
}

float transform::getX() {
    return x;
}
float transform::getY() {
    return y;
}
float transform::getZ() {
    return z;
}
float transform::getAngle() {
    return angle;
}
transtype transform::getType() {
    return type;
}


float transfTime::getX() {
    return x;
}
float transfTime::getY() {
    return y;
}
float transfTime::getZ() {
    return z;
}
int transfTime::getTime() {
    return time;
}
transtype transfTime::getType() {
    return type;
}
std::vector<utils::point> transfTime::getPoints() {
    return points;
}

std::vector<utils::point> transfTime::getCurvePoints() {
    return curvePoints;
}

void transfTime::setTransfTime() {
    x = 0;
    y = 0;
    z = 0;
    time = 0;
    type = transUtils::transtype::none;
    std::vector<utils::point> v;
    points = v;
}

void transfTime::setTranslateTime(int timer, std::vector<utils::point> p) {
    type = transUtils::transtype::translate;
    time = timer;
    points = p;
    x = 0;
    y = 0;
    z = 0;
}

void transfTime::setRotateTime(int timer, float xr, float yr, float zr) {
    x = xr;
    y = yr;
    z = zr;
    time = timer;
    type = transUtils::transtype::rotate;
}



void transfTime::setCurvePoints() {
    float res[3];
    float deriv[3];
    float t;
    for (t = 0; t < 1; t += 0.01) {
        catmull::getGlobalCatmullRomPoint(this, t, res, deriv);
        utils::point p;
        p.x = res[0];
        p.y = res[1];
        p.z = res[2];
        curvePoints.push_back(p);
    }

}
