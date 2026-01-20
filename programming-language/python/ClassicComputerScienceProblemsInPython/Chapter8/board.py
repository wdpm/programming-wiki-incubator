# board.py
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
from typing import NewType, List
from abc import ABC, abstractmethod

Move = NewType('Move', int)


# Piece 是一个基类，用于表示游戏棋盘上的棋子。
# Piece 还兼有回合指示器的作用，因此它需要带有 opposite 属性。我们必须知道给定回合之后该轮到谁走棋了
class Piece:
    @property
    def opposite(self) -> Piece:
        """
        表示下一个回合该走的棋子是什么类型，例如井字棋，如果当前是O，那么下一个回合就是X
        :return:
        """
        raise NotImplementedError("Should be implemented by subclasses.")


class Board(ABC):
    @property
    @abstractmethod
    def turn(self) -> Piece:
        ...

    @abstractmethod
    def move(self, location: Move) -> Board:
        ...

    @property
    @abstractmethod
    def legal_moves(self) -> List[Move]:
        """
        剩余可以合法走的步骤，长度为 0 表示棋局结束

        :return:
        """
        ...

    @property
    @abstractmethod
    def is_win(self) -> bool:
        ...

    @property
    def is_draw(self) -> bool:
        return (not self.is_win) and (len(self.legal_moves) == 0)

    @abstractmethod
    def evaluate(self, player: Piece) -> float:
        """
        评估当前位置，看看哪位玩家占据了优势
        :param player: 
        :return: 
        """
        ...

