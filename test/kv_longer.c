#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

// define hash table
#define SIZE 20
struct hashItem { 
   int key;
   char* data;  
};

struct hashItem* hashArray[SIZE]; 
struct hashItem* item;
// struct hashItem* dummyItem;

int hashCode(int key) {
    int res = key % SIZE;
    // printf("key:%d -> res:%d\n",key,res);
    return res;
}

struct hashItem *get(int key) { // input an int to get a hashItem
   // get the hash 
   int hashIndex = hashCode(key);  	
   if (key!=hashIndex) printf("key:%d -> hashIndex:%d\n",key,hashIndex);
   //move in array until an empty 
   while(hashArray[hashIndex] != NULL) {	
      if(hashArray[hashIndex]->key == key)
         return hashArray[hashIndex]; 			
      //go to next cell
      ++hashIndex;		
      //wrap around the table
      hashIndex %= SIZE;
   }  
//    if (hashArray[hashIndex] != NULL) return hashArray[hashIndex];
   return NULL;        
}

void put(int key, char* data) {    
    item = (struct hashItem*) malloc(sizeof(struct hashItem));
    //get the hash 
    int hashIndex = hashCode(key);
    item->key = key;
    item->data = data;  

    if (get(key) != NULL) {
        printf("write new value in old key\n");
        hashArray[key] = item; 
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
        // printf("final key:%d, value:%s\n",hashIndex,data);
        hashArray[hashIndex] = item;        
        printf("put (%d,%s)\n", key, data);
    }
}

struct hashItem* delete(struct hashItem* item) {
   int key = item->key;
   //get the hash 
   int hashIndex = hashCode(key);
   //move in array until an empty
   while(hashArray[hashIndex] != NULL) {	
      if(hashArray[hashIndex]->key == key) {
         struct hashItem* temp = hashArray[hashIndex]; 			
         //assign a dummy item at deleted position
        //  dummyItem = (struct hashItem*) malloc(sizeof(struct hashItem)); 
        //  dummyItem->key = -1; 
        //  dummyItem->data = "-1"; 
        //  hashArray[hashIndex] = dummyItem; 
         hashArray[hashIndex] = NULL;
         return temp;
      }		
      //go to next cell
      ++hashIndex;		
      //wrap around the table
      hashIndex %= SIZE;
   }      	
   return NULL;        
}

