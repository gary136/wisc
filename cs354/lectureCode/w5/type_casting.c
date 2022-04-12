#include <stdio.h>
#include <limits.h>

int main() {
    printf("\n");

    // positive number char to short
    char c = 0x1A; // (26) 
    short s = (short)c;
    printf("decimal c = %d; hex c = %X\n", c, c);
    printf("decimal s = %d; hex s = %X\n", s, s);
    printf("\n");

    // negative number char to short
    c = -26; // (26) 
    s = (short)c;
    printf("decimal c = %d; hex c = %X\n", c, c);
    printf("decimal s = %d; hex s = %X\n", s, s);
    printf("\n");

    // printing long long hex values does work
    long long ll = 0x1234567890ABCDEF;
    printf("decimal ll = %lld; hex ll = %llX\n", ll, ll);
    printf("\n");
    
    // positive number int to long long
    int i = 0x12345678; 
    ll = (long long)i;
    printf("decimal i = %d; hex i = %X\n", i, i);
    printf("decimal ll = %lld; hex ll = %llX\n", ll, ll);
    printf("\n");

    // negative number int to long long
    i = INT_MIN; 
    ll = (long long)i;
    printf("decimal i = %d; hex i = %X\n", i, i);
    printf("decimal ll = %lld; hex ll = %llX\n", ll, ll);
    printf("\n");


    // negative number int to long long
    ll = 0x1234567890ABCDEF;
    i = (int)ll; 
    printf("decimal ll = %lld; hex ll = %llX\n", ll, ll);
    printf("decimal i = %d; hex i = %X\n", i, i);
    printf("\n");

    // positive number short to char
    s = -200;
    c = (char)s;
    
    printf("decimal s = %d; hex s = %X\n", s, s);
    printf("decimal c = %d; hex c = %X\n", c, c);
    printf("\n");

    // unsigned char to short
    unsigned char uc = 1;
    unsigned short us = (unsigned short)uc; 
    printf("decimal us = %u; hex us = %X\n", us, us);
    printf("decimal uc = %u; hex uc = %X\n", uc, uc);
    printf("\n");

    // unsigned char to short
    uc = 0xFF;
    us = (unsigned short)uc; 
    printf("decimal us = %u; hex us = %X\n", us, us);
    printf("decimal uc = %u; hex uc = %X\n", uc, uc);
    printf("\n");

    return 0;
}
