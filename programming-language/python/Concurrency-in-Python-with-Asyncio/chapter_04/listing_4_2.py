import asyncio
import aiohttp
from aiohttp import ClientSession
from util import async_timed


@async_timed()
async def fetch_status(session: ClientSession, url: str) -> int:
    async with session.get(url) as result:
        return result.status


@async_timed()
async def main():
    async with aiohttp.ClientSession() as session:
        url = 'https://www.example.com'
        status = await fetch_status(session, url)
        print(f'Status for {url} was {status}')


asyncio.run(main())

# starting <function main at 0x000002229EF1E950> with args () {}
# starting <function fetch_status at 0x000002229EF1E4D0> with args (<aiohttp.client.ClientSession object at 0x000002229EEC2050>, 'https://www.example.com') {}
# finished <function fetch_status at 0x000002229EF1E4D0> in 1.0657 second(s)
# Status for https://www.example.com was 200
# finished <function main at 0x000002229EF1E950> in 1.0667 second(s)