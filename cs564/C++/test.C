# include <iostream>
using namespace std;

int arr1[5] = {0,1,2,3,4};
int arr2[5] = {5,6,7,8,9};
int arr3[5] = {0,1,2,3,4};

typedef struct Address {
    string city;
    int zip;
} Address;
  
typedef struct Student {
    int id;
    bool isGrad;
    Address addr;
} Student;

typedef struct ListNode {
    int data;
    ListNode *next;  // pointer to the next node in the list
} ListNode;

void print() {
    cout << "Hello world!" << endl;
}

int sum (int start, int end) {
    int res = 0;
    int tmp = start;
    while (tmp <= end) {
        res+=tmp;
        tmp+=1;
    }
    return res;
}

bool ArrayEq(int a1[], int a2[], int size) {
    // if (size1 != size2)
	// 	return false;
    for (int i=0; i<size; i++) {
        // cout << a1[i];
        if (a1[i] != a2[i]) return false;
    }
    return true;
}

int NumGrads(Student stds[], int size) {
    int count = 0;
    for (int i=0; i<size; i++) {
        if (stds[i].isGrad) count++;
    }
    return count;
}

void PrintList(ListNode *node) {
    while (node) {
        cout << node->data << '\n';
        node = node->next;
    }
}

int main() {
    // print();

    // cout << sum(1,646) << '\n';

    // cout << ArrayEq(arr1, arr3, 5) << '\n';
    // cout << ArrayEq(arr1, arr2, 5) << '\n';
    // if (ArrayEq(arr1, arr3, 5))
    //     cout << "equal\n";
    // else 
    //     cout << "not equal\n";
    // if (ArrayEq(arr1, arr2, 5))
    //     cout << "equal\n";
    // else 
    //     cout << "not equal\n";

    // Student A, B, C, D;
    // Student Students1[4] = {A, B, C, D};
    // A.isGrad=true;
    // B.isGrad=false;
    // C.isGrad=true;
    // D.isGrad=false;
    // Student Students2[4] = {A, B, C, D};
    // cout << NumGrads(Students1, 4) << '\n';
    // cout << NumGrads(Students2, 4) << '\n';

    // ListNode *head = NULL;  // head points to the list of nodes, initially empty
    // int k;
    // while (cin >> k) {
    //     if (k>5) break;
    //     // create a new list node containing the value read in
    //     ListNode *tmp = new ListNode; 
    //     tmp->data = k;

    //     // attach the new node to the front of the list
    //     tmp->next = head;
    //     head = tmp;        
    // }



    // PrintList(head);

    int k;
    int sum = 0;
    for (int i=0; i<10; i++) {
        cin >> k;
        sum+=k;
    }
    cout << sum << "\n";

    return 0;
}
