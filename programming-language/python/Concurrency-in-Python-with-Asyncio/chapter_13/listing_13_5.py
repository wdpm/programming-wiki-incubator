import asyncio
from asyncio.subprocess import Process


async def main():
    program = ['python3', 'listing_13_4.py']
    process: Process = await asyncio.create_subprocess_exec(*program,
                                                            stdout=asyncio.subprocess.PIPE)
    print(f'Process pid is: {process.pid}')

    return_code = await process.wait()
    print(f'Process returned: {return_code}')


asyncio.run(main())

# 执行结果：
# Process pid is: 109
# [之后没有任何输出]

# When the stream reader’s buffer fills up,
# any more calls to write into it block until more space in the buffer becomes available.

# While our stream reader buffer is blocked because its buffer is full, our pro-
# cess is still trying to finish writing its large output to the stream reader. This makes our
# process dependent on the stream reader becoming unblocked, but the stream reader
# will never become unblocked because we never free up any space in the buffer. This is
# a circular dependency and therefore a deadlock