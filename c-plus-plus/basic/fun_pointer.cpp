#include <iostream>

// 指向函数的指针
int (*binary_int_op)(int, int);

// 也可以使用typedef定义一种函数类型，更加规范
//typedef int (*p_bi_int_op)(int, int);

int foo1(int a, int b) { return a * b + b; }

int foo2(int a, int b) { return (a + b) * b; }

int main() {
    int choice;
    std::cin >> choice;
    if (choice == 1) {
        binary_int_op = foo1;
    } else {
        binary_int_op = foo2;
    }

    int m, n;
    std::cin >> m >> n;
    std::cout << binary_int_op(m, n);
}
