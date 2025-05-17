package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * GUI for the Registration screen with various management options.
 */
public class RegistrationScreen {
    public RegistrationScreen() {
        // Setting up the main frame for the registration screen
        // This frame serves as the window for the "New Account" registration form
        JFrame frame = new JFrame("New Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensures the program exits on close
        frame.setSize(350, 600); // Frame dimensions

        // Creating a panel for the components of the form
        // This panel helps in organizing all the GUI elements
        JPanel panel = new JPanel();
        panel.setBackground(new Color(3, 32, 64)); // Dark blue background to match the theme
        panel.setLayout(null); // Absolute positioning for precise placement of elements

        // Adding a title to the form
        // I used a JLabel for displaying "New Account" prominently
        JLabel titleLabel = new JLabel("New Account");
        titleLabel.setForeground(Color.WHITE); // White font color for better contrast
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Larger, bold font for visibility
        titleLabel.setBounds(90, 30, 200, 30); // Positioning the title at the top center
        panel.add(titleLabel);

        // Adding a subtitle for instructions
        // This provides context to the user about the purpose of the form
        JLabel subtitleLabel = new JLabel("Personal information is used for registration.");
        subtitleLabel.setForeground(Color.WHITE); // Matching font color with the title
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Subtle, smaller font for details
        subtitleLabel.setBounds(50, 70, 250, 20); // Positioning below the title
        panel.add(subtitleLabel);

        // Adding an input field for the email
        // I used a custom RoundedTextField for consistent design with rounded corners
        RoundedTextField emailField = new RoundedTextField(15);
        emailField.setBounds(75, 120, 200, 35); // Centered placement
        emailField.setText("   Email"); // Placeholder text
        panel.add(emailField);

        // Adding an input field for the user's role
        RoundedTextField roleField = new RoundedTextField(15);
        roleField.setBounds(75, 180, 200, 35); // Positioned below the email field
        roleField.setText("   Role"); // Placeholder text to indicate input
        panel.add(roleField);

        // Adding a password field for security
        RoundedTextField passwordField = new RoundedTextField(15);
        passwordField.setBounds(75, 240, 200, 35); // Positioned below the role field
        passwordField.setText("   Password"); // Placeholder text for clarity
        panel.add(passwordField);

        // Adding a field for society name (specific to society presidents)
        RoundedTextField societyField = new RoundedTextField(15);
        societyField.setBounds(75, 300, 200, 35); // Positioned below the password field
        societyField.setText("   Society Name (For President)"); // Placeholder for role-specific input
        panel.add(societyField);

        // Adding the Register button
        // This button initiates the registration process when clicked
        RoundedButton registerButton = new RoundedButton("Register", 15);
        registerButton.setBounds(110, 380, 120, 40); // Centered button placement
        registerButton.setBackground(Color.WHITE); // White background for contrast
        registerButton.setForeground(new Color(3, 32, 64)); // Text color matching the theme
        registerButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font for emphasis
        panel.add(registerButton);

        // Adding functionality to the Register button
        // This action listener captures user input and processes the registration
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieving user input from the text fields
                String email = emailField.getText().trim();
                String role = roleField.getText().trim();
                String password = passwordField.getText().trim();
                String societyName = societyField.getText().trim();

                try {
                    // Attempting to register the user using the input
                    boolean isSuccess = User.register(email, password, role, societyName);

                    if (isSuccess) {
                        // Displaying a success dialog and closing the form
                        new CustomDialog(frame, "Success", "Registration Successful!");
                        frame.dispose(); // Close the registration window
                    }
                } catch (CustomException ex) {
                    // Displaying an error dialog if registration fails
                    new CustomDialog(frame, "Error", ex.getMessage());
                }
            }
        });

        // Adding the panel to the frame and making it visible
        frame.add(panel); // Adding all components to the frame
        frame.setVisible(true); // Displaying the frame
    }
}