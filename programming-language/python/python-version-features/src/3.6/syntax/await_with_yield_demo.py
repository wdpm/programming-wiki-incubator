import asyncio


async def ticker(delay, to):
    """Yield numbers from 0 to *to* every *delay* seconds."""
    for i in range(to):
        await asyncio.sleep(delay)
        yield i


async def main():
    async for i in ticker(1, 10):
        print(i)


asyncio.run(main())
