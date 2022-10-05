#include <iostream>

using namespace std;

int main() {
    cout << "Hello, C++." << endl;

    int a = 1;
    int *p = &a;
    cout << "a=" << a << endl;
    cout << "&a=" << &a << endl; // 值取指针	int *intPtr = &data;
    cout << "p=" << p << endl;
    cout << "*p=" << *p << endl; // 指针取值	int data = *intPtr;

    int &&c = 2;
    cout << "&c=" << &c << endl;
    cout << "**c=" << c << endl;

    int *array = new int[10];
    array[0] = 0;
    delete[] array;

    // type inference by `auto` keyword
    auto d =20;

    return 0;
}