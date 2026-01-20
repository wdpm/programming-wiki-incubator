import asyncio
from asyncio.subprocess import Process


async def main():
    # process: Process = await asyncio.create_subprocess_exec('sleep', '3')
    process: Process = await asyncio.create_subprocess_exec('cmd', 'start', '/wait', 'timeout', '3')
    print(f'Process pid is: {process.pid}')
    try:
        status_code = await asyncio.wait_for(process.wait(), timeout=1.0)
        print(status_code)
    except asyncio.TimeoutError:
        print('Timed out waiting to finish, terminating...')
        process.terminate()
        # you may want to wrap this in a wait_for if this is a concern
        status_code = await process.wait()
        print(status_code)
        # 1


asyncio.run(main())
