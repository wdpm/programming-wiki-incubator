async def coroutine_add_one(number: int) -> int:
    return number + 1


def add_one(number: int) -> int:
    return number + 1


function_result = add_one(1)
coroutine_result = coroutine_add_one(1)

print(f'Function result is {function_result} and the type is {type(function_result)}')
print(f'Coroutine result is {coroutine_result} and the type is {type(coroutine_result)}')

# Function result is 2 and the type is <class 'int'>
# Coroutine result is <coroutine object coroutine_add_one at 0x0000023385C963B0> and the type is <class 'coroutine'>