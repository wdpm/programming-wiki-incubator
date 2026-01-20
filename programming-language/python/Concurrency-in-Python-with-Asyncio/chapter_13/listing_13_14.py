import asyncio
from asyncio import StreamWriter, StreamReader, Event
from asyncio.subprocess import Process


async def output_consumer(input_ready_event: Event, stdout: StreamReader):
    while (data := await stdout.read(1024)) != b'':
        print(data)
        # Our coroutine that reads standard output will set an event once it has received the input prompt we expect.
        if data.decode().endswith("Enter text to echo: "):
            input_ready_event.set()


async def input_writer(text_data, input_ready_event: Event, stdin: StreamWriter):
    for text in text_data:
        # Our coroutine that writes to standard input will wait for that event to be set, then once it is,
        # it will write the specified text

        # 此时，需要等待output那边来到“Enter text to echo: ”时刻
        await input_ready_event.wait()

        # 已经是“Enter text to echo: ”，那就输入文本，刷新buffer池
        stdin.write(text.encode())
        await stdin.drain()

        # 输入完毕后，重置event状态为False，造成下一次for loop的wait()等待
        input_ready_event.clear()


async def main():
    program = ['python3', 'listing_13_13.py']
    process: Process = await asyncio.create_subprocess_exec(*program,
                                                            stdout=asyncio.subprocess.PIPE,
                                                            stdin=asyncio.subprocess.PIPE)

    input_ready_event = asyncio.Event()

    text_input = ['one\n', 'two\n', 'three\n', 'four\n', 'quit\n']

    await asyncio.gather(output_consumer(input_ready_event, process.stdout),
                         input_writer(text_input, input_ready_event, process.stdin),
                         process.wait())


asyncio.run(main())

# We can address this by decoupling reading standard output from writing data to
# standard input, thus, separating the concerns of reading standard output and writ-
# ing to standard input