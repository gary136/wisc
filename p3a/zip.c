#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <pthread.h>

// define hash table
#define MAXBUFR 100000 // to pass the test
#define TMPBUFR 8 // to pass the test

int pcnt = 0;
int ccnt = 0;
char *buf, *tmp;
char prev = '\0'; 
char curr = '\0';
// check file exists
bool file_exists(const char *filename) {
    FILE *fp = fopen(filename, "r");
    bool is_exist = false;
    if (fp != NULL){ is_exist = true; }
    fclose(fp); // close the file
    return is_exist;
}

void zip(char *fileName) {    
    buf = (char *)malloc(MAXBUFR * sizeof(char));
    strcpy(buf, "");
    int len;
    char *line_buf;
    FILE *fp;
    size_t line_buf_size = 0;
    ssize_t line_size;

    // check file exists
    if (file_exists(fileName)) {
        fp = fopen(fileName , "r+");
        line_size = getline(&line_buf, &line_buf_size, fp);
        while (line_size >= 0){
            size_t len = strlen(line_buf);
            line_buf[strcspn(line_buf, "\n")] = 0;
            printf("new: %s\n", line_buf);
            // len--;
            // if (len > 0 && line_buf[len-1] == '\n') {/* trim */
            //     len-=2;
            //     line_buf[len] = '\0';
            // }
            for (int i=0; i<len; i++) {
                if (curr!=line_buf[i]) { // new  
                    // printf("find new word, new: %c | buf: %s | ccnt: %d | prev: %c | curr: %c\n", line_buf[i], buf, ccnt , prev, curr);
                    if (prev!='\0') { 
                        tmp = (char *)malloc(TMPBUFR * sizeof(char));
                        sprintf(tmp, "%d", pcnt);
                        // printf("concatenate previous count, new: %c | prev: %c | buf: %s | curr: %c\n", line_buf[i], prev, buf, curr);
                        strcat(buf, tmp);
                        strncat(buf, &prev, 1); // concatenate prev and ccnt of prev
                        // printf("concatenate previous word, new: %c | buf: %s | ccnt: %d | prev: %c | curr: %c\n", line_buf[i], buf, ccnt , prev, curr);
                    }
                    // printf("aftr new: %c | buf: %s | ccnt: %d | prev: %c | curr: %c\n", line_buf[i], buf, ccnt , prev, curr);
                    prev = curr;
                    pcnt = ccnt; // keep previous record
                    
                    ccnt = 1;  
                    curr = line_buf[i]; 
                    // printf("aftr new: %c | buf: %s | ccnt: %d | prev: %c | curr: %c\n", line_buf[i], buf, ccnt , prev, curr);
                }else {
                    ccnt++;
                    // printf("aftr new: %c | buf: %s | ccnt: %d | prev: %c | curr: %c\n", line_buf[i], buf, ccnt , prev, curr);
                }                 
            }                 
            // printf("buf\n"); 
            // printf("%s\n", buf);      
            line_size = getline(&line_buf, &line_buf_size, fp); /* Get the next line */
        }        
        if (prev!='\0') { 
            tmp = (char *)malloc(TMPBUFR * sizeof(char));
            sprintf(tmp, "%d", pcnt);
            strcat(buf, tmp);
            strncat(buf, &prev, 1); 
        }
        if (curr!='\0') { 
            tmp = (char *)malloc(TMPBUFR * sizeof(char));
            sprintf(tmp, "%d", ccnt);
            strcat(buf, tmp);
            strncat(buf, &curr, 1); 
        }
        free(line_buf); /* Free the allocated line buffer */
        line_buf = NULL;        
   }else {
        printf("file not exists\n");
        // fp = fopen(fileName , "w+");
    }
    printf("final %s\n", buf);
}

int main(int argc, char *argv[]) {
    // pthread_t p1, p2;
    // int rc;
    // printf("main: begin\n");

    // Pthread_create(&p1, NULL, zip, argv[1]);
    // Pthread_create(&p2, NULL, zip, argv[2]);
    // Pthread_join(p1, NULL);
    zip(argv[1]);
    // Pthread_join(p2, NULL);
    // printf("main: end\n");
    free(buf);
    return 0;
}