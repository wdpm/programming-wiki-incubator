# hanoi.py
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
from typing import TypeVar, Generic, List

T = TypeVar('T')


class Stack(Generic[T]):

    def __init__(self) -> None:
        self._container: List[T] = []

    def push(self, item: T) -> None:
        self._container.append(item)

    def pop(self) -> T:
        return self._container.pop()

    def __repr__(self) -> str:
        return repr(self._container)


num_discs: int = 3
tower_a: Stack[int] = Stack()
tower_b: Stack[int] = Stack()
tower_c: Stack[int] = Stack()
#  1 2 3 represents the big, middle, small plate respectively.
for i in range(1, num_discs + 1):
    tower_a.push(i)


def hanoi(begin: Stack[int], end: Stack[int], temp: Stack[int], n: int) -> None:
    """


    :param begin:
    :param end:
    :param temp:
    :param n:
    """
    if n == 1:
        end.push(begin.pop())
    else:

        #  begin A | end C | temp B

        # （1）将上层n−1个圆盘从塔A移到塔B（暂存塔），用塔C作为中转塔。
        hanoi(begin, temp, end, n - 1)
        # （2）将底层的圆盘从塔A移到塔C。
        hanoi(begin, end, temp, 1)
        # （3）将n−1个圆盘从塔B移到塔C，用塔A作为中转塔
        hanoi(temp, end, begin, n - 1)


if __name__ == "__main__":
    hanoi(tower_a, tower_c, tower_b, num_discs)
    print(tower_a)
    print(tower_b)
    print(tower_c)
