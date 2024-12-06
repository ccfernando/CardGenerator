import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CardGeneratorGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Deck deck;
    private JLabel[] cardLabels;
    private final String cardBackPath = "res/cards/back.png";  // Path to the card back image
    private Clip backgroundClip;

    public CardGeneratorGUI() {
        deck = new Deck();
        cardLabels = new JLabel[4];

        // Setting up the frame
        setTitle("Card Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
  	    setExtendedState(JFrame.MAXIMIZED_BOTH); // Make full-screen
  	  
        // Card panel for displaying cards
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(1, 4, 10, 10));
        cardPanel.setBackground(new Color(34, 139, 34));  // Dark green background like a card table

        // Adding the card labels and setting the initial display to the card back
        for (int i = 0; i < 4; i++) {
            cardLabels[i] = new JLabel(new ImageIcon(cardBackPath));
            cardLabels[i].setHorizontalAlignment(JLabel.CENTER);
            cardPanel.add(cardLabels[i]);
        }

        // Button panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 10));
        buttonPanel.setBackground(new Color(34, 139, 34));

        // Generate button
        JButton generateButton = new JButton("Generate Cards");
        generateButton.setFont(new Font("Arial", Font.BOLD, 16));
        generateButton.setBackground(new Color(255, 215, 0));  // Gold color
        generateButton.setForeground(Color.BLACK);
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateCards();
                
                if (backgroundClip!=null) {
                	backgroundClip.stop();
                }
             // Play background music
                playBackgroundMusic("res/music/ClassicOne.wav");

            }
        });
        buttonPanel.add(generateButton);

        // Reset button
        JButton resetButton = new JButton("Reset Cards");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(new Color(255, 69, 0));  // Orange-Red color
        resetButton.setForeground(Color.WHITE);
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetCards();
                backgroundClip.stop();
            }
        });
        buttonPanel.add(resetButton);

        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        
        setVisible(true);
    }

    private void generateCards() {
        for (int i = 0; i < 4; i++) {
            Card card = deck.drawCard();
            ImageIcon cardIcon = new ImageIcon(card.getImagePath());
            cardLabels[i].setIcon(cardIcon);
        }
    }

    private void resetCards() {
        for (JLabel cardLabel : cardLabels) {
            cardLabel.setIcon(new ImageIcon(cardBackPath));
        }
    }

    private void playBackgroundMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the music continuously
            backgroundClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CardGeneratorGUI();
            }
        });
    }
}