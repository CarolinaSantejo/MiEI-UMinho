#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif
#include <tgmath.h>
#include "MotorUtils/headers/drawFunctions.hpp"
#include "MotorUtils/headers/groupStruct.hpp"
#include "../tinyXML/tinyxml.h"
#include "MotorUtils/headers/catmullrom.hpp"
#include "../Utils/headers/matrixAux.hpp"


using namespace draw;
using namespace groupUtils;
using namespace std;

group grupo;
int validate=0;

GLuint buffers[1];
std::vector<float> pontosVBO;
size_t vboRead = 0;
int start; // Tempo inicial
float aux_y[3] = { 0,1,0 };
bool referencial = 0;
// CÂMARAS
/*
Tipos de Camara:
    camera1 - Camara que que se movimenta sempre a olhar para o centro do referencial
    camera2 - Camara em primeira pessoa
*/
float cameraMoves[3];
bool cameraType = 0; // 0 -> camera1; 1 -> camera2

// variaveis camera1

float alfa = 0.0f, beta = 0.0f, radius = 15.0f; 
int startX, startY, tracking = 0;

// variaveis camera2

float xrot = 0; float yrot = 0;
float lastx, lasty;
bool valid = false;

// Movimentar camera2 no referencial

void camera(void) {
    glRotatef(xrot, 1.0, 0.0, 0.0);  //rotate our camera on the x - axis(up and down)
    glRotatef(yrot, 0.0, 1.0, 0.0);  //rotate our camera on the y - axis(left and right)
    glTranslated(-cameraMoves[0], -cameraMoves[1], -cameraMoves[2]); //translate the screen to the position of our camera
}


 //Inicializar camera1
void spherical2Cartesian() {

    cameraMoves[0] = radius * cos(beta) * sin(alfa);
    cameraMoves[1] = radius * sin(beta);
    cameraMoves[2] = radius * cos(beta) * cos(alfa);
}


void changeSize(int w, int h)
{
    // Prevent a divide by zero, when window is too short
    // (you cant make a window with zero width).
    if (h == 0)
        h = 1;
    // compute window's aspect ratio
    float ratio = w * 1.0f / h;
    // Set the projection matrix as current
    glMatrixMode(GL_PROJECTION);
    // Load the identity matrix
    glLoadIdentity();
    // Set the viewport to be the entire window
    glViewport(0, 0, w, h);
    // Set the perspective
    gluPerspective(45.0f, ratio, 1.0f, 1000.0f);
    // return to the model view matrix mode
    glMatrixMode(GL_MODELVIEW);
}


void drawFigures(group g) {  //mudar o nome da função:  transforms_group
    glPushMatrix();
    for (transform t : g.getTransforms()) {
        switch (t.getType()) {
            case transtype::translate:
                glTranslatef(t.getX(), t.getY(), t.getZ());
                break;
            case transtype::rotate:
                glRotatef(t.getAngle(),t.getX(), t.getY(), t.getZ());
                break;
            case transtype::scale:
                glScalef(t.getX(), t.getY(), t.getZ());
                break;
            case transtype::color:
                glColor3f(t.getX(), t.getY(), t.getZ());
                break;
            default:
                break;
        }
    }
    for (transfTime t : g.getCurvas()) {
        
        if (t.getType() == transtype::translate) {
            std::vector<utils::point> pontos = t.getCurvePoints();
            // Desenhar a curva => drawFunctions.cpp
            glPushMatrix();
            drawCatmull(pontos);
            glPopMatrix();
            // Escolher um ponto da curva
            float pos[3] = { 0.0, 0.0, 0.0 };
            float deriv[3] = { 0.0, 0.0, 0.0 };

            float timeT = ((float) glutGet(GLUT_ELAPSED_TIME) / 1000) / (float)(t.getTime());
            catmull::getGlobalCatmullRomPoint(&t, timeT, (float*)pos, (float*)deriv);

            glTranslatef(pos[0], pos[1], pos[2]);

            float m[4][4];
            float x[3], z[3];

            matrixUtils::cross(deriv, aux_y, z);
            matrixUtils::cross(z, deriv, aux_y);
            matrixUtils::normalize(deriv);
            matrixUtils::normalize(aux_y);
            matrixUtils::normalize(z);
            matrixUtils::buildRotMatrix(deriv, aux_y, z, *m);
            glMultMatrixf(*m);

        }
        else if(t.getType() == transtype::rotate){
            float angle = (((float)glutGet(GLUT_ELAPSED_TIME) / 1000) * 360) / (float)t.getTime();
            glRotatef(angle, t.getX(),t.getY(),t.getZ());
        }
    }
    
    //draw_VBO
    if(g.getFSizes()!=0){
        
        glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
        glVertexPointer(3, GL_FLOAT, 0, 0);
        glDrawArrays(GL_TRIANGLES, vboRead, g.getFSizes());
        vboRead += g.getFSizes();
    }
    
    for (group gr : g.getFilhos()) {
        drawFigures(gr);
    }
    glPopMatrix();

}

