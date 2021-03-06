"""
Born in Warsaw, Benoît Mandelbrot (1924–2010) is considered the father of
fractal geometry. He studied mathematical processes that described self-similar
 and natural shapes known as fractals. Perhaps his most well-known contribution is
 the Mandelbrot set, which is pictured below (the set contains the black points):

https://open.kattis.com/problems/mandelbrot/file/statement/en/img-0001.png

The Mandelbrot set is typically drawn on the complex plane, a 2-dimensional plane
representing all complex numbers. The horizontal axis represents the real portion
of the number, and the vertical axis represents the imaginary portion. A complex
number c=x+yi (at position (x,y) on the complex plane) is not in the Mandelbrot
set if the following sequence diverges:

zn+1←z2n+c
beginning with z0=0. That is, limn→∞|zn|=∞. If the sequence does not diverge,
then c is in the set.

Recall the following facts about imaginary numbers and their arithmetic:

i=−1−−−√,i2=−1,(x+yi)2=x2−y2+2xyi,|x+yi|=x2+y2−−−−−−√
where x and y are real numbers, and |⋅| is known as the modulus of a complex number
(in the complex plane, the modulus of x+yi is equal to the straight-line distance
from the origin to the the point (x,y)).

Write a program which determines if the sequence zn diverges for a given value c
within a fixed number of iterations. That is, is c in the Mandelbrot set or not?
To detect divergence, just check to see if |zn|>2 for any zn that we compute – if
this happens, the sequence is guaranteed to diverge.

Input
There are up to 15 test cases, one per line, up to end of file.
Each test case is described by a single line containing three numbers:
two real numbers −3≤x,y≤3, and an integer 0≤r≤10000. Each real number has
at most 4 digits after the decimal point. The value of c for this case is
x+yi, and r is the maximum number of iterations to compute.


Output
For each case, display the case number followed by whether the given c is in
the Mandelbrot set, using IN or OUT.

Sample Input 1
0 0 100
1.264 -1.109 100
1.264 -1.109 10
1.264 -1.109 1
-2.914 -1.783 200
0.124 0.369 200

Sample Output 1
Case 1: IN
Case 2: OUT
Case 3: OUT
Case 4: IN
Case 5: OUT
Case 6: IN
"""

