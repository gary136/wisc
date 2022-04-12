#include <stdio.h>

void *imalloc(size_t n)
{ 
 char *p = sbrk(0);
 printf("%d\n",brk(p + n)); 
//  if (brk(p + n) == -1) return NULL;
 return p;
} 

int main() {
    char *p1 = imalloc(1); 
    char *p2 = imalloc(2);
    printf("p1 is at %p\n",p1); 
     printf("p2 is at %p\n",p2); 
    return 0;
}