#include <linux/module.h>
#include <linux/slab.h>
#include <linux/random.h>

/**
 * Testing module for slob module.
 */

static int __init malloc_init(void){
    void *tempOne,*tempTwo;
    int i = 0,randomOne,randomTwo;

    for(i = 0; i < 999999;i++){

        get_random_bytes(&randomOne, sizeof(randomOne));
        get_random_bytes(&randomTwo, sizeof(randomTwo));

        tempOne = kmalloc(randomTwo, GFP_KERNEL);
        kfree(tempOne);

         tempTwo = kmalloc(randomOne, GFP_KERNEL);
        kfree(tempTwo);
    }

    return 0;
}

static void __exit malloc_exit(void){


}

module_init(malloc_init);
module_exit(malloc_exit);

MODULE_AUTHOR("Rohan Barve, Zach Lerew");
MODULE_LICENSE("GPL");
