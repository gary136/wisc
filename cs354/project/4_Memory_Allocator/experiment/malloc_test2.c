#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(void)
{
    void *p;
    p = malloc(1);
    free(p);
    return(0);
}