import asyncio
from util import delay


async def main():
    delay_task = asyncio.create_task(delay(2))
    try:
        # 使用timeout来触发取消任务，是一种优雅的做法
        result = await asyncio.wait_for(delay_task, timeout=1)
        print(result)
    except asyncio.exceptions.TimeoutError:
        print('Got a timeout!')
        print(f'Was the task cancelled? {delay_task.cancelled()}')


asyncio.run(main())

# sleeping for 2 second(s)
# Got a timeout!
# Was the task cancelled? True
