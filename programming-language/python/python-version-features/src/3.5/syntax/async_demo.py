import asyncio


async def print_numbers():
    for i in range(10):
        print(i)
        await asyncio.sleep(1)


async def run_async_code():
    await print_numbers()


loop = asyncio.get_event_loop()
loop.run_until_complete(run_async_code())