"""
After an unsuccessful attempt at claiming power peacefully, Borko has decided to tear down Mirko’s village hall, which was built for him out of cardboard by his loyal servants.

For this he will use Mirko’s microprocessor (which was stolen from him by Borko’s friend Zvonko), a bucket of water and a bag of sodium. He will enter the time of the “explosion” into the microprocessor, and it will drop the sodium in the water after the time is up.

Borko knows the current time and when he wants the explosion. He is not very fond of arithmetic and Zvonko is currently playing with marbles in his back yard so he can’t help him.

Write a program that calculates the time to the explosion (this is the time Borko will enter into the microprocessor). The time Borko wants is at least one second and at most 24 hours.

Input
The first line of input contains the current time in hh:mm:ss format (hours, minutes, seconds). The hours will be between 0 and 23 (inclusive) and the minutes and seconds between 0 and 59. The second line contains the time of the explosion in the same format.

Output
Output the desired time on a single line, in the same format as the times in the input.

Sample Input 1
20:00:00
04:00:00


Sample Output 1
08:00:00


Sample Input 2
12:34:56
14:36:22

Sample Output 2
02:01:26
"""

import sys

# get current time and explosion time
c_time = sys.stdin.readline()[:-1].split(":")
e_time = sys.stdin.readline()[:-1].split(":")

# add as ints into list
current_time = [int(c_time[0]), int(c_time[1]), int(c_time[2])]
explosion_time = [int(e_time[0]), int(e_time[1]), int(e_time[2])]

# constants to convert hours, mins and secs
HOURS_IN_DAY = 24
HOURS_TO_SECONDS = 3600
MINUTES_TO_SECONDS = 60

# calculates total seconds to use to check to see if explosion time is tomorrow
c_seconds = (current_time[0] * HOURS_TO_SECONDS) + \
            (current_time[1] * MINUTES_TO_SECONDS) + \
            current_time[2]
e_seconds = (explosion_time[0] * HOURS_TO_SECONDS) + \
            (explosion_time[1] * MINUTES_TO_SECONDS) + \
            explosion_time[2]


# explosion is smaller than current, add 24 hours to it then calculate time difference
if e_seconds < c_seconds:
    hour_difference = (explosion_time[0] + HOURS_IN_DAY) - current_time[0]
else:
    hour_difference = explosion_time[0] - current_time[0]


minute_difference = explosion_time[1] - current_time[1]
second_difference = explosion_time[2] - current_time[2]

# get total time difference as seconds
total_seconds_difference = (hour_difference * HOURS_TO_SECONDS) + \
                           (minute_difference * MINUTES_TO_SECONDS) + \
                           second_difference

# convert from seconds back to whole time
final_hour = total_seconds_difference // 3600
total_seconds_difference -= final_hour * HOURS_TO_SECONDS
final_minute = total_seconds_difference // 60
total_seconds_difference -= final_minute * MINUTES_TO_SECONDS
final_seconds = total_seconds_difference

# formats time
if final_hour == 0 and final_minute == 0 and final_seconds == 0:
    print('24:00:00')
else:
    print(f'{int(final_hour):02}:{int(final_minute):02}:{int(final_seconds):02}')


