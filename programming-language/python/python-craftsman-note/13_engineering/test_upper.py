import unittest


class TestStringUpper(unittest.TestCase):
    def test_normal(self):
        self.assertTrue(1)
        self.assertEqual('foo'.upper(), 'FOO')

    def test_normal2(self):
        self.assertEqual('foo333'.upper(), 'FOO333')


if __name__ == '__main__':
    unittest.main()