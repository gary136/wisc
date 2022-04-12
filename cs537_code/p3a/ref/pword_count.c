#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <pthread.h>
#include <sys/types.h>
#define _GNU_SOURCE
#include <unistd.h>
#include <sys/syscall.h>

// define hash table
#define SIZE 99 // to pass the test
#define BUFSIZE 64 // for size content
#define THREADS 32 // to pass the test

// pid_t
// gettid(void)
// {
//     return syscall(SYS_gettid);
// }

typedef struct charCount { 
   char c; 
   int count; 
} charCount;

typedef struct hashItem {
    charCount* data;  
    int key;
} hashItem;

// typedef struct threadHash {
//     pid_t tid; 
//     hashItem* hashArray[SIZE]; // array of item
// } threadHash;

hashItem** hashArrayFromThreads[THREADS]; 
hashItem* hashArray[SIZE]; // array of item
hashItem* item; // single hash representing ascki code of arbitrary word and word's data
charCount* cnt; // data of item
int threadId = 0;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

int hashCode(int key) {
    int res = key % SIZE;
    return res;
}

hashItem *get(int key) { // input an int to get a hashItem   
   int hashIndex = hashCode(key); // get the hash 
   while(hashArray[hashIndex] != NULL) { // move in array until an empty
      if(hashArray[hashIndex]->key == key)
         return hashArray[hashIndex]; 			      
      ++hashIndex; // go to next cell      
      hashIndex %= SIZE; // wrap around the table
   }  
   return NULL;        
}
void put(int key, char c) { // put a key and corresponding data into hashItem array
    int hashIndex = hashCode(key); // get the hash
    if (get(key) != NULL) {
        // printf("key already occupied, hashArray[%d]+1\n", hashIndex);
        hashArray[hashIndex]->data->count += 1;
    }
    else { 
        cnt = (charCount*) malloc(sizeof(charCount));
        cnt->c = c;
        cnt->count = 1;  
        item = (hashItem*) malloc(sizeof(hashItem));  
        item->key = key;
        item->data = cnt;  
        // linear probing   
        while(hashArray[hashIndex] != NULL) { // move in array until an empty or deleted cell
            ++hashIndex;// go to next cell            
            hashIndex %= SIZE; // wrap around the table
        }	
        hashArray[hashIndex] = item;       
    }
}

// for debug
void display(int threadId) {
    // hashArrayFromThreads[threadId]
   for(int i = 0; i<SIZE; i++) {	
      if(hashArrayFromThreads[threadId][i] != NULL)
         printf("%c%d",hashArrayFromThreads[threadId][i]->data->c, hashArrayFromThreads[threadId][i]->data->count);
   }
   printf("\n");
}

void writeFile() {  
    FILE *fp;
    char *buf, *dest; 
    // char dest[1000];
    dest = (char *)malloc(1024 * sizeof(char));
    strcpy(dest, "");
    fp = fopen("file.z", "w");
    for(int i = 0; i<SIZE; i++) {	
        buf = (char *) malloc(BUFSIZE * sizeof(char)); /* define BUFSIZE before */
        if(hashArray[i] != NULL)
            // concatenate
            sprintf(buf, "%c%d", hashArray[i]->data->c, hashArray[i]->data->count);
            strcat(dest, buf); 
    }
    printf("%s\n", dest);
    fwrite(dest, 1, strlen(dest), fp);
    fclose(fp);
}
bool file_exists(const char *filename) // check file exists
{
    FILE *fp = fopen(filename, "r");
    bool is_exist = false;
    if (fp != NULL) {is_exist = true;}
    fclose(fp); // close the file
    return is_exist;
}
void* zip(void* fileName) {
    // struct hashItem* hashArray[SIZE]; 
    // struct hashItem* item;
    // charCount* cnt;
    // printf("%d\n", gettid());
    char *line_buf;
    FILE *fp;
    size_t line_buf_size = 0;
    ssize_t line_size;

    /* check file exists */
    if (file_exists(fileName)) {
        fp = fopen(fileName , "r+");
        line_size = getline(&line_buf, &line_buf_size, fp);
        while (line_size >= 0){
            size_t len = strlen(line_buf);
            // line_buf[strcspn(line_buf, "\n")] = 0;
            if (len > 0 && line_buf[len-1] == '\n') { /* trim */
                len-=2;
                line_buf[len] = '\0';
            }
            // put or update cnt in hash
            for (int i=0; i<len; i++){
                char tmp = line_buf[i];   
                if (tmp!='\0')
                    put((int)tmp, tmp);                
            }
            /* Get the next line */
            line_size = getline(&line_buf, &line_buf_size, fp);
        }
        /* Free the allocated line buffer */
        free(line_buf);
        line_buf = NULL;    
        fclose(fp); // close the file    
   }
//     display();
    // writeFile();
    pthread_mutex_lock(&mutex);
    hashArrayFromThreads[threadId] = hashArray;
    display(threadId);
    printf("threadId = %d\n", threadId);
    threadId++;  
    printf("threadId = %d\n", threadId);
    // display(threadId);
    pthread_mutex_unlock(&mutex);  
    return NULL;
}

int main(int argc, char *argv[]) {
    // fileName = (char *fileName) malloc(sizeof(char *fileName));
    // fileName = argv[1];
    // printf("%s", fileName);
    // pthread_t p1;
    // for int i; i<argc; i++
    int nprocs = argc-1;
    pthread_t pthreads[nprocs];
    // pthread_t p1, p2;
    printf("main: begin\n");

    for (int i = 0; i < nprocs; i++) {
        pthread_create(&pthreads[i], NULL, zip, argv[i+1]);
    }
    for (int i = 0; i < nprocs; i++) {
        pthread_join(pthreads[i], NULL);
        printf("i=%d, threadId = %d\n",i , threadId);
    }
        display(0);
        display(1);
    // pthread_create(&p1, NULL, zip, argv[1]);
    // pthread_join(p1, NULL);
    // display();
    // pthread_create(&p2, NULL, zip, argv[2]);
    // pthread_join(p2, NULL);
    // display();
    printf("main: end\n");
    return 0;
}