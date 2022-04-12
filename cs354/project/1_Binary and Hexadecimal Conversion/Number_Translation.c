#include <stdio.h>
#include <ctype.h>
#include <math.h>
#include <string.h>
#define BINARY_STRING_LENGTH 33
#define HEX_STRING_LENGTH 11

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
				printf("Binary: %s\nDecimal: %u\n\n", binary, decimal);
			} break;
			case hex_to_decimal: {
				GetHexFromUser(hex); 
				ConvertHexToDecimal(hex, &decimal); 
				printf("Hex: %s\nDecimal: %u\n\n", hex, decimal);
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
	printf("Please enter binary string here: ");
    scanf("%s", binary);
	// no need to transform alphabet
    return;
}

void GetHexFromUser(char * hex) {
    // this function has been written for you
    printf("Please enter hex string here (do include the 0x prefix): ");
    scanf("%s", hex);
	for (char *p=hex;*p;p++) {
		if(*p>='a') {
			*p-='a'-'A';
		}
	}   
    return;
}

void GetDecimalFromUser(unsigned int *x) {
    // write your code here to complete this function
	printf("Please enter decimal number here: ");
    scanf("%u", x);
	// note that x here is pointer
    return;
}

void ConvertBinaryToDecimal(const char *binary, unsigned int *decimal) {
    // write your code here to complete this function
	int currentDigit;
	double len;
	len = strlen(binary); // number of digits
	len--; // -1 since the right most digit maps to 2^0 

	// set initial value 0
	*decimal = 0;

	for (char *p=binary;*p;p++) {
		// from left(large) to right(small)
		currentDigit = *p-48; // for 0 48-48, for 1 49-48 
		*decimal+=currentDigit * pow(2, len); // +2^(number of digits) if currentDigit!=0
		len--; // number of digits-- 
	}
	return;
}

void ConvertHexToDecimal(const char *hex, unsigned int *decimal) {
    // write your code here to complete this function
	int currentDigit, len;
	len = strlen(hex);
	len--;
	*decimal = 0;

	// the logic is similar with binary tranformation 
	for (char *p=hex;*p;p++) {
		// not transform prefix
		if (*p == 'X' || (*p == '0' && len == strlen(hex)-1)) {
			len--;
			continue;
		}
		
		// 0-9
		if (!isalpha(*p)) {
			currentDigit = *p-48; // use ascii to map decimal value
		}
		// 10-15
		else {
			currentDigit = *p-55; 
		}
		*decimal+=currentDigit * pow(16, len);
		len--;
	}
	return;
}

void ConvertDecimalToBinary(const unsigned int decimal, char *binary) {
    // write your code here to complete this function
    // do not include leading zeros in the answer
    // if the input is 0 then the output should be just 0
	// for (int i=0;i<sizeof(binary);i++) printf("%c \n", binary[i]);
	int remainder, len;  
	unsigned int copy_decimal = decimal; // const cannot be assigned so use copy to manipulate
	len = (int)(log10(decimal) / log10(2))+1; // log2 = logy (x) / logy (2)

	// set initial container empty
	for (char *p=binary;*p;p++) {
		*p=NULL;
	}

	for (int i=0;copy_decimal>0;i++) {
		remainder = copy_decimal % 2;
		// the value is written in reverse order
		binary[len-1] = (char)(remainder+48); 
		copy_decimal = copy_decimal / 2; // decrement after the current digit is addressed
		len--; // number of digits--
	}
    return;
}

void ConvertDecimalToHex(const unsigned int decimal, char *hex) {
    // write your code here to complete this function
    // do not include leading zeros in the answer
    // do include the prefix 0x
    // if the input is 0 then the output should be 0x0 
	int remainder, len; 
	unsigned int copy_decimal=decimal; // const cannot be assigned so use copy to manipulate 
	len = (int)(log10(decimal) / log10(16))+1; // log16 (x) = logy (x) / logy (16)

	for (char *p=hex;*p;p++) {
		*p=NULL;
	}			
	// prefix should always be added
	len+=2;
	hex[0]='0';
	hex[1]='X';
	// 0x can be put in front of FFFFFFFF (max of unsigned 4294967295) 
	
	// the logic is similar with binary tranformation 
	for (int i= 0;copy_decimal>0;i++) {
		remainder = copy_decimal % 16;

		// 0-9
		if (remainder<=9) {
			hex[len-1] = (char)(remainder+48); 
		} 
		// 10-15
		else {
			hex[len-1] = (char)(remainder+55);
		}
		copy_decimal = copy_decimal / 16; 
		len--; 
	}
    return;
}