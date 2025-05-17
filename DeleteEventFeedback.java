package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteEventFeedback {
    private JFrame frame;
    private String currentUserRole;
    private String currentUserEmail;
    private String currentUserPassword;
    private String currentSocietyName;

    // Constructor accepting user details, allowing null values for default values
    public DeleteEventFeedback(String userDetails) {
        // If userDetails is null, set default values for role, email, password, and society name
        this.currentUserRole = (userDetails != null) ? userDetails : "Default Role";
        this.currentUserEmail = (userDetails != null) ? userDetails : "default@comsats.edu.pk";
        this.currentUserPassword = (userDetails != null) ? userDetails : "defaultPassword";
        this.currentSocietyName = (userDetails != null) ? userDetails : "Default Society";

        // Create the main frame with a title "Delete Event Feedback"
        frame = new JFrame("Delete Event Feedback");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation for the window
        frame.setSize(350, 600); // Set the size of the frame

        // Create a panel that will hold all components and set its layout to vertical box
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Arrange components vertically
        panel.setBackground(new Color(10, 25, 50)); // Set the background color to a dark shade

        // Create and customize title label
        JLabel titleLabel = new JLabel("Delete Event Feedback");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align the title to the left of the panel
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Set the font for the title
        titleLabel.setForeground(Color.WHITE);  // Set the text color to white
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Add spacing between components
        panel.add(titleLabel);

        // Create form fields with labels and text fields for user inputs
        JLabel eventNameLabel = new JLabel("Enter event name:");
        eventNameLabel.setForeground(Color.WHITE);  // Label color is white
        eventNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);  // Align the label to the left
        JTextField eventNameField = new JTextField();
        eventNameField.setMaximumSize(new Dimension(600, 30));  // Set text field width
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between components
        panel.add(eventNameLabel);
        panel.add(eventNameField);  // Add the text field for event name

        // Create email field for user input
        JLabel emailLabel = new JLabel("Enter your Email:");
        emailLabel.setForeground(Color.WHITE);  // Label color is white
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(600, 30));  // Set text field width
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(emailLabel);
        panel.add(emailField);  // Add the text field for email

        // Create role field for user input
        JLabel userRoleLabel = new JLabel("Enter your Role (Admin/Other):");
        userRoleLabel.setForeground(Color.WHITE);
        userRoleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField userRoleField = new JTextField();
        userRoleField.setMaximumSize(new Dimension(600, 30));  // Set text field width
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(userRoleLabel);
        panel.add(userRoleField);  // Add the text field for user role

        // Create feedback index field for user input
        JLabel feedbackIndexLabel = new JLabel("Enter Feedback Index:");
        feedbackIndexLabel.setForeground(Color.WHITE);
        feedbackIndexLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField feedbackIndexField = new JTextField();
        feedbackIndexField.setMaximumSize(new Dimension(600, 30));  // Set text field width
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(feedbackIndexLabel);
        panel.add(feedbackIndexField);  // Add the text field for feedback index

        // Create the 'Enter' button to submit the feedback deletion request
        JButton enterButton = new JButton("Enter");
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align the button to the center
        enterButton.setBackground(Color.WHITE);  // Set button background color
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve user inputs from text fields
                String eventName = eventNameField.getText();
                String email = emailField.getText();
                String userRole = userRoleField.getText();
                String feedbackIndexText = feedbackIndexField.getText();

                try {
                    // Try to convert feedback index to integer
                    int feedbackIndex = Integer.parseInt(feedbackIndexText);
                    EventFeedback eventFeedback = new EventFeedback();
                    eventFeedback.deleteFeedback(eventName, email, userRole, feedbackIndex); // Call deleteFeedback method
                    JOptionPane.showMessageDialog(frame, "Feedback deleted successfully!");  // Show success message
                } catch (CustomException ex) {
                    // If there is a custom exception, show the error message
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    // If there is an error converting index to integer, show the error message
                    JOptionPane.showMessageDialog(frame, "Invalid feedback index.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Create the 'Back' button to navigate back to the previous screen
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Align button to the center
        backButton.setBackground(Color.WHITE);  // Set button background color
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current DeleteEventFeedback screen
                frame.dispose();
                // Navigate to the FeedbackScreen, passing necessary user details
                new FeedbackScreen(currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName).display();
            }
        });

        // Set up the panel's borders and spacing between components
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(enterButton);  // Add the 'Enter' button
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(backButton);  // Add the 'Back' button

        // Add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);  // Display the frame
    }

    // Method to display the frame (re-used if needed elsewhere)
    public void display() {
        frame.setVisible(true);
    }
}
