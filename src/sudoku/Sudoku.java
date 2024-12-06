package sudoku;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    private String playerName; //tambahan fitur input name

    // Constructor
    public Sudoku() {
        welcomeScreen();

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Add a button to the south to re-start the game via board.newGame()
        JPanel panel = new JPanel();
        panel.add(btnNewGame);
        cp.add(panel, BorderLayout.SOUTH);

        btnNewGame.addActionListener(e -> board.newGame());


        // Initialize the game board to start the game
        board.newGame();

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }

    private void welcomeScreen() {
        // Prompt for player name first
        String name = JOptionPane.showInputDialog(this, "Enter Your Name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            name = "Player";
        }

        // Create dialog for welcome screen
        JDialog welcomeDialog = new JDialog(this, "Welcome to Sudoku", true);
        welcomeDialog.setLayout(new GridBagLayout());  // Use GridBagLayout for centering components

        // Typing effect for the welcome message
        JTextArea welcomeLabel = new JTextArea();
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 28));  // More suitable font for game
        welcomeLabel.setWrapStyleWord(true);  // Wrap at word boundaries
        welcomeLabel.setLineWrap(true);  // Enable line wrapping
        welcomeLabel.setEditable(false);  // Make it non-editable
        welcomeLabel.setBackground(welcomeDialog.getBackground());  // Match background color
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Padding around text

        // Set preferred size to control text area height and width
        welcomeLabel.setPreferredSize(new Dimension(450, 200));

        // Create a panel to center the text
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Create the complete text and variables to track progress
        String completeText = "Ready, Set, Solve! The Sudoku challenge awaits you!";
        Timer timer = new Timer();  // Use Timer from java.util package

        // Declare the Start Game button as a final variable to use it inside TimerTask
        final JButton startButton = new JButton("Start Game");
        startButton.setEnabled(false);  // Start button is disabled initially
        startButton.addActionListener(e -> welcomeDialog.dispose());

        // Create TimerTask for typing effect
        TimerTask typingTask = new TimerTask() {
            int index = 0;

            @Override
            public void run() {
                if (index < completeText.length()) {
                    welcomeLabel.setText(welcomeLabel.getText() + completeText.charAt(index));
                    index++;
                } else {
                    startButton.setEnabled(true);  // Enable the Start Game button after typing is complete
                    timer.cancel();  // Cancel the timer after the text is fully typed
                }
            }
        };
        timer.schedule(typingTask, 0, 100);  // Schedule the typing effect with 100ms delay

        // Add components to the dialog
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        welcomeDialog.add(textPanel, gbc);  // Add the text panel at the center

        gbc.gridy = 1;
        welcomeDialog.add(startButton, gbc);  // Add the Start button below the text panel

        // Set dialog properties
        welcomeDialog.setSize(500, 350);  // Adjust dialog size
        welcomeDialog.setLocationRelativeTo(null);  // Center the dialog on the screen
        welcomeDialog.setVisible(true);  // Show the dialog
    }
}