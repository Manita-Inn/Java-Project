import javax.swing.*;                   // Import for Swing components for GUI
import java.awt.*;                      // Import for AWT classes like Color and Graphics
import java.awt.event.*;                // Import for handling mouse events

public class GameOver5 extends JFrame {
    private String username; // Declare a variable to hold the username

    // Constructor for the GameOver5 class
    public GameOver5(String username) { // Constructor accepts a username
        this.username = username; // Initialize the username
        setTitle("Game Over"); // Set the window title
        setSize(800, 600); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application on close
        getContentPane().setBackground(hexToColor("#324071")); // Set background color
        setLayout(null); // Use absolute positioning for layout

        // Create a rounded panel for the "Game Over" banner
        RoundedPanel bannerPanel = new RoundedPanel();
        bannerPanel.setBackground(hexToColor("#5B69C0")); // Set background color for the banner
        bannerPanel.setBounds(40, 90, 700, 115); // Set position and size of the banner panel

        // Create label for the "Game Over" message
        JLabel bannerLabel = new JLabel("Game Over", JLabel.CENTER);
        bannerLabel.setFont(new Font("Rog Fonts", Font.BOLD, 80)); // Set font style and size
        bannerLabel.setForeground(hexToColor("#000000")); // Set text color
        bannerPanel.setLayout(new BorderLayout()); // Use BorderLayout for the panel
        bannerPanel.add(bannerLabel, BorderLayout.CENTER); // Add label to the center of the panel
        add(bannerPanel); // Add the banner panel to the frame

        // Instruction label
        JLabel instructionLabel = new JLabel("You may restart the chapter over again!", JLabel.CENTER);
        instructionLabel.setFont(new Font("Lato", Font.PLAIN, 27)); // Set font style and size
        instructionLabel.setForeground(Color.WHITE); // Set text color
        instructionLabel.setBounds(90, 250, 600, 30); // Set position and size
        add(instructionLabel); // Add instruction label to the frame

        // Create and add garden icon label
        JLabel gardenLabel = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\garden_icon.png", 850, 120, -25, 473);
        add(gardenLabel); // Add garden icon to the frame

        // Create and add falling splash icon label
        JLabel splashLabel = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\Screenshot 2024-11-11 224927 copy.png", 830, 100, -25, -9);
        add(splashLabel); // Add splash icon to the frame

        // Create "Home Page" button panel
        RoundedPanel homePanel = new RoundedPanel();
        homePanel.setBackground(hexToColor("#f7ac06")); // Set background color for the button
        homePanel.setBounds(200, 350, 180, 50); // Set position and size

        // Create label for the "Home Page" button
        JLabel homeLabel = new JLabel("Home Page", JLabel.CENTER);
        homeLabel.setFont(new Font("News706 BT", Font.PLAIN, 24)); // Set font style and size
        homeLabel.setForeground(Color.WHITE); // Set text color
        homePanel.setLayout(new BorderLayout()); // Use BorderLayout for the button panel
        homePanel.add(homeLabel, BorderLayout.CENTER); // Add label to the center of the panel
        add(homePanel); // Add button panel to the frame

        // Create "Restart" button panel
        RoundedPanel nextPanel = new RoundedPanel();
        nextPanel.setBackground(hexToColor("#f7ac06")); // Set background color for the button
        nextPanel.setBounds(400, 350, 180, 50); // Set position and size

        // Create label for the "Restart" button
        JLabel nextLabel = new JLabel("Restart", JLabel.CENTER);
        nextLabel.setFont(new Font("News706 BT", Font.PLAIN, 24)); // Set font style and size
        nextLabel.setForeground(Color.WHITE); // Set text color
        nextPanel.setLayout(new BorderLayout()); // Use BorderLayout for the button panel
        nextPanel.add(nextLabel, BorderLayout.CENTER); // Add label to the center of the panel
        add(nextPanel); // Add button panel to the frame

        // Add mouse listener to the "Home Page" button
        homePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Code to go to Home Page
                new HomePage(username); // Create a new HomePage instance
                dispose(); // Close the current frame
            }
        });

        // Add mouse listener to the "Restart" button
        nextPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Code to restart the chapter
                new chapter5(username); // Create a new chapter5 instance
                dispose(); // Close the current frame
            }
        });

        setVisible(true); // Make the frame visible
    }

    // Custom JPanel class with rounded corners
    private static class RoundedPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Call superclass paint method
            Graphics2D g2 = (Graphics2D) g; // Cast to Graphics2D for better rendering
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
            g2.setColor(getBackground()); // Set the color to the panel's background color
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight()); // Draw a rounded rectangle
        }

        @Override
        public void setOpaque(boolean isOpaque) {
            super.setOpaque(false); // Make the panel non-opaque for transparency
        }
    }

    // Helper method to create icon labels
    private JLabel createIconLabel(String imagePath, int width, int height, int x, int y) {
        ImageIcon iconOriginal = new ImageIcon(imagePath); // Load the original image
        Image iconImage = iconOriginal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // Scale the image
        ImageIcon icon = new ImageIcon(iconImage); // Create an ImageIcon from the scaled image
        JLabel iconLabel = new JLabel(icon); // Create a JLabel with the icon
        iconLabel.setBounds(x, y, width, height); // Set bounds for the icon label
        return iconLabel; // Return the created JLabel
    }

    // Utility method to convert hex color string to Color object
    private static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16), // Extract and convert red component
                Integer.valueOf(hex.substring(3, 5), 16), // Extract and convert green component
                Integer.valueOf(hex.substring(5, 7), 16)  // Extract and convert blue component
        );
    }

    // Main method to launch the GameOver5 frame
    public static void main(String[] args) {
        // Example usage: pass a username to GameOver5
        new GameOver5(""); // Create an instance with a placeholder username (replace with actual username if needed)
    }
}