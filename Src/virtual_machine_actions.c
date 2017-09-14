#include <stdio.h>
#include <string.h>
#include "virtual_machine_actions.h"
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>


/**
    Starts a i386 virtual machine receiving a disk name and iso name as params.
**/
void start_i386(char * disk_name, char *iso_name)
{
    char * exec_cmd = (char *) malloc(strlen(I386_START)+strlen(disk_name)+strlen(iso_name)+ sizeof(char));

    sprintf(exec_cmd, I386_START, disk_name, iso_name);

    pid_t pid =fork();

    if(pid==0)
    {
        system(exec_cmd);
    }
}