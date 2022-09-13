import random
from typing import List


class Duck:
    def __init__(self, color: str):
        self.color = color

    def quack(self) -> None:
        print(f"Hi, I'm a {self.color} duck!")


def create_random_ducks(number: int) -> List[Duck]:
    """创建一批随机颜色鸭子

    :param number: 需要创建的鸭子数量
    """
    ducks: List[Duck] = []
    # for _ in range(number):
    for _ in number:
        color = random.choice(['yellow', 'white', 'gray'])
        ducks.append(Duck(color=color))
    return ducks
