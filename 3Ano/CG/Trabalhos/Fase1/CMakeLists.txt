cmake_minimum_required(VERSION 3.10)
project(Fase1)

set(CMAKE_CXX_STANDARD 14)

if  (WIN32)

else(WIN32) #Linux and Mac
	find_package(OpenGL REQUIRED)
	find_package(GLUT REQUIRED)
endif(WIN32)

add_executable(motor src/Motor/motor.cpp)
add_executable(gerador src/Gerador/gerador.cpp)

add_library(
		Lib
		src/tinyXML/tinystr.cpp
		src/tinyXML/tinyxml.cpp
		src/tinyXML/tinyxmlerror.cpp
		src/tinyXML/tinyxmlparser.cpp
)

add_library(
		motorLib
		src/Motor/drawFunctions.cpp
		src/Motor/drawFunctions.hpp
)

add_library(
		geradorLib
		src/Gerador/calculaVertices.cpp
		src/Gerador/calculaVertices.hpp
)

add_library(
		utilsLib
		src/Utils/utils.cpp
		src/Utils/utils.hpp
)

target_link_libraries(gerador geradorLib)
target_link_libraries(gerador Lib)
target_link_libraries(gerador utilsLib)

target_link_libraries(motor motorLib)
target_link_libraries(motor Lib)
target_link_libraries(motor utilsLib)




if  (WIN32)

	find_package(OpenGL REQUIRED)
	include_directories(${OpenGL_INCLUDE_DIRS})
	link_directories(${OpenGL_LIBRARY_DIRS})
	add_definitions(${OpenGL_DEFINITIONS})

	if(NOT OPENGL_FOUND)
		message(ERROR " OPENGL not found!")
	endif(NOT OPENGL_FOUND)

	message(STATUS "Toolkits_DIR set to: " ${TOOLKITS_FOLDER})
	set(TOOLKITS_FOLDER "" CACHE PATH "Path to Toolkits folder")

	if (NOT EXISTS "${TOOLKITS_FOLDER}/glut/GL/glut.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glut/glut32.lib")
		message(ERROR ": GLUT not found")
	endif (NOT EXISTS "${TOOLKITS_FOLDER}/glut/GL/glut.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glut/glut32.lib")


	include_directories(${TOOLKITS_FOLDER}/glut )
	target_link_libraries(motor ${OPENGL_LIBRARIES}
			${TOOLKITS_FOLDER}/glut/glut32.lib)

	if (EXISTS "${TOOLKITS_FOLDER}/glut/glut32.dll" )
		file(COPY ${TOOLKITS_FOLDER}/glut/glut32.dll DESTINATION ${CMAKE_BINARY_DIR})
	endif(EXISTS "${TOOLKITS_FOLDER}/glut/glut32.dll" )

	set_property(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR} PROPERTY VS_STARTUP_PROJECT motor)

else (WIN32) #Linux and Mac
	target_link_libraries(motor ${OPENGL_LIBRARIES} ${GLUT_LIBRARY})
endif(WIN32)