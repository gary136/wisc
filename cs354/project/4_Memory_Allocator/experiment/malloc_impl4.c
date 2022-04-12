#include <stdio.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <fcntl.h>
#include <unistd.h>

int bss_end;

typedef struct BLOCK_HEADER {
  unsigned size;
  void *packed_pointer; // address of the next header + alloc bit.
} BLOCK_HEADER;

BLOCK_HEADER *h1, *h2, *h3;

void *imalloc(size_t n) { 
    size_t *p;
    if (n == 0) return NULL;
    size_t size = n + sizeof(n);
    p = mmap(NULL, size, PROT_READ|PROT_WRITE,
            MAP_PRIVATE|MAP_ANONYMOUS, 0, 0);
    if (p == (void*)-1) return NULL;
    printf("p=%d is at %x\n",*p,p); 
    *p = size; /* Store size in header */
    printf("p=%d is at %x\n",*p,p); 
    // p++; /* Move forward from header to payload */
    // printf("p=%d is at %x\n\n",*p,p); 
    return p;
}
void ifree(size_t *p) { 
    if (p == NULL) return;
    p--; /* Move backward from payload to header */
    munmap(p, *p);
} 

int main() {
    printf("bss ends at %x\n\n",&bss_end);
    // char *p1 = imalloc(1); printf("p1 is at %x\n",p1); 
    // char *p2 = imalloc(2);
    // char *p3 = imalloc(3);
    void *x = imalloc(1*sizeof(size_t));
    printf("size=%d at %x, payload -> %x\n\n",*x, x, x+1);
    // printf("size=%d is at %x\n\n",*(x-1), x);  
    
    h1 = (BLOCK_HEADER *)imalloc(1*sizeof(BLOCK_HEADER));
    // printf("size=%d at %x, payload -> %x\n\n",*h1, h1->size, h1+1);
    printf("size=%d at %x, payload -> %x\n\n",h1->size);
    
    // printf("%d\n",*(h1-1));
    h2 = (BLOCK_HEADER *)imalloc(2*sizeof(BLOCK_HEADER));
    h3 = (BLOCK_HEADER *)imalloc(3*sizeof(BLOCK_HEADER));
    // *board = (char *)malloc(rows * columns * sizeof(char));
    // h1->packed_pointer=h2;
    // printf("%d\n",h1->size);
    // h1->size=*(h1-1);










    return 0;
}