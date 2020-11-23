"""
Your favourite public transport company LS
(we cannot use their real name here, so we permuted the letters)
wants to change signs on all bus stops. Some bus stops have quite
a few buses that stop there and listing all the buses takes space.
However, if for example buses 141, 142, 143 stop there, we can write 141–143
instead and this saves space. Note that just for two buses this does not save space.

You are given a list of buses that stop at a bus stop.
What is the shortest representation of this list?

The first line of the input contains one integer N,1≤N≤1000, the number of buses
that stop at a bus stop. Then next line contains a list of N space separated
integers between 1 and 1000, which denote the bus numbers. All numbers are distinct.

Sample Input 1
6
180 141 174 143 142 175

Sample Output 1
141-143 174 175 180
"""

import sys

num_of_buses = int(sys.stdin.readline())
buses = sys.stdin.readline()[:-1].split(" ")

bus_numbers = []
for num in buses:
    bus_numbers.append(int(num))
bus_numbers.sort()

output = ''

start = bus_numbers[0]
group_size = 0

for i in range(num_of_buses):
    if i != (num_of_buses - 1):
        difference_is_1 = (bus_numbers[i+1] - bus_numbers[i]) == 1
        if difference_is_1:
            group_size += 1
            continue
        else:
            if group_size == 0:
                output += f'{start} '
            elif group_size == 1:
                output += f'{start} {bus_numbers[i]} '

            else:
                output += f'{start}-{bus_numbers[i]} '
            group_size = 0
            start = bus_numbers[i+1]
    else:
        if group_size == 0:
            output += f'{start}'
        elif group_size == 1:
            output += f'{start} {bus_numbers[i]}'

        else:
            output += f'{start}-{bus_numbers[i]}'

print(output)
