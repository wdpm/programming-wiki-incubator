# fib2.py
# From Classic Computer Science Problems in Python Chapter 1
# Copyright 2018 David Kopec
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


def fib2(n: int) -> int:
    """
    该函数使用如果 n 小于 2 则返回 n 的基本情况递归地计算第 n 个斐波纳契数，否则返回第 (n-2) 个和第 (n-1) 个斐波纳契数之和。

    :param n: 参数“n”是一个整数，表示所需数字在斐波那契数列中的位置。
    :type n: int
    :return: 函数“fib2”返回第 n 个斐波那契数，其中 n 是输入参数。
    """
    if n < 2:  # base case
        return n
    return fib2(n - 2) + fib2(n - 1)  # recursive case


if __name__ == "__main__":
    print(fib2(5))
    print(fib2(10))