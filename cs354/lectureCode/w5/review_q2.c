#include <stdio.h>
#include <stdlib.h>

#define ARRAY_LENGTH(A) (sizeof(A) / sizeof(A[0]))

int main () {
    int *p = malloc(sizeof(int) * 5);
    for (int i=0;i< ARRAY_LENGTH(p); ++i)
        printf("p[%d] = %d\n",i, p[i]);
    int a[5] = {1,2,3,4,5};
    for (int i=0;i< ARRAY_LENGTH(a); ++i)
        printf("a[%d] = %d\n",i, a[i]);
    return 0;


}
