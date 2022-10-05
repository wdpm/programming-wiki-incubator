#include <fstream>
using namespace std;  // 两个类型都在 std 命名空间里

ifstream fin("data.in");
ofstream fout("data.out");

int main() {
    /*
    中间的代码改变 cin 为 fin ，cout 为 fout 即可
    */
    fin.close();
    fout.close();
    return 0;
}
