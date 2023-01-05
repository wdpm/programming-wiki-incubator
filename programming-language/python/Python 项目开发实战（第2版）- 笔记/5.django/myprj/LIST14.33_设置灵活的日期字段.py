class PollFactory(factory.django.DjangoModelFactory):
    question_text = 'factory question'
    # 配上与测试匹配的日期
    pub_date = timezone.now()

    class Meta:
        madel = Poll

# 还可以在生成实例时设置
poll = PollFactory.create(pub_date=timezone.now())
