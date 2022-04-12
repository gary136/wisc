/******************************************************************************
 * FILENAME: mem.c
 * AUTHOR:   cherin@cs.wisc.edu <Cherin Joseph>
 * DATE:     20 Nov 2013
 * EDITED: for CS354 UW Madison Spring 2021 - Michael Doescher
 * PROVIDES: Contains a set of library functions for memory allocation
 * *****************************************************************************/

#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <string.h>
#include "mem.h"
#include <assert.h>
#include <stdlib.h>
#include <unistd.h>

// fitting policy
enum POLICY policy;

/* The BLOCK_HEADER structure serves as the header for each block.
 * 
 * The heaaders we are using for this project are similar to those described 
 * in the book for the implicit free list in section 9.9.6, and in lecture
 * In the implicit free packs both the size and allocated bit in one int.
 * The size in the implicit free list includes the size of the header.
 *
 * In this project we're going to use a struct that tracks additional 
 * information in the block header.
 * 
 * The first piece of information is a 'packed_pointer' that combines the 
 * absolute location (a memory address) of the next header and the alloc bit
 * The headers must begin on an address divisible by 4. This means the last 
 * two bits must be 0.  We use the least significant bit (LSB) to indicate 
 * if the block is free: LSB = 0; or allocated LSB = 1.
 *
 * The value stored in the size variable is either the size requested by 
 * the user for allocated blocks, or the available payload size (not including 
 * the size of the header) for free blocks.
 * This will allow us to calculate the memory utilization.  Memory utilization is
 * the requested_size / (padding + header_size).  
 * The provided function Mem_Dump takes care of this calculation for us.
 *
 * The end of the list (the last header) has the packed_pointer  set to NULL,
 * and the size set to 0.
 */

typedef struct BLOCK_HEADER {
  void *packed_pointer; 
  // address of the next header + alloc bit.
  // would be correct address if not allocted
  // wrong address if allocted
  unsigned size; 
  // for allocated blocks : the size requested by the user
  // for free blocks : the available payload size (not including the size of the header) 
} BLOCK_HEADER;

BLOCK_HEADER *first_header; // this global variable is a pointer to the first header
BLOCK_HEADER *last_header;
// *********************************************************************************
// *********************************************************************************
// *********************************************************************************

/*  We recommend you write some helper functions to unpack the headers 
 *  and retrieve specific pieces of data. I wrote functions named:
 *  
 *  1) Is_Allocated // return 1 if allocated 0 if not
 *  2) Is_Free      // return 1 if free 0 if not
 *  3) Get_Next_Header // unpacks the header and returns a pointer to the  
 *  the next header, NULL is this is the last BLOCK_HEADER
 *  4) Get_Size 
 *  5) Get_User_Pointer // the pointer that the user can write data to
 *  6) Get_Header_From_User_Pointer // the pointer that the user writes data to - used in Mem_Free
 *  7) Set_Next_Pointer
 *  8) Set_Allocated // set the allocated bit to 1
 *  9) Set_Free // set the allocated bit to 0
 *  10) Set_Size 
 */

 int Is_Allocated(BLOCK_HEADER *curr) {
     // return 1 if allocated 0 if not
    // allocated block
    if ((unsigned)curr->packed_pointer & 1) return 1;
    // make num other than lsb 0
    return 0;
 }

BLOCK_HEADER* Get_Next_Header(BLOCK_HEADER *curr) {
    // unpacks the header and returns a pointer to the  
    // the next header, NULL is this is the last BLOCK_HEADER
    if (curr->packed_pointer != NULL)
        return (BLOCK_HEADER *)((unsigned)curr->packed_pointer & 0xfffffffe);
        // extract header of next block
        // make lsb zero, meaning next <= current.packed_pointer
        // i.e. set alloc_bit zero
        // this would correct the address if the block is allocted
    return NULL;
 }

 unsigned Get_Size(BLOCK_HEADER *curr) {
     return curr->size;
 }

 void* Get_User_Pointer(BLOCK_HEADER *curr) {
     // the pointer that the user can write data to
    return (void *)curr + sizeof(BLOCK_HEADER);
    // curr -> [header block] -> [payload] -> [padding] -> next 
    // i.e. the first bit of payload
 }

