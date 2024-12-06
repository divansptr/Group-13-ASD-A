package sudoku;

import java.awt.*;
import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;

    // Private variables
    private final GameBoardPanel board = new GameBoardPanel();
    private final JButton btnNewGame = new JButton("New Game");
    private Clip backgroundMusicClip; // Clip for background music
    private boolean isMusicEnabled = true; // Flag to track if music is enabled

    // Constructor
    public Sudoku() {
        welcomeScreen();

        // Set up main content pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Add the game board to the center
        cp.add(board, BorderLayout.CENTER);

        // Add button panel to the south
        JPanel panel = new JPanel();
        panel.add(btnNewGame);
        cp.add(panel, BorderLayout.SOUTH);

        // Add action listener to the new game button
        btnNewGame.addActionListener(e -> {
            GameLevel defaultLevel = GameLevel.EASY; // Example: default level
            board.newGame(defaultLevel);
        });

        // Initialize the game board with default level
        GameLevel defaultLevel = GameLevel.EASY; // Example: default level
        board.newGame(defaultLevel);

        // Set up the menu bar
        setUpMenuBar();

        pack(); // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // To handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }

    // Method to set up the menu bar
    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // Create a menu bar

        JMenu optionsMenu = new JMenu("Options"); // Create the "Options" menu

        // Menu item to enable/disable music
        JMenuItem toggleMusicItem = new JMenuItem("Disable Music");
        toggleMusicItem.addActionListener(e -> {
            toggleMusic();
            // Update the menu item text based on the music status
            toggleMusicItem.setText(isMusicEnabled ? "Disable Music" : "Enable Music");
        });

        // Add menu items to the options menu
        optionsMenu.add(toggleMusicItem);

        // Add options menu to the menu bar
        menuBar.add(optionsMenu);

        // Set the menu bar for the frame
        setJMenuBar(menuBar);
    }

    // Method to play background music
    private void playBackgroundMusic(String filePath) {
        try {
            if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
                backgroundMusicClip.stop();
                backgroundMusicClip.close();
            }
            File musicFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop music continuously
            backgroundMusicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop music
    protected void stopMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
    }

    // Method to enable/disable music
    private void toggleMusic() {
        if (backgroundMusicClip != null) {
            if (isMusicEnabled) {
                backgroundMusicClip.stop(); // Stop music if it is currently enabled
            } else {
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Resume looping music
                backgroundMusicClip.start();
            }
            isMusicEnabled = !isMusicEnabled; // Toggle the music enabled flag
        }
    }

    // Welcome screen with typing effect
    private void welcomeScreen() {
        // Prompt for player name
        String playerName = JOptionPane.showInputDialog(this, "Enter Your Name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";
        }

        // Create dialog for welcome screen
        JDialog welcomeDialog = new JDialog(this, "Welcome to Sudoku", true);
        welcomeDialog.setLayout(new GridBagLayout());

        // Typing effect for the welcome message
        JTextArea welcomeLabel = new JTextArea();
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 28));
        welcomeLabel.setWrapStyleWord(true);
        welcomeLabel.setLineWrap(true);
        welcomeLabel.setEditable(false);
        welcomeLabel.setBackground(welcomeDialog.getBackground());
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        welcomeLabel.setPreferredSize(new Dimension(450, 200));

        // Create the complete welcome message
        String completeText = "Welcome, " + playerName + "! Ready, Set, Solve! The Sudoku challenge awaits you!";
        Timer timer = new Timer();

        // Start Game button
        final JButton startButton = new JButton("Start Game");
        startButton.setEnabled(false);
        startButton.addActionListener(e -> {
            welcomeDialog.dispose();
            // Begin the game after closing the welcome screen
            startGame();
        });

        // Typing effect task
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
        timer.schedule(typingTask, 0, 100); // Schedule typing effect with 100ms delay

        // Add components to the dialog
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        welcomeDialog.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        welcomeDialog.add(startButton, gbc);

        // Dialog properties
        welcomeDialog.setSize(500, 350);
        welcomeDialog.setLocationRelativeTo(null); // Center the dialog on screen
        welcomeDialog.setVisible(true);

        // Play music after welcome screen shows
        playBackgroundMusic("src/sudoku/music.wav");
    }

    private void showDifficultySelection() {
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

        // Tentukan tingkat kesulitan berdasarkan pilihan
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

        // Mulai permainan dengan level yang dipilih setelah pop-up
        board.newGame(selectedLevel);
    }


    // Start the game after welcome screen
    private void startGame() {
        // Add logic here to start the actual game
        showDifficultySelection();
    }
}
