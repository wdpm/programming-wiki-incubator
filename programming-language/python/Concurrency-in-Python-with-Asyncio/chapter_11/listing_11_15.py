import asyncio
from enum import Enum


class ConnectionState(Enum):
    WAIT_INIT = 0
    INITIALIZING = 1
    INITIALIZED = 2


class Connection:
    """
    We first have an underlying connection that can’t run multiple queries at the same time,
    and the database connection may not be initialized before someone tries to run a query
    """

    def __init__(self):
        self._state = ConnectionState.WAIT_INIT
        self._condition = asyncio.Condition()

    async def initialize(self):
        await self._change_state(ConnectionState.INITIALIZING)
        print('initialize: Initializing connection...')
        await asyncio.sleep(3)  # simulate connection startup time
        print('initialize: Finished initializing connection')
        await self._change_state(ConnectionState.INITIALIZED)

    async def execute(self, query: str):
        async with self._condition:
            print('execute: Waiting for connection to initialize')
            await self._condition.wait_for(self._is_initialized)
            print(f'execute: Running {query}!!!')
            await asyncio.sleep(3)  # simulate a long query

    async def _change_state(self, state: ConnectionState):
        async with self._condition:
            print(f'change_state: State changing from {self._state} to {state}')
            self._state = state
            self._condition.notify_all()

    def _is_initialized(self):
        if self._state is not ConnectionState.INITIALIZED:
            print(f'_is_initialized: Connection not finished initializing, state is {self._state}')
            return False
        print(f'_is_initialized: Connection is initialized!')
        return True


async def main():
    connection = Connection()
    query_one = asyncio.create_task(connection.execute('select * from table'))
    query_two = asyncio.create_task(connection.execute('select * from other_table'))
    asyncio.create_task(connection.initialize())
    await query_one
    await query_two


asyncio.run(main())

# execute: Waiting for connection to initialize
# _is_initialized: Connection not finished initializing, state is ConnectionState.WAIT_INIT
# execute: Waiting for connection to initialize
# _is_initialized: Connection not finished initializing, state is ConnectionState.WAIT_INIT

# change_state: State changing from ConnectionState.WAIT_INIT to ConnectionState.INITIALIZING
# initialize: Initializing connection...
# _is_initialized: Connection not finished initializing, state is ConnectionState.INITIALIZING
# _is_initialized: Connection not finished initializing, state is ConnectionState.INITIALIZING

# initialize: Finished initializing connection

# change_state: State changing from ConnectionState.INITIALIZING to ConnectionState.INITIALIZED
# _is_initialized: Connection is initialized!
# execute: Running select * from table!!!

# _is_initialized: Connection is initialized!
# execute: Running select * from other_table!!!