void display() {
   for(int i = 0; i<SIZE; i++) {	
      if(hashArray[i] != NULL)
         printf(" (%d,%s)",hashArray[i]->key,hashArray[i]->data);
      else
         printf(" ~~ ");
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

int main(int argc, char *argv[]) {
    // printf("number of arguments: %d\n", argc);

    int key, len;
    char *tmp, *tok, *line_buf, *value;
    FILE *fp;
    char *fileName = "file.txt";
    size_t line_buf_size = 0;
    int line_count = 0;
    ssize_t line_size;

    // innitiate db
    // dummyItem = (struct hashItem*) malloc(sizeof(struct hashItem));

    // display();
    // check file exists
    if (file_exists(fileName)) {
        printf("file exists\n");
        fp = fopen(fileName , "r+");
        /* Get the first line of the file. */
        line_size = getline(&line_buf, &line_buf_size, fp);
        /* Loop through until we are done with the file. */
        while (line_size >= 0){
            /* Increment our line count */
            line_count++;
            /* trim */
            size_t len = strlen(line_buf);
            if (len > 0 && line_buf[len-1] == '\n') line_buf[--len] = '\0';
            /* Show the line details */
            // printf("line[%06d]: chars=%06zd, buf size=%06zu, contents: %s\n", 
                // line_count, line_size, line_buf_size, line_buf);

            // put file into hash
            tmp = line_buf;          
            tok = strsep(&tmp, ","); // extract key and value  
            key = atoi(tok); // transform char to int
            // key = hashCode(atoi(tok)); // transform char to int
            len = strlen(tmp); // prepare allocating memory
            value = malloc(len+1); // needs to initiate pointer
            strcpy(value, tmp); // value = tmp; -> this will not work
            // printf("key=%d, ", key); // this becomes key
            // printf("value=%s ", value); // this becomes value
            // printf("\n");
            put(key, value);
            /* Get the next line */
            line_size = getline(&line_buf, &line_buf_size, fp);
        }
        /* Free the allocated line buffer */
        free(line_buf);
        line_buf = NULL;        
   }else {
        printf("file not exists\n");
        fp = fopen(fileName , "w+");
    }
    printf("show initiate db\n");
    display();

    // parse
    for (int i=1; i<argc; i++) {
        tmp = argv[i];
        printf("command = %s, len = %lu\n", tmp, strlen(tmp));
        
        // filter
        if ((tmp[0]!='g' || tmp[1]!=',' || atoi(&tmp[2])==0 || strlen(tmp)<3)
                && (tmp[0]!='p' || tmp[1]!=',' || atoi(&tmp[2])==0 || strlen(tmp)<5)  
                && (tmp[0]!='d' || tmp[1]!=',' || atoi(&tmp[2])==0 || strlen(tmp)<3)
                // && tmp[0]!='a'
                && (tmp[0]!='a' || strlen(tmp)!=1) 
                // && tmp[0]!='c'
                && (tmp[0]!='c' || strlen(tmp)!=1)
                ) 
            {
             printf("bad command\n");
             continue;
        }

        // get(read)
        if (tmp[0]=='g') { // use '' rather than ""
            // printf("=%d\n", toi(&tmp[2]));
            tok = strsep(&tmp, ","); // exctract command
            // printf("tmp=%s\n", tmp); 
            if (strstr(tmp, ",") != NULL) { 
                // contains
                printf("bad command\n");
            }else {
                key = atoi((tmp));
                int hashIndex = hashCode(key);
                item = get(key);
                if (item){
                    value = item->data;
                    printf("%d,%s\n", key, value);
                } else{
                    printf("%d not found\n", key);
                }
            }
        }

        // put(write)
        if (tmp[0]=='p' & tmp[1]==',') { // use '' rather than ""
            tok = strsep(&tmp, ","); // exctract command
            tok = strsep(&tmp, ","); // key
            // value = rest of tmp
            if (strstr(tmp, ",") != NULL) { 
                // contains
                printf("bad command\n");
            }else {
                key = atoi(tok);
                // char *xtmp = strdup(tmp); // value, a pointer to a null-terminated byte string
                len = strlen(tmp); // prepare allocating memory
                value = malloc(len+1); // needs to initiate pointer
                strcpy(value, tmp); // value = tmp; -> this will not work
                put(key, value);
                // while(tmp!=NULL) {
                    // tok = strsep(&tmp, ","); // extract key and value
                    // printf("%s ", tok);
                    // write
                    // fwrite(tok , 1 , sizeof(tok) , fp);
                // }
                // display();
                // printf("%s\n", xtmp);
                // put(key, value);
                // fwrite(xtmp, 1, sizeof(xtmp) ,fp);
                // fwrite("\n" , sizeof(char), 1, fp);
            }
        }

        // delete
        if (tmp[0]=='d' && tmp[1]==',') { // use '' rather than ""
            tok = strsep(&tmp, ","); // exctract command
            if (strstr(tmp, ",") != NULL) { 
                // contains
                printf("bad command\n");
            }else {
                key = atoi(tmp);
                // key = hashCode(atoi(tmp));
                item = get(key);
                if(item != NULL) {
                    printf("Element found: %d,%s\n", item->key, item->data);
                    delete(item);
                    printf("delete key %d\n", key);
                } else {
                    printf("%d not found\n", key);
                }
            }
        }

        // clear
        if (tmp[0]=='c') { // use '' rather than ""
            for (int i=0; i<SIZE; i++){
                // item = get(i);
                // if(item != NULL){
                //     hashArray[i]=NULL;
                // }
                hashArray[i]=NULL;
            }
        }

        // all
        if (tmp[0]=='a') { // use '' rather than ""
            for (int i=0; i<SIZE; i++){
                // item = get(i);
                // dont get since index may be different from key
                if(hashArray[i] != NULL){
                    printf("%d,%s\n", hashArray[i]->key, hashArray[i]->data);
                }
            }
        }
    }

    printf("show update db\n");
    display();
    // sync hash back to file
    fclose(fp);    
    fp = fopen(fileName , "w+");
    for (int i=0; i<SIZE; i++){
        // item = get(i);
        // dont get since index may be different from key
        if(hashArray[i] != NULL){
            // printf("found (%d,%s)\n", hashArray[i]->key, hashArray[i]->data);
            len = 99+strlen(hashArray[i]->data); // prepare allocating memory
            tmp = malloc(len); // needs to initiate pointer
            int output_len = snprintf(tmp, len
                , "%d,%s\n", hashArray[i]->key, hashArray[i]->data); // concatenate
            char *xtmp = strdup(tmp); // a pointer to a null-terminated byte string
            // printf("write: %s\n", xtmp);
            fwrite(tmp, sizeof(char), strlen(xtmp), fp);
        }
    }
    fclose(fp);  

    return 0;
}