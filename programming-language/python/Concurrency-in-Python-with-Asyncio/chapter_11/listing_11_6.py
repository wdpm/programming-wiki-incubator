import asyncio
from asyncio import Semaphore


async def operation(semaphore: Semaphore):
    print('Waiting to acquire semaphore...')
    async with semaphore:
        print('Semaphore acquired!')
        await asyncio.sleep(2)
    print('Semaphore released!')


async def main():
    semaphore = Semaphore(2)
    await asyncio.gather(*[operation(semaphore) for _ in range(4)])


asyncio.run(main())

# 场景描述：同时只能有两个并行的op，但是一共有4个任务需要执行。

# Waiting to acquire semaphore...
# Semaphore acquired!
# Waiting to acquire semaphore...
# Semaphore acquired!

# ----------------------------------------
# 此时信号量为0，后续的任务需要等待信号量的释放。
# Waiting to acquire semaphore...
# Waiting to acquire semaphore...
# ----------------------------------------

# ----------------------------------------
# 前面两个任务执行完毕，信号量释放，信号量回到2
# Semaphore released!
# Semaphore released!
# ----------------------------------------

# ----------------------------------------
# 最后的两个任务此时能够获取信号量了
# Semaphore acquired!
# Semaphore acquired!
# ----------------------------------------

# ----------------------------------------
# 最后的两个任务执行完毕，释放信号量
# Semaphore released!
# Semaphore released!
# ----------------------------------------
