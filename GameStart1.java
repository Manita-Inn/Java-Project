import javax.swing.*;                   // Import for Swing components for GUI
import java.awt.*;                      // Import for AWT classes like Color and Graphics
import java.awt.event.ActionEvent;      // Import for ActionEvent class
import java.awt.event.ActionListener;   // Import for ActionListener interface
import java.io.BufferedReader;           // Import for reading text from a character-input stream
import java.io.FileReader;              // Import for reading files
import java.io.IOException;              // Import for handling IO exceptions

public class GameStart1 extends JFrame {

    private String username; // Variable to hold the username
    private HomePage homePage; // Variable to hold a reference to the HomePage

    // Constructor for the GameStart1 class
    public GameStart1() {
        if (username == null || username.isEmpty()) {
            fileRead(); // Read from the file if username is not provided
        } else {
            this.username = username; // Use the passed username if valid
        }
        this.homePage = homePage;

        setTitle("GAME START - CHAPTER 1"); // Set the window title
        setSize(800, 600); // Set the window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the window on exit
        setLayout(null); // Use null layout for absolute positioning
        getContentPane().setBackground(hexToColor("#324071")); // Set the background color

        // Create the game interface
        RoundedPanel chapterPanel = new RoundedPanel(30); // Create a rounded panel for chapter display
        chapterPanel.setLayout(null);
        chapterPanel.setBounds(100, 100, 500, 300);
        chapterPanel.setBackground(hexToColor("#8292f1")); // Set background color for the panel

        JPanel frame = new JPanel(); // Create a frame panel
        frame.setLayout(null);
        frame.setBounds(105, 105, 490, 290);
        frame.setBackground(hexToColor("#324071")); // Set background color for the frame

        JPanel chapterNamePanelFrame = new JPanel(); // Create a panel for chapter name
        chapterNamePanelFrame.setLayout(null);
        chapterNamePanelFrame.setBounds(125, 220, 450, 150);
        chapterNamePanelFrame.setBackground(hexToColor("#8292f1")); // Set background color

        // Add label for selected chapter text
        JLabel chapterLabel = new JLabel("<html><center>Selected Chapter:</center></html>");
        chapterLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30)); // Set font style and size
        chapterLabel.setForeground(Color.WHITE); // Set text color
        chapterLabel.setBounds(-2, 70, chapterPanel.getWidth(), 40); // Set position and size
        chapterLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        frame.add(chapterLabel); // Add the chapter label to the frame

        // Add chapter name label
        JLabel chapterNameLabel = new JLabel("<html><center>CHAPTER 1: INTRODUCTION TO COMPUTERS,<br>PROGRAMS AND JAVA</center></html>");
        chapterNameLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18)); // Set font style and size
        chapterNameLabel.setForeground(hexToColor("#ffffff")); // Set text color
        chapterNameLabel.setBounds(-15, 60, chapterPanel.getWidth(), 40); // Set position and size
        chapterNamePanelFrame.add(chapterNameLabel); // Add chapter name label to the chapter name panel

        // Create and style the home button
        RoundedButton homeButton = new RoundedButton("Home"); // Create a rounded button for Home
        homeButton.setBounds(150, 450, 200, 50); // Set position and size
        homeButton.setBackground(hexToColor("#fbb748")); // Set button background color

        // Home button action listener
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePage(username); // Open the HomePage window
                dispose(); // Close the current window
            }
        });

        // Create and style the start button
        RoundedButton startButton = new RoundedButton("Start"); // Create a rounded button for Start
        startButton.setBounds(360, 450, 200, 50); // Set position and size
        startButton.setBackground(hexToColor("#fbb748")); // Set button background color

        // Start button action listener
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileRead(); // Ensure data is updated from the file
                System.out.println("READ " + username); // Log the read username
                new chapter1(username, homePage); // Open the chapter1 quiz window
                dispose(); // Close the current window
            }
        });

        // Load house icon
        ImageIcon houseIcon = new ImageIcon("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\house_icon.png"); // Load house icon image
        Image resizedHouseImage = houseIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Resize the image
        JLabel houseIconLabel = new JLabel(new ImageIcon(resizedHouseImage)); // Create label with the icon
        houseIconLabel.setBounds(323, 110, 70, 70); // Set position and size
        add(houseIconLabel); // Add the house icon label to the frame

        // Load paws image
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\Downloads\\paws.png"); // Load paws image
        Image resizedImage = imageIcon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH); // Resize the image
        JLabel imageLabel = new JLabel(new ImageIcon(resizedImage)); // Create label with the image
        imageLabel.setBounds(150, 0, 200, 100); // Set position and size
        frame.add(imageLabel); // Add the paws image label to the frame

        // Add components to the frame
        add(homeButton); // Add Home button
        add(startButton); // Add Start button
        add(chapterNamePanelFrame); // Add chapter name panel
        add(frame); // Add frame panel
        add(chapterPanel); // Add chapter panel
        setVisible(true); // Make the frame visible
    }

    // Getter method for username
    public String getUsername() {
        return username;
    }

    // Utility method to convert hex color string to Color object
    private static Color hexToColor(String hex) {
        return new Color(
                Integer.parseInt(hex.substring(1, 3), 16), // Extract red component
                Integer.parseInt(hex.substring(3, 5), 16), // Extract green component
                Integer.parseInt(hex.substring(5, 7), 16)  // Extract blue component
        );
    }

    // Custom rounded panel class
    public static class RoundedPanel extends JPanel {
        private int cornerRadius; // Variable to hold the corner radius

        // Constructor for the RoundedPanel class
        public RoundedPanel(int radius) {
            this.cornerRadius = radius; // Set the corner radius
            setOpaque(false);  // Make the panel transparent to show the rounded effect
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Call superclass method
            Graphics2D g2 = (Graphics2D) g; // Cast to Graphics2D for better rendering
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
            g2.setColor(getBackground()); // Set color to the panel's background
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // Draw a rounded rectangle
        }
    }

    // Custom rounded button class
    public static class RoundedButton extends JButton {
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

    // Read username from the file (if not passed)
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
        username = content.toString().trim(); // Update username
        System.out.println("Username " + username); // Log the read username
    }
}