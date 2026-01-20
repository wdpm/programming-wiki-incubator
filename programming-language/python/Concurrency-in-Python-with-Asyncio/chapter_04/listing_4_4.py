import asyncio
from util import async_timed, delay


@async_timed()
async def main() -> None:
    delay_times = [3, 3, 3]
    # 在创建task时await，是错误的做法。
    [await asyncio.create_task(delay(seconds)) for seconds in delay_times]

asyncio.run(main())

# 9s