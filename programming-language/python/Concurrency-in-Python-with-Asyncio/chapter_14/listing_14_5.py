import asyncio


# DeprecationWarning: "@coroutine" decorator is deprecated since Python 3.8, use "async def" instead
@asyncio.coroutine
def coroutine():
    print('Sleeping!')
    yield from asyncio.sleep(1)
    print('Finished!')


asyncio.run(coroutine())
