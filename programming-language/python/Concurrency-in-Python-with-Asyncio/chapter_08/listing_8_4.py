import asyncio
from util import delay


async def main():
    while True:
        delay_time = input('Enter a time to sleep:')
        asyncio.create_task(delay(int(delay_time)))
        # hack
        await asyncio.sleep(0)


asyncio.run(main())

# Enter a time to sleep:3
# sleeping for 3 second(s)
# Enter a time to sleep:5
# sleeping for 5 second(s)
# Enter a time to sleep: