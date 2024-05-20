import java.util.*;

// A class which has the solution methods for solving the 8-puzzle game
public class Solution {
    // Shows the goal state of the puzzle
    public static final int[][] GOAL_STATE = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    // Getter method for GOAL_STATE
    public static int[][] getGoalState() {
        return GOAL_STATE;
    }

    // A method to count the number of inversions in the puzzle configuration and returns the inversion count
    public static int countInversions(int[][] puzzle) {
        int inversions = 0;
        int[] flattenedPuzzle = flattenPuzzle(puzzle);

        // Iterates through the flattened puzzle to count inversions
        for (int i = 0; i < flattenedPuzzle.length; i++) {
            for (int j = i + 1; j < flattenedPuzzle.length; j++) {
                if (flattenedPuzzle[i] != 0 && flattenedPuzzle[j] != 0 && flattenedPuzzle[i] > flattenedPuzzle[j]) {
                    inversions++;
                }
            }
        }

        return inversions;
    }

    // A helper method to flatten the puzzle into a 1D array for easier inversion counting and returns the flattened puzzle
    private static int[] flattenPuzzle(int[][] puzzle) {
        int[] flattenedPuzzle = new int[puzzle.length * puzzle[0].length];
        int index = 0;

        // Flattens the puzzle
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                flattenedPuzzle[index++] = puzzle[i][j];
            }
        }

        return flattenedPuzzle;
    }

    // A method to check if the puzzle is solvable based on the number of inversions
    // if the inversion count is odd the initial state is not solvable
    // if the inversion count is even the initial state is solvable
    public static boolean isSolvable(int[][] puzzle) {
        int inversions = countInversions(puzzle);
        return inversions % 2 == 0;
    }

    // A* Algorithm implementation
    public static List<int[][]> aStar(int[][] initial, int[][] goal) {
        // Initializes open and closed lists
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(Node::getHeuristicValue));
        Set<Node> closedList = new HashSet<>();

        // Creates initial node
        Node initialNode = new Node(initial, 0, manhattanDistance(initial, goal), null);
        openList.add(initialNode);

        // A* Algorithm main loop
        while (!openList.isEmpty()) {
            // Selects node with lowest f value
            Node currentNode = openList.poll();
            // Moves node to closed list
            closedList.add(currentNode);

            // Checks if goal state reached
            if (currentNode.isGoalState()) {
                // Reconstructs path
                return reconstructPath(currentNode);
            }

            // Generates neighbors
            List<Node> neighbors = generateNeighbors(currentNode, goal);
            for (Node neighbor : neighbors) {
                if (closedList.contains(neighbor)) {
                    continue; // Neighbor is already evaluated
                }

                // Calculates new cost
                int newCost = currentNode.getCost() + 1;

                // Checks if neighbor is not in open list or if new cost is lower
                if (!openList.contains(neighbor) || newCost < neighbor.getCost()) {
                    // Updates neighbor's cost and heuristic value
                    neighbor.setCost(newCost);
                    neighbor.setHeuristicValue(newCost + manhattanDistance(neighbor.getPuzzle(), goal));
                    neighbor.setParent(currentNode); // Sets parent for path reconstruction

                    // Adds neighbor to open list
                    openList.add(neighbor);
                }
            }
        }

        // No solution found
        return null;
    }

    // A helper method to reconstruct path from goal node to initial node
    private static List<int[][]> reconstructPath(Node goalNode) {
        List<int[][]> path = new ArrayList<>();
        Node currentNode = goalNode;
        while (currentNode != null) {
            path.add(0, currentNode.getPuzzle());
            currentNode = currentNode.getParent();
        }
        return path;
    }

    // A helper method to generate neighboring states
    private static List<Node> generateNeighbors(Node node, int[][] goal) {
        List<Node> neighbors = new ArrayList<>();
        int[][] puzzle = node.getPuzzle();
        int emptyRow = -1, emptyCol = -1;

        // Finds the position of the empty space (0)
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }

        // Defines possible moves (up, down, left, right)
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Generates neighboring states
        for (int[] move : moves) {
            int newRow = emptyRow + move[0];
            int newCol = emptyCol + move[1];

            // Checks if new position is within bounds
            if (newRow >= 0 && newRow < puzzle.length && newCol >= 0 && newCol < puzzle[0].length) {
                // Creates a copy of the current puzzle configuration
                int[][] newPuzzle = deepCopy(puzzle);
                // Swaps empty space with neighboring tile
                int temp = newPuzzle[emptyRow][emptyCol];
                newPuzzle[emptyRow][emptyCol] = newPuzzle[newRow][newCol];
                newPuzzle[newRow][newCol] = temp;
                // Creates a new node for the neighbor
                neighbors.add(new Node(newPuzzle, 0, manhattanDistance(newPuzzle, goal), null));
            }
        }

        return neighbors;
    }

    // A helper method to deep copy a 2D array
    private static int[][] deepCopy(int[][] array) {
        int[][] newArray = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return newArray;
    }

    // Heuristic 1: Misplaced Tiles
    public static int misplacedTiles(int[][] puzzle, int[][] goal) {
        int misplaced = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] != goal[i][j]) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }

    // Heuristic 2: Manhattan Distance
    public static int manhattanDistance(int[][] puzzle, int[][] goal) {
        int distance = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                int value = puzzle[i][j];
                if (value != 0) {
                    int goalRow = (value - 1) / puzzle.length;
                    int goalCol = (value - 1) % puzzle.length;
                    distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return distance;
    }
}
