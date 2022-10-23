class InfoDescriptor:
    """打印帮助信息的描述符"""

    def __get__(self, instance, owner=None):
        print(f'Calling __get__, instance: {instance}, owner: {owner}')
        if not instance:
            print('Calling without instance...')
            return self
        return 'informative descriptor'

    def __set__(self, instance, value):
        print(f'Calling __set__, instance: {instance}, value: {value}')

    def __delete__(self, instance):
        raise RuntimeError('Deletion not supported!')


class Foo:
    bar = InfoDescriptor()


Foo.bar
# Calling __get__, instance: None, owner: <class '__main__.Foo'>
# Calling without instance...

Foo().bar
# Calling __get__, instance: <__main__.Foo object at 0x0000016F6FA73AC0>, owner: <class '__main__.Foo'>
