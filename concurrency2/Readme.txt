TO RUN THIS PROGRAM:

On server os2.engr.oregonstate.edu
Type "make" to build using javac
Type "java DiningPhil" to run the program


DINING PHILOSOPHERS PROBLEM:

For this concurrency assignment the Java programming language was utilized.
The thread library and its related functions were used in designing in a
protocol to solve the dining philosophers problem.


Here is pseudo code of the protocol: 

while(true)
	think();
	pickupleftfork();
	pickuprightfork();
	eat();
	putdownrightfork();
	putdownleftfork();


The Runnable interface was used to run Philosopher classes as separate
threads. Each Philosopher has access to 2 forks on the left and right sides.
The doMove() function simulates the actions of eating , thinking and acquiring
forks for eating. The invoking thread is suspended for a random amount of time
so that execution is not enforced by time alone. 

The synchronized keyword is used to simulate the action of Philosopher's
acquiring forks or in other words locking. The keyword needs to be used
so that 2 philosopher's cannot acquire it at the same time. 

This implementation avoids deadlock and starvation by having the last philosopher
pick up his right fork first. This breaks a circular wait situation where all
philosophers could have acquired their left fork but can't acquire their right
fork. All philosophers are given a fair chance to eat so there is no starvation.
All philosophers are alive at the end of the running of the program.
The driver file / client creates 5 Philosophers and starts all of them. 