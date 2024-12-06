package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();

    // Timer-related fields
    private Timer gameTimer;
    private int secondsElapsed;
    private JLabel timerLabel;

    /** Constructor */
    public GameBoardPanel() {
        // Set the layout manager for the panel
        super.setLayout(new BorderLayout());  // Use BorderLayout for proper positioning

        // Initialize the timer label and display it
        timerLabel = new JLabel("Time: 0", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(timerLabel, BorderLayout.NORTH);  // Add timer to the top of the layout

        // Panel for Sudoku grid
        JPanel boardPanel = new JPanel(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));
        // Initialize the 2D array of Cell, and add them to the boardPanel
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                boardPanel.add(cells[row][col]);  // Add each cell to the board panel
            }
        }

        // Add the boardPanel to the center of the layout
        add(boardPanel, BorderLayout.CENTER);

        // Allocate a common KeyListener for all the cells
        CellKeyListener keyListener = new CellKeyListener();

        // Add this common listener to all editable cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {  // Only add listeners to editable cells
                    cells[row][col].addKeyListener(keyListener);
                }
            }
        }

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

        // Initialize timer variables
        secondsElapsed = 0;
    }

    // Start a new game
    public void newGame() {
        puzzle.newPuzzle(2);
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
        startTimer();  // Start the timer when a new game begins
    }

    // Check if the puzzle is solved
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // Start the timer
    private void startTimer() {
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                int hours = secondsElapsed / 3600; // Calculate hours
                int minutes = (secondsElapsed % 3600) / 60; // Calculate minutes
                int seconds = secondsElapsed % 60; // Calculate seconds

                // Format the time as hh:mm:ss
                String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                timerLabel.setText("Keep Going! Time: " + timeFormatted);  // Update the time display
            }
        });
        gameTimer.start();  // Start the timer
    }


    // Stop the timer
    private void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();  // Stop the timer when the puzzle is solved
        }
    }

    // KeyListener implementation for cell inputs
    private class CellKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            // We don't need this method for our case, so we can leave it empty
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // We don't need this method for our case, so we can leave it empty
        }

        @Override
        public void keyTyped(KeyEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            char typedChar = e.getKeyChar();
            if (typedChar >= '1' && typedChar <= '9') {
                int numberIn = Character.getNumericValue(typedChar);
                if (numberIn == sourceCell.number) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                }
                sourceCell.paint();  // Repaint the cell based on its status

                // Check if the puzzle is solved
                if (isSolved()) {
                    stopTimer();  // Stop the timer
                    JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle in " + secondsElapsed + " seconds!", "Puzzle Solved", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