void* Set_Next_Pointer(BLOCK_HEADER *next, BLOCK_HEADER *last_header) {
    next->size = (unsigned)last_header - (unsigned)next - sizeof(BLOCK_HEADER);
    next->packed_pointer = last_header;
 }

 void Set_Allocated(BLOCK_HEADER *curr) {
    printf("change packed_pointer from %p", curr->packed_pointer);
    curr->packed_pointer = (void *)((unsigned)curr->packed_pointer | 1);
    // make LSB = 1
    printf(" to %p\n", curr->packed_pointer);
 }

 void Set_Free(BLOCK_HEADER *curr) {
    printf("change packed_pointer from %p", curr->packed_pointer);
    curr->packed_pointer = (void *)((unsigned)curr->packed_pointer & 0xfffffffe);
    // make LSB = 0
    printf(" to %p\n", curr->packed_pointer);
 }

// *********************************************************************************
// *********************************************************************************
// *********************************************************************************

/* Function used to Initialize the memory allocator */
/* Do not change this function */
/* Written by Cherin Joseph modified by Michael Doescher */
/* Not intended to be called more than once by a program */
/* Argument - sizeOfRegion: Specifies the size of the chunk which needs to be allocated 
   	      policy: indicates the policy to use eg: best fit is 0*/
/* Returns 0 on success and -1 on failure */
/* Notes we're using mmap here instead of sbrk as in the book to take advantage of caching
 * as described in the OS lectures
 *
 * Study the end of the function where the headers are initialized for hints!
 */
