import java.awt.event.KeyEvent; // for the constants of the keys on the keyboard
import java.util.List;


// A program that partially implements the 8 puzzle.
public class EightPuzzle {
   // The main method is the entry point where the program starts execution.
   public static void main(String[] args) {
      // StdDraw setup
      // -----------------------------------------------------------------------
      // set the size of the canvas (the drawing area) in pixels
      StdDraw.setCanvasSize(500, 500);
      // set the range of both x and y values for the drawing canvas
      StdDraw.setScale(0.5, 3.5);
      // enable double buffering to animate moving the tiles on the board
      StdDraw.enableDoubleBuffering();

      // create a random board for the 8 puzzle
      Board board = new Board();

      // Display initial board
      System.out.println("Initial Board:");
      board.draw();
      printBoard(board.getBoard());

      // Checks if the board is solvable
      if (!Solution.isSolvable(board.getBoard())) {
         System.out.println("The initial board configuration is not solvable.");
         // Prints the number of inversions
         System.out.println("Number of inversions: " + Solution.countInversions(board.getBoard()));
         return;  // Exits the program if the board is not solvable
      }

      // Gets the goal state
      int[][] goalState = Solution.getGoalState();

      // Solves the puzzle using A* algorithm
      List<int[][]> solutionPath = Solution.aStar(board.getBoard(), Solution.GOAL_STATE);

      if (solutionPath != null) {
         // Executes each move in the solution path
         for (int i = 0; i < solutionPath.size() - 1; i++) {
            // Clears the board before drawing the next move
            StdDraw.clear();

            // Gets the previous and current board states
            int[][] prevBoard = solutionPath.get(i);
            int[][] currBoard = solutionPath.get(i + 1);

            // Draw the board
            board.setBoard(currBoard);
            board.draw();
            StdDraw.show();

            // Prints the move
            System.out.print(getMoveDescription(prevBoard, currBoard));


            // Pause for a short time to visualize the solving process
            StdDraw.pause(500); // Adjust the duration as needed
         }
      } else {
         System.out.println("No solution found.");
      }
   }

   // Prints the board state to the console/terminal
   private static void printBoard(int[][] board) {
      for (int[] row : board) {
         for (int value : row) {
            System.out.print(value + " ");
         }
         System.out.println();
      }
   }
   // A helper method to get the description of a move
   private static String getMoveDescription(int[][] prevBoard, int[][] currBoard) {
      // Compares the positions of the empty cells in the previous and current board states
      int prevEmptyRow = -1;
      int prevEmptyCol = -1;
      int currEmptyRow = -1;
      int currEmptyCol = -1;

      // Finds the position of the empty cell in the previous board
      for (int i = 0; i < prevBoard.length; i++) {
         for (int j = 0; j < prevBoard[i].length; j++) {
            if (prevBoard[i][j] == 0) {
               prevEmptyRow = i;
               prevEmptyCol = j;
               break;
            }
         }
         if (prevEmptyRow != -1) {
            break;
         }
      }

      // Finds the position of the empty cell in the current board
      for (int i = 0; i < currBoard.length; i++) {
         for (int j = 0; j < currBoard[i].length; j++) {
            if (currBoard[i][j] == 0) {
               currEmptyRow = i;
               currEmptyCol = j;
               break;
            }
         }
         if (currEmptyRow != -1) {
            break;
         }
      }

      // Determines the move based on the changes in the empty cell positions
      if (prevEmptyRow < currEmptyRow) {
         return "D";
      } else if (prevEmptyRow > currEmptyRow) {
         return "U";
      } else if (prevEmptyCol < currEmptyCol) {
         return "R";
      } else if (prevEmptyCol > currEmptyCol) {
         return "L";
      } else {
         return "No Move"; // If the empty cell didn't move
      }
   }
}