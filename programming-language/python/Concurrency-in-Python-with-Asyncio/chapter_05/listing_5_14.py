import asyncio
from util import delay, async_timed


async def positive_integers_async(until: int):
    for integer in range(1, until):
        await delay(integer)
        yield integer


@async_timed()
async def main():
    async_generator = positive_integers_async(3)
    print(type(async_generator))
    async for number in async_generator:
        print(f'Got number {number}')


asyncio.run(main())

# starting <function main at 0x00000212F3AB6EF0> with args () {}
# <class 'async_generator'>
# sleeping for 1 second(s)
# finished sleeping for 1 second(s)
# Got number 1
# sleeping for 2 second(s)
# finished sleeping for 2 second(s)
# Got number 2
# finished <function main at 0x00000212F3AB6EF0> in 3.0229 second(s)
