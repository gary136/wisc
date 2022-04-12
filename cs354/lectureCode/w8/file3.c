#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
 
int main()
{
    int fd = open("testfile.txt", O_CREAT | O_WRONLY | O_APPEND, S_IRWXU);

    if(fd < 0) {
        fprintf(stderr, "Error");
        return 1;
    }
 
    write(fd, "Hello World\n", 13);
 
    close(fd);

    return 0;
}
