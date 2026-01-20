import asyncio
from util import async_timed


@async_timed()
async def cpu_bound_work() -> int:
    counter = 0
    for i in range(100000000):
        counter = counter + 1
    return counter


async def main() -> None:
    task_one = asyncio.create_task(cpu_bound_work())
    await task_one


# The default settings will log a warning if a coroutine takes longer than 100 millisecond
asyncio.run(main(), debug=True)

# starting <function cpu_bound_work at 0x000002031EDB2DD0> with args () {}
# finished <function cpu_bound_work at 0x000002031EDB2DD0> in 5.8860 second(s)
# Executing <Task finished name='Task-2' coro=<cpu_bound_work() done, ... took 5.891 seconds
