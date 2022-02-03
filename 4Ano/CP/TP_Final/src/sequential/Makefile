# source files.
SRC = sequentialBucketSort.c main.c

OBJ = $(SRC:.cpp=.o)

OUT = gemm

# include directories
INCLUDES = -I. 
 
# C compiler flags 

#CCFLAGS =  -O2 -Wall -I/share/apps/papi/5.4.1/include 

#With loop unroll and vetorization 
CCFLAGS = -funroll-loops -O3 -Wall -I/share/apps/papi/5.4.1/include


# compiler
CCC = gcc 
#CCC = /opt/intel/Compiler/11.1/073/bin/ia32/icpc 
#CCC = g++-4.5

# libraries
LIBS = -lm -lpapi -mavx -L/share/apps/papi/5.4.1/lib
LIBS = -lm -lpapi -L/share/apps/papi/5.4.1/lib

.SUFFIXES: .cpp .c 


default: $(OUT)

.cpp.o:
	$(CCC) $(CCFLAGS) $(INCLUDES)  -c $< -o $@

.c.o:
	$(CCC) $(CCFLAGS) $(INCLUDES) -c $< -o $@

$(OUT): $(OBJ)
	$(CCC) -o $(OUT) $(CCFLAGS) $(OBJ) $(LIBS) 

depend:  dep
#
#dep:
#	makedepend -- $(CFLAGS) -- $(INCLUDES) $(SRC)

clean:
	rm -f *.o .a *~ Makefile.bak $(OUT)
