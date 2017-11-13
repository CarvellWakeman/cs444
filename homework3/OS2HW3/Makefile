obj-m := sbd_enc.o
KDIR := /scratch/fall2017/26/linux-yocto-3.19/
PWD_C := $(shell pwd)

default:
	$(MAKE) -C $(KDIR) SUBDIRS=$(PWD_C) modules
clean:
	$(MAKE) -C $(KDIR) SUBDIRS=$(PWD_C) clean
