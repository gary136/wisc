// Online C compiler to run C program online
#include <stdio.h>

int main() {
    // Write C code here
    // int *x = malloc(1); 
    // int *x_copy;
    // printf("x1 is at %p\n", x);
    // for (int i=2; i<32; i++) {   
    //     x_copy = x;
    //     free(x);
    //     *x = malloc(i); 
    //     printf("x%d is at %p, x moves %d\n",i ,x , x-x_copy);
    // }
    
int *p1 = malloc(1); printf("p1 is at %p\n",p1);    
int *p2 = malloc(2); printf("p2 is at %p -> %d\n",p2,p2-p1);
int *p3 = malloc(23); printf("p3 is at %p -> %d\n",p3,p3-p2);
int *p4 = malloc(24); printf("p4 is at %p -> %d\n",p4,p4-p2);
int *p5 = malloc(25); printf("p5 is at %p -> %d\n",p5,p5-p2);
int *p6 = malloc(26); printf("p6 is at %p -> %d\n",p6,p6-p2);
int *p7 = malloc(27); printf("p7 is at %p -> %d\n",p7,p7-p2);
int *p8 = malloc(28); printf("p8 is at %p -> %d\n",p8,p8-p2);
free(p2);
int *new_p2 = malloc(1); printf("new_p2 is at %p\n",p2); 
    printf("\n");
    
char *n1 = malloc(3); printf("n1 is at %p\n",n1);
char *n2 = malloc(24); printf("n2 is at %p\n",n2);
char *n3 = malloc(25); printf("n3 is at %p\n",n3);
free(n2);
char *n4 = malloc(6); printf("n4 is at %p\n",n4);
free(n3);
char *n5 = malloc(2); printf("n5 is at %p\n",n5);
printf("\n");
    
    char *m1 = malloc(3); printf("m1 is at %p\n",m1);
    char *m2 = malloc(1); printf("m2 is at %p -> %d\n",m2,m2-m1);
    char *m3 = malloc(4); printf("m3 is at %p -> %d\n",m3,m3-m1);
    free(m2);
    char *m4 = malloc(6); printf("m4 is at %p -> %d\n",m4,m4-m1);
    free(m3);
    char *m5 = malloc(2); printf("m5 is at %p -> %d\n",m5,m5-m1);
    printf("\n");
    
    int *p[20];    
    for (int i=1; i<16; i++) {       
        p[i] = malloc(i); 
        printf("p[%d] is at %p\n",i,p[i]);
    }    
    free (p[2]);    printf("free p[2]\n");    
    int *rp = malloc(1);     
    printf("rp is at %p\n",rp);    
    printf("\n");
    
int *r1 = malloc(12); printf("r1 is at %p\n",r1);    
int *r2 = malloc(48); printf("r2 is at %p -> %d\n",r2,r2-r1);
int *r3 = malloc(12); printf("r3 is at %p -> %d\n",r3,r3-r1);
int *r4 = malloc(12); printf("r4 is at %p -> %d\n",r4,r4-r1);
int *r5 = malloc(12); printf("r5 is at %p -> %d\n",r5,r5-r1);
free(r2);    free(r4);    
int *x1 = malloc(12); printf("x1 is at %p -> %d\n",x1,x1-r1);
int *x2 = malloc(12); printf("x2 is at %p -> %d\n",x2,x2-r1);
int *x3 = malloc(12); printf("x3 is at %p -> %d\n",x3,x3-r1);
int *x4 = malloc(12); printf("x4 is at %p -> %d\n",x4,x4-r1);
    
    return 0;
}