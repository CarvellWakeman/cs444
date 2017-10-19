default: all

doc:
	latex writeup.tex
	dvips writeup.dvi
	ps2pdf writeup.ps

conc2:
	gcc -pthread -o conc2 -g conc2.c

all: conc2 doc clean


clean:
	rm -f *.o *.exe
	rm -f writeup.aux
	rm -f writeup.dvi
	rm -f writeup.log
	rm -f writeup.out
	rm -f writeup.ps