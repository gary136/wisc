#include <stdio.h>

#define BINARY_STRING_LENGTH 33
#define HEX_STRING_LENGTH 9

void GetBinaryFromUser(char *binary);
void GetHexFromUser(char *hex);
void GetDecimalFromUser(unsigned int *x);

void ConvertBinaryToDecimal(const char *binary, unsigned int *decimal);
void ConvertHexToDecimal(const char *hex, unsigned int *decimal);
void ConvertDecimalToBinary(const unsigned int decimal, char *binary); 
void ConvertDecimalToHex(const unsigned int decimal, char *hex); 

int main(){
    char binary[BINARY_STRING_LENGTH] = "11011";
    char hex[HEX_STRING_LENGTH] = "0x3F";
	unsigned int decimal = 31;
	
	enum MENU {binary_to_decimal=1, hex_to_decimal, decimal_to_binary, decimal_to_hex};
	while (1) {
		printf("Which conversion would you like to do?\n");
		printf("  %d. Binary to Decimal\n", binary_to_decimal);
		printf("  %d. Hex to Decimal\n", hex_to_decimal);
		printf("  %d. Decimal to Binary\n", decimal_to_binary);
		printf("  %d. Decimal to Hex\n", decimal_to_hex);
		printf("  q to Quit\n");
		int choice;
		if (!scanf("%d", &choice)) return 0;  // exits if user enters something other than a digit
		
		switch (choice) {
			case binary_to_decimal: {
				GetBinaryFromUser(binary); 
				ConvertBinaryToDecimal(binary, &decimal); 
				printf("Binary: %s\nDecimal: %d\n\n", binary, decimal);
			} break;
			case hex_to_decimal: {
				GetHexFromUser(hex); 
				ConvertHexToDecimal(hex, &decimal); 
				printf("Hex: %s\nDecimal: %d\n\n", hex, decimal);
			} break;
			case decimal_to_binary: {
				GetDecimalFromUser(&decimal); 
				ConvertDecimalToBinary(decimal, binary); 
				printf("Decimal: %u\nbinary: %s\n\n", decimal, binary);
			} break;
			case decimal_to_hex: {
				GetDecimalFromUser(&decimal); 
				ConvertDecimalToHex(decimal, hex); 
				printf("Decimal: %u\nHex: %s\n\n", decimal, hex);
			} break;
			default: return 0; // exit if user enters an invalid number
		}
	}
	return 0;
}

void GetBinaryFromUser(char * binary) {
    // write your code here to complete this function
}

void GetHexFromUser(char * hex) {
    // this function has been written for you
    printf("Please enter hex string here (do include the 0x prefix): ");
    scanf("%s", hex);
	for (char *p=hex;*p;p++) if(*p>='a') *p-='a'-'A';  // to upper case
    return;
}

void GetDecimalFromUser(unsigned int *x) {
    // write your code here to complete this function
}

void ConvertBinaryToDecimal(const char * binary, unsigned int *decimal) {
    // write your code here to complete this function
    *decimal = 32; // This hardcoded answer will be incorrect for all but one input. Change this line!
	return;
}

void ConvertHexToDecimal(const char *hex, unsigned int *decimal) {
    // write your code here to complete this function
	*decimal = 32; // This hardcoded answer will be incorrect for all but one input. Change this line!
	return;
}

void ConvertDecimalToBinary(const unsigned int decimal, char *binary) {
    // write your code here to complete this function
    // do not include leading zeros in the answer
    // if the input is 0 then the output should be just 0
    binary = "11101"; // This hardcoded answer will be incorrect for all but one input. Change this line!
    return;
}
void ConvertDecimalToHex(const unsigned int decimal, char *hex) {
    // write your code here to complete this function
    // do not include leading zeros in the answer
    // do include the prefix 0x
    // if the input is 0 then the output should be 0x0 
    hex = "0xF3"; // This hardcoded answer will be incorrect for all but one input. Change this line!
    return;
}










