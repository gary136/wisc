#include <stdio.h>
#define N 4


int Is_Number_Correct(int a2, int a3, int a4){
  signed int result;
  if ( a2 == a3 ){
    printf("Number %d: Correct. Nice Job.\n", a4);
    result = 1;
  }
  else{
    printf("Number %d: Incorrect\n", a4);
    result = 0;
  }
  return result;
}

signed int f0(){
  return 5589516;
}

signed int f1(){
  return 26;
}

int  f2(int a1){
  int v2; 
  signed int v3; 

  v3 = 0;
  v2 = 0;
  while ( v3 <= 27 ){
    v2 += v3 * v3 + a1;
    ++v3;
  }
  return v2;
}

void f3_helper(int *a1){
  int *result; // eax

  result = a1;
  *a1 += 3;
}

int *f3(int a1, int *a2){
  int *result; 

  f3_helper(&a1);
  f3_helper(&a1);
  result = a2;
  *a2 += 8 * a1;
  return result;
}

int main(void) {
    int i;
    int n[N];
    int a[N];
    int c = 0;

    // get numbers from user
    printf("Enter four numbers: ");
    for (i = 0; i<N; i++) scanf("%d", &n[i]);
    printf("\nYou have entered: %d, %d, %d, %d\n",n[0], n[1], n[2], n[3]);
    
    // get answers
    a[0] = f0(); 
    a[1] = f1();
    a[2] = f2(i);
    a[3] = 4046;  
    f3(a[3], &a[3]);

    // print answers
    // for (i = 0; i< N; i++) printf("answers #%d = %d\n",i,a[i]);

    // test 
    c += Is_Number_Correct(n[0], a[0], 0);
    c += Is_Number_Correct(n[1], a[1], 1);
    c += Is_Number_Correct(n[2], a[2], 2);
    c += Is_Number_Correct(n[3], a[3], 3);

    // report results
    if (c==0) printf("You didn't get any correct numbers. Please try again.\n");
    if (c > 0 && c < N) printf("You got %d correct numbers.  Please try again.\n", c);
    if (c==N) printf("All numbers are correct! Nice work!\n");

    return 0;
}
