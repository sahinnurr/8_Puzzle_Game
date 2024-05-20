The 8-Puzzle is a sliding puzzle consisting of 8 numbered tiles in a 3x3 grid with one empty space. The objective is to arrange the tiles in numerical order (12345678 and a empty space). The puzzle starts with a random configuration and the goal is to reach a goal state. Movements involve sliding tiles adjacent to the empty space. The solution is a sequence of moves encoded using four possible actions: U (up), D (down), R (right), and L (left). The problem is formally defined with state space S representing all possible puzzle states, an initial state randomly generated with one empty tile, and a goal state with tiles in numerical order. A transition function defines state changes. Solving the puzzle often involves heuristic search algorithms like A* search, which evaluates nodes based on a combination of the cost of the path from the initial state and a heuristic estimation of the cost to reach the goal state. Two common heuristics for the 8-Puzzle are counting misplaced tiles and calculating the Manhattan distance of each tile from its goal position. Determining solvability involves checking the number of inversions in the puzzle configuration. If the inversion count is even, the initial state is solvable and if it is odd, the initial state cannot be solved.

![image](https://github.com/sahinnurr/8_Puzzle_Game/assets/163745846/55c66b00-1b08-4ea7-9c4b-7a8ae67beeb1)
![image](https://github.com/sahinnurr/8_Puzzle_Game/assets/163745846/b05418f0-520d-4a94-82e7-4355cec195a0)

In either solvable or unsolvable case, the initial state is printed to the terminal. If the case is solvable the moves, which are generated by the empty cell, are printed with U for up, D for down, R for right and L for left. The moves from initial state to the goal state can be seen visually step by step with accurate calculation. If the initial state is not solvable “The initial board configuration is not solvable.” is printed to the terminal. This project is successfully generated. Two examples of the output are displayed, the first one is a solvable case and the second one is an unsolvable case. 
