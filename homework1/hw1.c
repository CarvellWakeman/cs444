/*
 * CS444 Homework #1 Concurrency #1 - The Producer-Consumer Problem
 * Zach Lerew, Rohan Barve
 * September 30, 2017
*/

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <cpuid.h>
#include "twister.h"

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


/*int try_lock_mutex(){
    return pthread_mutex_trylock(&mutex_lock);
}*/

/* based on answer from https://codereview.stackexchange.com/a/150230 */
int supports_rdrand(){
    unsigned int flag_RDRAND = (1 << 30);
    unsigned int eax, ebx, ecx, edx;
    __get_cpuid(1, &eax, &ebx, &ecx, &edx);
    return ((ecx & flag_RDRAND) == flag_RDRAND);
}

/* Mersenne Twister implementation credit mcs.anl.gov */
int random_num(int min, int max){
    unsigned long var = 0;

    if (supports_rdrand()){
        asm( "rdrand %0" : "=r" (var) );
    } else {
        var = (unsigned long) randomMT();
    }
    return ((var % (max-1)) + min);
}

void* produce(void* argument){
    /* Get lock */
    pthread_mutex_lock(&mutex_lock);
    int arg = *((int*)argument);

    printf("Producer %d locking mutex\n", arg);

    /* Sleep for a period of time */
    sleep (random_num(3,7));

    /* Produce an Item */
    struct Item naturalFreeRangeArtisanMadeGMOFreeItem;
    naturalFreeRangeArtisanMadeGMOFreeItem.val = random_num(1,10);
    naturalFreeRangeArtisanMadeGMOFreeItem.time = random_num(2,9);

    /* Place item in buffer */
    while (1){
        if (itemCount < DATASIZE){
            printf("Producer %d produced item %d\n", arg, itemCount);
            buffer[itemCount] = naturalFreeRangeArtisanMadeGMOFreeItem;
            itemCount++;
            break;
        }
    }

    /* Release lock */
    pthread_mutex_unlock(&mutex_lock);

    return NULL;
}

void* consume(void* argument){
    int arg = *((int*)argument);

    printf("Consumer %d locking mutex\n", arg);

    /* Check buffer for item to consume */
    int wait = 1;
    while (wait){
        /* Get lock */
        pthread_mutex_lock(&mutex_lock);

        if (itemCount > 0){
            int to_consume = itemCount;

            printf("Consumer %d consuming item %d for %d seconds\n", arg, itemCount, buffer[to_consume-1].time);

            itemCount--;

            /* work */
            sleep(buffer[to_consume-1].time);
            printf("Consumer %d consumed item with value %d\n", arg, buffer[to_consume-1].val);
            wait = 0;
        }

        /* Release lock */
        pthread_mutex_unlock(&mutex_lock);
    }


    return NULL;
}

int main() {
    /* Initialize */
    itemCount = 0;
    int i;
    for (i = 0; i < DATASIZE; i++){
        buffer[i].val = -1;
        buffer[i].time = -1;
    }

    seedMT(4357U);

    pthread_mutex_init(&mutex_lock, NULL);
    pthread_mutex_unlock(&mutex_lock);

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

    /* Producer threads */
    for (i = 0; i < NUM_PRODUCERS; i++){
        pthread_t producerID;
        int *arg = malloc(sizeof(*arg));
        *arg = i;
        int result = pthread_create( &producerID, NULL, (void*)produce, arg );
        if (result == 1) {
            printf("Failed to create producer thread\n");
            return 1;
        } else {
            printf("Created producer thread %d\n", i);
            producer_threads[i] = producerID;
        }
    }


    /* Wait for consumer threads */
    for (i = 0; i < NUM_CONSUMERS; i++){
        pthread_join(consumer_threads[i], NULL);
    }

    /* Wait for producer threads */
    for (i = 0; i < NUM_PRODUCERS; i++){
        pthread_join(producer_threads[i], NULL);
    }


    /* Cleanup */
    pthread_mutex_destroy(&mutex_lock);

    return 0;
}