void renderScene(void){
    // clear buffers
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // set camera
    glLoadIdentity();

     //Para definir a camera2
    if (cameraType) {
        camera();
    }
    else {
        // Para definir a camera1
        gluLookAt(cameraMoves[0], cameraMoves[1], cameraMoves[2],
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f);
    }
    
 
    if (referencial) drawReferencial();
    
    drawFigures(grupo);
    vboRead = 0;


    // End of frame
    glutSwapBuffers();
}

group lerFicheiro3D(string file, group g) {

    fstream fs;
    fs.open(file);
    if (fs.is_open()) {

        size_t fSize = 0;
        string line;
        float x1, y1, z1 = 0.0f; //Inicializa as coordenadas de cada ponto

        //Lê linha a linha do ficheiro, não esquecendo que cada linha é um vértice/ponto
        while (getline(fs, line)) {
            float cood[3]; //guarda num array as coordenadas de cada ponto

            std::string delimiter = " ";
            size_t pos = 0;
            std::string token;
            int i = 0;
            while ((pos = line.find(delimiter)) != std::string::npos) {
                token = line.substr(0, pos);
                cood[i] = std::stof(token); //converte para float e guarda a coordenada
                i++;
                line.erase(0, pos + delimiter.length());
            }
            pontosVBO.push_back(cood[0]);
            pontosVBO.push_back(cood[1]);
            pontosVBO.push_back(cood[2]);
            fSize++;
        }
        g.addFSize(fSize);
        fs.close();
    }

    else{
        std::cout << "Can't open file!"<< std::endl;
    }

    return g;
}


/**
 * Esta função faz parse dos vários elementos dentro de um <group> de um ficheiro XML.
 * Cria uma estrutura de dados do grupo.
 * @return 0 -> sucesso ; -1 -> ERROR
 */
