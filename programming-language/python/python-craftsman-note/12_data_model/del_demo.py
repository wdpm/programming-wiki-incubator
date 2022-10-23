class Foo:
    def __del__(self):
        print(f'cleaning up {self}...')


foo = Foo()  # ref 1
del foo
# cleaning up <__main__.Foo object at 0x00000195C4DB3880>...

foo = Foo()  # ref 1
a = [foo, ]  # ref 2
del foo  # ref -1, here ref =1
print(a)
# >>> del a
# cleaning up <__main__.Foo object at 0x000001C65609B640>...