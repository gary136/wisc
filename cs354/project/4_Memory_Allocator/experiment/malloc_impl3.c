#include <stdio.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>

int bss_end;

void *imalloc(size_t n) { 
    size_t *p;
    if (n == 0) return NULL;
    p = mmap(NULL, n + sizeof(size_t), PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS, 0, 0);
    if (p == (void*)-1) return NULL;
    *p = n + sizeof(size_t); /* Store size in header */
    p++; /* Move forward from header to payload */
    return p;
}
// void free(void *p) { 
//     if (p == NULL) return;
//     p--; /* Move backward from payload to header */
//     munmap(p, *p);
// } 

int main() {
    printf("bss ends at %x\n\n",&bss_end);
    char *p1 = imalloc(1); 
    char *p2 = imalloc(2);
    char *p3 = imalloc(3);
    printf("p1 is at %x\n",p1); 
    printf("p2 is at %x\n",p2);  
    printf("p3 is at %x\n",p3); 
    return 0;
}