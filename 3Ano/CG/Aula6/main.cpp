

#include<stdio.h>
#include<stdlib.h>

#define _USE_MATH_DEFINES
#include <iostream>
#include <math.h>
#include <vector>

#include <IL/il.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glew.h>
#include <GL/glut.h>
#endif

float ri = 35;
float rc = 15;
float animationIn = 0;
float animationOut = 0;

float camX = 00, camY = 30, camZ = 40;
int startX, startY, tracking = 0;

int alpha = 0, beta = 45, r = 50;
size_t ih, iw;

unsigned char* imageData;
std::vector<float> pontos;
GLuint buffers[1];

void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if(h == 0)
		h = 1;

	// compute window's aspect ratio 
	float ratio = w * 1.0 / h;

	// Reset the coordinate system before modifying
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	
	// Set the viewport to be the entire window
    glViewport(0, 0, w, h);

	// Set the correct perspective
	gluPerspective(45,ratio,1,1000);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}
float h(float x, float z) {
	double y = imageData[(int)(iw * x + z)];
	return y / 255 * 30;
}

float hf(float x, float z) {
	int x1 = floor(x); 
	int x2 = x1 + 1; 
	int z1 = floor(z); 
	int z2 = z1 + 1;
	float fz = z - z1;   // 0 <= fz<= 1
	float fx = x - x1;
	float h_x1_z = h(x1, z1) * (1 - fz) + h(x1, z2) * fz;
	float h_x2_z = h(x2, z1) * (1 - fz) + h(x2, z2) * fz;
	float height_xz = h_x1_z * (1 - fx) + h_x2_z * fx;
	//std::cout << "fz: " << fz << "fx: " << fx << std::endl; // print teste
	return height_xz;
}





void drawTerrain() {

    // colocar aqui o código de desnho do terreno usando VBOs com TRIANGLE_STRIPS
	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glVertexPointer(3, GL_FLOAT, 0, 0);

	for (int i = 0; i < ih - 1; i++) { 
		glDrawArrays(GL_TRIANGLE_STRIP, i * 2 * iw, iw * 2);
	}

}

void drawTrees() {
	float betaTree = 0;
	float xTree = 0;
	float zTree = 0;
	float rmin = 0;
	for (size_t i = 0; i < 1000; i++) {
		srand(i * 10);
		betaTree = (((float)(rand() % 360)) / 180) * M_PI;
		// Calcular x
		rmin = r * sin(betaTree);
		if (rmin > 0)
			xTree = rmin + (rand() % (int)(100 - rmin + 1));
		else xTree = -100 + (rand() % (int)(rmin - (-100) + 1));
		// Calcular z
		rmin = r * cos(betaTree);
		if (rmin > 0)
			zTree = rmin + (rand() % (int)(100 - rmin + 1));
		else zTree = -100 + (rand() % (int)(rmin - (-100) + 1));
		float yTree = hf(xTree + iw * 0.5, zTree + ih * 0.5);
		glColor3f(0.5f, 0.35f, 0.05f);
		glPushMatrix();
		glTranslatef(xTree, yTree, zTree); // posicao da arvore no plano
		glRotatef(-90, 1, 0, 0);
		glutSolidCone(0.4f, 3, 20, 1);
		glTranslatef(0, 0, 1.5f);
		glColor3f(0.0f, 1.0f, 0.0f);
		glutSolidCone(1.4f, 6, 20, 1);
		glPopMatrix();
	}
}

void drawTeapots() {
	// Draw teapots with rc
	glColor3f(0.0f, 0.0f, 1.0f);
	glPushMatrix();
	glRotatef(90, 0, 1, 0);
	glTranslatef(0, 0.8f, 0);
	float delta = 360 / 8;
	for (size_t i = 0; i < 8; i++) {
		glPushMatrix();
		glRotatef(i * delta + animationIn, 0, 1, 0);
		glTranslatef(rc, 0.0f, 0.0f);
		glutSolidTeapot(1);
		glPopMatrix();
	}
	glPopMatrix();
	animationIn += 1;
	// Draw teapots with ri
	glColor3f(1.0f, 0.0f, 0.0f);
	glPushMatrix();
	glTranslatef(0, 0.8f, 0);
	delta = 360 / 16;
	for (size_t i = 0; i < 16; i++) {
		glPushMatrix();
		glRotatef(i * delta + animationOut, 0, 1, 0);
		glTranslatef(0.0f, 0.0f, ri);
		glutSolidTeapot(1);
		glPopMatrix();
	}
	glPopMatrix();
	animationOut--;
}

