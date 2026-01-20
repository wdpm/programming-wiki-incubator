# minimax.py
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
from board import Piece, Board, Move


# Find the best possible outcome for original player
def minimax(board: Board, maximizing: bool, original_player: Piece, max_depth: int = 8) -> float:
    """
    该算法的核心就是：如果是最大化玩家，那就最小化对手；如果是最小化玩家，那就最大化对手。这是0和博弈问题。
    :param board:
    :param maximizing:
    :param original_player:
    :param max_depth:
    :return:
    """
    # Base case – terminal position or maximum depth reached
    if board.is_win or board.is_draw or max_depth == 0:
        return board.evaluate(original_player)

    # Recursive case - maximize your gains or minimize the opponent's gains
    if maximizing:
        best_eval: float = float("-inf")  # arbitrarily low starting point
        for move in board.legal_moves:
            # 对于每个移动，递归调用minimax函数，传入更新后的游戏状态，将maximizing参数设置为False（因为下一个回合是对手的回合）
            result: float = minimax(board.move(move), False, original_player, max_depth - 1)
            best_eval = max(result, best_eval)  # we want the move with the highest evaluation
        return best_eval
    else:  # minimizing
        worst_eval: float = float("inf")
        for move in board.legal_moves:
            result = minimax(board.move(move), True, original_player, max_depth - 1)
            worst_eval = min(result, worst_eval)  # we want the move with the lowest evaluation
        return worst_eval


# 然而，Minimax算法在实际应用中可能受到搜索复杂度的限制，因此通常会使用一些优化技术，如Alpha-Beta剪枝，以减少搜索的分支数量，提高算法的效率

# 跟踪记录递归调用minimax()间的两个值α和β，即可实现神奇的优化效果。
# α表示搜索树当前找到的最优极大化走法的评分，
# 而β则表示当前找到的对手的最优极小化走法的评分。
# 如果β小于或等于α，则不值得对该搜索分支做进一步搜索，因为已经发现的走法比继续沿着该分支搜索得到的走法都要好或相当。这种启发式算法能显著缩小搜索空间
def alphabeta(board: Board, maximizing: bool, original_player: Piece, max_depth: int = 8, alpha: float = float("-inf"),
              beta: float = float("inf")) -> float:
    """这是一个 Python 函数，它实现了双人棋盘游戏的 alpha-beta 剪枝算法。

    alpha代表得分下限，beta代表得分上限，如果你的上限比别人的下限还低，那就抛弃你。

    Parameters
    ----------
    board : Board
    	游戏板的当前状态。
    maximizing : bool
    	一个布尔值，指示当前玩家是最大化还是最小化他们的分数。如果为 True，则当前玩家正在最大化他们的分数，如果为 False，则他们正在最小化他们的分数。
    original_player : Piece
    	发起alphabeta搜索的原始玩家。这很重要，因为不同玩家的评估函数可能不同。
    max_depth : int, optional
    	算法在返回值之前将探索的搜索树的最大深度。这用于限制算法用于查找解决方案的时间和资源量。
    alpha : float
    	Alpha 是最大化玩家当前在该级别或更高级别可以保证的最佳值。它是最大化玩家可能得分的下限。
    beta : float
    	Beta 是最大化参与者愿意接受的可能解决方案的最小上限。它用于 alpha-beta 剪枝算法，以确定是否可以剪枝博弈树的分支。如果当前节点的beta值小于或等于的alpha值

    """

    # Base case – terminal position or maximum depth reached
    if board.is_win or board.is_draw or max_depth == 0:
        return board.evaluate(original_player)

    # Recursive case - maximize your gains or minimize the opponent's gains
    if maximizing:
        for move in board.legal_moves:
            result: float = alphabeta(board.move(move), False, original_player, max_depth - 1, alpha, beta)
            alpha = max(result, alpha)
            if beta <= alpha:
                break
        return alpha
    else:  # minimizing
        for move in board.legal_moves:
            result = alphabeta(board.move(move), True, original_player, max_depth - 1, alpha, beta)
            beta = min(result, beta)
            if beta <= alpha:
                break
        return beta

# 极小化极大搜索的最佳优化方案不外乎两种，一种是在规定的时间内搜索更深的深度，另一种就是改进棋局评分函数

# 1）思考迭代加深算法。
# 迭代加深技术使得AI能够耗费固定时长来找到下一步走法，而不是固定的搜索深度以及不定的完成时长。
# 先是最大深度1，然后最大深度2，......以此类推，直到timeout。最后一次完成的搜索深度的结果将会被返回

# 2）采用更多的参数或启发式算法来对棋局进行评分可能会
# 耗费更多的时间，但最终能够获得更优质的发动机，即用更少的搜索深度找到最优走法

# Find the best possible move in the current position
# looking up to max_depth ahead
# 因为minimax()只给出最佳评分，没有给出对应最佳评分的走法。
# 我们要创建一个辅助函数find_best_move()来为某棋局中每一
# 步合法的走法循环调用minimax()，以便找出评分最高的走法。
def find_best_move(board: Board, max_depth: int = 8) -> Move:
    best_eval: float = float("-inf")
    best_move: Move = Move(-1)
    for move in board.legal_moves:
        # result: float = minimax(board.move(move), False, board.turn, max_depth)
        result: float = alphabeta(board.move(move), False, board.turn, max_depth)
        if result > best_eval:
            best_eval = result
            best_move = move
    # print(f'best eval: {best_eval}')
    return best_move
