#include <stdio.h>
#define N 4


int Is_Number_Correct(int p1, int p2, int p3){

  int result;
  // mov    0x8(%ebp),%eax -> eax = ebp+8 = p1
  // cmp    0xc(%ebp),%eax 
  // jne    1239 <Is_Number_Correct+0x2c>
  // -> assume eax = p1 != ebp+12 = p2, jump 1239
  // reverse inside conditional statement
  if ( p1 == p2 ){
    // printf("Number %d: Correct. Nice Job.\n", p3);
    
    // mov    $0x1,%eax -> eax = 1
    result = 1;
    // jmp    1251 -> return 
  }

  else{ // 1239
    // printf("Number %d: Incorrect\n", p3);

    // mov    $0x0,%eax -> eax = 0
    result = 0;
  }

  return result;
}

int f0(){
  // mov    $0x554a0c,%eax -> eax = 5589516
  return 5589516;
}

int f1(){
  
  int a = 25027;  // 0x61c3 -> ebp-4
  int b = 51330;  // 0xc882 -> ebp-8
  int c = 67548; // 0x107dc -> ebp-0xc
  int d = 30404;  // 0x76c4 -> ebp-0x10 
  int e = 49;    // 0x31 -> ebp-0x14
  int f = 17;    // 0x11 -> ebp-0x18

  if(a > b+c){ 
    // cmp %eax,-0x4(%ebp) jle 12ab <f1+0x4a>

    // if -0x4(%ebp)<=%eax, jump 12ab, otherwise continue next
    
    // a = ebp-0x4
    // b = edx = ebp-0x8
    // c = eax = ebp-0xc
    // eax = eax + edx = b + c
    // namely cmp (b+c),a

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

int f2(int p1){
  int v4; 
  int v8; 

  v4 = 0; // movl   $0x0,-0x4(%ebp)
  v8 = 0; // movl   $0x0,-0x8(%ebp)

  // 1303
  while ( v4 <= 27 ){ 
    // cmpl   $0x1b,-0x4(%ebp)
    // Evaluates -0x4(%ebp) - $0x1b, namely v4-27

    // jle 12ef <f2+0x1a> 
    // jle & loop so [<=] in conditional statement
    // if(v4 <= 27), jump 12ef (the loop) 

    // 12ef
    // mov -0x4(%ebp),%eax -> eax = v4
    // imul %eax,%eax -> eax = v4*v4
    // %eax,%edx -> edx = eax = v4*v4
    // 0x8(%ebp),%eax -> eax = p1
    // add %edx,%eax -> eax = edx+eax = v4*v4+p1
    // add %eax,-0x8(%ebp) -> v8 = v8 + v4*v4+p1
    // addl $0x1,-0x4(%ebp) -> v4 = v4 + 1

    v8 += v4 * v4 + p1;
    ++v4;
  }
  
  // else dont jump (stop the loop)
  return v8; // mov    -0x8(%ebp),%eax
}

void f3_helper(int *p1){
  // movl   $0x3,-0x4(%ebp) -> ebp-4 = 3  
  // mov    0x8(%ebp),%eax -> eax = ebp+8 = p1
  // mov    (%eax),%edx -> edx = *p1 
  // mov    -0x4(%ebp),%eax -> eax = ebp-4 = 3
  // add    %eax,%edx -> edx = eax + edx = 3 + *p1  

  // mov    0x8(%ebp),%eax -> eax = ebp+8 = p1
  int *result; // eax
  result = p1;
  *p1 += 3;

  //  mov    %edx,(%eax) -> (%eax) = *p1 = edx = 3 + *p1
}

int *f3(int p1, int *p2){
  int *result; 

  // lea    0x8(%ebp),%eax -> eax = (ebp+8) [lea means assigning address]
  // ebp+8 is the parameter p1
    // so eax is actually p1
  // push   %eax -> Save caller-save register, 
    // esp = esp-4 & (%esp) = eax
      // this becomes new ebp-8 in deeper level
  // call   f3_helper
  // add    $0x4,%esp -> add esp back
  f3_helper(&p1);
  // do that again
  f3_helper(&p1);

  // mov    0xc(%ebp),%eax ->  eax = ebp+12 = p2
  // mov    (%eax),%eax -> eax = (eax) = *p2
  result = p2; // eax

  // 0x8(%ebp),%edx -> edx = ebp+8 = p1
  // shl    $0x3,%edx -> edx = p1*8
  // add    %eax,%edx -> edx = eax + edx = *p2 + p1*8
  // mov    0xc(%ebp),%eax -> eax = ebp+12 = p2
  // mov    %edx,(%eax) -> (eax) = *p2 = edx = *p2 + p1*8
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
