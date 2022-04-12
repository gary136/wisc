#include <stdio.h>

char *board = NULL;
int rows = 4; 
int columns = 6;

void Initialize_Board(int rows, int columns, char **board);
void Print_Board(int rows, int columns, char *board);

int main(int argc, char *argv[]) {

    Initialize_Board(rows, columns, &board);
    printf("\n");
    Print_Board(rows, columns, board);
    return 0;
}

void Initialize_Board(int rows, int columns, char **board){
    *board = (char *)malloc(rows * columns * sizeof(char));
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            *(*board + (i*columns + j ) * sizeof(char)) = '.';
            printf("%c ", *(*board + (i*columns + j ) * sizeof(char)));
        }
        printf("\n");
    }
}
void Print_Board(int rows, int columns, char *board){
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) 
            printf("%c ", *(board + i*columns + j));
        printf("\n");
    }
}