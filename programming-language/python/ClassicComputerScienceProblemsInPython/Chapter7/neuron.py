# neuron.py
# From Classic Computer Science Problems in Python Chapter 7
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
from typing import List, Callable
from util import dot_product


class Neuron:
    def __init__(self, weights: List[float], learning_rate: float, activation_function: Callable[[float], float], derivative_activation_function: Callable[[float], float]) -> None:
        self.weights: List[float] = weights
        self.activation_function: Callable[[float], float] = activation_function
        # 输出神经元的激活函数的导数将会应用于该神经元在其激活函数被应用之前输出的值，也就是output_cache
        self.derivative_activation_function: Callable[[float], float] = derivative_activation_function
        # 学习率是什么，如何确定的？
        self.learning_rate: float = learning_rate
        self.output_cache: float = 0.0
        # 将求导结果再乘以神经元的误差，求其delta。求delta公式用到了偏导数，其微积分推导过程超出了本书的范围
        self.delta: float = 0.0

    def output(self, inputs: List[float]) -> float:
        self.output_cache = dot_product(inputs, self.weights)
        return self.activation_function(self.output_cache)