group parseGroupXML(TiXmlElement* gr, group g){
    float x, y, z, angle;
    int translate =0, rotate=0, scale=0, color=0, models=0, translateTime = 0, rotateTime=0;
    int time = 0;

    //Anda por cada elemento filho do <group>
    for (TiXmlElement* elem = gr->FirstChild()->ToElement(); elem!=nullptr;elem = elem->NextSiblingElement()){
        transform t;
        transfTime tTime;
        //Caso o filho seja <transform>
        if(strcmp(elem->Value(),"translate")==0){

            if (elem->Attribute("time")) {
                if (translateTime == 1) {
                    validate = 1;
                    printf("ERRO TRANSLATE TIME - Couldn't parse XML file\n");
                    return g;
                } else {
                    time = atoi(elem->Attribute("time"));
                    TiXmlElement *point = elem->FirstChildElement("point");
                    std::vector<utils::point> pontos;
                    while (point) {
                        utils::point p;
                        p.x = atof(point->Attribute("X"));
                        p.y = atof(point->Attribute("Y"));
                        p.z = atof(point->Attribute("Z"));
                        pontos.push_back(p);
                        //next sibling
                        point = point->NextSiblingElement("point");
                    }
                    tTime.setTranslateTime(time, pontos);
                    tTime.setCurvePoints();
                    g.addCurva(tTime);
                    translateTime = 1;
                }
            }
            else {
                if (translate==1){
                    validate=1;
                    printf("ERRO TRANSLATE - Couldn't parse XML file\n");
                    return g;
                }
                else{
                    x = atof(elem->Attribute("X"));
                    y = atof(elem->Attribute("Y"));
                    z = atof(elem->Attribute("Z"));
                    t.setTransform(x, y, z, 0, transtype::translate);
                    g.addTransform(t);
                    translate = 1;
                }

            }

        }
        else if(strcmp(elem->Value(),"rotate")==0){

            if (elem->Attribute("time")) {
                if (rotateTime==1){
                    validate=1;
                    printf("ERRO ROTATE TIME- Couldn't parse XML file\n");
                    return g;
                }
                else {
                    time = atoi(elem->Attribute("time"));
                    x = atof(elem->Attribute("axisX"));
                    y = atof(elem->Attribute("axisY"));
                    z = atof(elem->Attribute("axisZ"));

                    tTime.setRotateTime(time, x, y, z);
                    g.addCurva(tTime);
                    rotateTime = 1;
                }
            }
            else {
                if (rotate==1){
                    validate=1;
                    printf("ERRO ROTATE - Couldn't parse XML file\n");
                    return g;
                }
                else {
                    angle = atof(elem->Attribute("angle"));
                    x = atof(elem->Attribute("axisX"));
                    y = atof(elem->Attribute("axisY"));
                    z = atof(elem->Attribute("axisZ"));

                    t.setTransform(x, y, z, angle, transtype::rotate);
                    g.addTransform(t);
                    rotate = 1;
                }
            }
        }
        else if(strcmp(elem->Value(),"scale")==0){

            if (scale==1){
                validate=1;
                printf("ERRO SCALE - Couldn't parse XML file\n");
                return g;
            }
            else {
                x = atof(elem->Attribute("X"));
                y = atof(elem->Attribute("Y"));
                z = atof(elem->Attribute("Z"));

                t.setTransform(x, y, z, 0, transtype::scale);
                g.addTransform(t);
                scale = 1;
            }
        }
        else if(strcmp(elem->Value(),"color")==0){

            if (color==1){
                validate=1;
                printf("ERRO COLOR - Couldn't parse XML file\n");
                return g;
            }
            else {
                x = atof(elem->Attribute("R"));
                y = atof(elem->Attribute("G"));
                z = atof(elem->Attribute("B"));

                t.setTransform(x, y, z, 0, transtype::color);
                g.addTransform(t);
                color=1;
            }
        }
        else if (strcmp(elem->Value(), "models") == 0) {

            if (models==1){
                validate=1;
                printf("ERRO MODELS - Couldn't parse XML file\n");
                return g;
            }
            else {

                TiXmlElement *model = elem->FirstChildElement("model");

                while (model) {
                    const char *ficheiro = model->Attribute("file");

                    //Abre o ficheiro .3d
                    g = lerFicheiro3D((getPath() + ficheiro), g);

                    //next sibling
                    model = model->NextSiblingElement("model");
                }
                models=1;
            }
        }
        else if (strcmp(elem->Value(), "group") == 0) { //caso encontre uma tag de grupo dentro deste grupo
            group childGr;
            childGr = parseGroupXML(elem, childGr);
            g.addGroup(childGr);
        }
    }
    return g;

}

int lerFicheiroXML(std::string xml){
    TiXmlDocument f;
    //Load do ficheiro XML com o nome que foi passado como argumento
    string name = getPath() + xml;
    bool b = f.LoadFile(name.c_str());

    if (b) {
        TiXmlElement* root = f.RootElement();

        grupo = parseGroupXML(root, grupo);
        if (validate==1) return -1;
    }
    else{
        std::cout <<"File does not exist!\n" << std::endl;
        return -1;
    }
    return 0;
}



void processMouseButtons(int button, int state, int xx, int yy) {
    if (cameraType) {
        lastx = xx;
        lasty = yy;
        valid = state == GLUT_DOWN;
    }
    else {
        if (state == GLUT_DOWN) {
            startX = xx;
            startY = yy;
            if (button == GLUT_LEFT_BUTTON)
                tracking = 1;
            else if (button == GLUT_RIGHT_BUTTON)
                tracking = 2;
            else
                tracking = 0;
        }
        else if (state == GLUT_UP) {

            if (tracking == 1) {
                alfa += (xx - startX);
                beta += (yy - startY);
            }
            else if (tracking == 2) {

                radius -= yy - startY;
                if (radius < 3)
                    radius = 3.0;
            }

            tracking = 0;
        }
    }
    
}


void processMouseMotion(int xx, int yy) {
    if (cameraType) {
        if (valid) {
            int diffx = xx - lastx; //check the difference between the current x and the last x position
            int diffy = yy - lasty; //check the difference between the current y and the last y position
            lastx = xx; //set lastx to the current x position
            lasty = yy; //set lasty to the current y position
            if (xrot < -87) xrot = -87;
            else {
                if (xrot > 87) xrot = 87;
                else {
                    xrot -= (float)diffy / 10;  //set the xrot to xrot with the addition of the difference in the y position
                }
            }
            yrot -= (float)diffx / 10;    //set the xrot to yrot with the addition of the difference in the x position
        }
    }
    else {
        int deltaX, deltaY;
        int alphaAux, betaAux;
        int rAux;

        if (!tracking)
            return;

        deltaX = xx - startX;
        deltaY = yy - startY;

        if (tracking == 1) {


            alphaAux = alfa + deltaX;
            betaAux = beta + deltaY;

            if (betaAux > 85.0)
                betaAux = 85.0;
            else if (betaAux < -85.0)
                betaAux = -85.0;

            rAux = radius;
        }
        else if (tracking == 2) {

            alphaAux = alfa;
            betaAux = beta;
            rAux = radius - deltaY;
            if (rAux < 3)
                rAux = 3;
        }

        cameraMoves[0] = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
        cameraMoves[2] = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
        cameraMoves[1] = rAux * sin(betaAux * 3.14 / 180.0);
    }
    glutPostRedisplay();
}


