import numpy as np


def step_function(x):
    # convert to bool
    y = x > 0
    # convert back to int: 0 or 1
    return y.astype(np.int)


a = np.array([-0.6, 4, -0.3, 9])
res = step_function(a)
print(res)
