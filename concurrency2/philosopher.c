#include<stdio.h>
#include<pthread.h>
#include "philosopher.h"

#define HUNGRY 1
#define EATING 2
#define THINKING 0

typedef struct {
  pthread_mutex_t *mon;
  pthread_cond_t **cv; // pthread condiiton variable
  int *state;
  int *tblock; /* This will be the time philosopher first blocks in pickup */
  int phil_count;
}Phil;

void *create_v(int phil_count){
  Phil *pp;
  int i;

  pp = (Phil *) malloc(sizeof(Phil));
  pp->phil_count = phil_count;
  pp->mon = (pthread_mutex_t*) malloc(sizeof(pthread_mutex_t));
  pp->cv = (pthread_cond_t **) malloc(sizeof(pthread_cond_t*)*phil_count);

  if(pp->cv == NULL){perror("malloc"); exit(1);}
  pp->state = (int*) malloc(sizeof(int)*phil_count);
  if(pp->state == NULL){perror("malloc"); exit(1);}
  pp->tblock = (int*) malloc(sizeof(int)*phil_count);
  if(pp->tblock == NULL){perror("malloc"); exit(1);}

  pthread_mutex_init(pp->mon,NULL);

  for(i = 0; i < phil_count; i++){
    pp->cv[i] = (pthread_cond_t *)malloc(sizeof(pthread_cond_t));
    pthread_cond_init(pp->cv[i],NULL);
    pp->state[i] = THINKING;
    pp->tblock[i] = 0;
  }

  return (void *)pp;
}
