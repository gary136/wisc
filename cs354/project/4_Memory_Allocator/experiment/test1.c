#include <stdio.h>
#include "mem.h"
#include <assert.h>
// #include "mem.c"

int main() {
    assert(Mem_Init(32, FIRST_FIT)==0);
    Mem_Dump();
    void *ptr = Mem_Alloc(1);
    // Mem_Dump();
    assert(ptr!=NULL);
    exit(0);
}