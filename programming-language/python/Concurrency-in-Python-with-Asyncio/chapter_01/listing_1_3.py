import threading


def hello_from_thread():
    print(f'Hello from thread {threading.current_thread()}!')


hello_thread = threading.Thread(target=hello_from_thread)
hello_thread.start()

total_threads = threading.active_count()
thread_name = threading.current_thread().name

print(f'Python is currently running {total_threads} thread(s)')
print(f'The current thread is {thread_name}')

hello_thread.join()

# Hello from thread <Thread(Thread-1 (hello_from_thread), started 15256)>!
# Python is currently running 2 thread(s)
# The current thread is MainThread
