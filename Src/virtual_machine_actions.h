#include <stdio.h>

const char * I386_START = "qemu-system-i386 -hda {{%s}} -cdrom {{%s}} -boot d";

void start_i386(char * disk_name, char *iso_name);