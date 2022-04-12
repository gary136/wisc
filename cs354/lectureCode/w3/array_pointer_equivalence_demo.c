#include <stdio.h>
#define ARR_MAX 5

void Print_Array(const int *arr) {
		printf("Print_Array\n");
		for (int i=0;i<ARR_MAX;i++) {
		printf("arr[%d] = %d address of arr[%d] = %p\n",i,arr[i],i,&arr[i]);
		}
	printf ("arr   = %p; address of arr = %p\n", arr, &arr);
}


int main() {
	
	printf("Welcome to Week 3 of CS354!\n\n");
	
	// pointer review + connection to memory model
	int a = 3;
	int b = 4;
	
	int *pa = &a;
	int *pb = &b;
	
	printf ("a   = %d; address of a = %p\n", a, &a);
	printf ("b   = %d; address of b = %p\n", b, &b);
	printf ("*pa = %d; pa = %p; address of pa = %p\n", *pa, pa, &pa);
	printf ("*pb = %d; pb = %p; address of pb = %p\n", *pb, pb, &pb);
	printf("\n");
	
	// array + connection to memory model
	int arr[ARR_MAX];
	for (int i=0;i<ARR_MAX;i++) arr[i] = i*10;
	for (int i=0;i<ARR_MAX;i++) {
		printf("arr[%d] = %d address of arr[%d] = %p\n",i,arr[i],i,&arr[i]);
	}
	printf ("arr   = %p; address of arr = %p\n", arr, &arr);
	
	int *parr = arr;
	for (int i=0;i<ARR_MAX;i++) {
		printf("parr[%d] = %d address of parr[%d] = %p\n",i,parr[i],i,&parr[i]);
	}
	printf("parr   = %p; address of parr = %p\n", parr, &parr);
	
	printf("*parr = %d; *arr = %d\n",*parr, *arr);
	parr = parr + 1;
	printf("parr   = %p; address of parr = %p\n", parr, &parr);
	printf("*parr = %d; *arr = %d\n",*parr, *arr);
	// arr = arr + 1; // FAILED can not assign to array
	parr++;
	parr += 1;
	
	for (int *p = arr; p != &arr[ARR_MAX]; p++) 
		printf("*p = %d\n",*p);
	
	printf("arr[3] = %d\n",*(arr+3));
	printf("arr[3] = %d\n",3[arr]);
	
	// passing arrays to functions
	printf("\n");printf("\n");
	for (int i=0;i<ARR_MAX;i++) {
		printf("arr[%d] = %d address of arr[%d] = %p\n",i,arr[i],i,&arr[i]);
	}
	printf ("arr   = %p; address of arr = %p\n", arr, &arr);
	Print_Array(arr);
	
	// character arrays strings
	char str[10] = "abcdefghi";  // 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', '\0' (null terminating character)
	printf("\n\n str = %s\n",str);
	for (int i = 0; i<15; i++) {
		printf("str[%d] %c, %d at %p\n", i, str[i], str[i], &str[i]);
	}
	
	// string copy
	char zyx[10] = "zyxwvutsr";
	printf("\n\n str = %s; zyx = %s\n",str, zyx);
	char *p = str;
	char *q = zyx;
	while ((*p++ = *q++))   ;
	printf("\n\n str = %s; zyx = %s\n",str, zyx);
	
	
	
	printf("\n");
	return 0;
	
}