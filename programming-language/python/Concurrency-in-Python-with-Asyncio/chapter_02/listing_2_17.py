import asyncio
from util import async_timed


@async_timed()
async def delay(delay_seconds: int) -> int:
    print(f'sleeping for {delay_seconds} second(s)')
    await asyncio.sleep(delay_seconds)
    print(f'finished sleeping for {delay_seconds} second(s)')
    return delay_seconds


@async_timed()
async def main():
    task_one = asyncio.create_task(delay(2))
    task_two = asyncio.create_task(delay(3))

    await task_one
    await task_two


asyncio.run(main())

# starting <function main at 0x0000016050772EF0> with args () {}

# starting <function delay at 0x000001604FFC2DD0> with args (2,) {}
# sleeping for 2 second(s)
# starting <function delay at 0x000001604FFC2DD0> with args (3,) {}
# sleeping for 3 second(s)

# finished sleeping for 2 second(s)
# finished <function delay at 0x000001604FFC2DD0> in 2.0126 second(s)
# finished sleeping for 3 second(s)
# finished <function delay at 0x000001604FFC2DD0> in 3.0006 second(s)

# finished <function main at 0x0000016050772EF0> in 3.0006 second(s)