class VisitRecord:
    """旅客记录

    - 当两条旅客记录的姓名与电话号码相同时，判定二者相等。
    """

    def __init__(self, first_name, last_name, phone_number, date_visited):
        self.first_name = first_name
        self.last_name = last_name
        self.phone_number = phone_number
        self.date_visited = date_visited

    def __hash__(self):
        return hash(self.comparable_fields)

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.comparable_fields == other.comparable_fields
        return False

    @property
    def comparable_fields(self):
        """获取用于比较对象的字段值"""
        return (self.first_name, self.last_name, self.phone_number)


def find_potential_customers_v3():
    # 转换为 VisitRecord 对象后计算集合差值
    return set(VisitRecord(**r) for r in users_visited_puket) - set(VisitRecord(**r) for r in users_visited_nz)


# ==============================

from dataclasses import dataclass, field


@dataclass(frozen=True)
class VisitRecordDC:
    first_name: str
    last_name: str
    phone_number: str
    date_visited: str = field(compare=False)


def find_potential_customers_v4():
    return set(VisitRecordDC(**r) for r in users_visited_puket) - set(
        VisitRecordDC(**r) for r in users_visited_nz
    )
