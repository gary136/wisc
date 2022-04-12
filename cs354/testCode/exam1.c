#include <stdio.h>

struct node {
    int a,b,c;
};
int main() {
    struct node num = {3,5,4};
    struct node *ptr = &num;
    printf("%d\n", *((int*)ptr--));
    return 0;
}

int main()
{
char s1[5] = "11111";
char s2[5] = "11110";
char s3[5] = "11101";

int *p1 = (int*)s1;
int *p2 = (int*)s2;
int *p3 = (int*)s3;
printf("%d %d %d",*p1,*p2,*p3);
if (*p1==*p2) printf("A");
if (*p1==*p3) printf("B");
if (*p2==*p3) printf("C");
printf("D\n");
    return 0;
}

void foo(int *ptr) {*ptr+=1;}
void bar(int **ptr) {*ptr+=1;}
int main()
{
    int arr[2]={0,1};
    int *ptr=arr;
    bar(&ptr);
    foo(ptr);

    printf("%d %d %d\n",*ptr,arr[0],arr[1]);
    return 0;
}

int main()
{
    int *ptr = malloc(4);
    char *str = malloc(7);
    *ptr = 0x41414141;
    strcpy(str, "ABCDEFGG");
    memcpy(str,ptr,sizeof(ptr)-1);
    printf("%s\n",str);
    return 0;
}

#include <stdlib.h>
#include <string.h>

char* myStrcpy(char * str, int *ptr){
 *ptr = strlen(str);
 char *newStr = malloc(++*ptr);
 strcpy(newStr, str);
 return newStr;
}
int main()
{
    int len;
    char *str = myStrcpy("Hello\0", &len);
    str = realloc(str, len*2);
    strcpy(str+len, "World");
    printf("%s%d\n",str,len);
    return 0;
}

int main()
{
    char s[] = "Exams are hard!";
printf("%p %c %p\n",s,s[10],&s[10]);
    return 0;
}

char * Binary(int n) {
    char * binary = malloc(n * sizeof(char));
    int i = 0;
    for ( ; n > 0; i++) {
        binary[i] = '0' + n%2;
        n /= 2;
    }
    binary[i] = '\0';
    return binary;
}
int main()
{
    printf("%s\n", Binary(100));
    return 0;
}

// void Input_Name(struct STUDENT *student) {
//     // Assume this function works correctly as described in the comments
// }

int main() {
    char* name = "Mike"; 
    // assume Get_Name asks the user to input their name.
    // the user enters the name "Mike"
    // the function correctly stores the name in the member variable name
    name[0]++;
    printf("%s\n",name);
} 