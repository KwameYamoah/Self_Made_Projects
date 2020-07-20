import random
def start_game():
    player1, player2 = get_players_character()
    board = [' ', ' ', ' ',
             ' ', ' ', ' ',
             ' ', ' ', ' ']

    turn = random.randint(0, 1)
    if turn == 0:
        print("Player 1 will go first")
    else:
        print("Player 2 will go first")
    display(board)
    game_over = False
    while not game_over:
        if turn % 2 == 0:
            print("Player1's move")
            move = get_move(board)
            board[move] = player1
            game_over = check_if_won(board, player1)
            if game_over:
                print("Player1 won the game")
        else:
            print("Player2's move")
            move = get_move(board)
            board[move] = player2
            game_over = check_if_won(board, player2)
            if game_over:
                print("Player2 won the game")
        if not game_over:
            game_over = check_if_full_board(board)

        display(board)
        turn += 1

    return restart_game()


def get_players_character():
    marker = "WRONG"
    while marker not in ['X', 'O']:
        marker = input("Enter player1's token(X or O) : ").upper()
        if marker not in ['X', 'O']:
            print("The value entered isn't acceptable, please re-enter")

    if marker == 'X':
        return 'X', 'O'
    else:
        return 'O', 'X'


def display(board):
    print(f'{board[0]}|{board[1]}|{board[2]}')
    print(f'{board[3]}|{board[4]}|{board[5]}')
    print(f'{board[6]}|{board[7]}|{board[8]}')


def get_move(board):
    move = validate_move(board)
    while board[move - 1] != ' ':
        print("This position is already occupied")
        display(board)
        move = validate_move(board)
    return move - 1


def validate_move(board):
    choice = -1
    while int(choice) not in range(1, 10):
        choice = input("Enter position(1 to 9) : ")
        if choice.isdigit():
            choice = int(choice)
            if choice not in range(1, 10):
                print("The value entered isn't in acceptable range, please re-enter")
                display(board)
        else:
            print("The value entered isn't a number, please re-enter")
            display(board)
            choice = -1
    return choice


def check_if_won(board, player_token):
    winning_condition = [player_token, player_token, player_token]
    possible_winning_spots = [[0, 1, 2], [3, 4, 5], [6, 7, 8], [0, 3, 6], [1, 4, 7], [2, 5, 8], [0, 4, 8], [2, 4, 6]]
    for winning_spots in possible_winning_spots:
        if [board[winning_spots[0]], board[winning_spots[1]], board[winning_spots[2]]] == winning_condition:
            return True
    return False


def check_if_full_board(board):
    spots_filled = 0
    for spots in board:
        if spots != ' ':
            spots_filled += 1
    if spots_filled == 9:
        print("No player won")
        return True
    else:
        return False


def restart_game():
    choice = "WRONG"
    while choice not in ['YES', 'NO']:
        choice = input("Replay game?(Yes or No) : ").upper()
        if choice not in ['YES', 'NO']:
            print("The value entered isn't acceptable, please re-enter")
    return choice == 'YES'


print("Welcome to Tic Tac Toe game")
replay = True
while replay:
    replay = start_game()

