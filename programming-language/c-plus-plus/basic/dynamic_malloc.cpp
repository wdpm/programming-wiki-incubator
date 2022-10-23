#include <cstddef>

int main() {
    int *p = new int(1234);
    /* ... */
    delete p;

    class A {
        int a;

    public:
        explicit A(int a_) : a(a_) {}
    };

    A *p2 = new A(1234);
    /* ... */
    delete p2;

    size_t element_cnt = 5;
    int *p3 = new int[element_cnt];
    delete[] p3;

    return 0;
}
