package sudoku;

import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super(); // supernya adalah object krn ga extend apa2
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int cellsToGuess) {
        // I hardcode a puzzle here for illustration and testing.
        int[][] hardcodedNumbers =
                {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                        {6, 7, 2, 1, 9, 5, 3, 4, 8},
                        {1, 9, 8, 3, 4, 2, 5, 6, 7},
                        {8, 5, 9, 7, 6, 1, 4, 2, 3},
                        {4, 2, 6, 8, 5, 3, 7, 9, 1},
                        {7, 1, 3, 9, 2, 4, 8, 5, 6},
                        {9, 6, 1, 5, 3, 7, 2, 8, 4},
                        {2, 8, 7, 4, 1, 9, 6, 3, 5},
                        {3, 4, 5, 2, 8, 6, 1, 7, 9}};

        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }

        // Initialize all cells as "given"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = true;
            }
        }

        // Randomly make 'cellsToGuess' number of cells not given
        Random random = new Random();
        int cellsRemoved = 0;
        while (cellsRemoved < cellsToGuess) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);

            // Ensure the cell is not already removed
            if (isGiven[row][col]) {
                isGiven[row][col] = false;
                cellsRemoved++;
            }
        }
    }

    //(For advanced students) use singleton design pattern for this class
}
