import asyncio
from asyncio import AbstractEventLoop
from threading import Thread

from chapter_07.listing_7_14 import LoadTester


class ThreadedEventLoop(Thread):  # A
    def __init__(self, loop: AbstractEventLoop):
        super().__init__()
        self._loop = loop
        # This type of infinite loop would prevent our GUI application from shutting down if we ran in non-daemon mode.
        self.daemon = True

    def run(self):
        self._loop.run_forever()


# 创一个带有自定义loop的线程，然后启动

loop = asyncio.new_event_loop()

asyncio_thread = ThreadedEventLoop(loop)
asyncio_thread.start()  # B

app = LoadTester(loop)  # C
app.mainloop()

# This technique of running the asyncio event loop in a separate thread is useful for building responsive GUIs,
# but also is useful for any synchronous legacy applications where coroutines and asyncio don’t fit smoothly.
