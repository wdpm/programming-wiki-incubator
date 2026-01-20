import asyncio

from util import async_timed, delay


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
    delay_task = asyncio.create_task(delay(4))
    await task_one
    await task_two
    await delay_task


asyncio.run(main())

# starting <function main at 0x00000276A5262EF0> with args () {}
# starting <function cpu_bound_work at 0x00000276A4A92DD0> with args () {}
# finished <function cpu_bound_work at 0x00000276A4A92DD0> in 5.3080 second(s)
# starting <function cpu_bound_work at 0x00000276A4A92DD0> with args () {}
# finished <function cpu_bound_work at 0x00000276A4A92DD0> in 5.8248 second(s)
# sleeping for 4 second(s)
# finished sleeping for 4 second(s)
# finished <function main at 0x00000276A5262EF0> in 15.1366 second(s)

# 对于CPU密集型运算，使用asyncio模型毫无增益
