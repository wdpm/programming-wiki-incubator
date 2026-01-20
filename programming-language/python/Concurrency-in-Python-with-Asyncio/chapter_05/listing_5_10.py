import asyncio
import logging
import asyncpg


async def main():
    connection = await asyncpg.connect(host='127.0.0.1',
                                       port=5432,
                                       user='postgres',
                                       database='products',
                                       password='123456')
    try:
        async with connection.transaction():
            insert_brand = "INSERT INTO brand VALUES(9999, 'big_brand')"
            await connection.execute(insert_brand)
            await connection.execute(insert_brand)  # A
    except Exception:
        logging.exception('Error while running transaction')  # B
    finally:
        query = """SELECT brand_name FROM brand 
                    WHERE brand_name LIKE 'big_%'"""
        brands = await connection.fetch(query)  # C
        print(f'Query result was: {brands}')

        await connection.close()


asyncio.run(main())

# asyncpg.exceptions.UniqueViolationError: 重复键违反唯一约束"brand_pkey"
# DETAIL:  键值"(brand_id)=(9999)" 已经存在
