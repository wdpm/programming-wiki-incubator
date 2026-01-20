# knapsack.py
# From Classic Computer Science Problems in Python Chapter 9
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
from typing import NamedTuple, List


class Item(NamedTuple):
    name: str
    weight: int
    value: float


# 所以，该集合{1,2,3}的幂集为： {{}, {1}, {2}, {3}, {1, 2}, {1, 3}, {2, 3}, {1, 2, 3}}。
# 可以看到，幂集包含了原始集合的所有可能子集，以及空集和原始集合本身。
# 幂集的大小可以通过以下公式计算：对于一个包含 n 个元素的集合，其幂集的大小为 2^n。
# 在上述例子中，原始集合 {1, 2, 3} 包含 3 个元素，所以它的幂集大小为 2^3 = 8
# 这种指数级别的暴力算法思路仅适用于小数据的集合。否则性能问题将是个不能忍受的问题。

# 性价比解法，将价值/重量得出性价比，按高到低进行排列。但是可能这样会产生无法填满背包的问题，因为这是个0/1问题。

# DP
# 具体而言，对于容纳能力为w的背包和前i个物品，我们有两种选择：
# - 不将第i个物品放入背包：最大价值等于前i-1个物品在容纳能力为w的背包中的最大价值，即dp[i-1][w]。
# - 将第i个物品放入背包：最大价值等于第i个物品的价值加上前i-1个物品在容纳能力为w-weight[i]的背包中的最大价值，即values[i] + dp[i-1][w-weight[i]]。
# 我们需要在这两种选择中选择较大的那个作为dp[i][w]的值。这样，我们可以利用已经计算出的dp表格中的结果来构建更大的子问题，并最终解决原始问题

def knapsack(items: List[Item], max_capacity: int) -> List[Item]:
    # build up dynamic programming table
    # 这个表有items+1行，有max_capacity + 1列。第一列表示0 capacity，第一行表示 0 items
    table: List[List[float]] = [[0.0 for _ in range(max_capacity + 1)] for _ in range(len(items) + 1)]
    # i表示前i个物品，item表示要偷取的下一个物品
    for i, item in enumerate(items):
        for capacity in range(1, max_capacity + 1):
            previous_items_value: float = table[i][capacity]
            print(f'capacity: {capacity}; item: {item}; i :{i}; pre_value: {previous_items_value}')
            if capacity >= item.weight:  # item fits in knapsack
                value_freeing_weight_for_item: float = table[i][capacity - item.weight]
                # only take if more valuable than previous item
                table[i + 1][capacity] = max(value_freeing_weight_for_item + item.value, previous_items_value)
                print(f"1) {table[i + 1][capacity]}")
            else:  # no room for this item
                table[i + 1][capacity] = previous_items_value
                print(f"2) {table[i + 1][capacity]}")

    # figure out solution from table
    solution: List[Item] = []
    capacity = max_capacity
    for i in range(len(items), 0, -1):  # work backwards
        # was this item used?
        print(f'table[{i - 1}][{capacity}]: {table[i - 1][capacity]} ?= table[{i}][{capacity}] :{table[i][capacity]}')
        if table[i - 1][capacity] != table[i][capacity]:
            # 如果i=N, items[i-1]表示最后一件物品，以此类推
            solution.append(items[i - 1])
            # if the item was used, remove its weight
            capacity -= items[i - 1].weight

    print(table)
    return solution


if __name__ == "__main__":
    items: List[Item] = [Item("television", 50, 500),
                         Item("candlesticks", 2, 300),
                         Item("stereo", 35, 400),
                         Item("laptop", 3, 1000),
                         Item("food", 15, 50),
                         Item("clothing", 20, 800),
                         Item("jewelry", 1, 4000),
                         Item("books", 100, 300),
                         Item("printer", 18, 30),
                         Item("refrigerator", 200, 700),
                         Item("painting", 10, 1000)]

    # 火柴（1斤）、手电筒（2斤）和书（1斤）。假设这些物品的价值分别
    # 为5美元、10美元和15美元
    small_items: List[Item] = [
        Item("火柴", 1, 5),
        Item("手电筒", 2, 10),
        Item("书", 1, 15),
    ]

    # print(knapsack(items, 75))

    print(knapsack(small_items, 3))

#        0   1     2     3
#  0  [[0.0, 0.0, 0.0, 0.0],
#  1   [0.0, 5.0, 5.0, 5.0],
#  2   [0.0, 5.0, 10.0, 15.0],
#  3   [0.0, 15.0, 20.0, 25.0]]
