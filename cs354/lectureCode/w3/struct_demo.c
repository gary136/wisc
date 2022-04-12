#include <stdio.h>


struct STUDENT {
	char *name; 
	int id;
};

typedef struct STUDENT STUDENT;

 
// struct STUDENT {char *name; int id;};
// typedef struct STUDENT {char *name; int id;} STUDENT;

typedef struct STUDENT {char *name; int id;} STUDENT;

typedef float T;


void Print_Student(struct STUDENT s);
void Print_Student_With_Pointer(struct STUDENT *s);

int main() {
	
	T x = 3.0;
	T y = 4.0;
	T z = x + y;
	
	
	printf("Welcome to Week 3 of CS354!\n\n");
	
	struct STUDENT student;
	student.name = "Mike";
	student.id = 123456;
	
printf("address of student = %p\n", &student);
printf("name: %s at address %p\n", student.name, &student.name);
printf("id: %d at address %p\n", student.id, &student.id);
printf("address of string Mike = %p\n\n", student.name);

	Print_Student(student);
	Print_Student_With_Pointer(&student);

	
	printf("\n");
	return 0;
}

void Print_Student(struct STUDENT s) {
	printf("address of s = %p\n", &s);
	printf("name: %s at address %p\n", s.name, &s.name);
	printf("id: %d at address %p\n", s.id, &s.id);
	printf("address of string Mike = %p\n\n", s.name);
}

void Print_Student_With_Pointer(struct STUDENT *s) {
	printf("address of s = %p\n", &s);
	printf("name: %s at address %p\n", (*s).name, &s->name);
	printf("id: %d at address %p\n", s->id, &s->id);
	printf("address of string Mike = %p\n\n", s->name);	
}














