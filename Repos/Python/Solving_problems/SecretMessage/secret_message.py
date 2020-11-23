"""
Jack and Jill developed a special encryption method, so they can enjoy conversations without worrrying about
eavesdroppers. Here is how: let L be the length of the original message, and M be the smallest square number greater
than or equal to L. Add (M−L) asterisks to the message, giving a padded message with length M. Use the padded message
to fill a table of size K×K, where K2=M. Fill the table in row-major order (top to bottom row, left to right column in
each row). Rotate the table 90 degrees clockwise. The encrypted message comes from reading the message in row-major
order from the rotated table, omitting any asterisks.

For example, given the original message ‘iloveyouJack’, the message length is L=12. Thus the padded message is
‘iloveyouJack****’, with length M=16. Below are the two tables before and after rotation.

i l o v
e y o u
J a c k
* * * *

* J e i
* a y l
* o c o
* k u v


Then we read the secret message as ‘Jeiaylcookuv’.

Input
The first line of input is the number of original messages, 1≤N≤100. The following N lines each have a message to
encrypt. Each message contains only characters a–z (lower and upper case), and has length 1≤L≤10000.

Output
For each original message, output the secret message.

Sample Input 1
2
iloveyoutooJill
TheContestisOver

Sample Output 1
iteiloylloooJuv
OsoTvtnheiterseC

"""

import sys
import math
number_of_messages = int(sys.stdin.readline())

all_messages = []


for messages in range(number_of_messages):
    unencrypted_message = sys.stdin.readline()[:-1]
    all_messages.append(unencrypted_message)

for messages in all_messages:
    length_of_message = len(messages)

    i = round(math.sqrt(length_of_message))

    greater_squared_number = -1
    while i < length_of_message:
        if i**2 >= length_of_message:
            greater_squared_number = i**2
            break
        else:
            i += 1

    encrypted_message = messages + (greater_squared_number - len(messages)) * '*'
    encryption_array = []

    x = 0
    for pos in range(i):
        temp_list = []
        for y in range(i):
            temp_list.append(encrypted_message[x])
            x += 1
        encryption_array.append(temp_list)

    row = i - 1
    column = i

    result = ''

    for column_num in range(column):
        for row_num in range(row, -1, -1):
            if encryption_array[row_num][column_num] != '*':
                result += encryption_array[row_num][column_num]

    print(result)
