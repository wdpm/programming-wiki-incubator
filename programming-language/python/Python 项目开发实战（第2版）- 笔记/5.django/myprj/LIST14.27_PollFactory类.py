import factory

from django.utils import timezone

from polls.models import Poll


class PollFactory(factory.django.DjangoModelFactory):
    question_text = 'factory question'
    pub_date = timezone.now()

    class Meta:
        model = Poll

# 返回没有save的Poll实例
poll = PollFactory.build()

# 返回已save的Poll实例
poll = PollFactory.create()

# 以字典形式返回生成Poll实例时所用的属性
attributes = PollFactory.attributes()

# 返回所有属性桩代码化后的对象
stub = PollFactory.stub()