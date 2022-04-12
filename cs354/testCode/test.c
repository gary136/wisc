// Online C compiler to run C program online
#include <stdio.h>

// void modify(int *m[], int row, int col){
//     for (int i=0; i<row; i++) {
//         for (int j=0; j<col; j++) m[i][j] = 0;
//     }
// }

void modify(int m[5][5], int row, int col){
    for (int i=0; i<row; i++) {
        for (int j=0; j<col; j++) m[i][j] = 0;
    }
}

int main() {
    // Write C code here
    int *m[5];
    for (int i=0; i<5; i++) {
        m[i] = malloc(sizeof(int) * 5);
    }
    for (int i=0; i<5; i++) {
        for (int j=0; j<5; j++) m[i][j] = i*j;
    }
    // for (int i=0; i<5; i++) {
    //     for (int j=0; j<5; j++) printf("%d ", m[i][j]);
    // }
    int x[5][5];
    for (int i=0; i<5; i++) {
        for (int j=0; j<5; j++) x[i][j] = m[i][j];
    }
    for (int i=0; i<5; i++) {
        for (int j=0; j<5; j++) printf("%d ", x[i][j]);
    }
    modify(x, 5, 5);
    for (int i=0; i<5; i++) {
        for (int j=0; j<5; j++) printf("%d ", x[i][j]);
    }
    return 0;
}