import pytest
from cards import Card

summaries = ["short", "a bit longer"]
owners = ["First", "First M. Last"]
states = ["todo", "in prog", "done"]


@pytest.mark.parametrize(
    "summary, owner, state",
    [
        ("short", "First", "todo"),
        ("short", "First", "in prog"),
        # ...
    ],
)
def test_add_lots(cards_db, summary, owner, state):
    """Make sure adding to db doesn't change values."""
    i = cards_db.add_card(Card(summary, owner=owner, state=state))
    card = cards_db.get_card(i)

    expected = Card(summary, owner=owner, state=state)
    assert card == expected

# test_multiple.py::test_add_lots[short-First-todo]
# test_multiple.py::test_add_lots[short-First-in prog]

@pytest.mark.parametrize("state", states)
@pytest.mark.parametrize("owner", owners)
@pytest.mark.parametrize("summary", summaries)
def test_stacking(cards_db, summary, owner, state):
    """Make sure adding to db doesn't change values."""
    ...
    expected = Card(summary, owner=owner, state=state)
    i = cards_db.add_card(Card(summary, owner=owner, state=state))
    card = cards_db.get_card(i)
    assert card == expected

# test_multiple.py::test_stacking[short-First-todo]
# test_multiple.py::test_stacking[short-First-in prog]
# test_multiple.py::test_stacking[short-First-done]
# test_multiple.py::test_stacking[short-First M. Last-todo]
# test_multiple.py::test_stacking[short-First M. Last-in prog]
# test_multiple.py::test_stacking[short-First M. Last-done]
# test_multiple.py::test_stacking[a bit longer-First-todo]
# test_multiple.py::test_stacking[a bit longer-First-in prog]
# test_multiple.py::test_stacking[a bit longer-First-done]
# test_multiple.py::test_stacking[a bit longer-First M. Last-todo]
# test_multiple.py::test_stacking[a bit longer-First M. Last-in prog]
# test_multiple.py::test_stacking[a bit longer-First M. Last-done]