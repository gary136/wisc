#include <stdio.h>
#define N 4


int Is_Number_Correct(int p1, int p2, int p3){
  signed int result;
  if ( p1 == p2 ){
    // printf("Number %d: Correct. Nice Job.\n", p3);
    result = 1;
  }
  else{
    // printf("Number %d: Incorrect\n", p3);
    result = 0;
  }
  return result;
}

signed int f0(){
  return 5589516;
}

signed int f1(){
  
  int a = 25027;  // 0x61c3 -> ebp-4
  int b = 51330;  // 0xc882 -> ebp-8
  int c = 67548; // 0x107dc -> ebp-0xc
  int d = 30404;  // 0x76c4 -> ebp-0x10 
  int e = 49;    // 0x31 -> ebp-0x14
  int f = 17;    // 0x11 -> ebp-0x18

  if(a > b+c){ 
    // cmp %eax,-0x4(%ebp)
    // a = ebp-0x4
    // b = edx = ebp-0x8
    // c = eax = ebp-0xc
    // eax = eax + edx = b + c
    // Evaluates -0x4(%ebp) - %eax, namely a-(b+c)

    // jle 12ab <f1+0x4a> 
    // jle so [>] in conditional statement
    // if(a <= b+c), jump 12ab 
    // else jump 12a2
    
    // a <= b+c so would not go
    // 12a2
    // a = ebp -4
    a = a >> 4; // sarl  $0x4,-0x4(%ebp)
                // move a right 4 bit
    // -0x4(%ebp),%eax -> put a to %eax, the parameter to return  
    return a; 
    // leave , ret
  }



  // a <= b+c so would go
  else{ //12ab
    if(d < a*b){ 
      // cmp %eax,-0x10(%ebp)
      // a = ebp-0x4
      // b = edx = ebp-0x8
      // d = ebp-0x10
      // mov -0x4(%ebp),%eax -> eax = a
      // imul -0x8(%ebp),%eax -> eax = eax*b = a*b
      // Evaluates -0x10(%ebp) - %eax, namely d-a*b

      // jge 12c6 <f1+0x65> 
      // jge so [<] in consitional statement
      // if(d >= a*b), jump 12c6 
      // else jump 12b7

      //12b7
      // c = ebp-0xc
      // e = ebp-0x14
      // eax = c
      // cltd -> enlarge eax as 64bit, [edx:eax]
      // idivl -> [edx:eax] / (ebp-0x14) = q .... r
      // q in eax, r in edx 
      // mov %edx,-0xc(%ebp) -> put r in c
      c = c % e;

      // -0x10(%ebp),%eax -> put c to %eax, the parameter to return
      return c;
      // leave , ret
    }
    // d < a*b so would not go
    else{ //12c6
          
      // eax = d
      // f = 0x11
      // cltd -> enlarge eax as 64bit, [edx:eax]
      // idivl -> [edx:eax] / (ebp-0x18) = q .... r
      // q in eax, r in edx 

      // mov  %eax,-0x10(%ebp) -> put q in d
      d = d / f;
      
      // -0x10(%ebp),%eax -> put d to %eax, the parameter to return
      return d;
      // leave , ret
    }
  }
}

int  f2(int p1){
  int v2; 
  signed int v3; 

  v3 = 0;
  v2 = 0;
  while ( v3 <= 27 ){
    v2 += v3 * v3 + p1;
    ++v3;
  }
  return v2;
}

void f3_helper(int *p1){
  int *result; // eax

  result = p1;
  *p1 += 3;
}

int *f3(int p1, int *p2){
  int *result; 

  f3_helper(&p1);
  f3_helper(&p1);
  result = p2;
  *p2 += 8 * p1;
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
