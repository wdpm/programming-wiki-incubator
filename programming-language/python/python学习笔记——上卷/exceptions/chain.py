def test():
    try:
        raise Exception("err")
    except Exception as exc:
        raise Exception("wrap") from exc  # 使用链保留原异常状态


def main():
    try:
        test()
    except Exception as exc:
        while True:  # 循环访问链上的所有异常
            print(repr(exc), exc.__traceback__)
            if not exc.__cause__: break
            exc = exc.__cause__

if __name__ == '__main__':
    main()
    # Exception('wrap') <traceback object at 0x0000026B81F76A40>
    # Exception('err') <traceback object at 0x0000026B81F76E00>