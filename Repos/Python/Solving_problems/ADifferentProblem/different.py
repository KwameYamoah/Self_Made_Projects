"""
Each line of the input consists of a pair of integers.
Each integer is between 0 and 1015 (inclusive).
The input is terminated by end of file.


For each pair of integers in the input, output one line,
containing the absolute value of their difference.

Sample input
10 12
71293781758123 72784
1 12345677654321

Sample output
2
71293781685339
12345677654320
"""
import sys

for line in sys.stdin:
    numbers = line.split(' ')
    num1 = int(numbers[0])
    num2 = int(numbers[1])
    print(abs(num1 - num2))
