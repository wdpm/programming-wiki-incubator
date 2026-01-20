import asyncio
from asyncio.subprocess import Process


async def main():
    process: Process = await asyncio.create_subprocess_exec('ls', '-l')
    # process: Process = await asyncio.create_subprocess_exec('cmd /c', 'dir')
    print(f'Process pid is: {process.pid}')
    status_code = await process.wait()
    print(f'Status code: {status_code}')


asyncio.run(main())

# asyncio.wait_for 可以引入task的超时机制，但是只会取消task，不会终止背后的process

# kill or terminate
# Note that both these methods are not coroutines and are also non-
# blocking. They just send the signal. If you want to try and get the return code once
# you’ve terminated the subprocess or you want to wait for any cleanup, you’ll need to call wait again.
