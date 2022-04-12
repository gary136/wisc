#include <stdio.h>
#define MAX_ROW 3
#define MAX_COL 5

// declaration or prototype
// void Print_Matrix(int m[][MAX_COL]);
void Print_Matrix(int m[][MAX_COL]);

int main() {
	
	printf("Welcome to Week 3 of CS354!\n\n");
	
	// declare the matrix
	int m[MAX_ROW][MAX_COL];
	
	// initialize the matrix 
	for (int i=0; i<MAX_ROW; i++) 
		for (int j=0; j<MAX_COL; j++)
			m[i][j] = 10 * (i+1) + (j+1);
	
	// print the matrix
	for (int i=0; i<MAX_ROW; i++) {
		for (int j=0; j<MAX_COL; j++)
			printf("%d\t",m[i][j]);
		printf("\n");
	}
	printf("\n");
	
	// where are all the number stored?
	for (int i=0; i<MAX_ROW; i++)
		for (int j=0; j<MAX_COL; j++)
			printf("m[%d][%d] = %d at address %p\n", i,j, m[i][j], &m[i][j]);
	printf ("m = %p and is at address %p",m, &m);
	
	printf("\n");
	Print_Matrix(m);
	
	printf("*(m + (i*COL + j)*sizeof(int))\n");
	
	/*
	char mat_of_strings[ROWS][COLS][NUMBER_OF_CHARACTERS];
	Print_Mat_Of_Strings(mat_of_strings);
	// prototype
	void Print_Mat_Of_Strings(char mos[*][COLS][NUMBER_OF_CHARACTERS]);
	*/
	
	printf("\n");
	return 0;
}

// definition
void Print_Matrix(int mat[][MAX_COL]) {
	// print the array
	printf("Print_Matrix Function\n");
	for (int i=0; i<MAX_ROW; i++) {
		for (int j=0; j<MAX_COL; j++)
			printf("%d\t",mat[i][j]);
		printf("\n");
	}
	printf ("mat = %p and is at address %p",mat, &mat);
	printf("\n");
}
















