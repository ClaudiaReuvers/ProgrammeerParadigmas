# Java solution Domino

## Getting a solution
The `Game` class has a main method where a small grid of 3x4 and a larger grid of 7x8 (this is the first example given in the assignment description) are already available. To get the solution of these grids, just run the `main` method of `Game`.
If you want to get the solution of another grid, create a new `Game` with as parameters 1) the width of the grid, 2) the height of the grid, 3) a list of the values on the grid (listed from left to right, top to bottom), and 4) the highest value a pip can have. Create a tree by running the `createGameTree` method on the game. Finally, retrieve the solutions from the tree by running `getSolutions`. If you would like to print the solutions of the grid, use `printSolutions` with the list of solutions as a parameter.

## Algorithm
In order to find all possible solutions, a tree is created. At each node, the next bone on the list (which corresponds with the depth of the current node) is tried to fit the current board. This fitting is achieved by retrieving all possible locations (pairs) where a bone could be placed on the board and trying if the placement of this bone at that pair is valid (the pips of the bone correspond to the values of the board). All the valid next board states are added as children of this node. Then, the next bone is tried. The algorithm stops when 1) all bones are placed on the board/the board is full or 2) when there are no free pairs left on the board to place a stone.
After the tree is created, all leaves are collected and the leaves which contain a full board (this is a valid solution for the game) are returned and printed on the screen.

## Testing
There are three tests available for the `Board`, `Game` and `TreeNode`. These are located in the same package as the classes.
