import javax.imageio.ImageIO;           // Import for image loading
import javax.swing.*;                    // Import Swing components for GUI
import java.awt.*;                       // Import AWT for layout and graphics
import java.awt.event.ActionEvent;       // Import for handling action events
import java.awt.event.ActionListener;    // Import for action listener interface
import java.awt.image.BufferedImage;     // Import for buffered images
import java.io.File;                     // Import for file handling
import java.io.IOException;               // Import for handling IO exceptions
import java.util.ArrayList;               // Import for using ArrayList
import java.util.List;                    // Import for using List interface
import java.util.Random;                  // Import for generating random numbers

public class congrats2 extends JFrame {
    private String username;              // Variable to store the username
    private BufferedImage dogImage;       // Variable to hold the dog image

    // Constructor for the congrats2 class
    public congrats2(String username) {
        this.username = username;          // Initialize username
        setTitle("Chapter Complete");      // Set window title
        setSize(800, 600);                 // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit
        setLayout(null);                   // Use absolute positioning for custom placements
        setVisible(true);                  // Make the frame visible

        // Add confetti panel as the background
        ConfettiPanel confettiPanel = new ConfettiPanel();
        confettiPanel.setBounds(0, 0, 800, 600); // Set bounds for the confetti panel
        add(confettiPanel);                  // Add confetti panel to the frame

        // Load images
        loadImages();                        // Load the dog image

        // Create and set up the congratulations banner
        JLabel congratulationsLabel = new JLabel("CONGRATULATIONS");
        congratulationsLabel.setForeground(Color.WHITE); // Set text color
        congratulationsLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 32)); // Set font style and size
        congratulationsLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label
        congratulationsLabel.setBounds(200, 110, 400, 60); // Set position and size
        confettiPanel.add(congratulationsLabel); // Add to the confetti panel

        // Create and set up the completion message
        JLabel messageLabel = new JLabel("You have successfully completed");
        messageLabel.setForeground(Color.WHITE); // Set text color
        messageLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18)); // Set font style and size
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label
        messageLabel.setBounds(200, 170, 400, 30); // Set position and size
        confettiPanel.add(messageLabel); // Add to the confetti panel

        // Create and set up the chapter label
        JLabel chapterLabel = new JLabel("CHAPTER 2");
        chapterLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20)); // Set font style
        chapterLabel.setForeground(Color.WHITE); // Set text color
        chapterLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label
        chapterLabel.setBounds(300, 220, 200, 30); // Set position and size
        confettiPanel.add(chapterLabel); // Add to the confetti panel

        // Pet image with high-quality scaling
        Image scaledDogImage = dogImage.getScaledInstance(400, 228, Image.SCALE_SMOOTH); // Scale dog image
        JLabel petLabel = new JLabel(new ImageIcon(scaledDogImage)); // Create JLabel for pet image
        petLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label
        petLabel.setBounds(220, 215, 350, 290); // Set position and size
        confettiPanel.add(petLabel); // Add to the confetti panel

        // Create and set up the Home Page button
        RoundButton homeButton = new RoundButton("Home Page");
        homeButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18)); // Set font style and size
        homeButton.setBackground(new Color(245, 174, 82)); // Set button background color
        homeButton.setForeground(Color.WHITE); // Set button text color
        homeButton.setFocusPainted(false); // Disable focus painting
        homeButton.setBounds(325, 450, 150, 50); // Set position and size
        confettiPanel.add(homeButton); // Add button to the confetti panel

        // Add action listener to the home button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage(username); // Open the HomePage window
                dispose(); // Close the current window
            }
        });

        // Start confetti animation
        confettiPanel.startAnimation(); // Begin the confetti animation
    }

    // Main method to launch the congrats2 frame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            congrats2 screen = new congrats2("testUser"); // Create an instance with a test username
            screen.setVisible(true); // Set the screen visible
        });
    }

    // Method to load images
    private void loadImages() {
        try {
            // Attempt to load the dog image from the specified path
            dogImage = ImageIO.read(new File("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\360_F_841667335_trwxMNEoCrd41jbjEZZnVTTdmVhZYxWD.png"));
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if there's an error loading the image
        }
    }

    // Custom round button class for aesthetic button design
    private class RoundButton extends JButton {
        public RoundButton(String label) {
            super(label); // Call superclass constructor with button label
            setOpaque(false); // Set to non-opaque for rounded effect
            setFocusPainted(false); // Remove focus ring
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create(); // Create a copy of the graphics context
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
            g2.setColor(getBackground()); // Set the background color
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); // Draw rounded rectangle
            super.paintComponent(g); // Call superclass paint method
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create(); // Create a copy of the graphics context
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
            g2.setColor(getForeground()); // Set the border color
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50); // Draw rounded border
            g2.dispose(); // Dispose of the graphics context
        }
    }

    // Confetti Panel Class for animated confetti effect
    private class ConfettiPanel extends JPanel {
        private final List<Confetti> confettiList = new ArrayList<>(); // List to hold confetti particles

        public ConfettiPanel() {
            setLayout(null); // Set layout to null for absolute positioning
            setBackground(new Color(34, 45, 90)); // Set background color
            for (int i = 0; i < 100; i++) { // Add 100 confetti particles
                Random random = new Random(); // Create a new random object
                confettiList.add(new Confetti(random)); // Add new Confetti particle to the list
            }
        }

        // Method to start the confetti animation
        public void startAnimation() {
            Timer timer = new Timer(30, e -> { // Create a timer that fires every 30ms
                for (Confetti confetti : confettiList) {
                    confetti.update(); // Update each confetti particle's position
                }
                repaint(); // Repaint the panel to update the display
            });
            timer.start(); // Start the timer
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Call superclass paint method
            Graphics2D g2d = (Graphics2D) g; // Cast to Graphics2D for better rendering
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
            for (Confetti confetti : confettiList) {
                confetti.draw(g2d); // Draw each confetti particle
            }
        }
    }

    // Confetti Particle Class to manage individual confetti behavior
    private class Confetti {
        private int x, y, size; // Position and size of the confetti
        private final Color color; // Color of the confetti
        private int speed; // Falling speed of the confetti

        // Constructor to initialize confetti particles
        public Confetti(Random random) {
            reset(random); // Reset to a random starting position
            color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255), 150); // Random color with transparency
        }

        // Update method to change position of the confetti
        public void update() {
            y += speed; // Move the confetti down by its speed
            if (y > getHeight()) { // If it goes off screen
                Random random = new Random(); // Create a new random object
                reset(random); // Reset its position
            }
        }

        // Method to draw the confetti particle
        public void draw(Graphics2D g) {
            g.setColor(color); // Set the color for drawing
            g.fillOval(x, y, size, size); // Draw the confetti as an oval
        }

        // Reset method to initialize confetti properties
        private void reset(Random random) {
            x = random.nextInt(800); // Random x position within the frame width
            y = random.nextInt(600) - 600; // Start above the screen
            size = random.nextInt(10) + 5; // Random size between 5 and 15
            speed = random.nextInt(5) + 1; // Random speed between 1 and 5
        }
    }
}