#include <stdlib.h> 
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES 
#include <math.h>
#include <vector>
using namespace std;

bool valid = false;
float mouseX = 0;
float mouseY = 0;
float cameraX = 5;
float cameraY = 5;
float cameraZ = 5;
float cameraBeta = 0;
float cameraAlpha = 0;
float cameraR = sqrt(pow(cameraX,2) + pow(cameraY, 2) + pow(cameraZ, 2));
float cameraRadius = cos(cameraAlpha) * cameraR;
std::vector<float> pontos;
GLuint buffers[1];

void changeSize(int w, int h) {

	// Prevent a divide by zero, when window is too short
	// (you cant make a window with zero width).
	if(h == 0)
		h = 1;

	// compute window's aspect ratio 
	float ratio = w * 1.0 / h;

	// Set the projection matrix as current
	glMatrixMode(GL_PROJECTION);
	// Load Identity Matrix
	glLoadIdentity();
	
	// Set the viewport to be the entire window
    glViewport(0, 0, w, h);

	// Set perspective
	gluPerspective(45.0f ,ratio, 1.0f ,1000.0f);

	// return to the model view matrix mode
	glMatrixMode(GL_MODELVIEW);
}

void drawReferencial() {
	// Desenhar referencial
	glBegin(GL_LINES);
	// X axis in red
	glColor3f(1.0f, 0.0f, 0.0f);
	glVertex3f(-100.0f, 0.0f, 0.0f);
	glVertex3f(100.0f, 0.0f, 0.0f);
	// Y Axis in Green
	glColor3f(0.0f, 1.0f, 0.0f);
	glVertex3f(0.0f, -100.0f, 0.0f);
	glVertex3f(0.0f, 100.0f, 0.0f);
	// Z Axis in Blue
	glColor3f(0.0f, 0.0f, 1.0f);
	glVertex3f(0.0f, 0.0f, -100.0f);
	glVertex3f(0.0f, 0.0f, 100.0f);
	glEnd();
}

void drawCylinderOpt(float radius, float height, int slices) {
	float beta = 2 * M_PI / slices;
	float p1x = 0;
	float p1y = height;
	float p1z = radius;
	float p2x;
	float p2y;
	float p2z;
	for (int i = 0; i < slices; i++) {
		p2x = sin(beta) * radius;
		p2y = height;
		p2z = cos(beta) * radius;
		// cima
		pontos.push_back(0.0f);
		pontos.push_back(height);
		pontos.push_back(0.0f);
		pontos.push_back(p1x);
		pontos.push_back(p1y);
		pontos.push_back(p1z);
		pontos.push_back(p2x);
		pontos.push_back(p2y);
		pontos.push_back(p2z);
		// lateral
		pontos.push_back(p1x);
		pontos.push_back(p1y);
		pontos.push_back(p1z);
		pontos.push_back(p1x);
		pontos.push_back(0.0f);
		pontos.push_back(p1z);
		pontos.push_back(p2x);
		pontos.push_back(0.0f);
		pontos.push_back(p2z);

		pontos.push_back(p2x);
		pontos.push_back(0.0f);
		pontos.push_back(p2z);
		pontos.push_back(p2x);
		pontos.push_back(p2y);
		pontos.push_back(p2z);
		pontos.push_back(p1x);
		pontos.push_back(p1y);
		pontos.push_back(p1z);
		
		// base
		pontos.push_back(0.0f);
		pontos.push_back(0.0f);
		pontos.push_back(0.0f);
		pontos.push_back(p2x);
		pontos.push_back(0.0f);
		pontos.push_back(p2z);
		pontos.push_back(p1x);
		pontos.push_back(0.0f);
		pontos.push_back(p1z);

		beta += 2 * M_PI / slices;
		p1x = p2x;
		p1z = p2z;
	}
	
}

void drawCylinder(float radius, float height, int slices) {
	float beta = 2 * M_PI/slices;
	float p1x = 0;
	float p1y = height;
	float p1z = radius;
	float p2x;
	float p2y;
	float p2z;
	
	for (int i=0; i<slices; i++) {
		p2x = sin(beta) * radius;
		p2y = height;
		p2z = cos(beta) * radius;
		glBegin(GL_TRIANGLES);
		// cima
		glColor3f(1.0f, 0.0f, 0.0f);
		glVertex3f(0.0f, height, 0.0f);
		glVertex3f(p1x, p1y, p1z);
		glVertex3f(p2x, p2y, p2z);
		// lateral
		glColor3f(0.0f, 1.0f, 0.0f);
		glVertex3f(p1x, p1y, p1z);
		glVertex3f(p1x, 0, p1z);
		glVertex3f(p2x, 0, p2z);

		glVertex3f(p2x, 0, p2z);
		glVertex3f(p2x, p2y, p2z);
		glVertex3f(p1x, p1y, p1z);
		// base
		glColor3f(0.0f, 0.0f, 1.0f);
		glVertex3f(0.0f, 0.0f, 0.0f);
		glVertex3f(p2x, 0, p2z);
		glVertex3f(p1x, 0, p1z);
		

		glEnd();
		beta += 2 * M_PI / slices;
		p1x = p2x;
		p1z = p2z;
	}

}

