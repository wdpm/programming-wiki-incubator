import asyncio
import logging

import aiohttp

from chapter_04 import fetch_status
from util import async_timed


@async_timed()
async def main():
    async with aiohttp.ClientSession() as session:
        good_request = fetch_status(session, 'https://www.example.com')
        bad_request = fetch_status(session, 'python://bad')

        fetchers = [asyncio.create_task(good_request),
                    asyncio.create_task(bad_request)]

        done, pending = await asyncio.wait(fetchers)

        print(f'Done task count: {len(done)}')
        print(f'Pending task count: {len(pending)}')

        for done_task in done:
            # await done_task will throw an exception
            exception = done_task.exception()

            if exception is None:
                print(done_task.result())
            else:
                logging.error("Request got an exception",
                              exc_info=exception)
                # print(done_task.exception())


asyncio.run(main())
