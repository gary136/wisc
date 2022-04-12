#include <stdio.h>
#include <stdlib.h>

#define MAX 1000

int main() {
    FILE *fp;
    char buff[MAX];

    fp = fopen("gates_quote.txt", "r");


    while (fgets(buff, MAX, fp)) {
        printf("%s", buff);
    }
    fclose(fp);

    return 0;
}
