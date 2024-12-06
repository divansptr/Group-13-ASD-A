package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;

    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    private String playerName;

    public Sudoku() {
        welcomeScreen();

        SoundEffect backSound = new SoundEffect("sudoku/music.wav");
        backSound.play();

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(board, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.add(btnNewGame);
        cp.add(panel, BorderLayout.SOUTH);

        btnNewGame.addActionListener(e -> {
            showDifficultySelection(); // Show difficulty selection when New Game is clicked
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    private void welcomeScreen() {
        // Get player's name from input dialog
        String name = JOptionPane.showInputDialog(this, "Enter Your Name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) {
            name = "Player"; // Default to "Player" if no name is entered
        }

        JDialog welcomeDialog = new JDialog(this, "Welcome to Sudoku", true);
        welcomeDialog.setLayout(new GridBagLayout());

        JTextArea welcomeLabel = new JTextArea();
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        welcomeLabel.setWrapStyleWord(true);
        welcomeLabel.setLineWrap(true);
        welcomeLabel.setEditable(false);
        welcomeLabel.setBackground(welcomeDialog.getBackground());
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        welcomeLabel.setPreferredSize(new Dimension(450, 200));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.add(welcomeLabel, BorderLayout.CENTER);

        String completeText = "Ready, Set, Solve! The Sudoku challenge awaits you!";
        Timer timer = new Timer();

        final JButton startButton = new JButton("Start Game");
        startButton.setEnabled(false);
        startButton.addActionListener(e -> {
            welcomeDialog.dispose();
            showDifficultySelection();  // Show level selection after clicking "Start Game"
        });

        TimerTask typingTask = new TimerTask() {
            int index = 0;

            @Override
            public void run() {
                if (index < completeText.length()) {
                    welcomeLabel.setText(welcomeLabel.getText() + completeText.charAt(index));
                    index++;
                } else {
                    startButton.setEnabled(true);
                    timer.cancel();
                }
            }
        };
        timer.schedule(typingTask, 0, 100);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        welcomeDialog.add(textPanel, gbc);

        gbc.gridy = 1;
        welcomeDialog.add(startButton, gbc);

        welcomeDialog.setSize(500, 350);
        welcomeDialog.setLocationRelativeTo(null);
        welcomeDialog.setVisible(true);
    }

    private void showDifficultySelection() {
        // Show difficulty selection dialog after starting the game
        String[] options = {"Easy", "Intermediate", "Difficult"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Difficulty Level:",
                "New Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        GameLevel selectedLevel;
        switch (choice) {
            case 1:
                selectedLevel = GameLevel.INTERMEDIATE;
                break;
            case 2:
                selectedLevel = GameLevel.DIFFICULT;
                break;
            case 0:
            default:
                selectedLevel = GameLevel.EASY;
                break;
        }

        board.newGame(selectedLevel);  // Pass the selected difficulty level to GameBoardPanel
    }

}