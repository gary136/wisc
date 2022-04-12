int factorial(int n) {
    int fact = 1;
    while (n > 1) {
        fact *= n;
        n--;
    }
    return fact;
}