void drawCone(float radius, float height, int slices, int stacks) {
	float theta = 0;
	float nextTheta = 0;
	float delta = (2 * M_PI) / slices;
	float raio = radius / stacks;
	float alturas = height / stacks;

	//fazer a circunferência da base

	for (int i = 0; i < slices; i++) {

		nextTheta = theta + delta;

		glBegin(GL_TRIANGLES);
		//glColor3f(0.3f, 0.0f, 1.0f);
		glColor3ub(rand() % 255, rand() % 255, rand() % 255);
		glVertex3f(0.0f, 0.0f, 0.0f);
		glVertex3f(radius * sin(nextTheta), 0, radius * cos(nextTheta));
		glVertex3f(radius * sin(theta), 0, radius * cos(theta));
		glEnd();

		theta = nextTheta;

	}

	// Fazer as laterais
	float r1 = radius;
	float r2 = radius - raio;
	float alt1 = 0;
	float alt2 = alturas;
	theta = 0;
	nextTheta = 0;

	for (int i = 0; i < slices; i++) {

		nextTheta += delta;

		for (int j = 0; j < stacks; j++) {

			glBegin(GL_TRIANGLES);

			//Triangulo 1
			//glColor3f(1.0f, 0.0f, 0.0f);
			glColor3ub(rand() % 255, rand() % 255, rand() % 255);
			glVertex3f(r1 * sin(nextTheta), alt1, r1 * cos(nextTheta));
			glVertex3f(r2 * sin(nextTheta), alt2, r2 * cos(nextTheta));
			glVertex3f(r1 * sin(theta), alt1, r1 * cos(theta));
			

			//Triangulo 2
			//glColor3f(0.0f, 1.0f, 1.0f);
			glColor3ub(rand() % 255, rand() % 255, rand() % 255);
			glVertex3f(r2 * sin(nextTheta), alt2, r2 * cos(nextTheta));
			glVertex3f(r2 * sin(theta), alt2, r2 * cos(theta));
			glVertex3f(r1 * sin(theta), alt1, r1 * cos(theta));



			glEnd();
			r1 -= raio;
			r2 -= raio;
			alt1 += alturas;
			alt2 += alturas;
		}
		r1 = radius;
		r2 = radius - raio;
		alt1 = 0;
		alt2 = alturas;
		theta = nextTheta;
	}

}

void renderScene(void) {

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(cameraX, cameraY, cameraZ,
		      0.0,0.0,0.0,
			  0.0f,1.0f,0.0f);
	drawReferencial();
// put the geometric transformations here
	

// put drawing instructions here
	//drawCone(1.0f, 3.0f, 50, 1);
	drawCylinder(1.0f, 3.0f, 30);

	// End of frame
	glutSwapBuffers();
}



// write function to process keyboard events

void mouse_func(int button, int state, int x, int y) {
	mouseX = x;
	mouseY = y;
	valid = state == GLUT_DOWN;
}

void motion_func(int x, int y) {
	if (valid) {
		int dx = x - mouseX;
		if (dx < 0) cameraBeta += M_PI / 30;
		if (dx > 0) cameraBeta -= M_PI / 30;
		cameraX = sin(cameraBeta) * cameraRadius;
		cameraZ = cos(cameraBeta) * cameraRadius;
		int dy = y - mouseY;
		if (dy > 0 && cameraAlpha < M_PI/2) cameraAlpha += M_PI / 30;
		if (dy < 0 && cameraAlpha > -M_PI / 2) cameraAlpha -= M_PI / 30;
		cameraRadius = cos(cameraAlpha) * cameraR;
		cameraY = sin(cameraAlpha) * cameraR;
		glutPostRedisplay();
	}
	mouseX = x;
	mouseY = y;
}

void keyboardFunc(unsigned char key, int x, int y) {

}


int main(int argc, char **argv) {

// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(100,100);
	glutInitWindowSize(800,800);
	glutCreateWindow("CG@DI-UM");
		
// Required callback registry 
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	//glGenBuffers(1, buffers);
	//glBufferData(GL_ARRAY_BUFFER, pontos.size() * sizeof(float), pontos, GL_STATIC_DRAW);

	
// put here the registration of the keyboard callbacks
	glutMouseFunc(mouse_func);
	glutMotionFunc(motion_func);
	//glutKeyboardFunc(keyboardFunc);


//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	glEnableClientState(GL_VERTEX_ARRAY);
	
// enter GLUT's main cycle
	glutMainLoop();
	
	return 1;
}
