default: all

doc:
	latex writeup.tex
	dvips writeup.dvi
	ps2pdf writeup.ps

hw2:
	gcc -pthread -o hw2 -g hw2.c

all: hw2 doc clean


clean:
	rm -f *.o *.exe
	rm -f writeup.aux
	rm -f writeup.dvi
	rm -f writeup.log
	rm -f writeup.out
	rm -f writeup.ps