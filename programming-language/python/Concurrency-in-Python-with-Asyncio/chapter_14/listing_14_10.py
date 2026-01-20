import functools
import selectors
import socket
from listing_14_8 import CustomFuture
from selectors import BaseSelector


def accept_connection(future: CustomFuture, connection: socket): #A
    print(f'We got a connection from {connection}!')
    future.set_result(connection)


async def sock_accept(sel: BaseSelector, sock) -> socket: #B
    print('Registering socket to listen for connections')
    future = CustomFuture()
    sel.register(sock, selectors.EVENT_READ, functools.partial(accept_connection, future))
    print('Pausing to listen for connections...')
    connection: socket = await future
    return connection


async def main(sel: BaseSelector):
    sock = socket.socket()
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    sock.bind(('127.0.0.1', 8000))
    sock.listen()
    sock.setblocking(False)

    print('Waiting for socket connection!')
    connection = await sock_accept(sel, sock) #C
    print(f'Got a connection {connection}!')


selector = selectors.DefaultSelector()
coro = main(selector)

while True: #D
    try:
        # advance to first wait()
        state = coro.send(None)

        # print('coro.send end')

        # Each time a selector event occurs, run the registered callback
        events = selector.select()

        for key, mask in events:
            print('Processing selector events...')
            print(f'key: {key}; mask: {mask}')
            # key: SelectorKey(fileobj=<socket.socket fd=512, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM,proto=0, laddr=('127.0.0.1', 8000)>, fd=512, events=1,
            # data=functools.partial(<function accept_connection at 0x000001B5F5053D90>,
            # <listing_14_8.CustomFuture object at 0x000001B5F52F0B20>)); mask: 1
            callback = key.data
            callback(key.fileobj)
    except StopIteration as si:
        print('Application finished!')
        break

# Waiting for socket connection!
# Registering socket to listen for connections
# Pausing to listen for connections...

# Processing selector events...
# We got a connection from <socket.socket fd=424, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM,
# proto=0, laddr=('127.0.0.1', 8000)>!
# Got a connection <socket.socket fd=424, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM,
# proto=0, laddr=('127.0.0.1', 8000)>!
# Application finished!