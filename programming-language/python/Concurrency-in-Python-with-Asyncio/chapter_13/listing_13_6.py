import asyncio
from asyncio.subprocess import Process


async def main():
    program = ['python3', 'listing_13_4.py']
    process: Process = await asyncio.create_subprocess_exec(*program,
                                                            stdout=asyncio.subprocess.PIPE)
    print(f'Process pid is: {process.pid}')

    # This coroutine blocks until the subprocess completes and concur-
    # rently consumes standard output and standard error, returning the output complete
    # once the application finishes.
    stdout, stderr = await process.communicate()
    print(stdout)
    print(stderr)
    print(f'Process returned: {process.returncode}')


asyncio.run(main())

#  While we avoid potential deadlocks, we have a serious drawback in
# that we can’t interactively process output from standard output.

# An additional drawback is that communicate buffers all the data from standard
# output and standard input in memory. If you’re working with a subprocess that could
# produce a large amount of data, you run the risk of running out of memory
