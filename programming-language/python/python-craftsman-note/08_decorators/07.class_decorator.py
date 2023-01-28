_validators = {}


class ValidatorMeta(type):
    """元类：将所有校验器类统一注册起来，方便后续使用"""

    # 注意：元类的 __new__ 方法会在创建类（声明）时被调用，而不是子类实例化时被调用。
    # (1) name：str，需要创建的类名。
    # (2) bases：Tuple[Type]，包含其他类的元组，代表类的所有基类。
    # (3) attrs：Dict[str, Any]，包含所有类成员（属性、方法）的字典
    def __new__(cls, name, bases, attrs):
        ret = super().__new__(cls, name, bases, attrs)
        _validators[attrs['name']] = ret
        return ret


class StringValidator(metaclass=ValidatorMeta):
    name = 'string'


class IntegerValidator(metaclass=ValidatorMeta):
    name = 'int'


print(_validators)

# =====================================

_validators = {}


def register(cls):
    """装饰器：将所有校验器类统一注册起来，方便后续使用"""
    _validators[cls.name] = cls
    return cls


@register
class StringValidator:
    name = 'string'


@register
class IntegerValidator:
    name = 'int'


print(_validators)