#include <iostream>

using namespace std;

void other_fun() {
    int(*a)[5] = new int[5][5];
    int *p = a[2];
    cout << "a[2] addr= " << &a[2] << endl;
    p += 1;
    cout << "a[3] addr= " << &a[3] << endl;

    // a[2] addr= 0x1ae1ce01d18
    // a[3] addr= 0x1ae1ce01d2c

    // a是 int[5]类型，因此a+1相对a而言是 5 个 int 型变量的长度偏移。
    // 2c -18 = 8+c = 20 bytes
    // one row = 5 elements = 5 int = 5 x 4(bytes) =20 bytes

    a[2][1] = 1;
    delete[] a;
}

int main() {
    int **a = new int *[5];

    // shape: 5 x 5, 这样获得的二维数组，不能保证其空间是连续的。
    for (int i = 0; i < 5; i++) {
        a[i] = new int[5];
    }

    // GC
    // 这样获得的内存的释放，则需要进行一个逆向的操作：即先释放每一个数组，再释放存储这些数组首地址的数组
    for (int i = 0; i < 5; i++) {
        delete[] a[i];
    }
    delete[] a;

    other_fun();
}


