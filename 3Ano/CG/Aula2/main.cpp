#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

#include <math.h>
#include <stdio.h>

GLfloat tMatrix[16];

bool init = true;

bool valid = false;
float mouseX = 0;
float mouseY = 0;


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

void drawPiramide() {
	glBegin(GL_TRIANGLES);
	// red
	glColor3f(1.0f, 0.0f, 0.0f);
	glVertex3f(0.0f, 2.0f, 0.0f);
	glVertex3f(-1.0f, 0.0f, 1.0f);
	glVertex3f(1.0f, 0.0f, 1.0f);
	// green
	glColor3f(0.0f, 1.0f, 0.0f);
	glVertex3f(0.0f, 2.0f, 0.0f);
	glVertex3f(1.0f, 0.0f, 1.0f);
	glVertex3f(1.0f, 0.0f, -1.0f);
	// blue
	glColor3f(0.0f, 0.0f, 1.0f);
	glVertex3f(0.0f, 2.0f, 0.0f);
	glVertex3f(1.0f, 0.0f, -1.0f);
	glVertex3f(-1.0f, 0.0f, -1.0f);
	// pink
	glColor3f(1.0f, 0.0f, 1.0f);
	glVertex3f(0.0f, 2.0f, 0.0f);
	glVertex3f(-1.0f, 0.0f, -1.0f);
	glVertex3f(-1.0f, 0.0f, 1.0f);
	// base
	glColor3f(1.0f, 1.0f, 1.0f);
	glVertex3f(-1.0f, 0.0f, 1.0f);
	glVertex3f(-1.0f, 0.0f, -1.0f);
	glVertex3f(1.0f, 0.0f, 1.0f);

	glVertex3f(1.0f, 0.0f, 1.0f);
	glVertex3f(-1.0f, 0.0f, -1.0f);
	glVertex3f(1.0f, 0.0f, -1.0f);

	glEnd();
}


void renderScene(void) {

	// clear buffers
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// set the camera
	glLoadIdentity();
	gluLookAt(5.0,5.0,5.0, 
		      0.0,0.0,0.0,
			  0.0f,1.0f,0.0f);

	drawReferencial();
	// put the geometric transformations here

	// inicializar tMatrix com a identidade
	if (init) { 
		glGetFloatv(GL_MODELVIEW_MATRIX, tMatrix); 
		init = false;
	}
	glMatrixMode(GL_MODELVIEW); // make sure we restore to MODELVIEW
	glLoadMatrixf(tMatrix);
	
	// put drawing instructions here
	drawPiramide();

	// End of frame
	glutSwapBuffers();
}

// Rato

void mouse_func(int button, int state, int x, int y) {
	mouseX = x;
	mouseY = y;
	valid = state == GLUT_DOWN;
}

void motion_func(int x, int y) {
	if (valid) {
		glMatrixMode(GL_MODELVIEW); // make sure we restore to MODELVIEW
		glLoadMatrixf(tMatrix); // restore it
		int dx = mouseX - x;
		int dy = mouseY - y;
		if (dx > 0) glRotatef(-5, -1 , 0.0f, -1);
		if (dx < 0) glRotatef(5, -1, 0.0f, -1);
		
		if (dy > 0) glRotatef(5, -1, 0.0f, 1);
		if (dy < 0) glRotatef(-5, -1, 0.0f, 1);
		glGetFloatv(GL_MODELVIEW_MATRIX, tMatrix);
		glutPostRedisplay();
	}
	mouseX = x;
	mouseY = y;
}

// teclado

void keyboardFunc(unsigned char key, int x, int y) {
	glMatrixMode(GL_MODELVIEW); // make sure we restore to MODELVIEW
	glLoadMatrixf(tMatrix); // restore it
	switch (key) {
	case 'a':
		glScalef(0.9f, 1.0f, 1.0f);
		break;
	case 'd':
		glScalef(1.1f, 1.0f, 1.0f);
		break;
	case 's':
		glTranslatef(0.0f, -0.1f, 0.0f);
		break;
	case 'w':
		glTranslatef(0.0f, 0.1f, 0.0f);
		break;
	case 'e':
		glRotatef(5, 0.0f, 1.0f, 0.0f);
		break;
	case 'q':
		glRotatef(-5, 0.0f, 1.0f, 0.0f);
		break;
	case 'r':
		glRotatef(-5, 1.0f, 0.0f, 0.0f);
		break;
	case 'f':
		glRotatef(5, 1.0f, 0.0f, 0.0f);
		break;
	default:
		break;
	}
	glGetFloatv(GL_MODELVIEW_MATRIX, tMatrix);
	glutPostRedisplay();
}

void specialKeyboardFunc(int key, int x, int y) {
	glMatrixMode(GL_MODELVIEW); // make sure we restore to MODELVIEW
	glLoadMatrixf(tMatrix); // restore it
	switch (key) {
	case GLUT_KEY_LEFT:
		glTranslatef(-0.1f, 0.0f, 0.0f);
		break;
	case GLUT_KEY_RIGHT:
		glTranslatef(0.1f, 0.0f, 0.0f);
		break;
	case GLUT_KEY_DOWN:
		glTranslatef(0.0f, 0.0f, 0.1f);
		break;
	case GLUT_KEY_UP:
		glTranslatef(0.0f, 0.0f, -0.1f);
		break;
	default:
		break;
	}
	glGetFloatv(GL_MODELVIEW_MATRIX, tMatrix);
	glutPostRedisplay();
}

int main(int argc, char **argv) {

// init GLUT and the window
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DEPTH|GLUT_DOUBLE|GLUT_RGBA);
	glutInitWindowPosition(0,0);
	glutInitWindowSize(1500,900);
	glutCreateWindow("CG@DI-UM");

	
		
// Required callback registry 
	glutDisplayFunc(renderScene);
	glutReshapeFunc(changeSize);
	

	
// put here the registration of the keyboard callbacks
	glutKeyboardFunc(keyboardFunc);
	glutSpecialFunc(specialKeyboardFunc);
	glutMouseFunc(mouse_func);
	glutMotionFunc(motion_func);

//  OpenGL settings
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_CULL_FACE);
	
// enter GLUT's main cycle
	glutMainLoop();
	
	return 1;
}
