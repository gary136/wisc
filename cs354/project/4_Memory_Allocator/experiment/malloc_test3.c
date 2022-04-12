#include <stdio.h>

// typedef struct free_block {
//     size_t size;
//     struct free_block* next;
// } free_block;

// static free_block free_block_list_head = { 0, 0 };
// static const size_t overhead = sizeof(size_t);
// static const size_t align_to = 16;

// void* mymalloc(size_t size) {
//     size = (size + sizeof(size_t) + (align_to - 1)) & ~ (align_to - 1);
//     free_block* block = free_block_list_head.next;
//     free_block** head = &(free_block_list_head.next);
//     while (block != 0) {
//         if (block->size >= size) {
//             *head = block->next;
//             return ((char*)block) + sizeof(size_t);
//         }
//         head = &(block->next);
//         block = block->next;
//     }

//     block = (free_block*)sbrk(size);
//     block->size = size;

//     return ((char*)block) + sizeof(size_t);
// }

// void free(void* ptr) {
//     free_block* block = (free_block*)(((char*)ptr) - sizeof(size_t));
//     block->next = free_block_list_head.next;
//     free_block_list_head.next = block;
// }

int main() {
    int *p1 = malloc(1); printf("p1 is at %p\n",p1);    
    int *p2 = malloc(2); printf("p2 is at %p -> %d\n",p2,p2-p1);
    int *p3 = malloc(23); printf("p3 is at %p -> %d\n",p3,p3-p2);
    int *p4 = malloc(24); printf("p4 is at %p -> %d\n",p4,p4-p2);
    brk(3200);
    sbrk(3200);
    int *p5 = malloc(25); printf("p5 is at %p -> %d\n",p5,p5-p2);
    int *p6 = malloc(26); printf("p6 is at %p -> %d\n",p6,p6-p2);
    int *p7 = malloc(27); printf("p7 is at %p -> %d\n",p7,p7-p2);
    int *p8 = malloc(28); printf("p8 is at %p -> %d\n",p8,p8-p2);
    return(0);
}