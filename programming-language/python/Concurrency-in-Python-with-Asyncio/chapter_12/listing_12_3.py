import asyncio
from asyncio import Queue, Task
from typing import List
from random import randrange
from aiohttp import web
from aiohttp.web_app import Application
from aiohttp.web_request import Request
from aiohttp.web_response import Response

routes = web.RouteTableDef()

QUEUE_KEY = 'order_queue'
TASKS_KEY = 'order_tasks'


async def process_order_worker(worker_id: int, queue: Queue): #A
    while True:
        print(f'Worker {worker_id}: Waiting for an order...')
        order = await queue.get()
        print(f'Worker {worker_id}: Processing order {order}')
        await asyncio.sleep(order)
        print(f'Worker {worker_id}: Processed order {order}')
        queue.task_done()


@routes.post('/order')
async def place_order(request: Request) -> Response:
    order_queue = app[QUEUE_KEY]
    # You may want to use the put_nowait variant and then respond with a HTTP 500
    # error or other error code asking the caller to try again later. Here, we’ve made a
    # tradeoff of a request potentially taking some time so that our order always goes on the queue
    await order_queue.put(randrange(5)) #B
    return Response(body='Order placed!')


async def create_order_queue(app: Application): #C
    print('Creating order queue and tasks.')
    queue: Queue = asyncio.Queue(10)
    app[QUEUE_KEY] = queue
    app[TASKS_KEY] = [asyncio.create_task(process_order_worker(i, queue))
                      for i in range(5)]


async def destroy_queue(app: Application): #D
    order_tasks: List[Task] = app[TASKS_KEY]
    queue: Queue = app[QUEUE_KEY]
    print('Waiting for pending queue workers to finish....')
    try:
        # We first wait for the queue to finish processing all its elements with Queue.join
        # 给你10秒，还是完成不了的话，那就cancel。
        await asyncio.wait_for(queue.join(), timeout=10)
    finally:
        print('Finished all pending items, canceling worker tasks...')
        [task.cancel() for task in order_tasks]


app = web.Application()
app.on_startup.append(create_order_queue)
# Since our application is shutting down, it won’t be serving any more HTTP requests,
# so no other orders can go into our queue.
# This means that anything already in the queue will be processed by a worker, and any-
# thing a worker is currently processing will finish as well.
app.on_shutdown.append(destroy_queue)

app.add_routes(routes)
web.run_app(app)
