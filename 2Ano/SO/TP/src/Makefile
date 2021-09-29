CC = gcc
CFLAGS = -O2 -Wall

all: argus argusd

argus: argus.o
	$(CC) -o $@ $<

argusd: argusd.o
	$(CC) -o $@ $<

%.o: %.c
	$(CC) -c -o $@ $< $(CFLAGS)

clean:
	rm -rf argus argusd *.o log* server_client_fifo client_server_fifo
	pkill argus