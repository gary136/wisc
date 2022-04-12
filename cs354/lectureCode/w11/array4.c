int main() {
    int a[5];
    a[0] = 10;
    a[1] = 20;
    a[2] = 30;
    a[3] = 40;
    a[4] = 50;

    int i = 2;
    int x = a[i];

    int *p = a + i;
    return 0;
}

a[i]   ===  *(a + i)
in assembly a + 4*i

/*
-32  :: 10  a[0]  
-28  :: 20  a[1]
-24  :: 30  a[2]        
-20  :: 40  a[3]
-16  :: 50  a[4]
-12  :: address of a  :: pointer p
-8   :: 30  x
-4   :: 2   i
%ebp :: stored %ebp
return value
*/
