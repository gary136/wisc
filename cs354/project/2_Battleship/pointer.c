#include <stdio.h>
#include <stdlib.h>

char *board = NULL;
int rows = 4; 
int columns = 6;

void Initialize_Board(int rows, int columns, char **board);
void Print_Board(int rows, int columns, char **board);

int main(int argc, char *argv[]) {
    printf("In the beginning, board points to %p\n\n", board);

    Initialize_Board(rows, columns, &board);

    printf("\n");
    printf("Back to main\n");
    printf("board pointers to %p\n", board);
    Print_Board(rows, columns, &board);

    return 0;
}


void Initialize_Board(int rows, int columns, char **board){
    printf("In Initialize_Board\n");
    *board = (char *)malloc(rows * columns * sizeof(char));
    printf("board pointers to %p\n", *board);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            *(*board + (i*columns + j ) * sizeof(char)) = '.';
        }
    }
    Print_Board(rows, columns, board);
}


void Print_Board(int rows, int columns, char **board){
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) 
            printf("%c ", *(*board + (i*columns + j) * sizeof(char)));
        printf("\n");
    }
}