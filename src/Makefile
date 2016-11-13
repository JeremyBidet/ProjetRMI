SRC=src
BIN=bin

IMPL=Authentication Park User Comment Vehicle
CLIENT=Client
SERVER=Server

CC=javac
STUB=rmic
REGISTRY=rmiregistry

EXEC=java

all:
	$(CC) *.java

server: all
	$(STUB) $(IMPL)
	$(REGISTRY) &
	$(EXEC) $(SERVER)

client: all
	$(EXEC) $(CLIENT)

