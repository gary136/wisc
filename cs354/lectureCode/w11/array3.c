int main() {
    int a[5];
    a[0] = 10;
    a[1] = 20;
    a[2] = 30;
    a[3] = 40;
    a[4] = 50;

    int i = 2;
    int x = a[i];
    return 0;
}

%eax i (2)
-28(%ebp) - start of the array
-28(%ebp, %eax, 4)

/*
-32  ::   
-28  :: 10  a[0]
-24  :: 20  a[1]        
-20  :: 30  a[2]
-16  :: 40  a[3]
-12  :: 50  a[4]
-8   :: 30  x 
-4   :: 2   i 
%ebp :: stored %ebp
return value
*/
