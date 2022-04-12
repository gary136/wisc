#include <stdio.h>

void print_array(int *a);

int main() {

    int a[5] = {1,2,3,4,5};
    print_array(a);
}

void print_array(int *a){
    for (int i=0;i<5;i++) 
        printf("%d ",a[i]);
    printf("\n");
}
