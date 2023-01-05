import factory


class UserFactory(factory.django.DjangoModelFactory):
    email = factory.Sequence(lambda n: 'person{0}@example.com'.format(n))

    class Meta:
        model = User

UserFactory().email  # => 'person0@example.com'
UserFactory().email  # => 'person1@example.com'
