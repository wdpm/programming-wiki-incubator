import asyncio
import logging
from asyncio.events import AbstractEventLoop
from concurrent.futures import ProcessPoolExecutor
from functools import partial
from typing import List

logger = logging.getLogger(__name__)

def countdown(count_from: int) -> int:
    counter = 0
    while counter < count_from:
        counter = counter + 1
    # print('countdown')
    # no log
    logger.info("countdown")
    return counter


async def main():
    with ProcessPoolExecutor() as process_pool:
        loop: AbstractEventLoop = asyncio.get_running_loop()
        nums = [1, 3, 5, 22, 100000000]
        calls: List[partial[int]] = [partial(countdown, num) for num in nums]
        call_coros = []

        for call in calls:
            call_coros.append(loop.run_in_executor(process_pool, call))

        # results = await asyncio.gather(*call_coros)
        # for result in results:
        #     print(result)

        for future in asyncio.as_completed(call_coros):
            print(await future)


if __name__ == "__main__":
    asyncio.run(main())
