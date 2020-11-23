from Deck import Deck


class Player:

    def __init__(self, name):
        self.name = name
        self.cards_on_hand = []

    def add_card_to_hand(self, deck, num_of_cards):
        cards = []
        for x in range(num_of_cards):
            cards.append(deck.deal_card())
        self.cards_on_hand.extend(cards)

    def calculate_player_hand_score(self, show_value):
        score = 0
        for card in self.cards_on_hand:
            if Deck.check_for_ace(card):
                while True:
                    try:
                        ace_value = int(input(f"What value would you want {card} to be(1 or 11)? "))
                        if ace_value in [1, 11]:
                            break
                    except ValueError:
                        print("Incorrect value entered, please re-enter")
                score += ace_value

            else:
                score += card.value
        if show_value:
            print(f"Value of {self.name}'s hand is {score}\n")
        return score

    def calculate_ai_hand_score(self, show_value):
        score = 0
        for card in self.cards_on_hand:
            score += card.value
        if show_value:
            print(f"Value of {self.name}'s hand is {score}\n")
        return score

    def show_hand(self, number_to_show=-1):
        # -1 shows all
        if number_to_show >= 0:
            if number_to_show > len(self.cards_on_hand):
                number_to_show = len(self.cards_on_hand)
            hidden_hand = self.cards_on_hand
            hidden_hand = hidden_hand[0:number_to_show]
            hidden_hand.extend(['?'] * (len(self.cards_on_hand)-number_to_show))
            print(f"{self.name}'s hand is {hidden_hand} ")
        else:
            print(f"{self.name}'s hand is {self.cards_on_hand}")


    def clear_hand(self):
        self.cards_on_hand = []
