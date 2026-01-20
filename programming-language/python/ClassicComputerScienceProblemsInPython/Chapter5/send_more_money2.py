# send_more_money2.py
# From Classic Computer Science Problems in Python Chapter 5
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
from __future__ import annotations
from typing import Tuple, List
from chromosome import Chromosome
from genetic_algorithm import GeneticAlgorithm
from random import shuffle, sample
from copy import deepcopy


class SendMoreMoney2(Chromosome):
    def __init__(self, letters: List[str]) -> None:
        self.letters: List[str] = letters

    def fitness(self) -> float:
        s: int = self.letters.index("S")
        e: int = self.letters.index("E")
        n: int = self.letters.index("N")
        d: int = self.letters.index("D")
        m: int = self.letters.index("M")
        o: int = self.letters.index("O")
        r: int = self.letters.index("R")
        y: int = self.letters.index("Y")
        send: int = s * 1000 + e * 100 + n * 10 + d
        more: int = m * 1000 + o * 100 + r * 10 + e
        money: int = m * 10000 + o * 1000 + n * 100 + e * 10 + y
        difference: int = abs(money - (send + more))
        # GeneticAlgorithm要追求fitness()的值最大化，需要将该值反转一下（值越小看起来越大）
        # 分母+1是为了避免除0错误
        return 1 / (difference + 1)

    @classmethod
    def random_instance(cls) -> SendMoreMoney2:
        letters = ["S", "E", "N", "D", "M", "O", "R", "Y", " ", " "]
        shuffle(letters)
        return SendMoreMoney2(letters)

    def crossover(self, other: SendMoreMoney2) -> Tuple[SendMoreMoney2, SendMoreMoney2]:
        child1: SendMoreMoney2 = deepcopy(self)
        child2: SendMoreMoney2 = deepcopy(other)
        idx1, idx2 = sample(range(len(self.letters)), k=2)
        l1, l2 = child1.letters[idx1], child2.letters[idx2]

        # child1.letters[child1.letters.index(l2)] = child1.letters[idx2] ，表示将child1原来idx2位置的字母转移到child1某个其他位置
        # child1.letters[idx2] = l2 ，表示child2给了一个字母到child1的idx2位置

        # child 2同理
        # 最后结果就是，child1和child2交换了一个字母。
        child1.letters[child1.letters.index(l2)], child1.letters[idx2] = child1.letters[idx2], l2
        child2.letters[child2.letters.index(l1)], child2.letters[idx1] = child2.letters[idx1], l1
        return child1, child2

    def mutate(self) -> None: # swap two letters' locations
        idx1, idx2 = sample(range(len(self.letters)), k=2)
        self.letters[idx1], self.letters[idx2] = self.letters[idx2], self.letters[idx1]

    def __str__(self) -> str:
        s: int = self.letters.index("S")
        e: int = self.letters.index("E")
        n: int = self.letters.index("N")
        d: int = self.letters.index("D")
        m: int = self.letters.index("M")
        o: int = self.letters.index("O")
        r: int = self.letters.index("R")
        y: int = self.letters.index("Y")
        send: int = s * 1000 + e * 100 + n * 10 + d
        more: int = m * 1000 + o * 100 + r * 10 + e
        money: int = m * 10000 + o * 1000 + n * 100 + e * 10 + y
        difference: int = abs(money - (send + more))
        return f"{send} + {more} = {money} Difference: {difference}"


if __name__ == "__main__":
    initial_population: List[SendMoreMoney2] = [SendMoreMoney2.random_instance() for _ in range(1000)]
    ga: GeneticAlgorithm[SendMoreMoney2] = GeneticAlgorithm(initial_population=initial_population, threshold=1.0, max_generations = 1000, mutation_chance = 0.2, crossover_chance = 0.7, selection_type=GeneticAlgorithm.SelectionType.ROULETTE)
    result: SendMoreMoney2 = ga.run()
    print(result)

# Generation 0 Best 0.029411764705882353 Avg 0.0001370105817256066
# Generation 1 Best 0.09090909090909091 Avg 0.0032282416894655265
# Generation 2 Best 0.09090909090909091 Avg 0.016374219165436382
# Generation 3 Best 0.2 Avg 0.02586842720375327
# Generation 4 Best 0.25 Avg 0.03168974686238955
# Generation 5 Best 0.3333333333333333 Avg 0.03590813566318278
# Generation 6 Best 0.3333333333333333 Avg 0.04035548581682559
# 7429 + 814 = 8243 Difference: 0