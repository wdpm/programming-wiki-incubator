import time


def print_fib(number: int) -> None:
    def fib(n: int) -> int:
        if n == 1:
            return 0
        elif n == 2:
            return 1
        else:
            return fib(n - 1) + fib(n - 2)

    print(f'fib({number}) is {fib(number)}')


def fibs_no_threading():
    print_fib(40)
    print_fib(41)


start = time.time()

fibs_no_threading()

end = time.time()

print(f'Completed in {end - start:.4f} seconds.')

# fib(40) is 63245986
# fib(41) is 102334155
# Completed in 88.8098 seconds.

# f(40) 和 f(41) 可以分离到不同的线程中计算。
