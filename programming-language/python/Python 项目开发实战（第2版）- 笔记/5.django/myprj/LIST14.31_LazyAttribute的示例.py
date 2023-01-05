import factory


class UserFactory(factory.django.DjangoModelFactory):
    first_name = 'Beproud'
    last_name = 'Taro'
    email = factory.LazyAttribute(lambda a:
                                  '{0}.{1}@example.com'.format(a.first_name, a.last_name).lower())

    class Meta:
        model = User


UserFactory().email  # => beproud.taro@example.com
