class Foo:
    def __getattr__(self, item):
        print(f'key: {item}')
        return 'Touch __getattr__'


f = Foo()
print(f.a)
# Touch __getattr__