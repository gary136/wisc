// assume that string_copy below is a function that tries to copy a given string
// this code has a bug in it.
// assume main declares a string and calls string_copy  
// pause the video and try to identify the bug.
// use the man pages for strlen and strcpy if needed
// assume the pointer allocated with malloc is freed in main
#include <stdlib.h>
#include <string.h>

char* string_copy(char *s) {
    char *d = malloc(strlen(s)); 
    // better version
    // malloc((strlen(s)+1)*sizeof(char))
    if (d==NULL)
        return NULL;
    strcpy(d,s);
    return d;
}


int main() {
    //
    // code
    //
    return 0;
}
