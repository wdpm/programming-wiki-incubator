import asyncio
from util import async_timed, delay


@async_timed()
async def main() -> None:
    delay_times = [3, 3, 3]
    tasks = [asyncio.create_task(delay(seconds)) for seconds in delay_times]
    [await task for task in tasks]

asyncio.run(main())

# 上面代码的弊端：
# 1.必须记住要分离task的创建和await
# 2.没有处理异常

# starting <function main at 0x00000299A45B2E60> with args () {}
# sleeping for 3 second(s)
# sleeping for 3 second(s)
# sleeping for 3 second(s)
# finished sleeping for 3 second(s)
# finished sleeping for 3 second(s)
# finished sleeping for 3 second(s)
# finished <function main at 0x00000299A45B2E60> in 3.0137 second(s)
