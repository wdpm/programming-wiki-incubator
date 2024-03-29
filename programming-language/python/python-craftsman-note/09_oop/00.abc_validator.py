# -*- coding: utf-8 -*-


class Validator:
    """校验器基类，校验不同种类的数据是否符合要求"""

    def validate(self, value):
        raise NotImplementedError

# 继承，多态：重写方法实现
class NumberValidator(Validator):
    """校验输入值是否是合法数字"""

    def validate(self, value):
        ...

# ==========================================

from abc import ABC, abstractmethod


# 利用ABC 实现抽象类
class Validator(ABC):
    """校验器抽象类"""

    @classmethod
    def __subclasshook__(cls, C):
        """任何提供了 validate 方法的类，都被当做是 Validator 的子类"""
        if any("validate" in Base.__dict__ for Base in C.__mro__):
            return True
        return NotImplemented

    @abstractmethod
    def validate(self, value):
        raise NotImplementedError


class StringValidator:
    def validate(self, value):
        ...


class InvalidValidator(Validator):
    ...


print(isinstance(StringValidator(), Validator))