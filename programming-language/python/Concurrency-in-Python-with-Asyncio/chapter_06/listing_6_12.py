from multiprocessing import Process, Value

# To avoid race conditions, we must make our parallel code sequential in critical sections.
# This can hurt the performance of our multiprocessing code.
def increment_value(shared_int: Value):
    shared_int.get_lock().acquire()
    shared_int.value = shared_int.value + 1
    shared_int.get_lock().release()

    # or
    # with shared_int.get_lock():
    #     shared_int.value += 1


if __name__ == '__main__':
    for _ in range(100):
        integer = Value('i', 0)
        procs = [Process(target=increment_value, args=(integer,)),
                 Process(target=increment_value, args=(integer,))]

        [p.start() for p in procs]
        [p.join() for p in procs]
        print(integer.value)
        assert (integer.value == 2)
