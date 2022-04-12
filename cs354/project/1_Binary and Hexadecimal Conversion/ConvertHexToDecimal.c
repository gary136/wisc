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