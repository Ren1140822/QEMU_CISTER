#
# Makefile
#

# Variables 
INCLUDES_W = virtual_machine_actions.h
SOURCES_W = virtual_machine_actions.c
OBJFILES_W = virtual_machine_actions.o
 

all: clean main

main: ${OBJFILES_W}
	gcc -Wall -g -o main main.c ${OBJFILES_W} -lrt -lm


run_main: main
	./main
	

clean:
	rm -f main ${OBJFILES_W}

# How to transform .c to .o ; $< -- file name
.c.o:
	gcc -Wall -g -c $< -lrt -lm
	
${OBJFILES_W}: ${SOURCES_W} ${INCLUDES_W}
