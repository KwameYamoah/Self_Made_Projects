from Deck import Deck
from Player import Player

# add bets
game_on = True
while game_on:

    deck = Deck()
    deck.shuffle_deck()
    player1 = Player('John')
    ai = Player('AI')
    players = [player1, ai]

    # Add two cards to players hand

    for player in players:
        player.add_card_to_hand(deck, 2)

    # Player goes first and decides to hit or stay and losses if he busts
    # hit adds a card to the player's hand
    # stay ends his turn
    game_state = 'No winner'

    # Shows both player hands and shows only 1 card from AI's hand
    player1.show_hand()
    ai.show_hand(1)
    while True:
        player1_score = player1.calculate_player_hand_score(True)

        choice = input('Hit or stand? : ')

        # Evaluates player decisions
        if choice.lower() == 'hit':
            player1.add_card_to_hand(deck, 1)
            player1.show_hand()

            # Check if player lost
            player1_score = player1.calculate_player_hand_score(False)
            if player1_score > 21:
                game_state = 'Player lost'
                break
        elif choice.lower() == 'stand':
            break
        else:
            print('Invalid choice')

    # AI goes next and AI will hit till it beats the player or bust
    if game_state != 'Player lost':

        # AI hits until it beats player
        ai_score = ai.calculate_ai_hand_score(False)
        while ai_score < player1_score:
            ai.add_card_to_hand(deck, 1)
            print('AI hits')
            ai.show_hand()
            ai_score = ai.calculate_ai_hand_score(True)

        # Checks against player
        ai_score = ai.calculate_ai_hand_score(False)
        if ai_score > 21:
            game_state = 'Ai lost'
        elif ai_score > player1_score:
            if ai_score > player1_score:
                game_state = 'Ai Won'
        else:
            break

    print(game_state)

    choice = input('Replay(Y or N)? : ')
    if choice.lower() == 'n':
        print("Thank you for playing")
        break
