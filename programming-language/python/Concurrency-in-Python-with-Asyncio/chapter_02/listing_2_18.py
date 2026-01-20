import asyncio
from util import async_timed


@async_timed()
async def cpu_bound_work() -> int:
    counter = 0
    for i in range(100000000):
        counter = counter + 1
    return counter


@async_timed()
async def main():
    task_one = asyncio.create_task(cpu_bound_work())
    task_two = asyncio.create_task(cpu_bound_work())
    await task_one
    await task_two


asyncio.run(main())

# starting <function main at 0x000002E6689D2EF0> with args () {}
# starting <function cpu_bound_work at 0x000002E6681A2DD0> with args () {}
# finished <function cpu_bound_work at 0x000002E6681A2DD0> in 6.2831 second(s)
# starting <function cpu_bound_work at 0x000002E6681A2DD0> with args () {}
# finished <function cpu_bound_work at 0x000002E6681A2DD0> in 6.2192 second(s)
# finished <function main at 0x000002E6689D2EF0> in 12.5023 second(s)