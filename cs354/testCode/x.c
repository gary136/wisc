#include <stdio.h>
void myFree(int *ptr) {
    free(ptr);
    ptr = NULL;
}
int main()
{
    // // 1
    // int a=1;
    // int b=2;
    // int c=3;
    // int d=4;
    // int e=5;
    // int arr[5] = {a,b,c,d,e};
    // int r = *((int*)((char*)arr+12));
    // printf("%d\n",r); // print 4

    // 5
    int *ptr = malloc(sizeof(int));
    printf("%p\n", ptr);
    myFree(ptr);
    printf("%p\n", ptr);

    return 0;
}
