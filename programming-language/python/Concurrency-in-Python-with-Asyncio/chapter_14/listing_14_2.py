import asyncio
from asyncio import StreamReader, StreamWriter
from contextvars import ContextVar


class Server:
    user_address = ContextVar('user_address') #A

    def __init__(self, host: str, port: int):
        self.host = host
        self.port = port

    async def start_server(self):
        server = await asyncio.start_server(self._client_connected, self.host, self.port)
        await server.serve_forever()

    def _client_connected(self, reader: StreamReader, writer: StreamWriter):
        # in our _client_connected callback, we set the data of the context variable to the client’s address.
        self.user_address.set(writer.get_extra_info('peername')) #B

        # This will allow any tasks spawned from this parent task to have access to the information we set;
        # in this instance, this will be tasks that listen for messages from the clients.
        asyncio.create_task(self.listen_for_messages(reader))

    async def listen_for_messages(self, reader: StreamReader):
        while data := await reader.readline():
            print(f'Got message {data} from {self.user_address.get()}') #C


async def main():
    server = Server('127.0.0.1', 9000)
    await server.start_server()


asyncio.run(main())

# Use netcat in terminal:
# > nc localhost 9000

# Got message b'hello\n' from ('127.0.0.1', 40188)
# Got message b'mad\n' from ('127.0.0.1', 40190)
