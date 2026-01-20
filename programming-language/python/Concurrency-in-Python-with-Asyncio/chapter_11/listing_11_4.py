import asyncio
from asyncio import Lock
from util import delay


async def a(lock: Lock):
    print('Coroutine a waiting to acquire the lock')
    async with lock:
        print('Coroutine a is in the critical section')
        await delay(2)
    print('Coroutine a released the lock')


async def b(lock: Lock):
    print('Coroutine b waiting to acquire the lock')
    async with lock:
        print('Coroutine b is in the critical section')
        await delay(2)
    print('Coroutine b released the lock')

# work in python 3.10+
# lock = Lock()

async def main():
    lock = Lock()
    await asyncio.gather(a(lock), b(lock))


asyncio.run(main())

# Coroutine a waiting to acquire the lock
# Coroutine a is in the critical section
# sleeping for 2 second(s)

# Coroutine b waiting to acquire the lock

# finished sleeping for 2 second(s)
# Coroutine a released the lock

# Coroutine b is in the critical section
# sleeping for 2 second(s)
# finished sleeping for 2 second(s)
# Coroutine b released the lock