void renderScene(void) {

	float pos[4] = {-1.0, 1.0, 1.0, 0.0};

	glClearColor(0.0f,0.0f,0.0f,0.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glLoadIdentity();
	gluLookAt(camX, camY, camZ, 
		      0.0,0.0,0.0,
			  0.0f,1.0f,0.0f);
	glColor3f(0, 0, 1.0);
	drawTerrain();
	// Draw scene 
	// Draw a tree
	drawTrees();

	// Draw torus
	glColor3f(1.0f, 0.0f, 1.0f);
	glutSolidTorus(1.0f, 3.0f, 50, 50);
	// Draw teapots
	drawTeapots();


// End of frame
	glutSwapBuffers();
}



void processKeys(unsigned char key, int xx, int yy) {

// put code to process regular keys in here


}



void processMouseButtons(int button, int state, int xx, int yy) {
	
	if (state == GLUT_DOWN)  {
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
			alpha += (xx - startX);
			beta += (yy - startY);
		}
		else if (tracking == 2) {
			
			r -= yy - startY;
			if (r < 3)
				r = 3.0;
		}
		tracking = 0;
	}
}


void processMouseMotion(int xx, int yy) {

	int deltaX, deltaY;
	int alphaAux, betaAux;
	int rAux;

	if (!tracking)
		return;

	deltaX = xx - startX;
	deltaY = yy - startY;

	if (tracking == 1) {


		alphaAux = alpha + deltaX;
		betaAux = beta + deltaY;

		if (betaAux > 85.0)
			betaAux = 85.0;
		else if (betaAux < -85.0)
			betaAux = -85.0;

		rAux = r;
	}
	else if (tracking == 2) {

		alphaAux = alpha;
		betaAux = beta;
		rAux = r - deltaY;
		if (rAux < 3)
			rAux = 3;
	}
	camX = rAux * sin(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
	camZ = rAux * cos(alphaAux * 3.14 / 180.0) * cos(betaAux * 3.14 / 180.0);
	camY = rAux * 							     sin(betaAux * 3.14 / 180.0);
}


void init() {

	// 	Load the height map "terreno.jpg"
	unsigned int t, tw, th;

	

	ilGenImages(1, &t);
	ilBindImage(t);
	// terreno.jpg is the image containing our height map
	ilLoadImage((ILstring)"terreno.jpg");
	// convert the image to single channel per pixel
	// with values ranging between 0 and 255
	ilConvertImage(IL_LUMINANCE, IL_UNSIGNED_BYTE);
	// important: check tw and th values
	// both should be equal to 256
	// if not there was an error loading the image
	// most likely the image could not be found
	tw = ilGetInteger(IL_IMAGE_WIDTH);
	th = ilGetInteger(IL_IMAGE_HEIGHT);
	// imageData is a LINEAR array with the pixel values
	imageData = ilGetData();

	std::cout << tw << " " << th << std::endl; // print teste


	 // 	Build the vertex arrays
	for (size_t h = 0; h < th; h++) {
		for (size_t w = 0; w < tw; w++) {
			pontos.push_back(w - (tw / 2.0));                               // x1
			pontos.push_back(imageData[th * h + w] * (50.0 / 255.0));       // y1
			pontos.push_back(h - (th / 2.0));                               // z1

			pontos.push_back(w - (tw / 2.0));                               // x2
			pontos.push_back(imageData[th * (h + 1) + w] * (50.0 / 255.0)); // y2
			pontos.push_back(h + 1.0 - (th / 2.0));                         // z2
		}
	}

	ih = th;
	iw = tw;

	glGenBuffers(1, buffers);
	glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
	glBufferData(
		GL_ARRAY_BUFFER,
		sizeof(float) * pontos.size(),
		pontos.data(),
		GL_STATIC_DRAW);

	// 	OpenGL settings
	glEnable(GL_DEPTH_TEST);
	//glEnable(GL_CULL_FACE);
	
	
}


int main(int argc, char **argv) {

// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(320,320);
	glutCreateWindow("CG@DI-UM");

	glEnableClientState(GL_VERTEX_ARRAY);
	glPolygonMode(GL_FRONT, GL_LINE);
	
// Required callback registry 
	glutDisplayFunc(renderScene);
	glutIdleFunc(renderScene);
	glutReshapeFunc(changeSize);
	


// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutMouseFunc(processMouseButtons);
	glutMotionFunc(processMouseMotion);

	glewInit();
	ilInit();
	init();	
	
// enter GLUT's main cycle
	glutMainLoop();
	
	return 0;
}

