from decimal import Decimal

print(1 / 3)
# 0.3333333333333333

print(0.1234567890123456789)
# 0.12345678901234568

# solution 1: use round()
print(round(0.1 * 3, 2) == round(0.3, 2))

print(round(0.1, 2) * 3 == round(0.3, 2))

# solution 2: use Decimal
print(Decimal("0.1") + Decimal("0.2"))
print(Decimal("0.3"))
print(Decimal("0.1") + Decimal("0.2") == Decimal("0.3"))

print(Decimal(0.1) == Decimal("0.1"))
# False
# 0.1在构建前已经丢失精度

# 使用timeit测试Decimal和float的运算效率，一般而言，Decimal慢于 float

print(round(2.5))
# 2
# 实际上可能是2.4xxxxxxxxxxxxxxxxxxxxxxxxxxxx => 2


# <1.24 -----1.245 ---------1.25>
print(round(1.245, 2))
# 1.25

from decimal import Decimal, ROUND_HALF_UP


def roundx(x, n):
    return Decimal(x).quantize(Decimal(n), ROUND_HALF_UP)


print(roundx("1.24", ".1"))
print(roundx("1.2456", ".01"))
