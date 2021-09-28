#include <tgmath.h>
#include "drawFunctions.hpp"
#include "../Structs/groupUtils.hpp"
#include "../tinyXML/tinyxml.h"

using namespace draw;
using namespace groupUtils;
using namespace std;
/*
Tipos de Camera:
    camera1 - Camera que que se movimenta sempre a olhar para o centro do referencial
    camera2 - Camera em primeira pessoa
*/
group grupo;
int validate=0;
float cameraMoves[3];
//float alfa = 0.0f, beta = 0.0f, radius = 15.0f; // variaveis camera1

// variaveis camera2
float xrot = 0; float yrot = 0; float angle = 0.0; 
float lastx, lasty;
bool valid = false;

// Movimentar camera2 no referencial
void camera(void) {
    glRotatef(xrot, 1.0, 0.0, 0.0);  //rotate our camera on the x - axis(up and down)
    glRotatef(yrot, 0.0, 1.0, 0.0);  //rotate our camera on the y - axis(left and right)
    glTranslated(-cameraMoves[0], -cameraMoves[1], -cameraMoves[2]); //translate the screen to the position of our camera
}


/* Calculo das coordenadas camera1
void spherical2Cartesian() {

    cameraMoves[0] = radius * cos(beta) * sin(alfa);
    cameraMoves[1] = radius * sin(beta);
    cameraMoves[2] = radius * cos(beta) * cos(alfa);
}
*/

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

void drawFigures(group g) {
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
    for (figure f : g.getFiguras()) {
        drawFigure(f);
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

    /* Para definir a camera1
    gluLookAt(cameraMoves[0], cameraMoves[1], cameraMoves[2],
              0.0f, 0.0f, 0.0f,
              0.0f, 1.0f, 0.0f);
              */

    camera();
    //drawReferencial();

    drawFigures(grupo);

    // End of frame
    glutSwapBuffers();
}

group lerFicheiro3D(string file, group g) {

    fstream fs;
    fs.open(file);
    if (fs.is_open()) {

        figure figura;
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
            x1=cood[0],y1=cood[1],z1=cood[2];
            figura.addPoint(x1,y1,z1);
        }
        g.addFigure(figura);
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
    int translate =0, rotate=0, scale=0, color=0, models=0;

    //Anda por cada elemento filho do <group>
    for (TiXmlElement* elem = gr->FirstChild()->ToElement(); elem!=nullptr;elem = elem->NextSiblingElement()){
        transform t;
        //Caso o filho seja <transform>
        if(strcmp(elem->Value(),"translate")==0){

            if (translate==1){
                validate=1;
                printf("ERRO TRANSLATE - Couldn't parse XML file\n");
                return g;
            }
            else {
                x = atof(elem->Attribute("X"));
                y = atof(elem->Attribute("Y"));
                z = atof(elem->Attribute("Z"));

                t.setTransform(x, y, z, 0, transtype::translate);
                g.addTransform(t);
                translate=1;
            }

        }
        else if(strcmp(elem->Value(),"rotate")==0){

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
                rotate=1;
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
/* Aproximar/afastar camera1
void nextFigureKey (unsigned char key, int x, int y){

    switch (key) {
        /* --- Map de figuras ---
        case 'd':
            if(ativarFig<(figurasMap.size()-1)){
                ativarFig++;
                renderScene();
            }
            break;
        case 'a':
            if(ativarFig>0){
                ativarFig--;
                renderScene();
            }
            break;*/
            /*
        case 'w':
            radius -= 0.1f;
            if (radius < 0.1f)
                radius = 0.1f;
            spherical2Cartesian();
            renderScene();
            break;

        case 's':
            radius += 0.1f;
            spherical2Cartesian();
            renderScene();
            break;
        default:
            break;
    }

}
*/

/* Rodar camera1
void processSpecialKeys(int key, int xx, int yy) {

    switch (key) {

        case GLUT_KEY_RIGHT:
            alfa += 0.1; break;

        case GLUT_KEY_LEFT:
            alfa -= 0.1; break;

        case GLUT_KEY_UP:
            beta += 0.1f;
            if (beta > 1.5f)
                beta = 1.5f;
            break;

        case GLUT_KEY_DOWN:
            beta -= 0.1f;
            if (beta < -1.5f)
                beta = -1.5f;
            break;

        case GLUT_KEY_PAGE_DOWN: radius -= 0.1f;
            if (radius < 0.1f)
                radius = 0.1f;
            break;

        case GLUT_KEY_PAGE_UP: radius += 0.1f; break;
    }
    spherical2Cartesian();
    glutPostRedisplay();

}
*/
// Movimentar camera2
void keyboard(unsigned char key, int x, int y) {
    if (key == 'r') // subir
    {
        cameraMoves[1] += 0.1;
    }

    if (key == 'f') // descer
    {
        cameraMoves[1] -= 0.1;
    }

    if (key == 'w') // frente
    {
        float xrotrad, yrotrad;
        yrotrad = (yrot / 180 * 3.141592654f);
        xrotrad = (xrot / 180 * 3.141592654f);
        cameraMoves[0] += float(sin(yrotrad));
        cameraMoves[2] -= float(cos(yrotrad));
        cameraMoves[1] -= float(sin(xrotrad));
    }

    if (key == 's') // tras
    {
        float xrotrad, yrotrad;
        yrotrad = (yrot / 180 * 3.141592654f);
        xrotrad = (xrot / 180 * 3.141592654f);
        cameraMoves[0] -= float(sin(yrotrad));
        cameraMoves[2] += float(cos(yrotrad));
        cameraMoves[1] += float(sin(xrotrad));
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

    if (key == 27)
    {
        exit(0);
    }
    glutPostRedisplay();
}

void mouse_func(int button, int state, int x, int y) {
    lastx = x;
    lasty = y;
    valid = state == GLUT_DOWN;
}

// Rodar a camera2
void mouseMovement(int x, int y) {
    if (valid) {
        int diffx = x - lastx; //check the difference between the current xand the last x position
        int diffy = y - lasty; //check the difference between the current yand the last y position
        lastx = x; //set lastx to the current x position
        lasty = y; //set lasty to the current y position
        if (xrot < -87) xrot = -87;
        else {
            if (xrot > 87) xrot = 87;
            else {
                xrot -= (float)diffy / 10;  //set the xrot to xrot with the addition of the difference in the y position
            }
        }
        yrot -= (float)diffx/10;    //set the xrot to yrot with the addition of the difference in the x position
        glutPostRedisplay();
    }
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
            //spherical2Cartesian();

            // Inicializar posicao da camera2
            cameraMoves[0] = 0;
            cameraMoves[1] = 0;
            cameraMoves[2] = 15;
            // put GLUT init here
            glutInit(&argc, argv);
            glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
            glutInitWindowPosition(100, 100);
            glutInitWindowSize(800, 800);
            glutCreateWindow("Projeto-CG");



            // put callback registry here
            glutDisplayFunc(renderScene);
            glutReshapeFunc(changeSize);

            glutKeyboardFunc(keyboard);
            //glutSpecialFunc(processSpecialKeys);
            glutMouseFunc(mouse_func);
            glutMotionFunc(mouseMovement);

            // some OpenGL settings
            glEnable(GL_DEPTH_TEST);
            glEnable(GL_CULL_FACE);
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

            // enter GLUT�s main cycle
            glutMainLoop();
        }
    }

    return 1;
}