int Mem_Init(int sizeOfRegion, enum POLICY policy_input)
{   
    policy = policy_input;
    
    int pagesize;
    int padsize;
    int fd;
    int alloc_size;
    void* space_ptr;
    static int allocated_once = 0;
    
    if(0 != allocated_once) {
      fprintf(stderr,"Error:mem.c: Mem_Init has allocated space during a previous call\n");
      return -1;}
    if(sizeOfRegion <= 0) {
        fprintf(stderr,"Error:mem.c: Requested block size is not positive\n");
        return -1;}
  
    /* Calculate padsize as the padding required to round up sizeOfRegion to a multiple of pagesize */
    pagesize = getpagesize(); //  Get the pagesize
    padsize = sizeOfRegion % pagesize;
    padsize = (pagesize - padsize) % pagesize;
    alloc_size = sizeOfRegion + padsize;
  
    /* Using mmap to allocate memory */
    fd = open("/dev/zero", O_RDWR);
    if(-1 == fd) {
      fprintf(stderr,"Error:mem.c: Cannot open /dev/zero\n");
      return -1;}
    space_ptr = mmap(NULL, alloc_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
    if (MAP_FAILED == space_ptr) {
      fprintf(stderr,"Error:mem.c: mmap cannot allocate space\n");
      allocated_once = 0;
      return -1;}
    
    allocated_once = 1;
    
    // To begin with, there is only one big, free block.
    // Initialize the first header */
    first_header = (BLOCK_HEADER*)space_ptr;
    // free size
    // Remember that the 'size' stored for free blocks excludes the space for the headers
    first_header->size = (unsigned)alloc_size - 2*sizeof(BLOCK_HEADER);
    // alloc_size : first_header -> size -> last_header, need not to address padding at this point 
    // address of last header
    first_header->packed_pointer = (void *)first_header + alloc_size - sizeof(BLOCK_HEADER);
    
    // initialize last header
    // packed_pointers are void pointer, the headers are not
    // BLOCK_HEADER *last_header = (BLOCK_HEADER *)first_header->packed_pointer;
    last_header = (BLOCK_HEADER *)first_header->packed_pointer;
    last_header->size = 0;
    last_header->packed_pointer = NULL;
    return 0;
}


// *********************************************************************************
// *********************************************************************************
// *********************************************************************************

/* Function for allocating 'size' bytes. */
/* Return the user writeable address of allocated block 
 * - this is the first byte of the payload, not the address of the header */
/* Return NULL on failure */

/* Here is what this function should accomplish */
/* - Check for sanity of size - Return NULL when appropriate - at least 1 byte*/
/* - Traverse the list of blocks and locate a free block which can accommodate 
 * the requested size based on the policy (e.g. first fit, best fit) */
/* - The next header must be aligned with an address divisible by 4. 
 *     - Add padding to accomodate this requirement. */
/* - When allocating a block - split it into two blocks when possible 
 *     - the allocated block should go first and the free block second
 *     - the free block must have a minimum payload size of 4 bytes 
 *     - do not split if the mininmum payload size can not be reserved */

/* Tips: Be careful with pointer arithmetic */
void* Mem_Alloc(int size) {

    /* Your code should go in here */

    /* - Check for sanity of size - Return NULL when appropriate - at least 1 byte*/
    if (size <= 0) return NULL;

    int available_space, alloc_size, T_size;
    // BLOCK_HEADER *last_header = (BLOCK_HEADER *)first_header->packed_pointer;
    BLOCK_HEADER *current = first_header; // traverse from the first 

    
    
    printf("\n");
    if (size % 4 != 0) alloc_size = size + (4-(size % 4));
    else alloc_size = size;
    T_size = alloc_size + 8;
    printf("alloc_size = %d, T_size = %d\n", alloc_size, T_size);    

    printf("current begins at %p\n", current);
    /* - Traverse the list of blocks and locate a free block which can accommodate 
    * the requested size based on the policy (e.g. first fit, best fit) */
    // if (Is_Allocated(current)==1) printf("meet allocated block\n");
    /* - The next header must be aligned with an address divisible by 4. 
    *     - Add padding to accomodate this requirement. */
    /* Calculate padsize as the padding required to round up size to a multiple of pagesize */

    available_space = (unsigned)Get_Next_Header(current) - (unsigned)current;
    if (Is_Allocated(current)==1) printf("block is allocated"); else printf("block is free");
    printf(", available_space in curr block = %d\n", available_space);
    while (Get_Next_Header(current)!=NULL && (Is_Allocated(current)==1 || available_space < T_size)) { 
        // current.packed_pointer : xxxxxxx1 
        current = Get_Next_Header(current);
        printf("current moves to %p\n", current);
        available_space = (unsigned)Get_Next_Header(current) - (unsigned)current;
        if (Is_Allocated(current)==1) printf("block is allocated"); else printf("block is free");
        printf(", available_space in curr block = %d\n", available_space);
    }
    if (Get_Next_Header(current)==NULL) {
        printf("all space is used\n");  
        return NULL;
    }
    BLOCK_HEADER *local_last_header = current->packed_pointer;
    printf("local_last_header is %p\n", local_last_header);

    /* - When allocating a block - split it into two blocks when possible 
    *     - the allocated block should go first and the free block second
    *     - the free block must have a minimum payload size of 4 bytes 
    *     - do not split if the mininmum payload size can not be reserved */

    // the least size = 8(header block) + 4 (payload) = 12
    // if space < 12 : not allocate block
    // else if 12 <= space < 24 : allocate 1 block
    // else if 24 <= space : split 2 block
    
    if (available_space < 12 || available_space < T_size) {
        printf("inadequate available space\n");  
        return NULL;
    }
    
    if (available_space - T_size < 12) {
        printf("can only allocate 1 block within available space\n");  
    
        // original : first_header -> [first_header.size] -> last_header
        // modified : first_header -> curr -> [header block] -> [payload] -> [padding] 
                                                            // [curr.alloc_size.....]
        // -> 1 bit(alloc_bit) -> curr.packed_pointer(after setting allocted)
        // [...........................packet pointer....................]

        // curr is allocated, size = the size requested by the user  
        printf("\nupdate current info\n");  
        printf("current is %p\n", current);
        current->size = size; // curr.size = size
        printf("current->size = %d\n", current->size);
        current->packed_pointer = Get_Next_Header(current);
        Set_Allocated(current); // current->packed_pointer += 1 
        printf("end allocation\n\n");
    }
    
    if (available_space - T_size >= 12) {
        printf("split available space into 2 blocks\n");  
        
        printf("\nupdate current info\n");  
        printf("current is %p\n", current);
        current->size = size; // curr.size = size
        printf("current->size = %d\n", current->size);
        current->packed_pointer = (void *)current + sizeof(BLOCK_HEADER) + alloc_size;
        Set_Allocated(current); // current->packed_pointer += 1
        
        // initialize next header
        // next = (BLOCK_HEADER *)((unsigned)current->packed_pointer & 0xfffffffe) 
        // next -> [header block] -> [payload](next.size) -> local_last_header

        BLOCK_HEADER *next = Get_Next_Header(current);
        Set_Next_Pointer(next, local_last_header); 
        printf("\nmark next info\n");  
        printf("next is %p\n", next);
        printf("next->size = %d\n", next->size);
        printf("next->packed_pointer is %p  (should = local_last_header)\n", next->packed_pointer);
        printf("\n");
    }
    return Get_User_Pointer(current);
}

// *********************************************************************************
// *********************************************************************************
// *********************************************************************************

/* Function for freeing up a previously allocated block */
/* Argument - ptr: Address of the block to be freed up i
 *     - this is the first address of the payload */
/* Returns 0 on success */
/* Returns -1 on failure */
/* Here is what this function should accomplish */
/* - Return -1 if ptr is NULL */
/*  Return -1 if ptr is not pointing to the first byte of an allocated block
 *     - hint: check all block headers, determine if the alloc bit is set */
/* - Mark the block as free */
/* - Coalesce if one or both of the immediate neighbours are free */
int Mem_Free(void *ptr)
{
    /* Your code should go in here */

    /* - Return -1 if ptr is NULL */
    /*  Return -1 if ptr is not pointing to the first byte of an allocated block
    *     - hint: check all block headers, determine if the alloc bit is set */

    BLOCK_HEADER *prev, *target, *next;
    target = (void *)ptr - sizeof(BLOCK_HEADER);
    printf("\n");
    printf("target is %p\n", target);
    if (ptr == NULL) return -1;    
    if ((unsigned)target % 4 != 0 ||
        (unsigned)target < (unsigned)first_header || 
        (unsigned)target > (unsigned)last_header) return -1; 
    if (Is_Allocated(target)==0) return -1;  
    
    /* - Mark the block as free */
    Set_Free(target);
    target->size = (unsigned)Get_Next_Header(target)- sizeof(BLOCK_HEADER) - (unsigned)target; 
    printf("target is freed\n");
    printf("target->size = %d\n", target->size);
    printf("target->packed_pointer is %p\n\n", target->packed_pointer);
    
    // coalesce
    
    // locate prev
    prev = first_header; // traverse from the first 
    while (Get_Next_Header(prev)!=NULL && (unsigned)prev < (unsigned)target
        && Get_Next_Header(prev)!=target) { 
        prev = Get_Next_Header(prev);
        printf("prev moves to %p\n", prev);
    }
    if (Is_Allocated(prev)==0 && prev!=target) {
        printf("prev needs to colaesce\n"); 
        int prevSize = prev->size;
        int targetSize = target->size;
        void *pntr = target->packed_pointer;
        target = prev;
        target->size = prevSize + targetSize + 8;
        target->packed_pointer = pntr;
        printf("after the coalecse, target is %p\n", target);
        printf("target->size = %d\n", target->size);
        printf("target->packed_pointer is %p\n", target->packed_pointer);
    }

    // locate next
    next = Get_Next_Header(target);
    if (Is_Allocated(next)==0 && next->size!=0) {
        printf("next needs to colaesce\n"); 
        int nextSize = next->size;
        int targetSize = target->size;
        void *pntr = next->packed_pointer;
        target->size = nextSize + targetSize + 8;
        target->packed_pointer = pntr;
        printf("after the coalecse, target is %p\n", target);
        printf("target->size = %d\n", target->size);
        printf("target->packed_pointer is %p\n", target->packed_pointer);
    }
    printf("\n");

    return 0;
}

// *********************************************************************************
// *********************************************************************************
// *********************************************************************************

/* Function to be used for debugging */
/* Prints out a list of all the blocks along with the following information for each block */
/* No.      : Serial number of the block */
/* Status   : free/busy */
/* Begin    : Address of the first user allocated byte - i.e. start of the payload */ 
/* End      : Address of the last byte in the block (payload or padding) */
/* Payload  : Payload size of the block - the size requested by the user or free size */
/* Padding  : Padding size of the block */
/* T_Size   : Total size of the block (including the header, payload, and padding) */
/* H_Begin  : Address of the block header */
void Mem_Dump()
{
    unsigned id = 0;
    unsigned total_free_size = 0;
    unsigned total_payload_size = 0;
    unsigned total_padding_size = 0;
    unsigned total_used_size = sizeof(BLOCK_HEADER); // end of heap header not counted in loop below
    char status[5];
    unsigned payload = 0;
    unsigned padding = 0;
    BLOCK_HEADER *current = first_header;
    
    fprintf(stdout,"************************************Block list***********************************\n");
    fprintf(stdout,"%5s %7s %12s %12s %9s %9s %8s %12s\n", 
            "Id.", "Status", "Begin", "End", "Payload", "Padding", "T_Size", "H_Begin");
    fprintf(stdout,"---------------------------------------------------------------------------------\n");
            
    while (current->packed_pointer != NULL) {
        id++;
        BLOCK_HEADER *next = (BLOCK_HEADER *)((unsigned)current->packed_pointer & 0xfffffffe);
        // extract header of next block
        // make lsb zero, meaning next <= current.packed_pointer
        // i.e. set alloc_bit zero
        // this would correct the address if the block is allocted  
        void *begin = (void *)current + sizeof(BLOCK_HEADER); 
        // first bit of payload
        void *end = (void *)next - 1;
        // payload + padding - 1
        
        if ((unsigned)current->packed_pointer & 1) { // allocated block
            // current.packed_pointer : xxxxxxx1 
            strcpy(status, "Busy");
            payload = current->size;
            // the packed_pointer is composed of 
            // the address of header and the alloc bit
            // (that describes if the block is allocated or free)
            padding = (unsigned)((unsigned)next-(unsigned)current)-payload-sizeof(BLOCK_HEADER);
            // curr -> [header block] -> [payload] -> [padding] -> next -> 1 bit(alloc_bit) -> curr.packed_pointer 
            total_payload_size += payload;
            total_padding_size += padding;
            total_used_size += payload + padding + sizeof(BLOCK_HEADER);
        }
        else { // free block 
            // current.packed_pointer : xxxxxxx0
            strcpy(status, "Free");
            payload = current->size;
            padding = 0;
            // curr -> [header block] -> [payload] -> curr.packed_pointer 
            total_used_size += sizeof(BLOCK_HEADER);
            total_free_size += payload;
        }
        unsigned total_block_size = sizeof(BLOCK_HEADER) + padding + payload;
        
        fprintf(stdout,"%5d %7s %12p %12p %9u %9u %8u %12p\n", 
            id, status, begin, end, payload, padding, total_block_size, current);
        current = next;
    }
    fprintf(stdout,"---------------------------------------------------------------------------------\n");
    fprintf(stdout,"*********************************************************************************\n");
    fprintf(stdout,"Total payload size = %d\n",total_payload_size);
    fprintf(stdout,"Total padding size = %d\n",total_padding_size);
    fprintf(stdout,"Total free size = %d\n",total_free_size);
    fprintf(stdout,"Total used size = %d\n",total_used_size);
    fprintf(stdout,"*********************************************************************************\n");
    fflush(stdout);

    return;
}

// int main() {
    
//     int f;
//     assert(Mem_Init(32, FIRST_FIT)==0);
//     Mem_Dump();
//     void *ptr1_1 = Mem_Alloc(1);
//     assert(ptr1_1!=NULL);
//     Mem_Dump();

//     f = Mem_Free(ptr1_1);
//     if (f==0) printf("free success!\n"); else printf("free fails!");
//     Mem_Dump();

//     void *ptr1_2 = Mem_Alloc(5);
//     assert(ptr1_2!=NULL);
//     Mem_Dump();

//     void *ptr2 = Mem_Alloc(4027);
//     assert(ptr2!=NULL);
//     Mem_Dump();

//     f = Mem_Free(ptr1_2);
//     if (f==0) printf("free success!\n"); else printf("free fails!");
//     Mem_Dump();

//     void *ptr1_3 = Mem_Alloc(1);
//     assert(ptr1_3!=NULL);
//     Mem_Dump();

//     f = Mem_Free(ptr1_3);
//     if (f==0) printf("free success!\n"); else printf("free fails!");
//     Mem_Dump();

//     f = Mem_Free(ptr2);
//     if (f==0) printf("free success!\n"); else printf("free fails!");
//     Mem_Dump();

//     void *ptr3 = Mem_Alloc(800);
//     assert(ptr3!=NULL);
//     Mem_Dump();

//     void *ptr4 = Mem_Alloc(34500);
//     assert(ptr4!=NULL);
//     Mem_Dump();

//     exit(0);
// }