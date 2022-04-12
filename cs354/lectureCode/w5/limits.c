#include<stdio.h>
#include<limits.h>


int main() {
    printf("\n");
    
    int num = 0xFFFFFFFF;
    printf("num as signed decimal    = %d\n",num);
    printf("num as unsigned          = %u\n",num);
    printf("num as hexadecimal       = %X\n",num);
    printf("\n");

    printf("sizeof(char) = %d bytes\n", sizeof(char));
    printf("sizeof(short) = %d bytes\n", sizeof(short));
    printf("sizeof(int) = %d bytes\n", sizeof(int));
    printf("sizeof(long) = %d bytes\n", sizeof(long));
    printf("sizeof(long long) = %d bytes\n", sizeof(long long));
    printf("\n");

    printf("sizeof(unsigned char) = %d bytes\n",      sizeof(unsigned char));
    printf("sizeof(unsigned short) = %d bytes\n",     sizeof(unsigned short));
    printf("sizeof(unsigned int) = %d bytes\n",       sizeof(unsigned int));
    printf("sizeof(unsigned long) = %d bytes\n",      sizeof(unsigned long));
    printf("sizeof(unsigned long long) = %d bytes\n", sizeof(unsigned long long));
    printf("\n");

    printf("CHAR_MIN    :   %d\n", CHAR_MIN);
    printf("CHAR_MAX    :   %+d\n", CHAR_MAX);
    printf("SCHAR_MIN   :   %d\n", SCHAR_MIN);
    printf("SCHAR_MAX   :   %+d\n", SCHAR_MAX);
    //printf("UCHAR_MIN   :   %d\n", UCHAR_MIN);
    printf("UCHAR_MAX   :   %+d\n", UCHAR_MAX);
    printf("\n");
    printf("SHRT_MIN    :   %d\n", SHRT_MIN);
    printf("SHRT_MAX    :   %+d\n", SHRT_MAX);
    printf("USHRT_MAX   :   %+d\n", USHRT_MAX);
    printf("\n");
    printf("INT_MIN     :   %d\n", INT_MIN);
    printf("INT_MAX     :   %+d\n", INT_MAX);
    printf("UINT_MAX    :   %u\n", UINT_MAX);
    printf("\n");
    printf("LONG_MIN    :   %ld\n", LONG_MIN);
    printf("LONG_MAX    :   %+ld\n", LONG_MAX);
    printf("ULONG_MAX   :   %lu\n", ULONG_MAX);
    printf("\n");
    printf("LLONG_MIN    :   %lld\n", LLONG_MIN);
    printf("LLONG_MAX    :   %+lld\n", LLONG_MAX);
    printf("ULLONG_MAX   :   %llu\n", ULLONG_MAX);
    printf("\n");
    
    int n = 0xFFFFFFFF;
    printf("n = %d\n",n);
    printf("n = %u\n",n);

    unsigned un = n;
    printf("un = %d\n",un);
    printf("un = %u\n",un);
    printf("\n");

    return 0;
}


























