import javax.swing.*;              // Import Swing components for GUI
import java.awt.*;                 // Import AWT for layout and graphics
import java.sql.Connection;        // Import for SQL connection
import java.sql.DriverManager;     // Import for managing SQL driver
import java.sql.PreparedStatement; // Import for prepared SQL statements

public class chapter5 extends JFrame {
    private String username; // Variable to store the username
    private HomePage homePage; // Reference to the home page object

    private JPanel panel; // Main panel for the GUI
    private JTextArea questionLabel; // Text area for displaying questions
    private JRadioButton[] options; // Array of radio buttons for answer options
    private ButtonGroup group; // Group to manage radio button selection
    private JButton nextButton; // Button to go to the next question
    private JButton previousButton; // Button to go back to the previous question

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b); // Call the superclass method to set visibility
    }

    // Array of questions for the quiz
    private String[] questions = {
            "In Java, what is a method?",
            "What is a method signature?",
            "What is method overloading?",
            "Which keyword is used to end a void method prematurely?",
            "What is the purpose of a method header?",
            "If a method has the void return type, what does it return?",
            "What does it mean to pass a parameter by value?",
            "Which of the following keywords is used to create a method in Java?",
            "What is method abstraction?",
            "Which method is automatically called when a class object is created?"
    };

    // 2D array of answer options for each question
    private String[][] optionsForQuestions = {
            {"A. A class variable", "B. A named block of code that performs a task", "C. A function that returns an integer", "D. A package"},
            {"A. A unique identifier for a method", "B. The method's name and parameters", "C. The return type of a method", "D. The code inside the method"},
            {"A. Creating multiple methods with the same name but different parameters", "B. Creating methods with the same return type", "C. Creating methods with the same parameters", "D. Creating methods with identical functionality"},
            {"A. Break", "B. Continue", "C. return", "D. Exit"},
            {"A. It contains the logic of the method", "B. It declares the method's name, parameters, and return type", "C. It initializes the method's body", "D. It defines the variables in the method"},
            {"A. 0", "B. A null value", "C. Nothing", "D. An empty string"},
            {"A. The parameter’s memory address is passed", "B. A copy of the parameter’s value is passed", "C. The parameter’s type is passed", "D. The return type is passed"},
            {"A. function", "B. method", "C. define", "D. void"},
            {"A. Separating the method’s name from its body", "B. Hiding the implementation details from the caller", "C. Using multiple methods in a program", "D. Defining methods without any parameters"},
            {"A. Main method", "B. toString method", "C. Constructor", "D. set method"}
    };

    private String[] correctAnswers = {"B", "B", "A", "C", "B", "C", "B", "D", "B", "C"}; // Array of correct answers
    private int currentQuestionIndex = 0; // Index of the current question
    private int score = 0; // User's score
    private String[] selectedAnswers = new String[10]; // Array to store user's selected answers

    // Constructor for the chapter5 class
    public chapter5(String username) {
        this.username = username; // Initialize username
        this.homePage = homePage; // Initialize homePage (but it's not properly assigned)
        setTitle("Java Quiz - Chapter 5"); // Set the window title
        setSize(800, 600); // Set the window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close application on exit
        getContentPane().setBackground(new Color(50, 64, 113)); // Set background color

        panel = new JPanel(); // Create a new panel
        panel.setLayout(null); // Use absolute positioning for custom placements
        panel.setOpaque(false); // Make the panel transparent

        // Set up the question label
        questionLabel = new JTextArea();
        questionLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24)); // Set font style and size
        questionLabel.setForeground(Color.WHITE); // Set text color
        questionLabel.setOpaque(false); // Make it transparent
        questionLabel.setEditable(false); // Make it non-editable
        questionLabel.setLineWrap(true); // Enable line wrapping
        questionLabel.setWrapStyleWord(true); // Wrap at word boundaries
        questionLabel.setBounds(50, 50, 700, 100); // Set position and size
        panel.add(questionLabel); // Add question label to the panel

        options = new JRadioButton[4]; // Initialize the options array
        group = new ButtonGroup(); // Initialize the button group

        // Create radio buttons for answer options
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton(); // Create a new radio button
            options[i].setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18)); // Set font
            options[i].setForeground(Color.WHITE); // Set text color
            options[i].setBackground(new Color(50, 64, 113)); // Set background color
            options[i].setBounds(50, 150 + i * 50, 700, 30); // Set position and size
            group.add(options[i]); // Add to the button group
            panel.add(options[i]); // Add to the panel
        }

        // Set up the Next button
        nextButton = new RoundedButton("Next");
        nextButton.setBounds(350, 450, 150, 50); // Set position and size
        nextButton.addActionListener(e -> { // Add action listener for Next button
            storeSelectedAnswer(); // Store the selected answer
            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++; // Move to the next question
                displayQuestion(); // Display the new question
            } else {
                calculateFinalScore(); // Calculate the final score
                storeProgressInFile(username, score); // Store progress in the database
                if (score >= 8) { // Check if the score is sufficient for completion
                    new congrats5(username); // Open the congratulations screen
                } else {
                    new GameOver5(username); // Open the game over screen
                }
                dispose(); // Close the current frame
            }
        });
        panel.add(nextButton); // Add Next button to the panel

        // Set up the Previous button
        previousButton = new RoundedButton("Previous");
        previousButton.setBounds(130, 450, 150, 50); // Set position and size
        previousButton.addActionListener(e -> { // Add action listener for Previous button
            storeSelectedAnswer(); // Store the selected answer
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--; // Move to the previous question
                displayQuestion(); // Display the new question
            }
        });
        panel.add(previousButton); // Add Previous button to the panel

        displayQuestion(); // Display the first question
        add(panel); // Add the main panel to the frame
        setVisible(true); // Make the frame visible
    }

    // Method to display the current question and options
    private void displayQuestion() {
        questionLabel.setText("Question " + (currentQuestionIndex + 1) + ": " + questions[currentQuestionIndex]); // Update the question text
        group.clearSelection(); // Clear previous selections
        for (int i = 0; i < 4; i++) {
            options[i].setText(optionsForQuestions[currentQuestionIndex][i]); // Set text for each option
            // Check if the answer was previously selected
            if (selectedAnswers[currentQuestionIndex] != null && selectedAnswers[currentQuestionIndex].equals(String.valueOf((char) ('A' + i)))) {
                options[i].setSelected(true); // Select the radio button
            }
        }
    }

    // Method to store the selected answer
    private void storeSelectedAnswer() {
        String selectedOption = ""; // Variable to hold the selected option
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedOption = String.valueOf((char) ('A' + i)); // Get the selected option
                break; // Exit the loop once found
            }
        }
        selectedAnswers[currentQuestionIndex] = selectedOption; // Store the selected answer
    }

    // Method to calculate the final score based on selected answers
    private void calculateFinalScore() {
        score = 0; // Reset score
        for (int i = 0; i < selectedAnswers.length; i++) {
            // Check if selected answer is correct
            if (selectedAnswers[i] != null && selectedAnswers[i].equals(correctAnswers[i])) {
                score++; // Increment score for correct answers
            }
        }
    }

    // Method to store progress in the database
    private void storeProgressInFile(String username, int score) {
        // Print the score and username for debugging
        System.out.println(score + "   " + username);
        if (score >= 8) { // Check if the score is sufficient for completion
            String url = "jdbc:mysql://localhost:3306/java"; // Database URL
            String dbUser = "root"; // Database username
            String dbPassword = "#mnta$mn$ta07.inn05/22"; // Database password

            try {
                // Establish a connection to the database
                Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
                String sql = "update users set chapter5_score =? where username = ?"; // SQL query to update score
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, score); // Set score parameter
                preparedStatement.setString(2, username); // Set username parameter
                int noodrecordsupdated = preparedStatement.executeUpdate(); // Execute the update
                System.out.println(noodrecordsupdated); // Print number of records updated
            } catch (Exception e) {
                e.printStackTrace(); // Print any exceptions for debugging
            }
        }
    }

    // Main method to launch the chapter5 quiz
    public static void main(String[] args) {
        new chapter5("testUser"); // Create an instance of chapter5 with a test username
    }

    // Inner class for creating rounded buttons
    static class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label); // Call superclass constructor
            setBackground(hexToColor("#fbb748")); // Set button background color
            setForeground(Color.WHITE); // Set text color
            setFocusPainted(false); // Disable focus painting
            setBorderPainted(false); // Disable border painting
            setContentAreaFilled(false); // Disable content area filling
            setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
        }

        // Custom paint method to create rounded corners
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g; // Cast to Graphics2D for advanced features
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
            g2.setColor(getBackground()); // Set the background color
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Draw rounded rectangle
            super.paintComponent(g); // Call superclass paint method
        }
    }

    // Utility method to convert hex color string to Color object
    private static Color hexToColor(String hex) {
        return new Color(
                Integer.parseInt(hex.substring(1, 3), 16), // Red component
                Integer.parseInt(hex.substring(3, 5), 16), // Green component
                Integer.parseInt(hex.substring(5, 7), 16)  // Blue component
        );
    }
}