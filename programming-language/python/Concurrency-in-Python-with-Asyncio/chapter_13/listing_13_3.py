import asyncio
from asyncio import StreamReader
from asyncio.subprocess import Process


async def write_output(prefix: str, stdout: StreamReader):
    while line := await stdout.readline():
        print(f'[{prefix}]: {line.rstrip().decode()}')


async def main():
    program = ['ls', '-la']
    # program = ['cmd', '/c dir']

    # we want to pipe stdout
    process: Process = await asyncio.create_subprocess_exec(*program,
                                                            stdout=asyncio.subprocess.PIPE)
    print(f'Process pid is: {process.pid}')
    stdout_task = asyncio.create_task(write_output(' '.join(program), process.stdout))

    return_code, _ = await asyncio.gather(process.wait(), stdout_task)
    print(f'Process returned: {return_code}')


asyncio.run(main())

# One crucial aspect of using pipes, and dealing with subprocesses input and output in
# general, is that they are susceptible to deadlocks. The wait coroutine is especially susceptible to this
# if our subprocess generates a lot of output, and we don’t properly consume it.