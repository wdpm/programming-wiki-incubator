import asyncio
from util import delay


def call_later():
    print("I'm being called in the future!")


async def main():
    # This function can potentially create a new event loop if it is called when one is
    # not already running, leading to strange behavior
    # loop = asyncio.get_event_loop()
    loop = asyncio.get_running_loop()
    loop.call_soon(call_later)
    await delay(1)


asyncio.run(main())

# sleeping for 1 second(s)
# I'm being called in the future!
# finished sleeping for 1 second(s)
