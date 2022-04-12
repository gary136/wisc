#include <stdio.h>
#include <stdbool.h>

typedef struct Address {
    char* city;
    int zip;
} Address;
  
typedef struct Student {
    int id;
    Address addr;
    bool isGrad;
} Student;

int NumGrads(Student stds[], int size) {
    int count = 0;
    for (int i=0; i<size; i++) {
        // cout << stds[i].isGrad << '\n';
        if (stds[i].isGrad) count++;
    }
    return count;
}

int main() {
    Student A, B, C, D;

    Student Students1[4] = {A, B, C, D};
    A.isGrad=true;
    B.isGrad=false;
    C.isGrad=true;
    D.isGrad=false;
    Student Students2[4] = {A, B, C, D};
    printf(A.isGrad ? "true" : "false");
    printf(Students1[0].isGrad ? "true" : "false");
    printf(Students2[0].isGrad ? "true" : "false");
    printf("\n");
    printf("%d\n",NumGrads(Students1, 4));
    printf("%d\n",NumGrads(Students2, 4));
    printf("%p %d %u\n", A, A.isGrad, &A.isGrad);
    printf("%p %d %u\n", Students1[0], Students1[0].isGrad, &Students1[0].isGrad);
    printf("%p %d %u\n", Students2[0], Students2[0].isGrad, &Students2[0].isGrad);

    printf("\n");
    int t1, t2, t3, t4;
    int test1[4] = {t1, t2, t3, t4};
    t1 = 93;
    t2 = 94;
    t3 = 95;
    t4 = 90;
    int test2[4]= {t1, t2, t3, t4};
    printf("%p\n", &t1);
    printf("%p\n", &test1[0]);
    printf("%p\n", &test2[0]);

}