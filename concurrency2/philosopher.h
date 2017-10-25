#include <stdlib.h>
#include<string.h>

typedef struct {
  long t0;  /*Time program started */
  long ms; /* Total time that philosopher can sleep / eats */
  void *v;
  int *blocktime; /* Total time that a philosopher is blocked */
  int *blockstart; /* If a philosopher is currently blocked the time that he started blocking*/
  int phil_count;
  pthread_mutex_t *blockreader ; /*tracking for blocktime*/ 
}Philosopher_struct;

extern void *create_v(int phil_count);
extern void pickup(Phil_struct *);
extern void putdown(Phil_struct *);
