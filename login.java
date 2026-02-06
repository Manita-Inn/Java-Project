import javax.swing.*;                       // Import Swing components for GUI
import java.awt.*;                          // Import AWT for layout and graphics
import java.awt.event.ActionEvent;          // Import for handling action events
import java.awt.event.ActionListener;       // Import for action listener interface
import java.io.IOException;                 // Import for handling IO exceptions
import java.io.FileWriter;                  // Import for file writing
import java.sql.Connection;                 // Import for SQL connection
import java.sql.DriverManager;              // Import for managing database connection
import java.sql.PreparedStatement;          // Import for prepared statements
import java.sql.ResultSet;                  // Import for handling results from queries

public class login extends JFrame {
    private String username; // Field to store the username

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b); // Calls the superclass method to set visibility
    }

    // Constructor to set up the login window
    public login() {
        setTitle("Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(hexToColor("#324071"));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.add(Box.createRigidArea(new Dimension(50, 50)));

        // Add paw image to the left
        JLabel pawLabel = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\Paws-removebg-preview.png", 300, 300, 500, 210);
        add(pawLabel); // Add label to the frame

        // Add second paw image
        JLabel pawLabel2 = createIconLabel("C:\\Users\\USER\\IdeaProjects\\Quizz\\src\\images\\Paws-removebg-preview.png", 100, 100, 480, 190); // Adjust x-position
        add(pawLabel2);  // Add the second label

        // Welcome Message
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 74));    // set font style and size
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(50, 50, 700, 100); // Adjusted x-position
        panel.add(welcomeLabel);

        // Instruction Label
        JLabel continueLabel = new JLabel("Please login to continue");
        continueLabel.setFont(new Font("Lato", Font.PLAIN, 19));
        continueLabel.setForeground(Color.WHITE);
        continueLabel.setBounds(60, 130, 700, 50);
        panel.add(continueLabel);

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        usernameLabel.setBounds(50, 200, 200, 30);
        panel.add(usernameLabel);

        // Username input field
        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 235, 400, 40);
        usernameField.setBackground(hexToColor("#8292f1"));
        usernameField.setForeground(Color.WHITE);
        usernameField.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        panel.add(usernameField);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
        passwordLabel.setBounds(50, 300, 200, 30);
        panel.add(passwordLabel);

        // Password input field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 334, 400, 40);
        passwordField.setBackground(hexToColor("#8292f1"));
        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));
        panel.add(passwordField);

        // Login button
        RoundedButton loginButton = new RoundedButton("Login");
        loginButton.setBounds(50, 400, 400, 50);
        panel.add(loginButton);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = usernameField.getText();                        // Get username input
                String password = new String(passwordField.getPassword());             // Get password input
                String[] loggedInUserInfo = authenticate(usernameInput, password);     // Authenticate user
                if (loggedInUserInfo != null) {
                    login.this.username = loggedInUserInfo[0];                         // Store the username
                    System.out.println(login.this.username);                           // Print the username to console
                    fileWrite();       // Write username to file
                    JOptionPane.showMessageDialog(null, "Login Successful!");  // SHow a message
                    new HomePage(login.this.username);  // Navigate to home page with the next chapter information
                    dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);  // Show error message
                }
            }
        });

        // Signup prompt label
        JLabel signupLabel = new JLabel("Not a member yet? Click this signup button");
        signupLabel.setForeground(Color.WHITE);
        signupLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 13));
        signupLabel.setBounds(50, 460, 400, 30);
        panel.add(signupLabel);

        // Signup button
        RoundedButton signUpButton = new RoundedButton("Sign Up");
        signUpButton.setBounds(50, 490, 400, 50);
        panel.add(signUpButton);

        // Action listener for signup button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new signup(); // Open the signup page
                dispose(); // Close the login window
            }
        });

        add(panel);       // Add the panel to the frame
        setVisible(true); // Make the login window visible
    }

    // Main method to launch the login window
    public static void main(String[] args) {
        // Launch the login window
        SwingUtilities.invokeLater(() -> new login());    // Use SwingUtilities to ensure thread safety
    }

    // Helper method to create an icon label
    private JLabel createIconLabel(String imagePath, int width, int height, int x, int y) {
        ImageIcon iconOriginal = new ImageIcon(imagePath);
        Image iconImage = iconOriginal.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(iconImage);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(x, y, width, height);
        return iconLabel;
    }

    // Authenticate method to check username and password
    public String[] authenticate(String username, String password) {
        String[] userInfo = new String[2]; // Array to hold username and next chapter to start
        String storedPassword = null; // To hold the password from the database

        String url = "jdbc:mysql://localhost:3306/java";
        String dbUser = "root";
        String dbPassword = "#mnta$mn$ta07.inn05/22";

        try {
            // Establishing a connection to the database
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String sql = "SELECT username, password FROM users WHERE username = ?";        // SQL query to retrieve user
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username); // Use the username parameter
            ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query

            if (resultSet.next()) {   // If user found
                storedPassword = resultSet.getString("password");  // Get stored password

                // Compare the entered password with the stored password
                if (password.equals(storedPassword)) {
                    // If the passwords match, return user info (username, next chapter)
                    userInfo[0] = resultSet.getString("username");
                }
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for any exceptions
        }

        // Return user info if login is successful, otherwise return null
        return userInfo[0] != null ? userInfo : null;
    }

    // Method to get the username after successful login
    public String getUsername() {
        return username; // Return the stored username
    }

    // Color conversion method from hex to color object
    private static Color hexToColor(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16),   // Red
                Integer.valueOf(hex.substring(3, 5), 16),   // Green
                Integer.valueOf(hex.substring(5, 7), 16)    // Blue
        );
    }

    // RoundedButton class for styled button
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setBackground(hexToColor("#fbb748"));      // Set background color
            setForeground(Color.WHITE);                // Set text color
            setBorderPainted(false);                   // Disable border painting
            setFocusPainted(false);                    // Disable focus painting
            setContentAreaFilled(false);               // Keep content area transparent
            setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16));     // Set font
            setPreferredSize(new Dimension(400, 50));                     // Set preferred size
            setMargin(new Insets(0, 0, 0, 0));                   // Set margins
            setOpaque(false);                                                         // Make button transparent
            setBorder(BorderFactory.createEmptyBorder());                             // Remove border
        }

        // Custom paint method for rounded button
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());    // Set color to background
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);      // Draw rounded rectangle
            super.paintComponent(g);     // Call superclass method to paint button
        }
    }

    // Method to write the username to a file
    public void fileWrite()
    {
        try {
          FileWriter writer = new FileWriter("users.txt", false);    // Create a FileWriter
          writer.write(username);                                                    // Write the username to the fie
          writer.close();                                                            // Close the writer
        } catch (IOException e) { e.printStackTrace(); }                             // Print stack trace for IO exceptions
    }

}