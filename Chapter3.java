import javax.swing.*;              // Import Swing components for GUI
import java.awt.*;                 // Import AWT for layout and graphics
import java.sql.Connection;        // Import for SQL connection
import java.sql.DriverManager;     // Import for managing SQL driver
import java.sql.PreparedStatement; // Import for prepared SQL statements

public class chapter3 extends JFrame {
    private static String username; // Static field to store the username
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
            "What is the result of the expression boolean b = (5 > 3);?",
            "Which relational operator checks if two values are equal in Java?",
            "In an if-else statement, what does the else block execute?",
            "Which of the following statements is correct about the && operator?",
            "What output will be produced by the statement System.out.println((5 % 2 == 0) ? \"Even\" : \"Odd\");?",
            "Which statement is used to terminate the execution of a case in a switch block?",
            "When does a leap year occur?",
            "In Java, what is the purpose of the default keyword in a switch statement?",
            "Which operator can be used as a shorthand for a simple if-else statement?",
            "What is a debugger mainly used for in programming?"
    };

    // 2D array of answer options for each question
    private String[][] optionsForQuestions = {
            {"A. true", "B. false", "C. null", "D. Syntax error"},
            {"A. =", "B. !=", "C. ==", "D. <>"},
            {"A. When the condition is true", "B. When the condition is false", "C. Only when there's a syntax error", "D. Always, regardless of the condition"},
            {"A. It returns true if both conditions are true.", "B. It returns false if both conditions are true.", "C. It is used to check if one condition is true.", "D. It is used only with integers."},
            {"A. Even", "B. Odd", "C. Error", "D. None"},
            {"A. stop", "B. return", "C. exit", "D. break"},
            {"A. Every year divisible by 4", "B. Every year divisible by 100", "C. Every year divisible by 4 but not by 100, or divisible by 400", "D. Every year divisible by 400"},
            {"A. To set the initial value", "B. To match any case that is not specified", "C. To stop the switch execution", "D. To throw an exception"},
            {"A. ||", "B. &&", "C. ?:","D. !"},
            {"A. To compile code", "B. To find and fix errors", "C. To generate random values", "D. To design user interfaces"}
    };

    private String[] correctAnswers = {"A", "C", "B", "A", "B", "D", "C", "B", "C", "B"}; // Array of correct answers
    private int currentQuestionIndex = 0; // Index of the current question
    private int score = 0; // User's score
    private String[] selectedAnswers = new String[10]; // Array to store user's selected answers

    // Constructor for the chapter3 class
    public chapter3(String username) {
        this.username = username; // Initialize username
        this.homePage = homePage; // This seems incorrect; it should be initialized properly if used
        setTitle("Java Quiz - Chapter 3"); // Set the window title
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
        panel.add(questionLabel); // Add to the panel

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
                if (score >= 8) {
                    //homePage.completeChapter(1, score); // Uncomment if HomePage method is defined
                    new congrats3(username); // Open the congratulations screen
                } else {
                    new GameOver3(username); // Open the game over screen
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
                String sql = "update users set chapter3_score =? where username = ?"; // SQL query to update score
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

    // Main method to launch the chapter3 quiz
    public static void main(String[] args) {
        new chapter3("testUser"); // Create an instance of chapter3 with a test username
    }

    // Inner class for creating rounded buttons
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text); // Call superclass constructor
            setBackground(hexToColor("#fbb748")); // Set button background color
            setForeground(Color.WHITE); // Set text color
            setBorderPainted(false); // Disable border painting
            setFocusPainted(false); // Disable focus painting
            setContentAreaFilled(false); // Disable content area filling
            setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 16)); // Set font
            setMargin(new Insets(0, 0, 0, 0)); // Set margin to zero
            setOpaque(false); // Make the button transparent
            setBorder(BorderFactory.createEmptyBorder()); // Set an empty border
        }

        // Custom paint method to create rounded corners
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground()); // Set the background color
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Draw rounded rectangle
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