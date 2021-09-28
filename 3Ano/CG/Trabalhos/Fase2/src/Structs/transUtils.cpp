#include "transUtils.hpp"

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