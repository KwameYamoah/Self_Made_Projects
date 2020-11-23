"""
The Collatz Conjecture is an interesting phenomenon. Though its principle is very simple, it still remains among unresolved problems in mathematics, even after many years of study. However, the years of intensive research brought at least some results, which is a huge advantage of the human race against the aliens, because they did not study the conjecture for so many years. We want to keep this advantage.

Imagine a sequence defined recursively as follows: Start with any positive integer x0 (so-called “starting value”). Then repeat the following:

if xi is even, then xi+1=xi/2 (“half …”)

if xi is odd, then xi+1=3xi+1 (“…or triple plus one”)

The Collatz Conjecture says that every such sequence will eventually reach 1. It has still not been proven until today but we already know for sure that this is true for every x0<258. (Never tell this to aliens!)

In this problem, you are given two starting values and your task is to say after how many steps their sequences “meet” for the first time (which means the first number that occurs in both sequences) and at which number is it going to happen. For simplicity, we will assume that the sequence does not continue once it has reached the number one. In reality, it would then turn into 1,4,2,1,4,2,1,…, which quickly becomes boring.

Input
The input contains at most 1 500 test cases. Each test case is described by a single line containing two integer numbers A and B, 1≤A,B≤1 000 000.

The last test case is followed by a line containing two zeros.

Output
For each test case, output the sentence “A needs SA steps, B needs SB steps, they meet at C”, where SA and SB are the number of steps needed in both sequences to reach the same number C. Follow the output format precisely.

Sample Input 1
7 8
27 30
0 0

Sample Output 1
7 needs 13 steps, 8 needs 0 steps, they meet at 8
27 needs 95 steps, 30 needs 2 steps, they meet at 46


"""

import sys

data = ''

zero_passed = False
while True:
    new_data = sys.stdin.read(1)
    data += new_data

    if zero_passed and new_data.rstrip() == '0':
        break

    if new_data .rstrip() == '0':
        zero_passed = True
    elif new_data == ' ':
        pass
    else:
        zero_passed = False

data = data.splitlines()
data.pop(-1)

for line in data:
    line_as_list = line.split()
    f_num = int(line_as_list[0])
    s_num = int(line_as_list[1])
    first_num = f_num
    second_num = s_num
    first_num_step = 0
    second_num_step = 0
    first_num_seq = [first_num]
    second_num_seq = [second_num]

    meeting_value = 0
    while first_num != second_num:

        if first_num != 1:
            if first_num % 2 == 0:
                first_num /= 2
            else:
                first_num = (3 * first_num) + 1

        first_num_seq.append(first_num)

        if second_num != 1:
            if second_num % 2 == 0:
                second_num /= 2

            else:
                second_num = (3 * second_num) + 1

        second_num_seq.append(second_num)

        intersection = set(first_num_seq).intersection(set(second_num_seq))
        if len(intersection) > 0:
            meeting_value = intersection.pop()
            first_num_step = first_num_seq.index(meeting_value)
            second_num_step = second_num_seq.index(meeting_value)
            break

    print(f'{f_num} needs {first_num_step} steps, {s_num} needs {second_num_step} steps, they meet at '
          f'{int(meeting_value)}')

