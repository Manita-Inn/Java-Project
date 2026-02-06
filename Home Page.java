import javax.swing.*;                    // Import Swing components for GUI
import java.awt.*;                       // Import AWT for layout and graphics
import java.awt.event.ActionEvent;       // Import for handling action events
import java.awt.event.ActionListener;    // Import for action listener interface
import java.sql.Connection;              // Import for SQL connection
import java.sql.DriverManager;           // Import for managing SQL driver
import java.sql.PreparedStatement;       // Import for prepared SQL statements
import java.sql.ResultSet;               // Import for handling SQL result sets

public class HomePage extends JFrame {
    private String username; // Field to store the logged-in username

    // Flags to track completion status of each chapter
    private boolean chapter1Completed = false;
    private boolean chapter2Completed = false;
    private boolean chapter3Completed = false;
    private boolean chapter4Completed = false;

    // Buttons for each chapter
    private signup.RoundedButton chapter1Button;
    private signup.RoundedButton chapter2Button;
    private signup.RoundedButton chapter3Button;
    private signup.RoundedButton chapter4Button;
    private signup.RoundedButton chapter5Button;

    // Constructor for HomePage
    public HomePage(String username) {
        this.username = username; // Initialize username
        setTitle("Home Page"); // Set the title of the window
        setSize(800, 600); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit
        getContentPane().setBackground(hexToColor("#324071")); // Set background color
        setLayout(null); // Use absolute positioning for custom placements

        // Create a panel for chapter title and button
        JPanel panel = new JPanel();
        panel.setLayout(null); // Use null layout for absolute positioning
        panel.setOpaque(false); // Make the panel transparent
        panel.setBounds(50, 200, 700, 300); // Set bounds for the panel

        // Home label
        JLabel homeLabel = new JLabel("Home Page");
        homeLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 74)); // Set font style and size
        homeLabel.setForeground(Color.WHITE); // Set text color
        homeLabel.setBounds(140, 40, 700, 100); // Position for the Home Page label

        // Create icons
        JLabel gardenLabel = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\garden_icon.png", 850, 120, -25, 473); // Garden icon
        JLabel houseLabel = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\house_icon.png", 60, 60, 70, 65); // House icon

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/java"; // Database URL
        String dbUser = "root"; // Database username
        String dbPassword = "#mnta$mn$ta07.inn05/22"; // Database password

        try {
            // Establishing a connection to the database
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String sql = "select * from users where username = ?"; // SQL query to get user data
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username); // Set the username parameter
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query

            // Check if user data exists
            if (resultSet.next()) {
                // Retrieve chapter scores
                int x1 = resultSet.getInt("chapter1_score");
                int x2 = resultSet.getInt("chapter2_score");
                int x3 = resultSet.getInt("chapter3_score");
                int x4 = resultSet.getInt("chapter4_score");
                int x5 = resultSet.getInt("chapter5_score");
                System.out.println(x1 + " " + x2 + " " + x3 + " " + x4 + " " + x5); // Print scores for debugging

                // Set chapter completion status based on scores
                if (x1 >= 8) chapter1Completed = true;
                if (x2 >= 8) chapter2Completed = true;
                if (x3 >= 8) chapter3Completed = true;
                if (x4 >= 8) chapter4Completed = true;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print any exceptions for debugging
        }

        // Create buttons for each chapter
        chapter1Button = new signup.RoundedButton("Chapter 1: Introduction to computers, programs, and Java");
        chapter1Button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font size
        chapter1Button.setBounds(10, 2, 600, 40); // Set position and size of the button
        chapter1Button.setBackground(hexToColor("#8292f1")); // Set the button color
        panel.add(chapter1Button); // Add button to panel

        // Add action listener for Chapter 1 button
        chapter1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameStart1(); // Open the GameStart1 window
                dispose(); // Close the HomePage window
            }
        });

        // Create and configure Chapter 2 button
        chapter2Button = new signup.RoundedButton("Chapter 2: Elementary programming");
        chapter2Button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        chapter2Button.setBounds(10, 55, 600, 40); // Set position and size
        chapter2Button.setBackground(hexToColor("#8292f1")); // Set button color
        panel.add(chapter2Button); // Add to panel
        chapter2Button.setEnabled(chapter1Completed); // Enable only if Chapter 1 is completed

        // Add action listener for Chapter 2 button
        chapter2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameStart2(); // Open the GameStart2 window
                dispose(); // Close the HomePage window
            }
        });

        // Create and configure Chapter 3 button
        chapter3Button = new signup.RoundedButton("Chapter 3: Selections");
        chapter3Button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        chapter3Button.setBounds(10, 107, 600, 40); // Set position and size
        chapter3Button.setBackground(hexToColor("#8292f1")); // Set button color
        panel.add(chapter3Button); // Add to panel
        chapter3Button.setEnabled(chapter2Completed); // Enable only if Chapter 2 is completed

        // Add action listener for Chapter 3 button
        chapter3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameStart3(); // Open the GameStart3 window
                dispose(); // Close the HomePage window
            }
        });

        // Create and configure Chapter 4 button
        chapter4Button = new signup.RoundedButton("Chapter 4: Loops");
        chapter4Button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        chapter4Button.setBounds(10, 160, 600, 40); // Set position and size
        chapter4Button.setBackground(hexToColor("#8292f1")); // Set button color
        panel.add(chapter4Button); // Add to panel
        chapter4Button.setEnabled(chapter3Completed); // Enable only if Chapter 3 is completed

        // Add action listener for Chapter 4 button
        chapter4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameStart4(); // Open the GameStart4 window
                dispose(); // Close the HomePage window
            }
        });

        // Create and configure Chapter 5 button
        chapter5Button = new signup.RoundedButton("Chapter 5: Methods");
        chapter5Button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        chapter5Button.setBounds(10, 215, 600, 40); // Set position and size
        chapter5Button.setBackground(hexToColor("#8292f1")); // Set button color
        panel.add(chapter5Button); // Add to panel
        chapter5Button.setEnabled(chapter4Completed); // Enable only if Chapter 4 is completed

        // Add action listener for Chapter 5 button
        chapter5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GameStart5(); // Open the GameStart5 window
                dispose(); // Close the HomePage window
            }
        });

        // Paw icon on the right side
        JLabel pawLabel = createIconLabel("D:\\GoGo Beauty\\Fall 2024\\Java programming\\image\\Paws-removebg-preview.png", 150, 80, 600, 40);

        // Subtitle label
        JLabel subtitleLabel = new JLabel("JAVA PROGRAMMING QUIZ CHAPTER 1 TO CHAPTER 5");
        subtitleLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20)); // Set font
        subtitleLabel.setForeground(Color.WHITE); // Set text color
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER); // Center align the text
        subtitleLabel.setBounds(60, 150, 600, 40); // Position for subtitle label

        // Add components to the main frame
        add(houseLabel); // Add house icon
        add(homeLabel); // Add home label
        add(pawLabel); // Add paw icon
        add(subtitleLabel); // Add subtitle label
        add(gardenLabel); // Add garden icon
        add(panel); // Add the panel containing chapter buttons

        setVisible(true); // Make the frame visible
    }

    // Default constructor (not used)
    public HomePage() {

    }

    // Helper method to create icon labels
    private JLabel createIconLabel(String imagePath, int width, int height, int x, int y) {
        ImageIcon iconOriginal = new ImageIcon(imagePath); // Load the original image
        Image iconImage = iconOriginal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // Scale the image
        ImageIcon icon = new ImageIcon(iconImage); // Create a new ImageIcon
        JLabel iconLabel = new JLabel(icon); // Create a label with the icon
        iconLabel.setBounds(x, y, width, height); // Set the position and size
        return iconLabel; // Return the label
    }

    // Utility method to convert hex color string to Color object
    private static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16), // Red component
                Integer.valueOf(hex.substring(3, 5), 16), // Green component
                Integer.valueOf(hex.substring(5, 7), 16)  // Blue component
        );
    }

    // Main method to launch the application
    public static void main(String[] args) {
        new HomePage("testUser"); // Create an instance of HomePage with a test username
    }
}