import asyncio
import logging

import aiohttp

from chapter_04 import fetch_status
from util import async_timed


@async_timed()
async def main():
    async with aiohttp.ClientSession() as session:
        fetchers = [
            asyncio.create_task(fetch_status(session, 'python://bad.com')),
            asyncio.create_task(fetch_status(session, 'https://www.example.com', delay=3)),
            asyncio.create_task(fetch_status(session, 'https://www.example.com', delay=3))
        ]

        done, pending = await asyncio.wait(fetchers, return_when=asyncio.FIRST_EXCEPTION)

        print(f'Done task count: {len(done)}')
        print(f'Pending task count: {len(pending)}')

        for done_task in done:
            exception = done_task.exception()

            if exception is None:
                print('exception is None')
                print(done_task.result())
            else:
                print('exception is not None')
                logging.error("Request got an exception",
                              exc_info=exception)

        for pending_task in pending:
            pending_task.cancel()


asyncio.run(main())
