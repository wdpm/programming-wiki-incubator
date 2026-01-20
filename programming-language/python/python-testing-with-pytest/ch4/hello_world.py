def hello():
    with open("hello.txt", "w") as f:
        f.write("Hello World!\n")


def test_hello():
    with open('hello.txt','r') as f:
        assert f.read() == 'Hello World!\n'


if __name__ == "__main__":
    # hello()
    test_hello()
