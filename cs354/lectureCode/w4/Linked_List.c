#include <stdio.h>
#include <stdlib.h>

struct node {
    int data;
    struct node *next;
};

struct node* Append(struct node *head, int data){
    // create the new node
    struct node *new_node = malloc(sizeof(struct node));
    new_node->data = data;
    new_node->next = NULL;

    if (head == NULL)
        return new_node;
    
    struct node *current_node = head;
    while (current_node->next != NULL)
        current_node = current_node->next;
    current_node->next = new_node;
    return head;
}

void Print_List(struct node *head) {
    while (head != NULL){
        printf("%d|%p -> ",head->data,head->next);
        head = head->next;
    }
    printf("\n");
}

void Delete_First(struct node **phead) {
    if (*phead == NULL) 
        return;

    struct node *first = *phead;
    *phead = (*phead)->next;

    free(first);
}


int main() {

struct node *head = NULL;
struct node *first = malloc(sizeof(struct node));
first->data = 10;
first->next = NULL;
head = first;

    Print_List(head);

    head = Append(head, 20);
    Print_List(head);

    head = Append(head, 30);
    Print_List(head);

    Delete_First(&head);
    Print_List(head);
    Delete_First(&head);
    Print_List(head);

    Delete_First(&head);
    Print_List(head);
    Delete_First(&head);
    Print_List(head);


}
