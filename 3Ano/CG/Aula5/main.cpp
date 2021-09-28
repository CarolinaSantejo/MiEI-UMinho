#include <stdio.h>
#include <stdlib.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#define _USE_MATH_DEFINES
#include <math.h>

float alfa = 0.0f, beta = 0.5f, radius = 100.0f;
float camX, camY, camZ;
int r = 50;
float ri = 35;
float rc = 15;
float animationIn = 0;
float animationOut = 0;


void spherical2Cartesian() {

	camX = radius * cos(beta) * sin(alfa);
	camY = radius * sin(beta);
	camZ = radius * cos(beta) * cos(alfa);
}

void drawTrees() {
	float betaTree = 0;
	float xTree = 0;
	float zTree = 0;
	float rmin = 0;
	for (size_t i = 0; i < 1000; i++) {
		srand(i * 3);
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

		glColor3f(0.5f, 0.35f, 0.05f);
		glPushMatrix();
		glTranslatef(xTree, 0, zTree); // posicao da arvore no plano
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

void renderScene(void) {

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(camX, camY, camZ,
		0.0, 0.0, 0.0,
		0.0f, 1.0f, 0.0f);

	glColor3f(0.2f, 0.8f, 0.2f);
	glBegin(GL_TRIANGLES);
		glVertex3f(100.0f, 0, -100.0f);
		glVertex3f(-100.0f, 0, -100.0f);
		glVertex3f(-100.0f, 0, 100.0f);

		glVertex3f(100.0f, 0, -100.0f);
		glVertex3f(-100.0f, 0, 100.0f);
		glVertex3f(100.0f, 0, 100.0f);
	glEnd();
	drawReferencial();
	// End of frame
	
	
	// put code to draw scene in here

	// Draw a tree
	drawTrees();
	
	// Draw torus
	glColor3f(1.0f, 0.0f, 1.0f);
	glutSolidTorus(1.0f, 3.0f, 50, 50);
	// Draw teapots
	drawTeapots();
	
	glutSwapBuffers();
	
}



void processKeys(unsigned char c, int xx, int yy) {

	switch (c) {
		case 'w':
		radius -= 1; break;

		case 's':
		radius += 1; break;
	}
	spherical2Cartesian();
	glutPostRedisplay();
}


void processSpecialKeys(int key, int xx, int yy) {

	switch (key) {

	case GLUT_KEY_RIGHT:
		alfa -= 0.1; break;

	case GLUT_KEY_LEFT:
		alfa += 0.1; break;

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

	case GLUT_KEY_PAGE_DOWN: radius -= 1.0f;
		if (radius < 1.0f)
			radius = 1.0f;
		break;

	case GLUT_KEY_PAGE_UP: radius += 1.0f; break;
	}
	spherical2Cartesian();
	glutPostRedisplay();

}


void printInfo() {

	printf("Vendor: %s\n", glGetString(GL_VENDOR));
	printf("Renderer: %s\n", glGetString(GL_RENDERER));
	printf("Version: %s\n", glGetString(GL_VERSION));

	printf("\nUse Arrows to move the camera up/down and left/right\n");
	printf("Home and End control the distance from the camera to the origin");
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
	glutIdleFunc(renderScene);
	
// Callback registration for keyboard processing
	glutKeyboardFunc(processKeys);
	glutSpecialFunc(processSpecialKeys);

//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);

	spherical2Cartesian();

	printInfo();

// enter GLUT's main cycle
	glutMainLoop();
	
	return 1;
}
