#include <stdio.h>

int bss_end;
char *pPad = NULL;
char *pBrk = NULL; 
void *imalloc(size_t n) { 
    enum {MIN_ALLOC = 4096};
    char *p;
    char *pNewBrk;
    
    printf("pBrk is at %x\n",pBrk);
    printf("pPad is at %x\n",pPad);
    printf("program break is at %x\n\n",sbrk(0));
    if (pBrk == NULL) { 
        pBrk = sbrk(0);
        pPad = pBrk;
        printf("pBrk is at %x\n",pBrk);
        printf("pPad is at %x\n",pPad);
        printf("program break is at %x\n",sbrk(0));
    } 
    printf("pPad + n is at %x\n\n",pPad + n);
    if (pPad + n > pBrk)  { /* move pBrk */
        pNewBrk = (pPad + n > pBrk + MIN_ALLOC ) ? pPad + n : pBrk + MIN_ALLOC;
        // pNewBrk = max(pPad + n, pBrk + MIN_ALLOC);
        // pNewBrk = pBrk + MIN_ALLOC;
        printf("pNewBrk is at %x\n",pNewBrk);
        
        int res = brk(pNewBrk);
        printf("brk -> %d\n",res);
        if (res == -1) return NULL;
        pBrk = pNewBrk;
        printf("new program break is at %x\n",sbrk(0));
    }
    p = pPad;
    pPad += n;
    printf("pBrk is at %x\n",pBrk);
    printf("pPad is at %x\n",pPad);
    printf("p is at %x\n\n",p);
    return p; 
} 

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