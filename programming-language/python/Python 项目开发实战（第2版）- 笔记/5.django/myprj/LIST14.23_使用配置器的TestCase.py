from django.test import TestCase
from polls.models import Poll

class PollsTestCase(TestCase):
    fixtures = ['polls.json']

    def setUp(self):
        # 一般的测试定义

    def testPoll(self):
        # 使用配置器的测试
