from Card import Card
from random import shuffle


class Deck:

    card_suits = ("Hearts", "Diamonds", "Spades", "Clubs")
    card_ranks = {"Two": 2, "Three": 3, "Four": 4, "Five": 5, "Six": 6, "Seven": 7, "Eight": 8, "Nine": 9,
                  "Ten": 10, "Jack": 10, "Queen": 10, "King": 10, "Ace": 11}

    def __init__(self, ace_value=1):
        self.all_cards = []
        self.card_ranks["Ace"] = ace_value
        self.create_deck()

    def create_deck(self):
        for suit in self.card_suits:
            for rank in self.card_ranks:
                self.all_cards.append(Card(rank, suit, self.card_ranks[rank]))

    def shuffle_deck(self):
        shuffle(self.all_cards)

    def deal_card(self):
        return self.all_cards.pop()

    def deal_cards(self, number):
        dealt_cards = []
        for x in range(0, number):
            dealt_cards.append(self.deal_card())
        return dealt_cards

    def __repr__(self):
        return f"A Deck with {len(self.all_cards)} cards"

    def __str__(self):
        return str(self.all_cards)

    @staticmethod
    def check_for_ace(card):
        return card.rank == 'Ace'


if __name__ == '__main__':
    deck = Deck(21)
    print([deck])
