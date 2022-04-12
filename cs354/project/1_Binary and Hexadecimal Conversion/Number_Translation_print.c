#include <stdio.h>
#include <ctype.h>
#include <math.h>
#include <string.h>
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
	printf("Please enter binary string here: ");
    scanf("%s", binary);
	// no need to transform alphabet
    return;
}

void GetHexFromUser(char * hex) {
    // this function has been written for you
    printf("Please enter hex string here (do include the 0x prefix): ");
    scanf("%s", hex);

	// printf("the hex number is %c %c %c %c\n", hex[0] ,hex[1] ,hex[2] ,hex[3]);
	// printf("the hex number is %u %u %u %u\n", hex[0] ,hex[1] ,hex[2] ,hex[3]);
	// printf("the hex number is %x %x %x %x\n", hex[0] ,hex[1] ,hex[2] ,hex[3]);
	// for (int i=0; i<sizeof(hex); i++) {
	// 	printf("the hex[%d] is %c\n", i, hex[i]);
	// }
	// printf("the hex number is %p %p %p %p\n", &hex[0] ,&hex[1] ,&hex[2] ,&hex[3]);
	// printf("p conversion \n");
	for (char *p=hex;*p;p++) {
		// printf("the hex here means %c %u %x %p\n", hex, hex, hex, &hex);
		// printf("the *p here means %c %u %x %p\n", *p, *p, *p, &*p);
		printf("p -> ");
		if(*p>='a') {
			// printf("%c %u %x %p -> ", *p, *p, *p, &*p);
			*p-='a'-'A';
			// printf("%c %u %x %p\n", *p, *p, *p, &*p);
		}
		// else {
		// 	printf("%c \n", *p);
		// }
		printf("%c %u %x %p \n", *p, *p, *p, &*p);
	}  // to upper case
	// printf("the hex number is %c %c %c %c\n", hex[0] ,hex[1] ,hex[2] ,hex[3]);
	// printf("the hex number is %u %u %u %u\n", hex[0] ,hex[1] ,hex[2] ,hex[3]);
	// printf("the hex number is %x %x %x %x\n", hex[0] ,hex[1] ,hex[2] ,hex[3]);
	// for (int i=0; i<sizeof(hex); i++) {
	// 	printf("the hex[%d] is %c\n", i, hex[i]);
	// }
	// printf("the hex number is %p %p %p %p\n", &hex[0] ,&hex[1] ,&hex[2] ,&hex[3]);
	
    return;
}

void GetDecimalFromUser(unsigned int *x) {
    // write your code here to complete this function
	printf("Please enter decimal string here : ");
    scanf("%u", x);
	// note that x here is pointer
    return;
}

void ConvertBinaryToDecimal(const char *binary, unsigned int *decimal) {
    // write your code here to complete this function
	int currentDigit;
	double len;
	len = strlen(binary);
	len--;
	*decimal = 0;

	for (char *p=binary;*p;p++) {
		currentDigit = *p-48;
		printf("%d %f \n", currentDigit, currentDigit * pow(2.0, len));
		*decimal+=currentDigit * pow(2, len);
		len--;
	}
    // *decimal = 32; // This hardcoded answer will be incorrect for all but one input. Change this line!
	return;
}

void ConvertHexToDecimal(const char *hex, unsigned int *decimal) {
    // write your code here to complete this function
	int currentDigit, len;
	len = strlen(hex);
	len--;
	*decimal = 0;

	for (char *p=hex;*p;p++) {
		// pass prefix
		printf("%p %c ", p, *p);
		if (*p == 'X' || (*p == '0' && len == strlen(hex)-1)) {
			len--;
			printf("\n");
			continue;
		}
		
		// 0-9
		if (!isalpha(*p)) {
			currentDigit = *p-48;
		}
		// 10-15
		else {
			currentDigit = *p-55;
		}
		printf("%d %f \n", currentDigit, currentDigit * pow(16, len));
		*decimal+=currentDigit * pow(16, len);
		len--;
	}
	// *decimal = 32; // This hardcoded answer will be incorrect for all but one input. Change this line!
	return;
}

void ConvertDecimalToBinary(const unsigned int decimal, char *binary) {
    // write your code here to complete this function
    // do not include leading zeros in the answer
    // if the input is 0 then the output should be just 0
	// for (int i=0;i<sizeof(binary);i++) printf("%c \n", binary[i]);

	for (char *p=binary;*p;p++) {
		printf("%c %p -> ", *p, p);
		*p=NULL;
		printf("%c %p\n", *p, p);
	}

	int remainder, len;  
	int copy_decimal=decimal; // const cannot be assigned so use copy to manipulate
	len = (int)(log10(decimal) / log10(2))+1; // log2
	printf("len = %d\n", len);

	// for (int i=0;i<sizeof(binary);i++) printf("%c \n", binary[i]);

	for (int i=0;copy_decimal>0;i++) {
		remainder = copy_decimal % 2;
		binary[len-1] = (char)(remainder+48); // see ascii code
		copy_decimal = copy_decimal / 2;
		printf("remainder = %d, binary[%d] = %c, copy_decimal = %d\n"
			, remainder, len-1, binary[len-1], copy_decimal);
		len--;
	}
    // binary = "11101"; // This hardcoded answer will be incorrect for all but one input. Change this line!
    return;
}
void ConvertDecimalToHex(const unsigned int decimal, char *hex) {
    // write your code here to complete this function
    // do not include leading zeros in the answer
    // do include the prefix 0x
    // if the input is 0 then the output should be 0x0 
	for (char *p=hex;*p;p++) {
		printf("%c %p -> ", *p, p);
		*p=NULL;
		printf("%c %p\n", *p, p);
	}









	char *prefix = "0x";
	if (decimal==0) hex = "0x0";

	int remainder, len; 
	int copy_decimal=decimal; // const cannot be assigned so use copy to manipulate 
	len = (int)(log10(decimal) / log10(16))+1; // log16 (x) = logy (x) / logy (16)
	printf("%d\n", len);

	for (int i= 0;copy_decimal>0;i++) {
		remainder = copy_decimal % 16;
		if (remainder<=9) {
			hex[len-1] = (char)(remainder+48);
		} // see ascii code
		else {
			hex[len-1] = (char)(remainder+55);
		}
		copy_decimal = copy_decimal / 16;
		printf("remainder = %d, hex[%d] = %c, copy_decimal = %d\n"
			, remainder, len-1, hex[len-1], copy_decimal);
		len--;
	}
    // hex = "0xF3"; // This hardcoded answer will be incorrect for all but one input. Change this line!
    return;
}










