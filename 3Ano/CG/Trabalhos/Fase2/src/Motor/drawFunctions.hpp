#include "../Utils/utils.hpp"

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif



using namespace utils;

namespace draw {

    void drawFigure(utils::figure);
    void drawReferencial();

}