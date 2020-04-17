import numpy as np

# (w1, w2, θ) = (−0.5, −0.5, −0.7), θ =-b
def NAND(x1, x2):
    x = np.array([x1, x2])
    w = np.array([-0.5, -0.5])
    b = 0.7
    tmp = np.sum(w * x) + b
    if tmp <= 0:
        return 0
    else:
        return 1

# (w1, w2, θ) = (0.5, 0.5, 0.2), θ =-b
def OR(x1, x2):
    x = np.array([x1, x2])
    w = np.array([0.5, 0.5])
    b = -0.2
    tmp = np.sum(w * x) + b
    if tmp <= 0:
        return 0
    else:
        return 1


# (w1, w2, θ) = (0.5, 0.5, 0.7), θ =-b
def AND(s1, s2):
    x = np.array([s1, s2])
    w = np.array([0.5, 0.5])
    b = -0.7
    tmp = np.sum(w * x) + b
    if tmp <= 0:
        return 0
    else:
        return 1

# NAND & OR = XOR
def XOR(x1, x2):
    s1 = NAND(x1, x2)
    s2 = OR(x1, x2)
    y = AND(s1, s2)
    return y


xor00 = XOR(0, 0)
xor01 = XOR(0, 1)
xor10 = XOR(1, 0)
xor11 = XOR(1, 1)
print(xor00,xor01,xor10,xor11)
# 0 1 1 0
