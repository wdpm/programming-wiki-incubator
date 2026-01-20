import asyncio
import logging
from asyncio import Queue

import aiohttp
from aiohttp import ClientSession
from bs4 import BeautifulSoup


class WorkItem:
    def __init__(self, item_depth: int, url: str):
        # the depth of that URL
        self.item_depth = item_depth
        self.url = url


async def worker(worker_id: int, queue: Queue, session: ClientSession, max_depth: int):
    print(f'Worker {worker_id}')
    while True:  # A
        work_item: WorkItem = await queue.get()
        print(f'Worker {worker_id}: Processing {work_item.url} with {work_item.item_depth} depth')
        await process_page(work_item, queue, session, max_depth)
        print(f'Worker {worker_id}: Finished {work_item.url}')
        queue.task_done()


async def process_page(work_item: WorkItem, queue: Queue, session: ClientSession, max_depth: int):  # B
    try:
        response = await asyncio.wait_for(session.get(work_item.url), timeout=3)
        if work_item.item_depth == max_depth:
            print(f'Max depth reached, not processing more for {work_item.url}')
        else:
            body = await response.text()
            soup = BeautifulSoup(body, 'html.parser')
            links = soup.find_all('a', href=True)
            for link in links:
                queue.put_nowait(WorkItem(work_item.item_depth + 1,
                                          link['href']))
    except Exception as e:
        logging.exception(f'Error processing url {work_item.url}')


async def main():  # C
    start_url = 'http://example.com'
    # not bounded queue
    url_queue = Queue()
    url_queue.put_nowait(WorkItem(0, start_url))
    async with aiohttp.ClientSession() as session:
        workers = [asyncio.create_task(worker(i, url_queue, session, 3))
                   for i in range(100)]
        await url_queue.join()
        [w.cancel() for w in workers]


asyncio.run(main())

# 100 个 worker，共用一个queue，退出的条件是queue任务为空。
# 初始化时，queue含有一个link作为爬虫的ROOT url。

# Worker 0: Finished http://example.com
# Worker 0: Processing https://www.iana.org/domains/example with 1 depth
# Worker 0: Finished https://www.iana.org/domains/example
# Worker 0: Processing / with 2 depth
# Worker 2: Processing /domains with 2 depth
# Worker 3: Processing /protocols with 2 depth
# Worker 4: Processing /numbers with 2 depth
# Worker 5: Processing /about with 2 depth
# Worker 6: Processing /go/rfc2606 with 2 depth
# Worker 7: Processing /go/rfc6761 with 2 depth
# Worker 8: Processing http://www.icann.org/topics/idn/ with 2 depth
# Worker 9: Processing http://www.icann.org/ with 2 depth
# Worker 10: Processing /domains/root/db/xn--kgbechtv.html with 2 depth
# Worker 11: Processing /domains/root/db/xn--hgbk6aj7f53bba.html with 2 depth
# Worker 12: Processing /domains/root/db/xn--0zwm56d.html with 2 depth
# ......
