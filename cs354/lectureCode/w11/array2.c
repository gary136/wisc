int main() {
    char a[6];
    a[0] ='C'; 
    a[1] ='S'; 
    a[2] ='3'; 
    a[3] ='5'; 
    a[4] ='4'; 
    a[5] ='\0'; 

    return 0;
}

// allocation
// number of elements * size of element = number of bytes

// char *B[8] // requires 32 bytes (pointers are 4 bytes)

where is a[i]?
base address a[0] = -1(%ebp)
add size of element * i
a[4] = -1(%ebp) + 1*4

/*
-6  :: 67 # C
-5  :: 83 # S
-4  :: 51 # 3
-3  :: 53 # 5
-2  :: 52 # 4
-1  :: 0  # \0
%ebp :: stored %ebp
return value
*/
