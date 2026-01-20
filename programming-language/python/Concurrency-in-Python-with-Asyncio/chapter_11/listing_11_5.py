import asyncio
from asyncio import Lock


class MockSocket:
    def __init__(self):
        self.socket_closed = False

    async def send(self, msg: str):
        if self.socket_closed:
            raise Exception('Socket is closed!')
        print(f'Sending: {msg}')
        await asyncio.sleep(1)
        print(f'Sent: {msg}')

    def close(self):
        self.socket_closed = True


user_names_to_sockets = {'John': MockSocket(),
                         'Terry': MockSocket(),
                         'Graham': MockSocket(),
                         'Eric': MockSocket()}


async def user_disconnect(username: str, user_lock: Lock):
    print(f'{username} disconnected!')
    async with user_lock: #A
        print(f'Removing {username} from dictionary')
        socket = user_names_to_sockets.pop(username)
        socket.close()


async def message_all_users(user_lock: Lock):
    print('Creating message tasks')
    async with user_lock: #B
        messages = [socket.send(f'Hello {user}')
                    for user, socket
                    in user_names_to_sockets.items()]
        await asyncio.gather(*messages)


async def main():
    user_lock = Lock()
    await asyncio.gather(message_all_users(user_lock),
                         user_disconnect('Eric', user_lock))


asyncio.run(main())


# We first acquire the lock and create the message tasks. While this is happening, Eric
# disconnects, and the code in disconnect tries to acquire the lock.
#
# Since message_all_users still holds the lock, we need to wait for it to finish before running the code in disconnect.
#
# This lets all the messages finish sending out before closing out the socket, preventing our bug.