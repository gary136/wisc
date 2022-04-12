#include <stdio.h>
#include <stdlib.h>

int main() {
    int *p1 = malloc(1); printf("p1 is at %p\n",p1);
    int *p2 = malloc(1); printf("p2 is at %p\n",p2);
    int *p3 = malloc(1); printf("p3 is at %p\n",p3);
    int *p4 = malloc(1); printf("p4 is at %p\n",p4);
    printf("\n\n");

    int *p[20];
    for (int i=1; i<16; i++) {
        p[i] = malloc(i); printf("p[%d] is at %p\n",i,p[i]);
    }

    free (p[2]);
    printf("free p[2]\n");
    
    int *rp = malloc(1); 
    printf("rp is at %p\n",rp);
    printf("\n\n");
    
    int *r1 = malloc(12); printf("r1 is at %p\n",r1);
    int *r2 = malloc(44); printf("r2 is at %p\n",r2);
    int *r3 = malloc(12); printf("r3 is at %p\n",r3);
    int *r4 = malloc(12); printf("r4 is at %p\n",r4);
    int *r5 = malloc(12); printf("r5 is at %p\n",r5);

    free(r2);
    free(r4);

    int *r = malloc(12); printf("r is at %p\n",r);
    

    return 0;
}
