import asyncio

import aiohttp

async def main():
    async with aiohttp.ClientSession() as session:
        async with session.get("https://www.example.com") as resp:
            if resp.status == 200:
                content = await resp.text()
                words = content.split()
                # hey, look here, async comprehensions
                word_lengths = [len(word) for word in words if word.isalnum()]
                average = sum(word_lengths) / len(word_lengths)
                print(f"Average word length: {average}")

asyncio.run(main())
