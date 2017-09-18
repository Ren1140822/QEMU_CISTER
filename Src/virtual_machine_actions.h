#include <stdio.h>
#include <sys/types.h>
const char * I386_START = "qemu-system-i386 -hda %s -cdrom %s -boot d";

pid_t start_i386(char * disk_name, char *iso_name);