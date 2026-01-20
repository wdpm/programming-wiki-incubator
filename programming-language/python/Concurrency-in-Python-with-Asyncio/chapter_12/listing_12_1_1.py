import asyncio
from asyncio import Queue


async def main():
    customer_queue = Queue()
    customer_queue.get_nowait()


asyncio.run(main())
# asyncio.queues.QueueEmpty

# 场景：如果队列为空时，使用get_nowait去消费，那么会直接抛出异常，不会block等待。
# 要想修复此问题，可以使用阻塞的get()
