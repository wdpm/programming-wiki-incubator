import asyncio
from asyncio import Semaphore


async def acquire(semaphore: Semaphore):
    print('Waiting to acquire')
    async with semaphore:
        print('Acquired')
        await asyncio.sleep(5)
    print('Releasing')


async def release(semaphore: Semaphore):
    print('Releasing as a one off!')
    semaphore.release()
    print('Released as a one off!')


async def main():
    semaphore = Semaphore(2)

    print("Acquiring twice, releasing three times...")
    await asyncio.gather(acquire(semaphore),
                         acquire(semaphore),
                         release(semaphore))

    print("Acquiring three times...")
    await asyncio.gather(acquire(semaphore),
                         acquire(semaphore),
                         acquire(semaphore))


asyncio.run(main())

# 这个代码有问题，因为第一次的gather中，超量释放了锁，导致后续的代码逻辑很诡异。
# 本来第二次的gather是不可能同时获取3把锁的，因为前面的超量释放，导致居然可以同时获取3把锁，很离谱。
# 虽然代码还是顺利执行了。但是这种现象是不符合期望的。应该避免。
