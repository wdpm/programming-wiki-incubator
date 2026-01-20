# connectfour.py
# From Classic Computer Science Problems in Python Chapter 8
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
from typing import List, Optional, Tuple
from enum import Enum
from board import Piece, Board, Move


class C4Piece(Piece, Enum):
    B = "B"
    R = "R"
    E = " " # stand-in for empty

    @property
    def opposite(self) -> C4Piece:
        if self == C4Piece.B:
            return C4Piece.R
        elif self == C4Piece.R:
            return C4Piece.B
        else:
            return C4Piece.E

    def __str__(self) -> str:
        return self.value


def generate_segments(num_columns: int, num_rows: int, segment_length: int) -> List[List[Tuple[int, int]]]:
    """
    每个子列表包含4个网格方位。这4个网格方位组成的列表被称为一个区段.
    Tuple[int, int] 表示的是(列，行)，而不是(行，列)。注意顺序。
    :param num_columns:
    :param num_rows:
    :param segment_length:
    :return:
    """
    segments: List[List[Tuple[int, int]]] = []
    # generate the vertical segments
    # column: [0...col-1] => 特定列
    for c in range(num_columns):
        # row: [0,rows-seg+1) 注意右括号是不包含 => 特定列的垂直移动。
        for r in range(num_rows - segment_length + 1):
            segment: List[Tuple[int, int]] = []
            for t in range(segment_length):
                segment.append((c, r + t))
            segments.append(segment)

    # generate the horizontal segments
    for c in range(num_columns - segment_length + 1):
        for r in range(num_rows):
            segment = []
            for t in range(segment_length):
                segment.append((c + t, r))
            segments.append(segment)

    # generate the top left to bottom right diagonal segments
    for c in range(num_columns - segment_length + 1):
        for r in range(num_rows - segment_length + 1):
            segment = []
            for t in range(segment_length):
                segment.append((c + t, r + t))
            segments.append(segment)

    # generate the bottom left to top right diagonal segments
    for c in range(num_columns - segment_length + 1):
        for r in range(segment_length - 1, num_rows):
            segment = []
            for t in range(segment_length):
                segment.append((c + t, r - t))
            segments.append(segment)
    return segments


class C4Board(Board):
    NUM_ROWS: int = 6
    NUM_COLUMNS: int = 7
    SEGMENT_LENGTH: int = 4
    SEGMENTS: List[List[Tuple[int, int]]] = generate_segments(NUM_COLUMNS, NUM_ROWS, SEGMENT_LENGTH)

    class Column:
        def __init__(self) -> None:
            self._container: List[C4Piece] = []

        @property
        def full(self) -> bool:
            return len(self._container) == C4Board.NUM_ROWS

        def push(self, item: C4Piece) -> None:
            if self.full:
                raise OverflowError("Trying to push piece to full column")
            self._container.append(item)

        def __getitem__(self, index: int) -> C4Piece:
            if index > len(self._container) - 1:
                return C4Piece.E
            return self._container[index]

        def __repr__(self) -> str:
            return repr(self._container)

        def copy(self) -> C4Board.Column:
            temp: C4Board.Column = C4Board.Column()
            temp._container = self._container.copy()
            return temp

    def __init__(self, position: Optional[List[C4Board.Column]] = None, turn: C4Piece = C4Piece.B) -> None:
        if position is None:
            self.position: List[C4Board.Column] = [C4Board.Column() for _ in range(C4Board.NUM_COLUMNS)]
        else:
            self.position = position
        self._turn: C4Piece = turn

    @property
    def turn(self) -> Piece:
        return self._turn

    def move(self, location: Move) -> Board:
        temp_position: List[C4Board.Column] = self.position.copy()
        # 这里为何需要分别复制每个列，上面的position.copy不足够，担心共享最外层的List引用
        for c in range(C4Board.NUM_COLUMNS):
            temp_position[c] = self.position[c].copy()

        temp_position[location].push(self._turn)
        return C4Board(temp_position, self._turn.opposite)

    @property
    def legal_moves(self) -> List[Move]:
        return [Move(c) for c in range(C4Board.NUM_COLUMNS) if not self.position[c].full]

    # Returns the count of black & red pieces in a segment
    def _count_segment(self, segment: List[Tuple[int, int]]) -> Tuple[int, int]:
        black_count: int = 0
        red_count: int = 0
        for column, row in segment:
            if self.position[column][row] == C4Piece.B:
                black_count += 1
            elif self.position[column][row] == C4Piece.R:
                red_count += 1
        return black_count, red_count

    @property
    def is_win(self) -> bool:
        for segment in C4Board.SEGMENTS:
            black_count, red_count = self._count_segment(segment)
            if black_count == 4 or red_count == 4:
                return True
        return False

    def _evaluate_segment(self, segment: List[Tuple[int, int]], player: Piece) -> float:
        black_count, red_count = self._count_segment(segment)
        if red_count > 0 and black_count > 0:
            return 0 # mixed segments are neutral

        # 这里要么只有红，要么只有黑
        count: int = max(red_count, black_count)
        score: float = 0
        if count == 2:
            score = 1
        elif count == 3:
            score = 100
        elif count == 4:
            score = 1000000

        # 得到当前color
        color: C4Piece = C4Piece.B
        if red_count > black_count:
            color = C4Piece.R

        # 如果颜色变量与当前玩家不匹配（即评估的线段颜色与当前玩家颜色不一致），则返回负的评分值，表示这个线段对当前玩家不利
        if color != player:
            return -score

        return score

    def evaluate(self, player: Piece) -> float:
        total: float = 0
        for segment in C4Board.SEGMENTS:
            total += self._evaluate_segment(segment, player)
        return total

    def __repr__(self) -> str:
        display: str = ""
        # 为何reverse rows，这是为了符合人的思维习惯，将棋盘底部视为开始位置，作为一个view。上面的计算都是将顶部视为棋盘开始位置的。
        for r in reversed(range(C4Board.NUM_ROWS)):
            display += "|"
            for c in range(C4Board.NUM_COLUMNS):
                display += f"{self.position[c][r]}" + "|"
            display += "\n"
        return display

