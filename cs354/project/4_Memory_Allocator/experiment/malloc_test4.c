// Online C compiler to run C program online
#include <stdio.h>
int a;
int main() {
    printf("bss ends at %x\n",&a);
    printf("program break = %x\n",sbrk(0));
    int *p1 = malloc(1); printf("p1 is at %x\n",p1);    
    int *p2 = malloc(2); printf("p2 is at %x\n",p2);
    int *p3 = malloc(23); printf("p3 is at %x\n",p3);
    int *p4 = malloc(24); printf("p4 is at %x\n",p4);
    printf("program break = %x\n",sbrk(0));
    brk(0);
    printf("brk -> brk(0)\n"); 
    //brk通过传递的addr来重新设置program break, 成功则返回0，否则返回-1
    printf("program break = %x\n",sbrk(0));
    // sbrk用来增加heap，增加的大小通过参数increment决定，返回增加大小前的heap的program break，如果increment为0则返回program break
    brk(p1);
    printf("brk -> brk(p1)\n"); 
    printf("program break = %x\n",sbrk(0));
    printf("program break = %x\n",sbrk(3200));
    printf("program break = %x\n",sbrk(0));
    
// int *p5 = malloc(25); printf("p5 is at %p -> %d\n",p5,p5-p2);
// int *p6 = malloc(26); printf("p6 is at %p -> %d\n",p6,p6-p2);
// int *p7 = malloc(27); printf("p7 is at %p -> %d\n",p7,p7-p2);
// int *p8 = malloc(28); printf("p8 is at %p -> %d\n",p8,p8-p2);
    
    return 0;
}