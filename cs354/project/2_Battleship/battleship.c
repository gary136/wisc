#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// dynamic memory allocation, traversing 2D arrays, using address arithmetic, and file I/O in C 

typedef struct SHIP{
    int row, column, length;
    char direction;
} SHIP;

typedef struct ATTACK{
    int row, column;
} ATTACK;

// these are the prototype functions developed for our solution.  You are welcome to use them 
// or modify them if you have a different strategy for solving this project.
void Read_Save_Game_File(char *filename, int *rows, int *columns, int *ship_count, 
    SHIP **ships, int *attack_count, ATTACK **attacks);
void Initialize_Board(int rows, int columns, char **board);
void Print_Board(int rows, int columns, char *board);
void Add_Ships(int rows, int columns, char *board, int ship_count, SHIP *ships, int *partial_ships);
void Process_Attacks(int rows, int columns, char *board, int attack_count, ATTACK *attacks, int *partial_ships);
// void Game_Over(int rows, int columns, char *board);
// modified
void Game_Over(int partial_ships);

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
    int partial_ships = 0; // compute number of "S"
    
    // read the save game file
    Read_Save_Game_File(argv[1], &rows, &columns, &ship_count, &ships, &attack_count, &attacks);
    /* Note: Reading the file and using malloc to reserve space for 
     * the data may be easier to do here, in main, rather than in a 
     * function */

    // Uncomment each function as you write and test it
    // Generate the board
    printf("Initializing Board\n");
    Initialize_Board(rows, columns, &board);
    Print_Board(rows, columns, board);

    // Add Ships to the board;
    Add_Ships(rows, columns, board, ship_count, ships, &partial_ships);
    Print_Board(rows, columns, board);

    // // Compute how many 'S'
    // for (int i=0; i<ship_count; i++)
    //     partial_ships+=ships[i].length;
    // printf("Total number of S = %d\n", partial_ships);

    // Process Attacks
    Process_Attacks(rows, columns, board, attack_count, attacks, &partial_ships);
    Print_Board(rows, columns, board);
    // printf("Total number of S = %d\n", partial_ships);

    Game_Over(partial_ships);
    
    // free memory
    free(board);
    free(ships);
    free(attacks);
    
    return 0;
}

////////////////////////////////////////////
// add comments here
////////////////////////////////////////////
void Read_Save_Game_File(char *filename, int *rows, int *columns, int *ship_count, 
    SHIP **ships, int *attack_count, ATTACK **attacks) {
    // loading a save game file for one player. 
    // Verify that the ships are valid as they are added to the board 
    // Then add attacks to that player’s board.  
    // Finally, determine if the player has lost or 
    // if the game is still in progress
    
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
        exit(1);
    }

    // read the board size
    fscanf(input, "%d", rows);
    fscanf(input, "%d", columns);
    fgets(skip_this_text, 1000, input); // read the newline character
    printf("Board Size = (%d, %d)\n", *rows, *columns);
    fgets(skip_this_text, 1000, input); // read the newline character
    
    // read Number of Ships
    fscanf(input, "%d", ship_count);
    printf("Number of Ships = %d\n", *ship_count);

    // assign ship to ships
    *ships = (SHIP *)malloc(*ship_count * sizeof(SHIP));
    for (int i=0; i<*ship_count; i++) {
        fscanf(input, "%d", &(*ships)[i].row);
        fscanf(input, "%d", &(*ships)[i].column);
        fscanf(input, "%d", &(*ships)[i].length);
        fscanf(input, "%c", &(*ships)[i].direction); // 2 times for different length
        fscanf(input, "%c", &(*ships)[i].direction);
        fgets(skip_this_text, 1000, input); // read the newline character
    }

    for (int i=0; i<*ship_count; i++)
        printf("Ship %d: (%d, %d), length = %d, direction %c\n"
            ,i , (*ships)[i].row, (*ships)[i].column, (*ships)[i].length, (*ships)[i].direction);
    
    // read Number of Attacks
    fgets(skip_this_text, 1000, input); // read the newline character
    fscanf(input, "%d", attack_count);
    printf("Number of Attacks = %d\n",*attack_count);
    
    // assign attack to attacks
    *attacks = (ATTACK *)malloc(*attack_count * sizeof(ATTACK));
    for (int i=0; i<*attack_count; i++) {
        fscanf(input, "%d", &(*attacks)[i].row);
        fscanf(input, "%d", &(*attacks)[i].column);
        fgets(skip_this_text, 1000, input); // read the newline character
    }
    for (int i=0; i<*attack_count; i++)
        printf("Attack %d: (%d, %d)\n"
            ,i , (*attacks)[i].row, (*attacks)[i].column);
    printf("\n");
    /*  Complete the rest of the function below - or move the code above to main */
    fclose(input);
}

