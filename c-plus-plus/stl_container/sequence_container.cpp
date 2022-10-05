#include <array>
#include <iostream>
#include <deque>

using namespace std;

// 数组
void use_array() {
    // 1. 创建空array，长度为3; 常数复杂度
    array<int, 3> v0{};
    v0.fill(1);  // 填充数组

    // 2. 用指定常数创建array; 常数复杂度
    array<int, 3> v1{1, 2, 3};

    // 访问数组
    for (int i = 0; i != v1.size(); ++i)
        cout << v1[i] << " ";

    for (int i: v1)
        cout << i << " ";
}

// double-end 数组
void use_deque() {
    // 1. 定义一个int类型的空双端队列 v0
    // deque的默认初始化size是多少？
    deque<int> v0;
    // 2. 定义一个int类型的双端队列 v1，并设置初始大小为10; 线性复杂度
    deque<int> v1(10);
    // 3. 定义一个int类型的双端队列 v2，并初始化为10个1; 线性复杂度
    deque<int> v2(10, 1);
    // 4. 复制已有的双端队列 v1; 线性复杂度
    deque<int> v3(v1);
    // 5. 创建一个v2的拷贝deque v4，其内容是v4[0]至v4[2]; 线性复杂度
    deque<int> v4(v2.begin(), v2.begin() + 3);
    // 6. 移动v2到新创建的deque v5，不发生拷贝; 常数复杂度; 需要 C++11
    deque<int> v5(std::move(v2));
}

// 双向链表
void use_list(){

}

//单向链表
void use_forward_list(){

}

int main() {
//    use_array();
//    use_deque();
    return 0;
}