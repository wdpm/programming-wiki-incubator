// 容器适配器是什么
// 基础容器的改造，或者认为是进一步的封装，重新定义了一些性质和行为。典型代表，使用deque封装为stack
#include <stack>
#include <iostream>
#include <queue>

using namespace std;

stack<TypeName> s;  // 使用默认底层容器 deque，数据类型为 TypeName
stack<TypeName, Container> s;  // 使用 Container 作为底层容器
stack<TypeName> s2(s1);        // 将 s1 复制一份用于构造 s2

// 以下所有函数均为常数复杂度
//top() 访问栈顶元素（如果栈为空，此处会出错）
//push(x) 向栈中插入元素 x
//pop() 删除栈顶元素
//size() 查询容器中的元素数量
//empty() 询问容器是否为空

void use_stack() {
    stack<int> s1;
    s1.push(2);
    s1.push(1);
    stack<int> s2(s1);
    s1.pop();
    cout << s1.size() << " " << s2.size() << endl;  // 1 2
    cout << s1.top() << " " << s2.top() << endl;    // 2 1
    s1.pop();
    cout << s1.empty() << " " << s2.empty() << endl;  // 1 0
}


queue<TypeName> q;  // 使用默认底层容器 deque，数据类型为 TypeName
queue<TypeName, Container> q;  // 使用 Container 作为底层容器
queue<TypeName> q2(q1);  // 将 s1 复制一份用于构造 q2

//以下所有函数均为常数复杂度
//front() 访问队首元素（如果队列为空，此处会出错）
//push(x) 向队列中插入元素 x
//pop() 删除队首元素
//size() 查询容器中的元素数量
//empty() 询问容器是否为空

void use_deque() {
    queue<int> q1;
    q1.push(2);
    q1.push(1); // 2 1
    queue<int> q2(q1); // 2 1
    q1.pop(); // 1
    cout << q1.size() << " " << q2.size() << endl;    // 1 2
    cout << q1.front() << " " << q2.front() << endl;  // 1 2
    q1.pop();
    cout << q1.empty() << " " << q2.empty() << endl;  // 1 0
}

priority_queue<TypeName> q;             // 数据类型为 TypeName
priority_queue<TypeName, Container> q;  // 使用 Container 作为底层容器
priority_queue<TypeName, Container, Compare> q;// 使用 Container 作为底层容器，使用 Compare 作为比较类型

void use_priority_queue() {
    // 默认使用底层容器 vector
    // 比较类型 less<TypeName>（此时为它的 top() 返回为最大值）
    // 若希望 top() 返回最小值，可令比较类型为 greater<TypeName>
    // 注意：不可跳过 Container 直接传入 Compare

    // 从 C++11 开始，如果使用 lambda 函数自定义 Compare
    // 则需要将其作为构造函数的参数代入，如：
    auto cmp
            = [](const pair<int, int> &l, const pair<int, int> &r) {
                return l.second < r.second;
            };
    priority_queue<pair<int, int>, vector<pair<int, int> >,decltype(cmp)> pq(cmp);

    // demo
    priority_queue<int> q1; // 默认顺序less<TypeName>
    priority_queue<int, vector<int> > q2;
    // C++11 后空格可省略
    priority_queue<int, deque<int>, greater<int> > q3;
    // q3 为小根堆

    for (int i = 1; i <= 5; i++) q1.push(i);
    // q1 中元素 :  [1, 2, 3, 4, 5]
    cout << q1.top() << endl;
    // 输出结果 : 5

    q1.pop();
    // 堆中元素 : [1, 2, 3, 4]
    cout << q1.size() << endl;
    // 输出结果 ：4

    for (int i = 1; i <= 5; i++) q3.push(i);
    // q3 中元素 :  [1, 2, 3, 4, 5]
    cout << q3.top() << endl;
    // 输出结果 : 1
}

int main() {

}