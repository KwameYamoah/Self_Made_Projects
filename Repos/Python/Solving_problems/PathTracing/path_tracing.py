"""
Billy likes to wander around. Each day he follows a sequence of up, down, left and right moves. At the end of the day,
he would like to know where he’s been. You are going to help by providing Billy with a program that draws a map for him.
The program should read a sequence of moves he makes on a given day and produce a map showing where he’s been.

Input
Input is a sequence of up to 500 moves, one move per line, until end of file. Each move is one of left, right,
 up or down.

Output
Print a map of the path described by the sequence of moves. Mark the start and end locations with S and E, respectively.
 Mark other locations with *. Outline the whole map with a rectangle made of the # character. The whole map is the
 smallest rectangle containing the path. The path never starts and ends at the same location. Use spaces to indicate
  parts of the map which are not visited by the path, but do not use any extra spaces (e.g. outside the map outline).


Sample Input 1
down
down
left
left
up
up
up
left
left

Sample Output 1
#######
#E**  #
#  * S#
#  * *#
#  ***#
#######
"""