from cards import Card


def test_less_than():
    c1 = Card("a task")
    c2 = Card("b task")
    assert c1 < c2

# TypeError: '<' not supported between instances of 'Card' and 'Card'

def test_equality():
    c1 = Card("a task")
    c2 = Card("a task")
    assert c1 == c2
