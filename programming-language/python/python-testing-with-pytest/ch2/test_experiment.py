import cards


def test_no_path_fail():
    cards.CardsDB()

# E       TypeError: CardsDB.__init__() missing 1 required positional argument: 'db_path'
