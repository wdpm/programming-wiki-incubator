import asyncio
from asyncio import Event
from contextlib import suppress


async def trigger_event_periodically(event: Event):
    while True:
        print('Triggering event!')
        event.set()
        await asyncio.sleep(1)


async def do_work_on_event(event: Event):
    while True:
        print('Waiting for event...')
        await event.wait()
        event.clear()
        print('Performing work!')
        await asyncio.sleep(5)
        print('Finished work!')


async def main():
    event = asyncio.Event()
    trigger = asyncio.wait_for(trigger_event_periodically(event), 5.0)

    with suppress(asyncio.TimeoutError):
        # await asyncio.gather(do_work_on_event(event), do_work_on_event(event), trigger)
        await asyncio.gather(do_work_on_event(event), trigger)


asyncio.run(main())

# Waiting for event...
# Triggering event!
# Performing work!

# 此时do_work_on_event在等待5秒，trigger_event_periodically在每秒set一次flag

# Triggering event!
# Triggering event!
# Triggering event!
# Triggering event!

# 5秒后，do_work_on_event第一次执行完毕
# Finished work!

# do_work_on_event进行下一次while循坏
# Waiting for event...
# Performing work!

# 思考,为何会第二次 print('Performing work!') 之后会出现timeout error?

# When we run the preceding listing, we’ll see our event fires and our two workers start
# their work concurrently. In the meantime, we keep triggering our event.
#
# Since our workers are busy, they won’t see that our event fired a second time until they finish
# their work and call event.wait() a second time. If you care about responding every
# time an event occurs, you’ll need to use a queueing mechanism.