void Initialize_Board(int rows, int columns, char **board){
    // a rectangular board with R rows and C columns of characters
    // Use malloc to reserve dynamically allocated memory 
    // on the heap after reading the size of the game board

    // an empty board with dimensions 3 4 would look like:
    // ....
    // ....
    // ....

    // reserve a contiguous block of 1D memory 
    // then use math to map the 2D indexes to this block of memory
    *board = (char *)malloc(rows * columns * sizeof(char));
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            *(*board + (i*columns + j ) * sizeof(char)) = '.';
        }
    }
}

void Print_Board(int rows, int columns, char *board){
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) 
            printf("%c", *(board + i*columns + j));
        printf("\n");
    }
    printf("\n");
}

void Add_Ships(int rows, int columns, char *board, int ship_count, SHIP *ships, int *partial_ships) {
    // Four pieces of data - 
    // The first two numbers are the upper left coordinates of the ship. 
    // The third number is the length of the ship. 
    // And the fourth datum character is either ‘V’ or ‘H’, 
    // indicating the ship's orientation – either vertical or horizontal.

    // Adding ship “0 1 3 V” to the board 
    // -> from (0,1) towards vertical for 3 block

    // .S..
    // .S..
    // .S..

    printf("Adding Ships\n");
    for (int i=0; i<ship_count; i++) {
        // verifies that each ship fits on the board and 
        if (ships[i].row<0 
            || ships[i].column<0
            || ships[i].row>rows-1 
            || ships[i].column>columns-1
            ) {
            printf("Ship %d is out of bounds - skipping\n", i);
            continue;
        }        
    
        // confirms that each ship does not go out of bounds
        if (ships[i].direction=='V') {
            if (ships[i].row+ships[i].length-1>rows-1) {
                printf("Ship %d is out of bounds - skipping\n", i); 
                continue;
            }
        }
        else {
            if (ships[i].column+ships[i].length-1>columns-1) {
                printf("Ship %d is out of bounds - skipping\n", i); 
                continue;
            }
        }
        
        
        
        
        // confirms that ships do not overlap with any already placed ships
        int overlap = 0;
        for (int j=0; j<ships[i].length; j++) {
            if (ships[i].direction=='V') {
                if (*(board + ((ships[i].row + j)*columns + ships[i].column) * sizeof(char)) == 'S') {
                    overlap = 1;
                }
            }
            else {
                if (*(board + (ships[i].row*columns + ships[i].column + j) * sizeof(char)) == 'S') {
                    overlap = 1;
                }
            }
        }
        if (overlap == 1) {
            printf("Ship %d overlaps another ship - skipping\n", i); 
            continue;
        }
        
        // If the ship is valid, replace the ‘.’ with the letter ‘S’
        for (int j=0; j<ships[i].length; j++) {
            if (ships[i].direction=='V') {
                *(board + ((ships[i].row+j)*columns + ships[i].column) * sizeof(char)) = 'S';
                ++*partial_ships; // Compute how many 'S'
            }
            else {
                *(board + (ships[i].row*columns + ships[i].column +j) * sizeof(char)) = 'S';
                ++*partial_ships; // Compute how many 'S'
            }
        }
    }
}

void Process_Attacks(int rows, int columns, char *board, int attack_count, ATTACK *attacks, int *partial_ships) {
    // Each attack has two pieces of data the row and column coordinates.  
    // For each attack, we will change the game board.  
    // If the attack hits a ship, we will replace the ‘S’ with the letter ‘H’. 
    // If the attack misses a ship, we will replace the ‘.’ with the letter ‘M’.

    // Adding attacks 0 1, 0 2 to the board 

    // .HM.
    // .S..
    // .S..

    printf("Processing Attacks\n");
    for (int i=0; i<attack_count; i++) {
        // verifies that each attack fits on the board and does not go out of bounds
        if (attacks[i].row<0 
            || attacks[i].column<0 
            || attacks[i].row>rows-1 
            || attacks[i].row>columns-1) {
            printf("Attack %d is out of bounds - skipping\n", i);
            continue;
        }
        
        // if the attack hits a ship, update from ‘S’ to ‘H’
        if (*(board + (attacks[i].row*columns + attacks[i].column) * sizeof(char))=='S') {
            *(board + (attacks[i].row*columns + attacks[i].column) * sizeof(char)) = 'H';
            --*partial_ships; // Compute how many 'S'
        }
        if (*(board + (attacks[i].row*columns + attacks[i].column) * sizeof(char))=='H') {
            // Duplicate attacks are ignored with no error message
        }
        // if the attack misses, update from ‘.’ to ‘M’
        else *(board + (attacks[i].row*columns + attacks[i].column) * sizeof(char)) = 'M';
    }
}

void Game_Over(int partial_ships) {
    // analyzes the state of the game.  
    // If all of the ships have been sunk the player has lost 
    // and the message “All ships have been sunk – game over” is printed. 
    // Otherwise the message “Ships remain afloat – game on” is printed.  
    // If any square of the board contains an ‘S’ 
    // then the battle has not yet been lost
    if (partial_ships==0) printf("All ships have been sunk - game over\n");
    else printf("Ships remain afloat - game on\n");
}