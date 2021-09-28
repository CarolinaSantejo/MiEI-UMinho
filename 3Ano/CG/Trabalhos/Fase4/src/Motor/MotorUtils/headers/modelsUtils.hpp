#ifndef FASE3_MODELUTILS_HPP
#define FASE3_MODELUTILS_HPP

#include <vector>
#include <string>
#include <GL/glew.h>


namespace modelsUtils {
	class model {
		float cores[4][4] = { {-1.0f,-1.0f,-1.0f,1.0f},
							  {-1.0f,-1.0f,-1.0f,1.0f},
							  {-1.0f,-1.0f,-1.0f,1.0f},
							  {-1.0f,-1.0f,-1.0f,1.0f} };
		
	public:
		std::vector<float> vertices;
		std::vector<size_t> indices;
		std::vector<float> normal;
		std::vector<float> texCoords;
		bool vboGerados = 0;
		GLuint verticesVBO;
		GLuint indicesVBO;
		GLuint normaisVBO;
		GLuint texPontos;
		GLuint texImagem;
		std::string texName = "";
		//int textID = -1;
	public:
		bool temDifusa();
		bool temAmbiente();
		bool temEspecular();
		bool temEmissiva();
		void addCorDifusa(float, float, float);
		void addCorAmbiente(float, float, float);
		void addCorEspecular(float, float, float);
		void addCorEmissiva(float, float, float);
		void generateVBOs();
		void loadTexture();

		float* getCorDifusa();
		float* getCorAmbiente();
		float* getCorEspecular();
		float* getCorEmissiva();
	};
}

#endif