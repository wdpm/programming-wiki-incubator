class PollFactory(factory.django.DjangoModelFactory):
    question_text = 'factory question'
    # 添加的列
    user_name = 'factory san'
    pub_date = timezone.now()

    class Meta:
        model = Poll
