#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <pthread.h>

// define hash table
#define MAXBUFR 100000 // to pass the test
#define SIZE 2 // to pass the test
#define TMPBUFR 8 // to pass the test
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;


// int cnts[MAXBUFR];
// int *cnts;
// int *foo = malloc(sizeof *foo * N);

// char *buf;
// char *buf, *tmp;

// int threadId = 0;
// char **bufcontainer;
// int **cntscontainer;

typedef struct threadInfo { 
    int threadId; 
    int *cnts;
    char *buf;
} threadInfo;

threadInfo* thrdcontainer[SIZE];

typedef struct argf{ 
    char *fileName; 
    int threadId;
} argf;

// check file exists
bool file_exists(const char *filename) {
    FILE *fp = fopen(filename, "r");
    bool is_exist = false;
    if (fp != NULL){ is_exist = true; }
    fclose(fp); // close the file
    return is_exist;
}

void* zip(void *x) {   
    char prev = '\0'; 
    char curr = '\0'; 
    int latestsize = 0;
    int pcnt = 0;
    int ccnt = 0;
    argf* a = (argf *)x;
    threadInfo* tinfo;
    tinfo = (threadInfo *)malloc(sizeof(threadInfo));  
    tinfo->threadId = a->threadId; 
    // thrdcontainer[threadId] = tinfo;
    // cnts = (int *)malloc(MAXBUFR * sizeof(int));
    tinfo->cnts = (int *)malloc(MAXBUFR * sizeof(int));
    // buf = (char *)malloc(MAXBUFR * sizeof(char));
    tinfo->buf = (char *)malloc(MAXBUFR * sizeof(char));
    // strcpy(buf, "");
    strcpy(tinfo->buf, "");
    printf("tid: %d\n", tinfo->threadId);

    char *line_buf;
    FILE *fp;
    size_t line_buf_size = 0;
    ssize_t line_size;

    // check file exists
    if (file_exists(a->fileName)) {
        
        fp = fopen(a->fileName , "r+");
        line_size = getline(&line_buf, &line_buf_size, fp);
        while (line_size >= 0){
            size_t len = strlen(line_buf);
            line_buf[strcspn(line_buf, "\n")] = 0;
            // printf("new: %s\n", line_buf);
            // len--;
            // if (len > 0 && line_buf[len-1] == '\n') {/* trim */
            //     len-=2;
            //     line_buf[len] = '\0';
            // }
            for (int i=0; i<len; i++) {
                if (curr!=line_buf[i]) { // new  
                    if (prev!='\0') { 
                        // tmp = (char *)malloc(TMPBUFR * sizeof(char));
                        // sprintf(tmp, "%d", pcnt);
                        // strcat(tinfo->buf, tmp);
                        tinfo->cnts[latestsize] = pcnt; // record pcnt
                        // strncat(tinfo->buf, &prev, 1); // record prev
                        tinfo->buf[latestsize] = prev; // record prev
                        latestsize++; 
                    }
                    prev = curr;
                    pcnt = ccnt; // keep previous record
                    
                    ccnt = 1;  
                    curr = line_buf[i]; 
                }else {
                    ccnt++;
                }                 
            }              
            line_size = getline(&line_buf, &line_buf_size, fp); /* Get the next line */
        }        
        if (prev!='\0') { 
            tinfo->cnts[latestsize] = pcnt; // record pcnt
            // strncat(tinfo->buf, &prev, 1); // record prev 
            tinfo->buf[latestsize] = prev; // record prev
            latestsize++; 
        }
        if (curr!='\0') { 
            // tmp = (char *)malloc(TMPBUFR * sizeof(char));
            // sprintf(tmp, "%d", ccnt);
            // strcat(tinfo->buf, tmp);
            // strncat(tinfo->buf, &curr, 1); 
            tinfo->cnts[latestsize] = pcnt; // record pcnt
            // strncat(tinfo->buf, &prev, 1); // record prev 
            tinfo->buf[latestsize] = prev; // record prev
            latestsize++; 
        }
        free(line_buf); /* Free the allocated line buffer */
        line_buf = NULL;        
   }else { printf("file not exists\n");}
    // printf("final %s\n", tinfo->buf);
    // bufcontainer[threadId]=tinfo->buf;
    // tinfo->cnts = cnts;
    // tinfo->buf = buf;
    pthread_mutex_lock(&mutex);
    printf("t:%d\n", tinfo->cnts[0]);
    thrdcontainer[a->threadId] = tinfo;
    // threadId++;
    pthread_mutex_unlock(&mutex);
    return NULL;
}

void display(int threadId, int leng) {
    printf("%p %d\n", thrdcontainer[threadId], thrdcontainer[threadId]->threadId);
    printf("cnts: ");
    for (int i=0; i<leng; i++)
        printf("%d", thrdcontainer[threadId]->cnts[i]);
    printf("\nchrs: ");
    for (int i=0; i<leng; i++)
        printf("%c", thrdcontainer[threadId]->buf[i]);
    printf("\n");
}

int main(int argc, char *argv[]) {
    int nprocs = argc-1;
    pthread_t pthreads[nprocs];
    argf* x;
    printf("main: begin\n");
    for (int i = 0; i < nprocs; i++) {
        x = (argf *)malloc(sizeof(argf));
        x->fileName = argv[i+1];
        x->threadId = i;
        pthread_create(&pthreads[i], NULL, zip, x);
    }
    for (int i = 0; i < nprocs; i++) {
        pthread_join(pthreads[i], NULL);
    }
    for (int i=0; i<nprocs; i++) {
        display(i, 15);
    }
    // free(cnts);
    // free(buf);
    printf("main: end\n");
    return 0;
}