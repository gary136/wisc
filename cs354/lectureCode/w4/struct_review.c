#include <stdio.h>
#include <string.h>


struct address {
    char street[100];
    char state[3];
    int zip;
};

struct person {
    char name[100];
    int id;
    struct address addr;
};

int main() {
    struct person p;
    p.id = 123456;
    p.addr.zip=54321;

    strncpy(p.name, "Mike", 100);

    struct person *pp = &p;
    pp->id = 45678;
}
