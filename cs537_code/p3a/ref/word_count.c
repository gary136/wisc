#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <pthread.h>
// #include "common.h"
// #include "common_threads.h"
// #include<assert.h>
// #include<sys/stat.h>
// #include<fcntl.h>
// #include<unistd.h>
// #include<sys/mman.h>

// define hash table
#define SIZE 99 // to pass the test

typedef struct charCount { 
   char c; 
   int count; 
} charCount;

struct hashItem { 
   int key;
   charCount* data;  
};

struct hashItem* hashArray[SIZE]; 
struct hashItem* item;
struct charCount* cnt;
// char *fileName;

int hashCode(int key) {
    int res = key % SIZE;
    return res;
}

struct hashItem *get(int key) { // input an int to get a hashItem
   // get the hash 
   int hashIndex = hashCode(key);  	
   //move in array until an empty 
   while(hashArray[hashIndex] != NULL) {	
      if(hashArray[hashIndex]->key == key)
         return hashArray[hashIndex]; 			
      //go to next cell
      ++hashIndex;		
      //wrap around the table
      hashIndex %= SIZE;
   }  
   return NULL;        
}

void put(int key, char c) { // put a key and corresponding data into hashItem array
    cnt = (struct charCount*) malloc(sizeof(struct charCount));
    cnt->c = c;
    cnt->count = 1;  
    item = (struct hashItem*) malloc(sizeof(struct hashItem));

    //get the hash 
    int hashIndex = hashCode(key);
    item->key = key;
    item->data = cnt;  

    if (get(key) != NULL) {
        // printf("key already occupied, hashArray[%d]+1\n", hashIndex);
        hashArray[hashIndex]->data->count += 1;
    }
    else {
        // linear probing
        // move in array until an empty or deleted cell
        while(hashArray[hashIndex] != NULL) {
            //go to next cell
            ++hashIndex;		
            //wrap around the table
            hashIndex %= SIZE;
        }	
        hashArray[hashIndex] = item;       
    }
}

// struct hashItem* delete(struct hashItem* item) {
//    int key = item->key;
//    //get the hash 
//    int hashIndex = hashCode(key);
//    //move in array until an empty
//    while(hashArray[hashIndex] != NULL) {	
//       if(hashArray[hashIndex]->key == key) {
//          struct hashItem* temp = hashArray[hashIndex]; 
//          hashArray[hashIndex] = NULL;
//          return temp;
//       }		
//       //go to next cell
//       ++hashIndex;		
//       //wrap around the table
//       hashIndex %= SIZE;
//    }      	
//    return NULL;        
// }

// for debug
void display() {
   for(int i = 0; i<SIZE; i++) {	
      if(hashArray[i] != NULL)
         printf("%c%d",hashArray[i]->data->c, hashArray[i]->data->count);
    //   else
        //  printf(" ~~ ");
   }
   printf("\n");
}

// check file exists
bool file_exists(const char *filename)
{
    FILE *fp = fopen(filename, "r");
    bool is_exist = false;
    if (fp != NULL)
    {
        is_exist = true;
        fclose(fp); // close the file
    }
    return is_exist;
}

void zip(char *fileName) {
    // printf("%s\n", fileName);
    int key, len;
    char *line_buf;
    char tmp;
    FILE *fp;
    size_t line_buf_size = 0;
    int line_count = 0;
    ssize_t line_size;

    // check file exists
    if (file_exists(fileName)) {
        fp = fopen(fileName , "r+");
        line_size = getline(&line_buf, &line_buf_size, fp);
        while (line_size >= 0){
            // /* trim */
            size_t len = strlen(line_buf);
            // line_buf[strcspn(line_buf, "\n")] = 0;
            if (len > 0 && line_buf[len-1] == '\n') {
                len-=2;
                line_buf[len] = '\0';
            }
            // /* Show the line details */
            // printf("line[%06d]: chars=%06zd, buf size=%06zu, contents: %s\n", 
            //     line_count, len, line_buf_size, line_buf);
            for (int i=0; i<len; i++){
                tmp = line_buf[i];    
                key = (int)tmp;
                // printf("i=%d, tmp=%c, key=%d\n", i, tmp, key);
                if (tmp!='\n' && tmp!='\0') { 
                    item = get(key); // check already in hash
                    if (item==NULL){
                        // printf("[%c] not found, put\n", tmp);
                    } 
                    put(key, tmp);                                          
                } else{
                    printf("new line\n");
                }                 
            }
            /* Get the next line */
            line_size = getline(&line_buf, &line_buf_size, fp);
        }
        /* Free the allocated line buffer */
        free(line_buf);
        line_buf = NULL;        
   }else {
        // printf("file not exists\n");
        fp = fopen(fileName , "w+");
    }
    // printf("show initiate hash\n");
    display();
}

int main(int argc, char *argv[]) {
    // fileName = (char *fileName) malloc(sizeof(char *fileName));
    // fileName = argv[1];
    // printf("%s", fileName);
    pthread_t p1, p2;
    int rc;
    printf("main: begin\n");

    Pthread_create(&p1, NULL, zip, argv[1]);
    Pthread_create(&p2, NULL, zip, argv[2]);
    Pthread_join(p1, NULL);
    // zip(argv[1]);
    Pthread_join(p2, NULL);
    printf("main: end\n");
    return 0;
}