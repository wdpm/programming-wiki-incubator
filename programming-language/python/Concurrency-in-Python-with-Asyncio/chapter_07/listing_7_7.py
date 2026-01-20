import requests
import asyncio
from util import async_timed


def get_status_code(url: str) -> int:
    response = requests.get(url)
    return response.status_code


@async_timed()
async def main():
    urls = ['https://www.example.com' for _ in range(1000)]
    # compare: loop.run_in_executor(pool, functools.partial(get_status_code, url))
    # `to_thread()` provide more friendly interface to call function
    tasks = [asyncio.to_thread(get_status_code, url) for url in urls]
    results = await asyncio.gather(*tasks)
    print(results)

asyncio.run(main())

# finished <function main at 0x0000029C4C04D1B0> in 94.1292 second(s)
