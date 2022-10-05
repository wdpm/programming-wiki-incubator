#include <cstdio>
#include <iostream>


int main() {
    // use freopen
    freopen("data.in", "r", stdin);
    freopen("data.out", "w", stdout);
    /*
    中间的代码不需要改变，直接使用 cin 和 cout 即可
    */
    fclose(stdin);
    fclose(stdout);

    // use fopen
    FILE *in, *out;  // 定义文件指针
    in = fopen("data.in", "r");
    out = fopen("data.out", "w");
    /*
    do what you want to do
    */
    fclose(stdin);
    fclose(stdout);

    return 0;
}
