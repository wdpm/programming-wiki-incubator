import asyncio
import requests
from util import async_timed


@async_timed()
async def get_example_status() -> int:
    return requests.get('https://www.example.com').status_code


@async_timed()
async def main():
    task_1 = asyncio.create_task(get_example_status())
    task_2 = asyncio.create_task(get_example_status())
    task_3 = asyncio.create_task(get_example_status())
    await task_1
    await task_2
    await task_3

asyncio.run(main())

# starting <function main at 0x000001F6915793F0> with args () {}
# starting <function get_example_status at 0x000001F6904D3D00> with args () {}
# finished <function get_example_status at 0x000001F6904D3D00> in 1.2101 second(s)
# starting <function get_example_status at 0x000001F6904D3D00> with args () {}
# finished <function get_example_status at 0x000001F6904D3D00> in 1.2590 second(s)
# starting <function get_example_status at 0x000001F6904D3D00> with args () {}
# finished <function get_example_status at 0x000001F6904D3D00> in 1.2571 second(s)
# finished <function main at 0x000001F6915793F0> in 3.7272 second(s)

# requests.get() is blocking the only one thread => block event loop

# If you need to use the requests library, you can still use async syntax, but you’ll
# need to explicitly tell asyncio to use multithreading with a thread pool executor. We’ll see
# how to do this in chapter 7