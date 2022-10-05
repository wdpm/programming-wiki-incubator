// unordered_set，unordered_multiset，unordered_map，unordered_multimap

// 制造哈希冲突

// 在哈希函数确定的情况下，可以构造出数据使得容器内产生大量哈希冲突，导致复杂度达到上界。
//
//在标准库实现里，每个元素的散列值是将值对一个质数取模得到的，V%N。更具体地说，是 这个列表 中的质数
// （g++ 6 及以前版本的编译器，这个质数一般是126271 ，g++ 7 及之后版本的编译器，这个质数一般是107897 ）。

// 因此可以通过向容器中插入这些模数的倍数来达到制造大量哈希冲突的目的。


#include <cstddef>
#include <cstdint>

// 自定义hash函数
struct my_hash {
    size_t operator()(int x) const { return x; }
};

// more complicated custom hash function
// refer https://codeforces.com/blog/entry/62393
struct my_hash2 {
    static uint64_t splitmix64(uint64_t x) {
        x += 0x9e3779b97f4a7c15;
        x = (x ^ (x >> 30)) * 0xbf58476d1ce4e5b9;
        x = (x ^ (x >> 27)) * 0x94d049bb133111eb;
        return x ^ (x >> 31);
    }

    size_t operator()(uint64_t x) const {
        static const uint64_t FIXED_RANDOM =
                chrono::steady_clock::now().time_since_epoch().count();
        return splitmix64(x + FIXED_RANDOM);
    }

    // 针对 std::pair<int, int> 作为主键类型的哈希函数
    size_t operator()(pair <uint64_t, uint64_t> x) const {
        static const uint64_t FIXED_RANDOM =
                chrono::steady_clock::now().time_since_epoch().count();
        return splitmix64(x.first + FIXED_RANDOM) ^
               (splitmix64(x.second + FIXED_RANDOM) >> 1);
    }
};

// usage
// - unordered_map<int, int, my_hash> my_map;
// - unordered_map<pair<int, int>, int, my_hash> my_pair_map;