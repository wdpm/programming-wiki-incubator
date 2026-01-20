import asyncio
import logging

import asyncpg


async def main():
    connection = await asyncpg.connect(host='127.0.0.1',
                                       port=5432,
                                       user='postgres',
                                       database='products',
                                       password='123456')
    async with connection.transaction():
        await connection.execute("INSERT INTO brand VALUES(DEFAULT,'my_new_brand')")

        try:
            async with connection.transaction():
                await connection.execute("INSERT INTO product_color VALUES(1,'black')")
        except Exception as ex:
            logging.warning('Ignoring error inserting product color', exc_info=ex)

    await connection.close()


asyncio.run(main())

# DETAIL:  键值 "(product_color_id)=(1)" 已经存在。这个 rollback 不会 roll back 外层 brand 的插入语句。
# 如果 INSERT INTO product_color VALUES(1, 'black') 没有被 transaction 包裹，那么会 rollback 会蔓延到外层。
