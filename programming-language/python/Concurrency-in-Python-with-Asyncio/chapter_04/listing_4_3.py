import asyncio

import aiohttp
from aiohttp import ClientSession


async def fetch_status(session: ClientSession,
                       url: str) -> int:
    # override timeout
    ten_millis = aiohttp.ClientTimeout(total=.1)  # asyncio.exceptions.TimeoutError
    async with session.get(url, timeout=ten_millis) as result:
        return result.status


async def main():
    session_timeout = aiohttp.ClientTimeout(total=10, connect=3)
    async with aiohttp.ClientSession(timeout=session_timeout) as session:

        status = await fetch_status(session, 'https://example.com')
        # print(status)

        # try:
        #     status = await fetch_status(session, 'https://example.com')
        #     print(status)
        # except Exception as e:
        #     print(e)


asyncio.run(main())

# main()

# connect=.01
# aiohttp.client_exceptions.ServerTimeoutError: Connection timeout to host https://example.com

# total=.1
# asyncio.exceptions.TimeoutError
