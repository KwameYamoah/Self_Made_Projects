# suit rank value
class Card:

    def __init__(self, rank, suit, value):
        self.rank = rank
        self.suit = suit
        self.value = value

    def __lt__(self, other):
        if type(self) == type(other):
            return self.value < other.value
        else:
            return False

    def __eq__(self, other):
        if type(self) == type(other):
            return self.value == other.value
        else:
            return False

    def __gt__(self, other):
        if type(self) == type(other):
            return self.value > other.value
        else:
            return False

    def __len__(self):
        return self.value

    def __repr__(self):
        return f"{self.rank} of {self.suit}"

    def __str__(self):
        return f"{self.rank} of {self.suit}"


if __name__ == '__main__':
    cards = [Card("Two", "Spades", 2), Card("Two", "Hearts", 2)]
    print(cards[0].value)
