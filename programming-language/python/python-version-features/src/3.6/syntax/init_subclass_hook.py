class PluginBase:
    subclasses = []

    def __init_subclass__(cls, **kwargs):
        super().__init_subclass__(**kwargs)
        cls.subclasses.append(cls)


class Plugin1(PluginBase):
    pass


class Plugin2(PluginBase):
    pass


Plugin1()
Plugin2()

print(PluginBase.subclasses)
# [<class '__main__.Plugin1'>, <class '__main__.Plugin2'>]
