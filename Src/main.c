#include <signal.h>
//Testing purposes
int main(void)
{
    pid_t machine1 = start_i386("linux.img","linux.iso");
   
    sleep(4);
    
    kill(machine1, SIGSTOP);
    
}