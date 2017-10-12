# Haskell solution Domino

## Getting a solution
The script has a `main` method which needs two input variables: a `Board` and a list of `Bone`s. By running `main board bones` the solutions for a 7x8 grid (this is the first example given in the assignment description) is calculated. If you want to calculate the solutions for a smaller (predefined), adapt the width to 3 (line 35) and height to 4 (line 38) and run `main smallBoard smallBones`;
If you would like to calculate the solutions for another grid than these predefined grids. Generate a `Board`, which is a list of `Value`s. These need two variables: the position and the value. the `Value`s are listed from left to right, top to bottom. Next to that, create a list of `Bone`s. A `Bone` is a tuple with three arguments: 1) the first pip, 2) the second pip, and 3) the number of the bone. Finally, run the `main` method with as arguments the created board and list of bones. This will print all possible solutions of the given grid.

## Algorithm
In order to find all possible solutions, a tree is created. A tree is defined as a node with as value a `Board`, which contains a list of other `Tree`s. At each node, the next bone on the list is tried to fit the current board. This fitting is achieved by retrieving all possible locations (`getPairs`) where a bone could be placed on the board and trying if the placement of this bone at that pair is valid (the pips of the bone correspond to the values of the board). All the valid next board states are added as a new `Tree` in the list with as node value the new board state. Then, the next bone is tried. The algorithm stops when 1) all bones are placed on the board/the board is full or 2) when there are no free pairs left on the board to place a stone.
After the tree is created, all leaves are collected and the leaves which contain a full board (this is a valid solution for the game) are returned and printed on the screen.

## Testing
There are no tests available for the Haskell implementation of Domino.
