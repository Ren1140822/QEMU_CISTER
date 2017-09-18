#include <stdio.h>
#include <string.h>
#include "virtual_machine_actions.h"
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>


/**
    Starts a i386 virtual machine receiving a disk name and iso name as params.
**/
pid_t start_i386(char * disk_name, char *iso_name)
{
    char * exec_cmd = (char *) malloc(strlen(I386_START)+strlen(disk_name)+strlen(iso_name)+ sizeof(char));

    sprintf(exec_cmd, I386_START, disk_name, iso_name);

    pid_t pid =fork();

    if(pid==0)
    {
        pid_t son_pid = getpid();

        set_stop_signal_handler();

        system(exec_cmd);

        free(exec_cmd);
        return son_pid;
    }
    return NULL;
}

 
/**
    Signal handler.
**/
void sig_catch(int sig_number) {
     printf("Caught signal number %d.\n",sig_number);

     char * stop_cmd = (char *)malloc(strlen("kill -STOP")+4);

     sprintf(stop_cmd,"kill -STOP %d",getpid());

     printf("%s\n",stop_cmd);


     system(stop_cmd);

     free(stop_cmd);
}

/**
    Sets the signal handler. TODO: receive PID as param to store in shared memory area.
**/
  void set_stop_signal_handler(){
    if (signal(SIGSTOP, sig_catch) == SIG_ERR) {
       puts("Handler set.\n");
    }
}