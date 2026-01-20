import socket

from chapter_14.listing_14_12 import EventLoop
from chapter_14.listing_14_11 import CustomTask


async def read_from_client(conn, loop: EventLoop): #A
    print(f'Reading data from client {conn}')
    try:
        while data := await loop.sock_recv(conn):
            print(f'Got {data} from client!')
    finally:
        print(f'close connection {conn}')
        loop.sock_close(conn)


async def listen_for_connections(sock, loop: EventLoop): #B
    while True:
        print('Waiting for connection...')
        conn, addr = await loop.sock_accept(sock)
        CustomTask(read_from_client(conn, loop), loop)
        print(f'I got a new connection from {sock}!')


async def main(loop: EventLoop):
    server_socket = socket.socket()
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    server_socket.bind(('localhost', 8000))
    server_socket.listen()
    server_socket.setblocking(False)

    await listen_for_connections(server_socket, loop)


event_loop = EventLoop() #C
event_loop.run(main(event_loop))

# 注意：windows下的telnet命令会一个一个发送字符，而不是输入一句话后enter发送。
# 解决方法：可以使用命令模式，具体步骤：
# 1. telnet ip port
# 2. Ctrl + ]
# 3. send hello world
# console log示例：Got b'hello world' from client!

# Waiting for connection...
# Registering socket to accept connections...

# Selector has an event, processing...
# I got a new connection from <socket.socket fd=444, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM, proto=0, laddr=('127.0.0.1', 8000)>!
# Waiting for connection...
# Registering socket to accept connections...
# Reading data from client <socket.socket fd=528, family=AddressFamily.AF_INET, type=SocketKind.SOCK_STREAM, proto=0, laddr=('127.0.0.1', 8000), raddr=('127.0.0.1', 34439)>
# Registering socket to listen for data...

