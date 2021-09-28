cmake_minimum_required(VERSION 3.10)
project(Fase3)

set(CMAKE_CXX_STANDARD 14)

if  (WIN32)

else(WIN32) #Linux and Mac
	find_package(OpenGL REQUIRED)
	find_package(GLUT REQUIRED)
	find_package(GLEW REQUIRED)

endif(WIN32)

add_executable(motor src/Motor/motor.cpp)
add_executable(gerador src/Gerador/gerador.cpp)

add_library(
		Lib
		src/tinyXML/tinystr.cpp
		src/tinyXML/tinyxml.cpp
		src/tinyXML/tinyxmlerror.cpp
		src/tinyXML/tinyxmlparser.cpp)



add_library(
		geradorUtils
		src/Gerador/GeradorUtils/calculaPrimitivas.cpp
		src/Gerador/GeradorUtils/headers/calculaPrimitivas.hpp
		src/Gerador/GeradorUtils/bezier.cpp
		src/Gerador/GeradorUtils/headers/bezier.hpp)

add_library(
		utilsLib
		src/Utils/pointFigure.cpp
		src/Utils/headers/pointFigure.hpp
		src/Utils/matrixAux.cpp
		src/Utils/headers/matrixAux.hpp)

add_library(
		motorUtils
		src/Motor/MotorUtils/drawFunctions.cpp
		src/Motor/MotorUtils/headers/drawFunctions.hpp
		src/Motor/MotorUtils/headers/groupStruct.hpp
		src/Motor/MotorUtils/groupStruct.cpp
		src/Motor/MotorUtils/headers/transforms.hpp
		src/Motor/MotorUtils/transforms.cpp
		src/Motor/MotorUtils/headers/catmullrom.hpp
		src/Motor/MotorUtils/catmullrom.cpp)

target_link_libraries(gerador geradorUtils)
target_link_libraries(gerador Lib)
target_link_libraries(gerador utilsLib)

target_link_libraries(motor motorUtils)
target_link_libraries(motor Lib)
target_link_libraries(motor utilsLib)





if  (WIN32)

	message(STATUS "Toolkits_DIR set to: " ${TOOLKITS_FOLDER})
	set(TOOLKITS_FOLDER "" CACHE PATH "Path to Toolkits folder")
	
	if (NOT EXISTS "${TOOLKITS_FOLDER}/glut/GL/glut.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glut/glut32.lib")
		message(ERROR ": GLUT not found")
	endif (NOT EXISTS "${TOOLKITS_FOLDER}/glut/GL/glut.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glut/glut32.lib")	
	
	if (NOT EXISTS "${TOOLKITS_FOLDER}/glew/GL/glew.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glew/glew32.lib")
		message(ERROR ": GLEW not found")
	endif (NOT EXISTS "${TOOLKITS_FOLDER}/glew/GL/glew.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/glew/glew32.lib")	
	
	if (NOT EXISTS "${TOOLKITS_FOLDER}/devil/IL/il.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/devil/devIL.lib")
		message(ERROR ": DevIL not found")
	endif (NOT EXISTS "${TOOLKITS_FOLDER}/devil/IL/il.h" OR NOT EXISTS "${TOOLKITS_FOLDER}/devil/devIL.lib")	
	
	include_directories(${TOOLKITS_FOLDER}/glut ${TOOLKITS_FOLDER}/glew ${TOOLKITS_FOLDER}/devil)
	target_link_libraries(motor ${OPENGL_LIBRARIES} 
										  ${TOOLKITS_FOLDER}/glut/glut32.lib 
										  ${TOOLKITS_FOLDER}/devil/devIL.lib
										  ${TOOLKITS_FOLDER}/glew/glew32.lib )
	
	if (EXISTS "${TOOLKITS_FOLDER}/glut/glut32.dll" AND EXISTS "${TOOLKITS_FOLDER}/glew/glew32.dll"
													AND EXISTS "${TOOLKITS_FOLDER}/devil/devil.dll")
		file(COPY ${TOOLKITS_FOLDER}/glut/glut32.dll DESTINATION ${CMAKE_BINARY_DIR})
		file(COPY ${TOOLKITS_FOLDER}/devil/devil.dll DESTINATION ${CMAKE_BINARY_DIR})
		file(COPY ${TOOLKITS_FOLDER}/glew/glew32.dll DESTINATION ${CMAKE_BINARY_DIR})
	endif(EXISTS "${TOOLKITS_FOLDER}/glut/glut32.dll" AND EXISTS "${TOOLKITS_FOLDER}/glew/glew32.dll"
													AND EXISTS "${TOOLKITS_FOLDER}/devil/devil.dll")
	
	set_property(DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR} PROPERTY VS_STARTUP_PROJECT motor)

else (WIN32) #Linux and Mac
	target_link_libraries(motor ${OPENGL_LIBRARIES} ${GLUT_LIBRARY} ${GLEW_LIBRARIES} )
endif(WIN32)
