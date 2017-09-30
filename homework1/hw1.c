/*
 * CS444 Homework #1 Concurrency #1 - The Producer-Consumer Problem
 * Zach Lerew, Rohan Barve
 * September 30, 2017
*/

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define DATASIZE 32
#define NUM_CONSUMERS 5
#define NUM_PRODUCERS 5

struct Item {
    int val;
    int time;
};

int itemCount;
struct Item buffer[DATASIZE];

pthread_t consumer_threads[NUM_CONSUMERS];
pthread_t producer_threads[NUM_PRODUCERS];
pthread_mutex_t mutex_lock;

void consume(void* argument){
	int arg = *((int*)argument);
	printf( " Started consumer thread %d\n", arg);
	sleep( arg+1 );
	printf( " Stopped consumer thread %d\n", arg );
}

int main() {
	/* Initialize */
	itemCount = 0;
	int i;
	for (i = 0; i < DATASIZE; i++){
		buffer[i].val = -1;
		buffer[i].time = -1;
	}


	/* Consumer threads */
	for (i = 0; i < NUM_CONSUMERS; i++){
		pthread_t consumerID;
		int *arg = malloc(sizeof(*arg));
		*arg = i;
		int result = pthread_create( &consumerID, NULL, (void*)consume, arg );
		if (result == 1) {
			printf("Failed to create consumer thread\n");
			return 1;
		} else {
			printf("Created consumer thread %d\n", i);
			consumer_threads[i] = consumerID;
		}
	}

	/* Wait for consumer threads */
	for (i = 0; i < NUM_CONSUMERS; i++){
		pthread_join(consumer_threads[i], NULL);
	}



	return 0;
}