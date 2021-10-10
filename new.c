#include <stdlib.h>
#include <string.h>
#include <stdio.h>

// const char *tmp = "This string literal is arbitrary";

void translate(char* syscall, char* res) {
	FILE *cmd=popen(syscall, "r");
    // char res[24]={0x0};
    // while (fgets(res, sizeof(res), cmd) !=NULL)
    //        printf("res: %s\n", res);
    fgets(res, sizeof(res), cmd);  
    printf("inside res: %s\n", res);
    pclose(cmd);
	
	// return res;
}

int main(int argc, char *argv[]){
    char *ret;
    ret = (char *)malloc(24 * sizeof(char));
    // size_t bufsize = 32;
    // int characters = getline(&ret, &bufsize, system ("which ls"));
    // // ret = strstr(tmp, " ");
    // // if (ret)
    // //     printf("found substring %s\n", ret);
    // // else
    // //     printf("no substring found!\n");
    // printf("location: %s\n", ret);

    translate("which ls", ret);

    printf("out location: %s\n", ret);
    translate("ls -al", ret);
    exit(EXIT_SUCCESS);
}