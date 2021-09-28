#include "drawFunctions.hpp"
#include "../tinyXML/tinyxml.h"

#include <iostream>
#include <iterator>
#include <map>

using namespace draw;
using namespace std;

map<int, figure> figurasMap;
int ativarFig = 0; //Vai buscar a chave/identificador da figura para desenha-la após obter permissão


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



void renderScene(void){
	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set camera
	glLoadIdentity();
	gluLookAt(5.0, 5.0, 5.0,
		0.0, 0.0, 0.0,
		0.0f, 1.0f, 0.0f);


	drawReferencial();
    auto pos = figurasMap.find(ativarFig);
    if (pos == figurasMap.end()) {
        cout << "Não existem mais figuras." << endl;
        glutDestroyWindow(0);
    } else {
        figure value = pos->second;
        drawFigure(value);
    }

	// End of frame
	glutSwapBuffers();
}

int lerFicheiroXML(std::string xml) {
    TiXmlDocument f;
    //Load do ficheiro XML com o nome que foi passado como argumento
    string name = getPath() + xml;
    bool b = f.LoadFile(name.c_str());

    if (b) {
        TiXmlElement* root = f.RootElement();
        int i=0;

        //Inicialização do ciclo que percorre pelo ficheiro XML e lê os files que nele estão guardados
        for (TiXmlElement* elem = root->FirstChild()->ToElement(); elem!=nullptr; elem = elem->NextSiblingElement()) {

            const char* ficheiro = elem->Attribute("file");
            std::fstream fs;

            //Abre o ficheiro .3d
            fs.open(getPath()+ficheiro);
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
                fs.close();
                figurasMap.insert(pair<int, figure>(i,figura));
                i++;
            }

            else{
                std::cout << "Can't open file!"<< std::endl;
                return -1;
            }
        }
       /* //Este pedaço de código seria para apagar o ficheiro XML
        if( remove(name.c_str()) != 0 )
            perror( "Error deleting file" );*/
    }
    else{
        std::cout <<"File does not exist!\n" << std::endl;
        return -1;
    }
    return 0;
}

void nextFigureKey (unsigned char key, int x, int y){

    switch (key) {
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
            break;
        default:
            break;
    }
    //renderScene(); para mudar de cor :)
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
        if(lerFicheiroXML(argv[1])==0) {

            // put GLUT init here
            glutInit(&argc, argv);
            glutInitDisplayMode(GLUT_DEPTH | GLUT_DOUBLE | GLUT_RGBA);
            glutInitWindowPosition(100, 100);
            glutInitWindowSize(800, 800);
            glutCreateWindow("Projeto-CG");



            // put callback registry here
            glutDisplayFunc(renderScene);
            glutReshapeFunc(changeSize);

            glutKeyboardFunc(nextFigureKey);

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