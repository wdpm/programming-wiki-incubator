import asyncio

import aiohttp

from chapter_04 import fetch_status
from util import async_timed


@async_timed()
async def main():
    async with aiohttp.ClientSession() as session:
        urls = ['https://example.com' for _ in range(1000)]
        # sync one by one
        # status_codes = [await fetch_status(session, url) for url in urls]

        requests = [fetch_status(session, url) for url in urls]
        status_codes = await asyncio.gather(*requests)
        print(status_codes)


asyncio.run(main())

# finished <function main at 0x0000023FD3F23640> in 62.6669 second(s)
