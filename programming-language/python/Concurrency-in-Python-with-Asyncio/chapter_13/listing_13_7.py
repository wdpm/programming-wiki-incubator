import asyncio
import random
import string
import time
from asyncio.subprocess import Process


async def encrypt(text: str) -> bytes:
    program = ['gpg', '-c', '--batch', '--passphrase', '3ncryptm3', '--cipher-algo', 'TWOFISH']

    process: Process = await asyncio.create_subprocess_exec(*program,
                                                            stdout=asyncio.subprocess.PIPE,
                                                            stdin=asyncio.subprocess.PIPE)
    stdout, stderr = await process.communicate(text.encode())
    return stdout


async def main():
    text_list = [''.join(random.choice(string.ascii_letters) for _ in range(1000)) for _ in range(100)]

    s = time.time()
    tasks = [asyncio.create_task(encrypt(text)) for text in text_list]
    encrypted_text = await asyncio.gather(*tasks)
    e = time.time()

    print(f'Total time: {e - s}')
    print(len(encrypted_text))


asyncio.run(main())

# Total time: 22.161126136779785

# 这里在同一时刻创建了100个process，但是本地CPU却没有这么多核心。因此，CPU上下文的切换不容忽视。
# 为了更匹配本地计算资源，可以使用semaphore来限制同一时刻存在process的数量。
