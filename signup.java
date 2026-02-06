import javax.swing.*;                 // Import Swing components for GUI
import java.awt.*;                    // Import AWT for layout and graphics
import java.awt.event.ActionEvent;    // Import for handling action events
import java.awt.event.ActionListener; // Import for action listener interface
import java.io.*;                     // Import for IO operations
import java.sql.*;                    // Import for SQL database operations

public class signup extends JFrame {
    private String username; // Field to store the username

    // Constructor for the signup window
    public signup() {
        setTitle("Sign Up"); // Set the title of the window
        setSize(800, 700); // Set the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit
        getContentPane().setBackground(hexToColor("#324071")); // Set background color

        // Add paw images to the window
        JLabel pawLabel = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\Paws-removebg-preview.png", 300, 300, 500, 210);
        add(pawLabel); // Add the first paw image

        JLabel pawLabel2 = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\Paws-removebg-preview.png", 100, 100, 480, 190);
        add(pawLabel2); // Add the second paw image

        // Create a main panel for layout
        JPanel panel = new JPanel();
        panel.setLayout(null); // Use absolute positioning
        panel.setOpaque(false); // Make the panel transparent
        add(panel); // Add the panel to the frame

        // Sign up label
        JLabel signupLabel = new JLabel("Sign Up");
        signupLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 74)); // Set font style and size
        signupLabel.setForeground(Color.WHITE); // Set text color
        signupLabel.setBounds(50, 50, 700, 100); // Set position and size
        panel.add(signupLabel); // Add to panel

        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Set text color
        usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18)); // Set font
        usernameLabel.setBounds(50, 150, 200, 30); // Set position and size
        panel.add(usernameLabel); // Add to panel

        JTextField usernameField = new JTextField(); // Create a text field for username input
        usernameField.setBounds(50, 185, 400, 40); // Set position and size
        usernameField.setBackground(hexToColor("#8292f1")); // Set background color
        usernameField.setForeground(Color.WHITE); // Set text color
        usernameField.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        panel.add(usernameField); // Add to panel

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Set text color
        passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18)); // Set font
        passwordLabel.setBounds(50, 240, 200, 30); // Set position and size
        panel.add(passwordLabel); // Add to panel

        JPasswordField passwordField = new JPasswordField(); // Create a password field for password input
        passwordField.setBounds(50, 275, 400, 40); // Set position and size
        passwordField.setBackground(hexToColor("#8292f1")); // Set background color
        passwordField.setForeground(Color.WHITE); // Set text color
        passwordField.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        panel.add(passwordField); // Add to panel

        // Confirm password label and field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE); // Set text color
        confirmPasswordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18)); // Set font
        confirmPasswordLabel.setBounds(50, 340, 200, 30); // Set position and size
        panel.add(confirmPasswordLabel); // Add to panel

        JPasswordField confirmPasswordField = new JPasswordField(); // Create a password field for confirming password
        confirmPasswordField.setBounds(50, 375, 400, 40); // Set position and size
        confirmPasswordField.setBackground(hexToColor("#8292f1")); // Set background color
        confirmPasswordField.setForeground(Color.WHITE); // Set text color
        confirmPasswordField.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        panel.add(confirmPasswordField); // Add to panel

        // Signup button
        RoundedButton signupButton = new RoundedButton("Sign Up"); // Create a custom rounded button
        signupButton.setBounds(50, 450, 400, 50); // Set position and size
        panel.add(signupButton); // Add to panel

        // Action listener for the signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get input values
                String username = usernameField.getText(); // Get username input
                String password = new String(passwordField.getPassword()); // Get password input
                String confirmPassword = new String(confirmPasswordField.getPassword()); // Get confirm password input

                // Validate input fields
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE); // Show error if fields are empty
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE); // Show error if passwords do not match
                } else {
                    try {
                        // Attempt to register the user
                        boolean isRegistered = registerUser(username, password); // Call the registration method
                        if (isRegistered) {
                            JOptionPane.showMessageDialog(null, "Successfully registered!", "Success", JOptionPane.INFORMATION_MESSAGE); // Show success message
                            new login(); // Open the login window
                            dispose(); // Close the signup window
                        } else {
                            JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE); // Show error if username exists
                        }
                    } catch (SQLException | IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Handle exceptions
                    }
                }
            }
        });

        add(panel); // Add the main panel to the frame
        setVisible(true); // Make the signup window visible
    }

    // Helper method to create an icon label
    private JLabel createIconLabel(String imagePath, int width, int height, int x, int y) {
        ImageIcon originalIcon = new ImageIcon(imagePath); // Load the image
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // Scale the image
        JLabel label = new JLabel(new ImageIcon(scaledImage)); // Create a label with the scaled image
        label.setBounds(x, y, width, height); // Set position and size
        label.setOpaque(false); // Ensure the label is transparent
        return label; // Return the label
    }

    // Combined method to check if username exists, register user, and write to text file
    private boolean registerUser(String username, String password) throws SQLException, IOException {
        String[] userInfo = new String[2]; // Array to hold user information (not used here)

        String DB_URL = "jdbc:mysql://localhost:3306/java"; // Database URL
        String USER = "root"; // Database username
        String PASS = "#mnta$mn$ta07.inn05/22"; // Database password

        // Check if username already exists
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT username FROM users WHERE username = ?"; // SQL query to check for existing username
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username); // Set the username parameter
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query

            if (resultSet.next()) {
                // Username exists
                return false; // Return false to indicate registration failure
            }

            // If username doesn't exist, insert the new user into the database
            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)"; // SQL query to insert new user
            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setString(1, username); // Set username
            insertStatement.setString(2, password); // Set password
            insertStatement.executeUpdate(); // Execute the insert

            // Save user data to a text file
            writeToFile(username, password); // Write user info to a file

            return true; // Registration successful
        }
    }

    // Write user data to a text file
    private void writeToFile(String username, String password) throws IOException {
        // Use BufferedWriter to append user data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write("Username: " + username + "\n"); // Write username
            writer.write("Password: " + password + "\n"); // Write password
            writer.write("------------\n"); // Separator for clarity
            writer.newLine(); // New line for the next entry
            System.out.println("User information has been successfully saved to TEXT FILE"); // Confirmation message in console
        }
    }

    // Convert hex color code to Color object
    static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16), // Red component
                Integer.valueOf(hex.substring(3, 5), 16), // Green component
                Integer.valueOf(hex.substring(5, 7), 16)  // Blue component
        );
    }

    // Main method to launch the signup window
    public static void main(String[] args) {
        new signup(); // Create an instance of the signup class
    }

    // Custom button class for rounded button style
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text); // Call the superclass constructor
            setBackground(hexToColor("#fbb748")); // Set background color
            setForeground(Color.WHITE); // Set text color
            setBorderPainted(false); // Disable border painting
            setFocusPainted(false); // Disable focus painting
            setContentAreaFilled(false); // Keep content area transparent
            setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
            setPreferredSize(new Dimension(400, 50)); // Set preferred size
            setMargin(new Insets(0, 0, 0, 0)); // Set margins
            setOpaque(false); // Make button transparent
            setBorder(BorderFactory.createEmptyBorder()); // Remove border
        }

        // Custom paint method for rounded button
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground()); // Set color to background
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Draw rounded rectangle
            super.paintComponent(g); // Call superclass method to paint button
        }
    }
}