/*
 * CS444 Homework #2 - CLOOK Scheduler
 * Zach Lerew, Rohan Barve
 * October 19th, 2017
*/

#include <linux/blkdev.h>
#include <linux/elevator.h>
#include <linux/bio.h>
#include <linux/module.h>
#include <linux/slab.h>
#include <linux/init.h>

struct clook_data {
    struct list_head queue;
};

static void clook_merged_requests(struct request_queue *q, struct request *rq,
                 struct request *next)
{
    list_del_init(&next->queuelist);

    printk(KERN_INFO "CLOOK merge\n");
}

static int clook_dispatch(struct request_queue *q, int force)
{
    struct clook_data *nd = q->elevator->elevator_data;
    char readorwrite;
    struct request *rq;

    if (!list_empty(&nd->queue)) {

        /* Read next entry in queue */
        rq = list_entry(nd->queue.next, struct request, queuelist);
        list_del_init(&rq->queuelist);
        elv_dispatch_sort(q, rq);

        readorwrite = (rq_data_dir(rq) & REQ_WRITE) ? 'W' : 'R';
        printk(KERN_INFO "CLOOK dispatch %c %lu\n", readorwrite,
            blk_rq_pos(rq));

        return 1;
    }
    return 0;
}

static void clook_add_request(struct request_queue *q, struct request *rq)
{
    struct clook_data *nd = q->elevator->elevator_data;
    struct list_head *iter = NULL;

    /* Insertion sort request in the correct place based on physical sector location */
    /* Note: iter is advanced by list_for_each */
    list_for_each(iter, &nd->queue)
    {

        /* If request is greater than iter, break the loop */
        if (rq_end_sector(rq) <
            rq_end_sector(list_entry(iter, struct request, queuelist)))
        {
            break;
        }
    }

    /* Insert request into queue*/
    list_add_tail(&rq->queuelist, iter);

    printk(KERN_INFO "CLOOK add %lu\n", blk_rq_pos(rq));
}

static struct request *
clook_former_request(struct request_queue *q, struct request *rq)
{
    struct clook_data *nd = q->elevator->elevator_data;

    printk(KERN_INFO "CLOOK former\n");

    if (rq->queuelist.prev == &nd->queue)
        return NULL;
    return list_entry(rq->queuelist.prev, struct request, queuelist);
}

static struct request *
clook_latter_request(struct request_queue *q, struct request *rq)
{
    struct clook_data *nd = q->elevator->elevator_data;

    printk(KERN_INFO "CLOOK latter\n");

    if (rq->queuelist.next == &nd->queue)
        return NULL;
    return list_entry(rq->queuelist.next, struct request, queuelist);
}

static int clook_init_queue(struct request_queue *q, struct elevator_type *e)
{
    struct clook_data *nd;
    struct elevator_queue *eq;

    eq = elevator_alloc(q, e);
    if (!eq)
        return -ENOMEM;

    nd = kmalloc_node(sizeof(*nd), GFP_KERNEL, q->node);
    if (!nd) {
        kobject_put(&eq->kobj);
        return -ENOMEM;
    }
    eq->elevator_data = nd;

    INIT_LIST_HEAD(&nd->queue);

    spin_lock_irq(q->queue_lock);
    q->elevator = eq;
    spin_unlock_irq(q->queue_lock);

    printk(KERN_INFO "CLOOK initQueue\n");
    return 0;
}

static void clook_exit_queue(struct elevator_queue *e)
{
    struct clook_data *nd = e->elevator_data;

    BUG_ON(!list_empty(&nd->queue));
    kfree(nd);

    printk(KERN_INFO "CLOOK exitQueue\n");
}

static struct elevator_type elevator_clook = {
    .ops = {
        .elevator_merge_req_fn      = clook_merged_requests,
        .elevator_dispatch_fn       = clook_dispatch,
        .elevator_add_req_fn        = clook_add_request,
        .elevator_former_req_fn     = clook_former_request,
        .elevator_latter_req_fn     = clook_latter_request,
        .elevator_init_fn           = clook_init_queue,
        .elevator_exit_fn           = clook_exit_queue,
    },
    .elevator_name = "clook",
    .elevator_owner = THIS_MODULE,
};

static int __init clook_init(void)
{
    return elv_register(&elevator_clook);
}

static void __exit clook_exit(void)
{
    elv_unregister(&elevator_clook);
}

module_init(clook_init);
module_exit(clook_exit);


MODULE_AUTHOR("Group 26");
MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("CLOOK IO scheduler");
