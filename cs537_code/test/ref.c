#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>
#define TABLE_SIZE 1000

struct node
{
	char word[1000];
	int count;
	struct node *next;
};

struct node hashtable[TABLE_SIZE];

int main()
{
	char newword[1000],path[100];
	int i;
	FILE *fptr;
	
	printf("Enter file path: ");
    scanf("%s",&path);
    fptr = fopen(path, "r");
    if (fptr == NULL)
    {
        printf("Unable to open file.\n");
        exit(0);
    }
    
    // init hashtable
    for (i = 0; i < TABLE_SIZE; i++) {
    	hashtable[i].word[0] = '\0';
    	hashtable[i].count = 0;
    	hashtable[i].next = NULL;
	}
    
    int wordCursor = 0, sum = 0, key, wordCnt = 0;
	while (1)
	{
		char ch = fgetc(fptr);
		int c=ch;
		//printf("%d",c);
	//	printf("%c",ch);
		if (ch ==EOF) // on file end break the loop
			break;

		// find the word boundary with other than
		if (isalpha(ch) || isdigit(ch)) {
			if (wordCursor < 1000-1) {
				newword[wordCursor++] = ch;
				sum=sum+c;
			}
			continue;
		} else newword[wordCursor] = '\0';	// null byte termination of word
		if (wordCursor == 0)
			continue;
		wordCursor=0;

		strlwr(newword);
	  //  printf("%s (%d)",newword, wordCnt);

		key=sum%TABLE_SIZE;
	  //  printf("%d\n",key);
	    sum = 0;

	    // is the hashtable location for key is free?
	    if (hashtable[key].count == 0) {
	    	hashtable[key].count++;
	    	strcpy(hashtable[key].word, newword);
	    	wordCnt++;
		} else {	// hash table already has some entry(ies)
			struct node *head = &hashtable[key];
			int isFound = 0;
			while(1) {	// traverse the list to locate the right node
				if(strcmp(head->word, newword) == 0) {
					head->count++;
					isFound = 1;
					break;
				}
				if (head->next == NULL) break;
				head = head->next;
			}
			if (isFound == 0) {	// insert a node at the end of the list
				head->next = (struct node *)malloc(sizeof(struct node));
				if (head->next == NULL) {
			        printf("Unable to allocate memory.\n");
    			    exit(0);
				}
				head->next->next = NULL;
				head->count = 1;
				strcpy(head->word, newword);
				wordCnt++;
			}
		}
	}
	
//	printf("No of Words:%d\n", wordCnt);
	for(i = 0; i < TABLE_SIZE; i++)
	{
		if (hashtable[i].count != 0) 
			printf("%s : %d\n",hashtable[i].word,hashtable[i].count);
	}
}