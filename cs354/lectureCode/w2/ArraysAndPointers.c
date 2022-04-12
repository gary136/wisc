#include <stdio.h>

int main() {

    printf("size of int is %d\n",sizeof(int));
    printf("size of short is %d\n",sizeof(short));
    printf("size of long is %d\n",sizeof(long));

    int arr[4] = {10, 20, 30, 40};
    arr[0] = 15;

    for (int i = 0; i < 4; i++) {
        printf("arr[%d] = %d at address %p\n", i, arr[i], &arr[i]);
    }
    printf("\n");

    // pointer a variable that holds the memory address of a variable
    int n = 3;
    int *pn = &n;
    int x, *px, y, *py;
    px = &x;
    py = &y;
    x = 3;
    printf("x = %d, px = %p, *px = %d\n", x, px, *px);
    
    *py = 5;
    printf("y = %d, py = %p, *py = %d\n", y, py, *py);
    printf("\n");
    
    // pointer declaration
    // int*, char*, float*, double*, 
    // int * pn; int* pn; int *pn;

    int a[] = {0,1,2,3,4,5};
    // an array is a pointer, but it's constant
    int *p;
    p = a;
    printf("a[0] = %d\t; p[0] = %d\n",a[0],p[0]);
    printf("a[1] = %d\t; p[1] = %d\n",a[1],p[1]);
    
    printf("*p = %d\t; p = %p\n",*p, p);
    p = p + 1;
    printf("*p = %d\t; p = %p\n",*p, p);
    p++;
    printf("*p = %d\t; p = %p\n",*p, p);
    p += 1;
    printf("*p = %d\t; p = %p\n",*p, p);

    printf("\n");

    for (pn = arr; pn != &arr[4]; pn++)
        printf("*pn = %d\t; pn = %p\n",*pn, pn);


    p = a;
    printf("*p = %d\t; p = %p\n",*p, p);
    p = p + 25;
    printf("*p = %d\t; p = %p\n",*p, p);


    return 0;
}
