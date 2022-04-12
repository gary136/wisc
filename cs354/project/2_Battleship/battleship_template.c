#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct SHIP{
    int row, column, length;
    char direction;
} SHIP;

typedef struct ATTACK{
    int row, column;
} ATTACK;

// these are the prototype functions developed for our solution.  You are welcome to use them 
// or modify them if you have a different strategy for solving this project.
void Read_Save_Game_File(char *filename, int *rows, int *columns, int *ship_count, SHIP **ships, int *attack_count, ATTACK **attacks);
void Initialize_Board(int rows, int columns, char **board);
void Print_Board(int rows, int columns, char *board);
void Add_Ships(int rows, int columns, char *board, int ship_count, SHIP *ships);
void Process_Attacks(int rows, int columns, char *board, int attack_count, ATTACK *attacks);
void Game_Over(int rows, int columns, char *board);

int main(int argc, char *argv[] ) {

    // verify command line parameters
    if(argc != 2){
        printf("expected usage: %s <save_game_file>", argv[0]);
        exit(1);
    }

    // declare variables - feel free to add additional variables as needed
    char *board = NULL;
    SHIP *ships = NULL;
    ATTACK *attacks = NULL;
    int rows=0, columns=0, ship_count=0, attack_count=0;
    
    // read the save game file
    Read_Save_Game_File(argv[1], &rows, &columns, &ship_count, &ships, &attack_count, &attacks);
    /* Note: Reading the file and using malloc to reserve space for 
     * the data may be easier to do here, in main, rather than in a 
     * function */

    /* Uncomment each function as you write and test it
    // Generate the board;
    Initialize_Board(rows, columns, &board);
    Print_Board(rows, columns, board);

    // Add Ships to the board;
    Add_Ships(rows, columns, board, ship_count, ships);
    Print_Board(rows, columns, board);

    // Process Attacks
    Process_Attacks(rows, columns, board, attack_count, attacks);
    Print_Board(rows, columns, board);

    Game_Over(rows, columns, board);
    
    // free memory
    free(board);
    free(ships);
    free(attacks);
    */
    
    return 0;
}

////////////////////////////////////////////
// add comments here
////////////////////////////////////////////
void Read_Save_Game_File(char *filename, int *rows, int *columns, int *ship_count, SHIP **ships, int *attack_count, ATTACK **attacks) {
    printf("Reading Save Game File\n");
    FILE *input = fopen(filename, "r");
    if(!input){
        printf("INPUT File open failed\n");
        exit(1);
    }
    
    // skip the board size line
    char skip_this_text[1000];
    if (!fgets(skip_this_text, 1000, input)) {
        printf("Error reading board size");
        exit(1);}

    // read the board size
    fscanf(input, "%d", rows);
    fscanf(input, "%d", columns);
    fgets(skip_this_text, 1000, input); // read the newline character
    printf("Board Size = (%d, %d)\n",*rows, *columns);

    /*  Complete the rest of the function below - or move the code above to main */

    fclose(input);
}
