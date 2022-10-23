from typing import List, Iterable


class User(object):
    def list_related_posts(self, title=List[any]) -> List[int]:
        pass


class Admin(User):
    def list_related_posts(self, title=Iterable[any]) -> List[int]:
        pass


admin = Admin()
posts = admin.list_related_posts()
