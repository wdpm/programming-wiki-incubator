class DB:
    def __init__(self, name):
        self.name = name

    def __enter__(self):
        print(f"__enter__: {self.name}.open")
        return self

    def __exit__(self, typ, val, tb):
        print(f"__exit__: {self.name}.close; exception: {val}; tb: {repr(tb)}")
        return False

def logic():
    with DB("mysql") as db:
        print(f"exec in {db.name}")
        raise Exception("logic error")

logic()

# __enter__: mysql.open
# exec in mysql
# __exit__: mysql.close; exception: logic error

# Exception: logic error