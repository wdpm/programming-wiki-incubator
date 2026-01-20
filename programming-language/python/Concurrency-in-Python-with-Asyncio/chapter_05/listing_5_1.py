import asyncpg
import asyncio


async def main():
    connection = await asyncpg.connect(host='127.0.0.1',
                                       port=5432,
                                       user='postgres',
                                       database='postgres',
                                       password='123456')
    version = connection.get_server_version()
    print(f'Connected! Postgres version is {version}')
    await connection.close()


asyncio.run(main())

# Connected! Postgres version is ServerVersion(major=9, minor=5, micro=17, releaselevel='final', serial=0)

# psql -U postgres