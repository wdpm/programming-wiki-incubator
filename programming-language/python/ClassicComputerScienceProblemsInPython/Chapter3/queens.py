# queens.py
# From Classic Computer Science Problems in Python Chapter 3
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
from csp import Constraint, CSP
from typing import Dict, List, Optional


class QueensConstraint(Constraint[int, int]):
    def __init__(self, columns: List[int]) -> None:
        super().__init__(columns)
        self.columns: List[int] = columns

    def satisfied(self, assignment: Dict[int, int]) -> bool:
        """
        八皇后的约束，横向、竖向、对角线方向都不能看见别的皇后，否则她们会打架。

        * 在这个实现中，不可能同列
        * 因此只需要检测是否同行和是否同对角线。

        :param assignment:
        :return:
        """
        # q1c = queen 1 column, q1r = queen 1 row
        for q1c, q1r in assignment.items():
            # q2c = queen 2 column [q1c+1...N]
            for q2c in range(q1c + 1, len(self.columns) + 1):
                # 如果当前q2c这一列已经被赋值，那就需要检测，否则就跳过检测
                if q2c in assignment:
                    q2r: int = assignment[q2c] # q2r = queen 2 row
                    if q1r == q2r: # same row?
                        return False
                    if abs(q1r - q2r) == abs(q1c - q2c): # same diagonal? 行差=列差
                        return False
        return True # no conflict


if __name__ == "__main__":
    #  8x8 棋盘

    # 先定义竖直的列
    columns: List[int] = [1, 2, 3, 4, 5, 6, 7, 8]

    # 特定列的row取值，看成是竖直滑动。
    rows: Dict[int, List[int]] = {}
    for column in columns:
        rows[column] = [1, 2, 3, 4, 5, 6, 7, 8]

    csp: CSP[int, int] = CSP(columns, rows)
    csp.add_constraint(QueensConstraint(columns))
    solution: Optional[Dict[int, int]] = csp.backtracking_search()
    if solution is None:
        print("No solution found!")
    else:
        print(solution)
        # {1: 1, 2: 5, 3: 8, 4: 6, 5: 3, 6: 7, 7: 2, 8: 4}

# 问题，如何求出多组解，或者全部解的集合。
