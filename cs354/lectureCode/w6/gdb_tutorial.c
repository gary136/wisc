#include <stdio.h>
#include <string.h>

int do_math(int big, int temp) {
    int i;
    i = big;
       if (big > 10) i=i%10;
    return (i*temp); 
}

int main() {
    int sum, i;
    char string[120];
    sum=0;
    
    strcpy(string,"Hello!");

    for (i=1;i<10;i++) {
        sum+=i;
        sum=do_math(sum,i);
    }
    printf("sum=%d\n",sum);

    return 0;
}
