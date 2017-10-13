# Haskell solution Domino

## Getting a solution
The script has a `main`-method which needs a list of Integers as input. This are the values on the board from left to right, top to bottom. It assumes that you use values from zero upwards to create the `Bone`s. There are three boards already available which correspond with the boards given in the assignment, these are called `ex1`, `ex2` and `ex3`. To get the solution of these board just run `main <boardname>` and this will print all possible solutions on your screen.

If you would like to calculate the solutions for another board, create a list of Integers of the values on the board, adapt the width and height (line 58 and 61 respectively) to the dimensions of the board and run the `main`-method.

## Algorithm
In order to find all possible solutions, a tree is created. A tree is defined as a node with as value a `Board`, which contains a list of other `Tree`s. At each node, the next bone on the list is tried to fit the current board. This fitting is achieved by retrieving all possible locations (`getPairs`) where a bone could be placed on the board and trying if the placement of this bone at that pair is valid (the pips of the bone correspond to the values of the board). All the valid next board states are added as a new `Tree` in the list with as node value the new board state. Then, the next bone is tried. The algorithm stops when 1) all bones are placed on the board/the board is full or 2) when there are no free pairs left on the board to place a stone.
After the tree is created, all leaves are collected and the leaves which contain a full board (this is a valid solution for the game) are returned and printed on the screen.

## Testing
There are no tests available for the Haskell implementation of Domino.
