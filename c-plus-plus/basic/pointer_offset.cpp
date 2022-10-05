#include <iostream>

using namespace std;

int main() {
    int a[3] = {1, 2, 3};
    int *p = a;  // p 指向 a[0]
    *p = 4;      // a: [4, 2, 3]
    cout << p << endl;
    // 0xac33ff9ac

    p = p + 1;   // p 指向 a[1]
    *p = 5;      // a: [4, 5, 3]
    cout << p << endl;
    // 0xac33ff9b0

    p++;         // p 指向 a[2]
    *p = 6;      // a: [4, 5, 6]
    cout << p << endl;
    // 0xac33ff9b4

}
