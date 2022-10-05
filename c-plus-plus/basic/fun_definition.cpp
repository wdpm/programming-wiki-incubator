#include <iostream>

using namespace std;

int some_function(int, int);  // 声明

int some_function(int x, int y) {  // 定义
    int result = 2 * x + y;
    return result;
}

// 声明的同时，进行函数定义
int some_function2(int x, int y) { return 2 * x + y; }

// int &x 表示传引用
void foo(int &x, int &y) {
    x = x * 2;
    y = y + 3;
}

// int *x表示传地址，这种形式是时代的眼泪C语言的常用交换模式
void foo2(int *x, int *y) {
    //需要解引用（或者称为指针读值）才能进行内容的修改
    *x = *x * 2;
    *y = *y + 3;
}

int main() {
    int a = 1;
    int b = 1;
    // 调用前：a = 1, b = 1

    // 函数swap(int &a, int &b)为什么只需要swap(a, b)而不需要swap(&a, &b)?
    // => 为什么swap(a, b)中的a,b能够表示传引用？
    // c++自动把a,b的地址作为参数传递给swap函数。这是C++的机制。
    foo(a, b);  // 调用 foo
    // 调用后：a = 2, b = 4
    cout << a << " "<< b << endl;

    int a1 = 1;
    int b1 = 1;
    foo2(&a1, &b1);
    cout << a1 << " "<< b1 << endl;
}