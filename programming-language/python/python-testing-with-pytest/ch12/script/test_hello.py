from subprocess import run


def test_hello():
    # here we hard code the path: hello.py
    result = run(["python", "hello.py"], capture_output=True, text=True)
    output = result.stdout
    assert output == "Hello, World!\n"
