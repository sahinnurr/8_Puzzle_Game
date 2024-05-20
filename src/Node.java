import java.util.Arrays;

// A class which represents a node in the puzzle-solving algorithm
public class Node {
    // Instance variables
    private int[][] puzzle; // Current state of puzzle
    private int cost; // The cost of getting the node form the initial state
    private int heuristicValue; // The Heuristic value of the node
    private Node parent; // Parent node

    // Constructor
    public Node(int[][] puzzle, int cost, int heuristicValue, Node parent) {
        this.puzzle = puzzle;
        this.cost = cost;
        this.heuristicValue = heuristicValue;
        this.parent = parent;
    }

    //Getters
    public int[][] getPuzzle() {
        return puzzle;
    }

    public int getCost() {
        return cost;
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public Node getParent() {
        return parent;
    }

    //Setters
    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    // A boolean method for checking the current state whether it is the same as the goal state
    public boolean isGoalState() {
        // Checks if current puzzle state is the goal state (123456780)
        return Arrays.deepEquals(puzzle, new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
    }
}
