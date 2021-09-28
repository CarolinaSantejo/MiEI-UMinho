#include "../tinyXML/tinyxml.h"
#include "calculaVertices.hpp"



using namespace std;
using namespace generate;
TiXmlDocument doc;


int write_XML(string file, string fxml) {

    string xml = getPath() + fxml;


    if(!(doc.LoadFile(xml.c_str()))){
        TiXmlElement *element = new TiXmlElement("scene");
        doc.LinkEndChild(element);
        TiXmlElement *element2 = new TiXmlElement("model");
        element2->SetAttribute("file", file.c_str());
        element->LinkEndChild(element2);
        std::cout <<"\nXML File Created.\n"<< std::endl;
    }
    else{
        TiXmlHandle docHandle(&doc);
        TiXmlElement* fileLog = docHandle.FirstChild("scene").ToElement();
        if (fileLog) {

            TiXmlElement newCategory2("model");
            newCategory2.SetAttribute("file", file.c_str());
            fileLog->InsertEndChild(newCategory2);
        }
    }
    doc.SaveFile(xml.c_str());
}


int createFileType (vector<point> vertices, string name){

    fstream file;

    // in out(write) mode
    // ios::out Open for output operations.
    file.open(getPath() + name,ios::out);
    for(point p : vertices){
        file << p.x << " ";
        file << p.y << " ";
        file << p.z << " ";
        file << "\n";
    }

    if(!file){
        cout<<"\n       Error in creating file!!!\n";
        return -1;
    }
    cout<<"File created successfully.";
    file.close();
    return 0;
}


int main(int argc, char* argv[]) {

    figure f;

    if(argc<=1) cout << "Missing Arguments" << endl;
    else {

        //Gerar os vértices para o desenho do plano e transcrever para o ficheiro .3d
        if ((strcmp(argv[1], "Plane") == 0) && (argc == 6)) {

            float x = std::stof(argv[2]);
            float z = std::stof(argv[3]);

            f = createPlane(x, z);

            if (createFileType(f.pontos, argv[4]) == 0) {
                write_XML(argv[4],argv[5]);
                std::cout << "Done\n" << std::endl;
            }

        }

            //Gerar os vértices para o desenho do cubo/caixa e transcrever para o ficheiro .3d
        else if ((strcmp(argv[1], "Box") == 0) && (argc == 8)) {
            float x = std::stof(argv[2]);
            float y = std::stof(argv[3]);
            float z = std::stof(argv[4]);
            stringstream aux(argv[5]);
            int camadas = 0;
            aux >> camadas;

            f = createBox(x, y, z, camadas);

            if (createFileType(f.pontos, argv[6]) == 0) {
                write_XML(argv[6],argv[7]);
                std::cout << "Done\n" << std::endl;
            }
        }

            //Gerar os vértices para o desenho da esfera e transcrever para o ficheiro .3d
        else if ((strcmp(argv[1], "Sphere") == 0) && (argc == 7)) {
            float radius = std::stof(argv[2]);
            stringstream aux(argv[3]);
            int slices = 0;
            aux >> slices;
            stringstream aux2(argv[4]);
            int stacks = 0;
            aux2 >> stacks;

            f = createSphere(radius, stacks, slices);

            if (createFileType(f.pontos, argv[5]) == 0) {
                write_XML(argv[5],argv[6]);
                std::cout << "Done\n" << std::endl;
            }

        }

            //Gerar os vértices para o desenho do cone e transcrever para o ficheiro .3d
        else if ((strcmp(argv[1], "Cone") == 0) && (argc == 8)) {
            float radius = std::stof(argv[2]);
            float height = std::stof(argv[3]);
            stringstream aux(argv[4]);
            int slices = 0;
            aux >> slices;
            stringstream aux2(argv[5]);
            int stacks = 0;
            aux2 >> stacks;

            f = createCone(radius, height, stacks, slices);

            if (createFileType(f.pontos, argv[6]) == 0) {
                write_XML(argv[6],argv[7]);
                std::cout << "Done\n" << std::endl;
            }
        }

            //Gerar os vértices para o desenho do torus e transcrever para o ficheiro .3d
        else if ((strcmp(argv[1], "Torus") == 0) && (argc == 8)) {
            float radius_in = std::stof(argv[2]);
            float radius_out = std::stof(argv[3]);
            stringstream aux(argv[4]);
            int stacks = 0;
            aux >> stacks;
            stringstream aux2(argv[5]);
            int slices = 0;
            aux2 >> slices;

            f = createTorus(radius_in, radius_out, slices, stacks);

            if (createFileType(f.pontos, argv[6]) == 0) {
                write_XML(argv[6],argv[7]);
                std::cout << "Done\n" << std::endl;
            }

        }

            //Tela de ajuda e comandos
        else if (strcmp(argv[1], "-help") == 0) {
            std::cout << "Plane         [x] [y] [file.3d] [file.xml]\n"
                         "Box           [x] [y] [z] [divisions per edge] [file.3d] [file.xml]\n"
                         "Sphere        [radius] [slices] [stacks] [file.3d] [file.xml]\n"
                         "Cone          [radius] [height] [slices] [stacks] [file.3d] [file.xml]\n"
                         "Torus         [radius_OUT] [radius_IN] [stacks] [slices] [file.3d] [file.xml]\n" << std::endl;
        } else {
            std::cout << "\nMissing arguments\n" << std::endl;
        }
    }

    return 1;
}
