import javax.swing.*;                   // Import for Swing components to create GUI
import java.awt.*;                      // Import for AWT classes like Color and Graphics
import java.awt.event.ActionEvent;      // Import for ActionEvent class
import java.awt.event.ActionListener;   // Import for ActionListener interface
import java.io.BufferedReader;           // Import for reading text from a character-input stream
import java.io.FileReader;              // Import for reading files
import java.io.IOException;              // Import for handling IO exceptions

public class GameStart5 extends JFrame {

    private String username; // Variable to hold the username

    // Constructor for the GameStart5 class
    public GameStart5() {
        this.username = username; // Initialize username (should be set before)
        setTitle("GAME START - CHAPTER 5"); // Set the title of the window
        setSize(800, 600); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application when the window is closed
        setLayout(null); // Use null layout for absolute positioning
        getContentPane().setBackground(hexToColor("#324071")); // Set background color

        fileRead(); // Read the username from the file

        // Create a rounded panel for the chapter selection
        RoundedPanel chapterPanel = new RoundedPanel(30); // Set corner radius for the panel
        chapterPanel.setLayout(null); // Use null layout for the chapter panel
        chapterPanel.setBounds(100, 100, 500, 300); // Set position and size of the chapter panel
        chapterPanel.setBackground(hexToColor("#8292f1")); // Set background color for the chapter panel

        JPanel frame = new JPanel(); // Create a frame panel
        frame.setLayout(null); // Use null layout for the frame
        frame.setBounds(105, 105, 490, 290); // Set position and size of the frame
        frame.setBackground(hexToColor("#324071")); // Set background color for the frame

        JPanel chapterNamePanelFrame = new JPanel(); // Create a panel for chapter name
        chapterNamePanelFrame.setLayout(null); // Use null layout for the chapter name panel
        chapterNamePanelFrame.setBounds(125, 220, 450, 150); // Set position and size
        chapterNamePanelFrame.setBackground(hexToColor("#8292f1")); // Set background color

        // Add label for selected chapter text
        JLabel chapterLabel = new JLabel("<html><center>Selected Chapter:</center></html>");
        chapterLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 40)); // Set font style and size
        chapterLabel.setForeground(Color.WHITE); // Set text color
        chapterLabel.setBounds(0, 60, chapterPanel.getWidth(), 50); // Set position and size
        chapterLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally
        frame.add(chapterLabel); // Add the chapter label to the frame

        // Center alignment for chapterNameLabel
        JLabel chapterNameLabel = new JLabel("<html><center>Chapter 5: Methods</center></html>");
        chapterNameLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25)); // Set font style and size
        chapterNameLabel.setForeground(hexToColor("#ffffff")); // Set text color
        chapterNameLabel.setBounds(-15, 60, chapterPanel.getWidth(), 40); // Set position and size
        chapterNameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally
        chapterNamePanelFrame.add(chapterNameLabel); // Add chapter name label to the chapter name panel

        // Create and style the home button
        RoundedButton homeButton = new RoundedButton("Home"); // Create a rounded button for Home
        homeButton.setBounds(150, 450, 200, 50); // Set position and size for the Home button
        homeButton.setBackground(hexToColor("#fbb748")); // Set button background color

        // Add action listener to the home button
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage(username); // Open the HomePage with the username
                dispose(); // Close the current window
            }
        });

        // Create and style the start button
        RoundedButton startButton = new RoundedButton("Start"); // Create a rounded button for Start
        startButton.setBounds(360, 450, 180, 50); // Set position and size for the Start button
        startButton.setBackground(hexToColor("#fbb748")); // Set button background color

        // Add action listener to the start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new chapter5(username); // Open the chapter5 quiz window with the username
                dispose(); // Close the current window
            }
        });

        // Load and add the house icon
        ImageIcon houseIcon = new ImageIcon("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\house_icon.png"); // Load the house icon
        Image resizedHouseImage = houseIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Resize image to 40x40 pixels
        JLabel houseIconLabel = new JLabel(new ImageIcon(resizedHouseImage)); // Create a JLabel to hold the resized image
        houseIconLabel.setBounds(338, 125, 40, 40); // Set bounds for the house icon label
        add(houseIconLabel); // Add the house icon label to the frame

        // Load and add another image
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\Downloads\\paws.png"); // Load the image
        Image resizedImage = imageIcon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH); // Resize image to 150x100 pixels
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage)); // Create a JLabel to hold the resized image
        imageLabel.setBounds(150, 0, 200, 100); // Set bounds for the image label
        frame.add(imageLabel); // Add the image label to the frame

        // Add components to the frame
        add(homeButton); // Add the home button
        add(startButton); // Add the start button
        add(chapterNamePanelFrame); // Add chapter name panel
        add(frame); // Add frame panel
        add(chapterPanel); // Add chapter panel
        setVisible(true); // Make the frame visible
    }

    // Utility method to convert hex color string to Color object
    private static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16), // Extract red component
                Integer.valueOf(hex.substring(3, 5), 16), // Extract green component
                Integer.valueOf(hex.substring(5, 7), 16)  // Extract blue component
        );
    }

    // Main method to run the application
    public static void main(String[] args) {
        String username = loadUsernameFromFile("users.txt"); // Load username from file
        new GameStart5(); // Create an instance of the GameStart5 class
    }

    // Method to load username from a specified file
    private static String loadUsernameFromFile(String filename) {
        StringBuilder content = new StringBuilder(); // StringBuilder to hold file content
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // Use try-with-resources for safe file reading
            String line;
            if ((line = reader.readLine()) != null) { // Read the first line for the username
                return line.trim(); // Return the username after trimming whitespace
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file."); // Log error
            e.printStackTrace(); // Print stack trace for debugging
        }
        return null; // Return null if no username is found
    }

    // Custom rounded panel class
    public static class RoundedPanel extends JPanel {
        private int cornerRadius; // Variable to hold the corner radius

        // Constructor for the RoundedPanel class
        public RoundedPanel(int radius) {
            this.cornerRadius = radius; // Set the corner radius
            setOpaque(false); // Make the panel transparent to show the rounded effect
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Call superclass method
            Graphics2D g2 = (Graphics2D) g; // Cast to Graphics2D for better rendering
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing

            // Draw a rounded rectangle
            g2.setColor(getBackground()); // Set the drawing color to the panel's background color
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // Draw the rounded rectangle
        }
    }

    // Custom rounded button class
    public static class RoundedButton extends JButton {
        // Constructor for the RoundedButton class
        public RoundedButton(String text) {
            super(text); // Call JButton constructor
            setForeground(Color.WHITE); // Set text color
            setBorderPainted(false); // Disable border painting
            setFocusPainted(false); // Disable focus painting
            setContentAreaFilled(false); // Make button background transparent
            setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font style and size
            setPreferredSize(new Dimension(200, 50)); // Set preferred size
            setMargin(new Insets(0, 0, 0, 0)); // Remove margins

            // Rounded button effect
            setOpaque(false); // Make the button transparent
            setBorder(BorderFactory.createEmptyBorder()); // Remove any default border
        }

        // Method to create rounded borders
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground()); // Set the button background color
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Fill with rounded corners
            super.paintComponent(g); // Call superclass paint method
        }
    }

    // Method to read the username from the file
    public void fileRead() {
        StringBuilder content = new StringBuilder(); // StringBuilder to hold file content
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) { // Use try-with-resources for safe file reading
            String line;
            while ((line = reader.readLine()) != null) { // Read lines until EOF
                content.append(line).append(System.lineSeparator()); // Append each line to content
            }
            System.out.println("Data read from file successfully."); // Log success
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file."); // Log error
            e.printStackTrace(); // Print stack trace for debugging
        }
        username = content.toString().trim(); // Update username variable
        System.out.println("Username: " + username); // Log the read username
    }
}