import cProfile, pstats, functools, time


def profile(sortby="cumtime", limit=10, timer=time.perf_counter):
    def decorator(func):

        @functools.wraps(func)
        def wrap(*args, **kwargs):
            p = cProfile.Profile(timer)
            p.enable()
            try:
                return func(*args, **kwargs)
            finally:
                p.disable()
                s = pstats.Stats(p).sort_stats(sortby)
                s.print_stats(limit)

        return wrap

    return decorator


