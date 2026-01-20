import asyncio

import asyncpg

from util import async_timed

product_query = \
    """
SELECT
p.product_id,
p.product_name,
p.brand_id,
s.sku_id,
pc.product_color_name,
ps.product_size_name
FROM product as p
JOIN sku as s on s.product_id = p.product_id
JOIN product_color as pc on pc.product_color_id = s.product_color_id
JOIN product_size as ps on ps.product_size_id = s.product_size_id
WHERE p.product_id = 100"""


async def main2():
    connection = await asyncpg.connect(host='127.0.0.1',
                                       port=5432,
                                       user='postgres',
                                       database='products',
                                       password='123456')
    print('Creating the product database...')
    queries = [connection.execute(product_query),
               connection.execute(product_query)]
    results = await asyncio.gather(*queries)

# Since we have only one connection and we’re trying to read the results of
# multiple queries concurrently, we experience an error. We can resolve this by creating
# multiple connections to our database and executing one query per connection

async def query_product(pool):
    async with pool.acquire() as connection:
        return await connection.fetchrow(product_query)


@async_timed()
async def query_products_synchronously(pool, queries):
    return [await query_product(pool) for _ in range(queries)]


@async_timed()
async def query_products_concurrently(pool, queries):
    queries = [query_product(pool) for _ in range(queries)]
    return await asyncio.gather(*queries)


async def main():
    async with asyncpg.create_pool(host='127.0.0.1',
                                   port=5432,
                                   user='postgres',
                                   password='123456',
                                   database='products',
                                   min_size=6,
                                   max_size=6) as pool:
        await query_products_synchronously(pool, 10000)
        await query_products_concurrently(pool, 10000)


asyncio.run(main())

# starting <function query_products_synchronously at 0x00000224D2749000> with args (<asyncpg.pool.Pool object at 0x00000224D2720520>, 10000) {}
# finished <function query_products_synchronously at 0x00000224D2749000> in 8.1744 second(s)
# starting <function query_products_concurrently at 0x00000224D2749120> with args (<asyncpg.pool.Pool object at 0x00000224D2720520>, 10000) {}
# finished <function query_products_concurrently at 0x00000224D2749120> in 4.0626 second(s)

# 这里使用了pool池化、异步查询，但是忽略了异常的处理，也就是没有保证事务一致性。