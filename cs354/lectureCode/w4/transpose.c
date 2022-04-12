#include <stdio.h>
#include <stdlib.h>

#define ROWS 3
#define COLS 3

int main() {
    int a[ROWS][COLS] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                printf("a[%d][%d] = %d\t", i, j, a[i][j]);
            }
        printf("\n");
    }
    printf("\n");

    return 0;
}
