int Is_Number_Correct(int guess, int ans) {
    int num = 0;
    while (guess>ans) {
        guess--;
        num++;
    }
    return num;
};

int main() {
    int x = Is_Number_Correct(4, 1);
    return 0;
}