#include <stdio.h>

int main() {
    FILE *fp;

    fp = fopen("bar.bin", "wb");
    int num = 0x12345678;
    fwrite(&num, sizeof(int), 1, fp);
    fclose(fp);
 
    fp = fopen("bar.bin", "rb");
    int readNum;
    fread(&readNum, sizeof(int), 1, fp);
    printf("readNum = 0x%x\n", readNum);
    fclose(fp);

    return 0;
}
