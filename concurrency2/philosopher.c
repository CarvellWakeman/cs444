#include<stdio.h>
#include<pthread.h>
#include "philosopher.h"

#define HUNGRY 1
#define EATING 2
#define THINKING 0

typedef struct {
  pthread_mutex_t *mon;
  pthread_cond_t **ct;
  int *state;
  int *tblock; /* This will be the time philosopher first blocks in pickup */
  int phil_count;
}Phil;
