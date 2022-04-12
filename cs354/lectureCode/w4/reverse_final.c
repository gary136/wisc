#include <stdio.h>
#include <stdlib.h>

void print_array(int *a);
int * reverse(int a[]); 

int main() {

    int a[5] = {1,2,3,4,5};
    print_array(a);
    int *p = reverse(a);
    print_array(p);
    free(p);

    return 0;
}

void print_array(int a[]){
    for (int i=0;i<5;i++) 
        printf("%d ",a[i]);
    printf("\n");
}

/* bad version
int * reverse(int a[]) {
    int r[5];
    int i=0, j=4;
    while (i<5)
        r[i++] = a[j--];
    return r;
}
// */

int * reverse(int a[]) {
    int i=0, j=4;
    int *r = malloc(5 * sizeof(int));
    while (i<5)
        r[i++] = a[j--];
    return r;
}
