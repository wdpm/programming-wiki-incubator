from django.db import models


class Poll(models.Model):
    question_text = models.CharField(max_length=200)
    # 添加公布日期
    pub_date = models.DateTimeField('date published')
