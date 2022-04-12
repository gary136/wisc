#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void recursive_countdown(int n);

int main() {
    int num = 3;
    int sum = 0;
    char str[100];

    strcpy(str, "gdb is awesome!");

    num = num + 3;

    for (int i=0; i<4; i++) 
        sum = sum + num;
    printf("sum = %d \n", sum);    

    recursive_countdown(3);

    char *text = malloc(99999999999999999999999999999999999);
    *text = 'a';

    printf("Thanks for watching");
    return 0;
}

void recursive_countdown(int n) {
    if (n == 0) {
        printf("Blastoff!!! \n");
        return;
    }
    printf("t-minus %d \n",n);
    n = n-1;
    recursive_countdown(n);
    return;
}
