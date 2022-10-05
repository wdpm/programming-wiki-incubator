// Set

// 插入与删除操作
// - insert(x) 当容器中没有等价元素的时候，将元素 x 插入到 set 中。
// - erase(x) 删除值为 x 的 所有 元素，返回删除元素的个数。
// - erase(pos) 删除迭代器为 pos 的元素，要求迭代器必须合法。
// - erase(first,last) 删除迭代器在  范围内的所有元素。
// - clear() 清空 set。

// set 提供了以下几种迭代器：
// - begin()/cbegin()
// 返回指向首元素的迭代器，其中 *begin = front。
// - end()/cend()
// 返回指向数组尾端占位符的迭代器，注意是没有元素的。
// - rbegin()/crbegin()
// 返回指向逆向数组的首元素的逆向迭代器，可以理解为正向容器的末元素。
// - rend()/crend()
// 返回指向逆向数组末元素后一位置的迭代器，对应容器首的前一个位置，没有元素。

// 查找操作
// - count(x) 返回 set 内键为 x 的元素数量。
// - find(x) 在 set 内存在键为 x 的元素时会返回该元素的迭代器，否则返回 end()。
// - lower_bound(x) 返回指向首个不小于给定键的元素的迭代器。如果不存在这样的元素，返回 end()。
// - upper_bound(x) 返回指向首个大于给定键的元素的迭代器。如果不存在这样的元素，返回 end()。
// - empty() 返回容器是否为空。
// - size() 返回容器内元素个数。

// Map
// 插入与删除操作
// 可以直接通过下标访问来进行查询或插入操作。例如 mp["Alan"]=100。
// 通过向 map 中插入一个类型为 pair<Key, T> 的值可以达到插入元素的目的，例如 mp.insert(pair<string,int>("Alan",100));；
// - erase(key) 函数会删除键为 key 的 所有 元素。返回值为删除元素的数量。
// - erase(pos): 删除迭代器为 pos 的元素，要求迭代器必须合法。
// - erase(first,last): 删除迭代器在 [first,last) 范围内的所有元素。
// - clear() 函数会清空整个容器。

//查询操作
//count(x): 返回容器内键为 x 的元素数量。复杂度为 （关于容器大小对数复杂度，加上匹配个数）。
//find(x): 若容器内存在键为 x 的元素，会返回该元素的迭代器；否则返回 end()。
//lower_bound(x): 返回指向首个不小于给定键的元素的迭代器。
//upper_bound(x): 返回指向首个大于给定键的元素的迭代器。若容器内所有元素均小于或等于给定键，返回 end()。
//empty(): 返回容器是否为空。
//size(): 返回容器内元素个数。

#include <set>
#include <map>
#include <string>

using namespace std;

struct cmp {
    bool operator()(int a, int b) { return a > b; }
};

// 想要维护一个存储整数，且较大值靠前的 set
set<int, cmp> s;

int main(){
    // 现存可用的元素
    set<int> available;
    // 需要大于等于的值
    int x;

    // 查找最小的大于等于x的元素
    auto it = available.lower_bound(x);
    if (it == available.end()) {
        // 不存在这样的元素，则进行相应操作……
    } else {
        // 找到了这样的元素，将其从现存可用元素中移除
        available.erase(it);
        // 进行相应操作……
    }


    // map
    // 存储状态与对应的答案
    map<string, int> record;

    // 新搜索到的状态与对应答案
    string status;
    int ans;
    // 查找对应的状态是否出现过
    auto it2 = record.find(status);
    if (it2 == record.end()) {
        // 尚未搜索过该状态，将其加入状态记录中
        record[status] = ans;
        // 进行相应操作……
    } else {
        // 已经搜索过该状态，进行相应操作……
    }
}