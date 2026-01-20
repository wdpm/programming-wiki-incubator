import asyncio
from asyncio import BoundedSemaphore


async def main():
    semaphore = BoundedSemaphore(1)

    await semaphore.acquire()
    semaphore.release()

    # ValueError: BoundedSemaphore released too many times
    semaphore.release()


asyncio.run(main())
