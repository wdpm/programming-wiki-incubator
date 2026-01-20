import asyncio
from asyncio import Queue


async def main():
    queue = Queue(maxsize=1)
    queue.put_nowait(1)
    queue.put_nowait(2)


asyncio.run(main())
# asyncio.queues.QueueFull

# 当队列满时，此时不能继续入列。
