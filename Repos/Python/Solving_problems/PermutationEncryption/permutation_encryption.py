"""
Working for the Texas Spy Agency, you are in charge of writing software for handling secure communications between your clients who wish to pass you messages without anyone else being able to read them. Therefore, you have been commissioned to write a program which takes messages and encrypts them according to a key that you have. The spy agency is not very technologically advanced, and everything has been done by hand until now, which is why you were hired. Thus, their codes are also fairly simple – they’re just character permutations.

A permutation of length n, as you know, is a list of the numbers 1 through n but in some rearranged order. So “1 2 3” is a permutation of length 3, and so is “2 1 3”, but “5 3 6 4 1 2” is a permutation of length 6. Notice that every number between 1 and n appears in the permutation.

A key is a permutation which is used to change the message. For instance, the key “1 2 3 4 5” is a permutation of length 5 which does absolutely nothing – an encrypted message and a decrypted message would be identical. The key “2 1” would reverse every two characters. So the phrase ‘I like ice cream’ would be encrypted as ‘ Iileki ecc erma’. The key “5 1 4 2 3” would encrypt the word ‘howdy’ as ‘yhdow’.

As you might imagine, if the message is longer than the key, just keep applying the key to each set of n characters. One last example. If the key is “4 1 3 5 2 6”, then n is 6, and we would encrypt the message “Four score and seven years ago” as (spaces, vertical bars, and single quotes are added for clarity):

index:      1 2 3 4 5 6|1 2 3 4 5 6|1 2 3 4 5 6|1 2 3 4 5 6|1 2 3 4 5 6|1 2 3 4 5 6
message:   'F o u r   s|c o r e   a|n d   s e v|e n   y e a|r s   a g o|.          '
key:        4 1 3 5 2 6|4 1 3 5 2 6|4 1 3 5 2 6|4 1 3 5 2 6|4 1 3 5 2 6|4 1 3 5 2 6
encrypted: 'r F u   o s|e c r   o a|s n   e d v|y e   e n a|a r   g s o|  .        '

Note from this example that if the message length is not a multiple of the key length, your program should pad the
original message with extra spaces at the end so that the message length is a multiple of the key length.
Input
The input to your program is a list of up to 150 messages to encrypt. Each message has two lines (one for the key,
one for the message content). The first line starts with an integer 1≤n≤20, the length of the key, followed by n
integers which form the permutation. The second line contains the message content. Your program should encrypt
verything on the second line, spaces and all. Input ends when n is zero.

Output
For each message, pad the input with spaces as necessary, encrypt the message with the key, and output the encrypted
message. Include single quotes around the encrypted message.

Sample Input 1
1 1
Four score and seven years ago
2 2 1
our fathers brough forth on this continent a new nation,
5 1 3 2 5 4
conceived in liberty and dedicated to the proposition
10 5 10 8 1 3 6 4 7 2 9
that all men are created equal.
0

Sample Output 1
'Four score and seven years ago'
'uo rafhtre srbuohgf rohto  nhtsic noitentna n wen taoi,n'
'cnoeciev di nilbreyt na dddeciaet dt ohtep orpsotiino  '
' mltaatlh rece ea nr luaeedqta   .      '


TODO: had one mistake with test cases
"""

import sys

data = ''

# Reads data until newline and 0
new_line_passed = False
while True:
    new_data = sys.stdin.read(1)
    data += new_data

    if new_line_passed and new_data.rstrip() == '0':
        break

    if new_data == '\n':
        new_line_passed = True
    else:
        new_line_passed = False

data = data.splitlines()

length_of_data = len(data)


# For each line in data retrieved
for line_number in range(0, length_of_data, 2):

    # Get
    # key length and key code
    key_length_and_key = data[line_number].split()

    # If key length is 0 , end program
    if key_length_and_key[0] == '0':
        break

    # store key length and encryption key
    key_length = int(key_length_and_key[0])
    key = key_length_and_key[1:]

    # stored unencrypted message and store length
    unencrypted_message = data[line_number + 1]
    length_of_message = len(unencrypted_message)

    # find out if padding is needed
    length_of_encryption = key_length
    while length_of_encryption < length_of_message:
        length_of_encryption += key_length

    # pads message with spaces if needed
    if length_of_encryption > length_of_message:
        unencrypted_message += " " * (length_of_encryption - length_of_message)
    encrypted_message = [' '] * length_of_encryption

    # keeps track of what group encryption code currently affects
    partition_start_spot = 0

    for position in range(length_of_encryption):

        # loops through unencrypted message and encrypts based on encryption key
        key_spot = position % key_length
        if key_spot == 0:
            partition_start_spot = position
        position_to_change = partition_start_spot + (position % key_length)
        encrypted_message[position] = unencrypted_message[partition_start_spot + (int(key[key_spot]) - 1)]

    print("'" + ''.join(encrypted_message) + "'")