// Movimentar camera2

void keyboard(unsigned char key, int x, int y) {
    if (key == '1') {
        cameraType = 0;
        xrot = 0; yrot = 0;
    }
    if (key == '2') {
        cameraType = 1;
    }
    if (key == 'x') {
        referencial = !referencial;
    }
    if (cameraType) {

        if (key == 'w') // frente
        {
            float xrotrad, yrotrad;
            yrotrad = (yrot / 180 * 3.141592654f);
            xrotrad = (xrot / 180 * 3.141592654f);
            cameraMoves[0] += float(sin(yrotrad)) * 0.2;
            cameraMoves[2] -= float(cos(yrotrad)) * 0.2;
            cameraMoves[1] -= float(sin(xrotrad)) * 0.2;
        }

        if (key == 's') // tras
        {
            float xrotrad, yrotrad;
            yrotrad = (yrot / 180 * 3.141592654f);
            xrotrad = (xrot / 180 * 3.141592654f);
            cameraMoves[0] -= float(sin(yrotrad)) * 0.2;
            cameraMoves[2] += float(cos(yrotrad)) * 0.2;
            cameraMoves[1] += float(sin(xrotrad)) * 0.2;
        }

        if (key == 'd') // direita
        {
            float yrotrad;
            yrotrad = (yrot / 180 * 3.141592654f);
            cameraMoves[0] += float(cos(yrotrad)) * 0.2;
            cameraMoves[2] += float(sin(yrotrad)) * 0.2;
        }

        if (key == 'a') // esquerda
        {
            float yrotrad;
            yrotrad = (yrot / 180 * 3.141592654f);
            cameraMoves[0] -= float(cos(yrotrad)) * 0.2;
            cameraMoves[2] -= float(sin(yrotrad)) * 0.2;
        }
    }
    glutPostRedisplay();
}


void showOptions() {
    std::cout << "-------------------------KEYBOARD CONTROLS-------------------------\n"
        "1: Activate camera1\n"
        "2: Activate camera2\n"
        "x: Show/Hide axes\n"
        "Camera1: \n"
        "   Mouse: hold left button and move to rotate camera\n"
        "   Mouse: hold right button and move up/down to go further/closer\n\n"
        "Camera2: \n"
        "   w : move front\n"
        "   s : move back\n"
        "   a : move left\n"
        "   d : move right\n"
        "   Mouse: hold left button and move to rotate camera\n\n" << std::endl;
}


int main(int argc, char** argv){
    if (argc <=1) {
        std::cout << "\nMissing arguments\n" << std::endl;
    }
    else if(argc > 2){
        std::cout << "\nToo many arguments\n" << std::endl;
    }
    else {
        std::cout << "\nReading .... ..... ....\n" << std::endl;
        if (lerFicheiroXML(argv[1]) == 0) {
            
            // Inicializar posicao da camera1
            spherical2Cartesian();

            // put GLUT init here
            glutInit(&argc, argv);
            glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
            glutInitWindowPosition(100, 100);
            glutInitWindowSize(800, 800);
            glutCreateWindow("Projeto-CG");



            // put callback registry here
            glutDisplayFunc(renderScene);
            glutReshapeFunc(changeSize);
            glutIdleFunc(renderScene);


            glutMouseFunc(processMouseButtons); // para utilizar camera1
            glutMotionFunc(processMouseMotion); // para utilizar camera1
            glutKeyboardFunc(keyboard);

            #ifndef __APPLE__
                glewInit();
            #endif
            glEnableClientState(GL_VERTEX_ARRAY);
            glGenBuffers(1, buffers);
            glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
            glBufferData(GL_ARRAY_BUFFER, pontosVBO.size() * sizeof(float), &pontosVBO[0], GL_STATIC_DRAW);
            

            // some OpenGL settings
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_CULL_FACE);
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            // enter GLUT�s main cycle
            start = glutGet(GLUT_ELAPSED_TIME);
            showOptions();
            glutMainLoop();
        }
    }

    return 1;
}
