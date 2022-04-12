#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <pthread.h>

// define hash table
#define SIZE 99 // to pass the test
#define BUFSIZE 64 // for size content

typedef struct charCount { 
   char c; 
   int count; 
} charCount;
struct hashItem { 
   int key;
   charCount* data;  
};

// struct hashItem* hashArray[SIZE]; 
// struct hashItem* item;
// struct charCount* cnt;
// char *fileName;

int hashCode(int key) {
    int res = key % SIZE;
    return res;
}

struct hashItem *get(int key) { // input an int to get a hashItem   
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
        cnt = (struct charCount*) malloc(sizeof(struct charCount));
        cnt->c = c;
        cnt->count = 1;  
        item = (struct hashItem*) malloc(sizeof(struct hashItem));  
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
   }
   printf("\n");
}

void write() {  
    FILE *fp;
    char *buf, *dest; 
    // char dest[1000];
    dest = (char *)malloc(1024 * sizeof(char));
    strcpy(dest, "");
    fp = fopen("file.z", "w");
    for(int i = 0; i<SIZE; i++) {	
        // char str[100];
        buf = (char *) malloc(BUFSIZE * sizeof(char)); /* define BUFSIZE before */
        if(hashArray[i] != NULL)
            // concatenate
            sprintf(buf, "%c%d", hashArray[i]->data->c, hashArray[i]->data->count);
            // snprintf(buf, BUFSIZE, "%c%d", hashArray[i]->data->c, hashArray[i]->data->count); 
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
    struct hashItem* hashArray[SIZE]; 
    struct hashItem* item;
    struct charCount* cnt;

    // printf("%s\n", fileName);
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
            // /* Show the line details */
            // printf("line[%06d]: chars=%06zd, buf size=%06zu, contents: %s\n", 
            //     line_count, len, line_buf_size, line_buf);

            // put or update cnt in hash
            for (int i=0; i<len; i++){
                char tmp = line_buf[i];   
                // if (tmp!='\n' && tmp!='\0') { 
                //     // item = get(key); // check already in hash
                //     // if (item==NULL){
                //     //     printf("[%c] not found, put\n", tmp);
                //     // } 
                //     put(key, tmp);                                          
                // } 
                // else{
                //     printf("new line\n");
                // } 
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
//    else {printf("file not exists\n");}
//     display();
    // write();
    return NULL;
}

int main(int argc, char *argv[]) {
    // fileName = (char *fileName) malloc(sizeof(char *fileName));
    // fileName = argv[1];
    // printf("%s", fileName);
    // pthread_t p1;
    pthread_t p1, p2;
    printf("main: begin\n");

    pthread_create(&p1, NULL, zip, argv[1]);
    pthread_create(&p2, NULL, zip, argv[2]);
    pthread_join(p1, NULL);
    display();
    // zip(argv[1]);
    pthread_join(p2, NULL);
    display();
    printf("main: end\n");
    return 0;
}