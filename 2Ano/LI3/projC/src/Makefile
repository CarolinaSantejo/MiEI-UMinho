CC = gcc
IDIR = ../include

CFLAGS = -Wall -ansi `pkg-config --cflags --libs glib-2.0` -I$(IDIR)
OPTIFLAGS = -O2

OBJ = $(patsubst %.c,%.o,$(wildcard *.c))
DEPS = $(IDIR)/$(wildcard *.h)
EXE = SGV

.PHONY: all run time clean
all: $(EXE)

$(EXE): $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS) $(OPTIFLAGS)

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS) $(OPTIFLAGS)

program: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS) $(OPTIFLAGS)

run: $(EXE)
	./$(EXE)

$(EXE).debug: $(OBJ)
	$(CC) -o $(EXE).debug $^ $(CFLAGS) -g -Og

debug: $(EXE).debug
	gdb $(EXE).debug

grind: $(EXE).debug
	valgrind --leak-check=full --show-reachable=no --show-leak-kinds=all --track-origins=yes --verbose --log-file=valgrind-out.txt ./$(EXE).debug

clean:
	rm -f $(EXE) *.o $(EXE).debug valgrind-out.txt program
