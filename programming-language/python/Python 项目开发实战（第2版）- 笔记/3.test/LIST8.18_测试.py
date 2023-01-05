def setUpModule():
    _setup_db()

def tearDownModule():
    _teardown_db()

class TestMyView(unittest.TestCase):
    def setUp(self):
        from .models import DBSession
        self.session = DBSession

    def _setup_entry(self, **kwargs):
        from .models import Entry
        entry = Entry(**kwargs)
        self.session.add(entry)
        return entry

    def test_it(self):
        e = self._setup_entry(name=u"this-is-test")

        result = self._callFUT(entry_id=e.id)

        self.assertEqual(result['name'], e.name)
