package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEventFeedback {
    private JFrame frame;

    public AddEventFeedback(JFrame feedbackPage) {
        // Create the main frame
        frame = new JFrame("Add Event Feedback");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);

        // Create the main panel with BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(10, 25, 74));

        // Add title
        JLabel titleLabel = new JLabel("Add Event Feedback");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        panel.add(titleLabel);

        // Event Name
        JLabel eventNameLabel = new JLabel("Enter Event Name:");
        eventNameLabel.setForeground(Color.WHITE);
        eventNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left
        JTextField eventNameField = new JTextField();
        eventNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(eventNameLabel);
        panel.add(eventNameField);

        // Feedback Text
        JLabel feedbackLabel = new JLabel("Enter Feedback Text:");
        feedbackLabel.setForeground(Color.WHITE);
        feedbackLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left
        JTextArea feedbackArea = new JTextArea(5, 20);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane feedbackScroll = new JScrollPane(feedbackArea);
        feedbackScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(feedbackLabel);
        panel.add(feedbackScroll);

        // Email
        JLabel emailLabel = new JLabel("Enter Your Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(emailLabel);
        panel.add(emailField);

        // Name
        JLabel nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align left
        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(nameLabel);
        panel.add(nameField);

        // Add padding around the main panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a new panel for the buttons and set the layout to center
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Enter Button
        RoundedButton enterButton = new RoundedButton("Enter", 15);
        enterButton.setBackground(Color.WHITE);
        enterButton.setForeground(new Color(0, 20, 60));
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve user inputs
                String eventName = eventNameField.getText();
                String feedbackText = feedbackArea.getText();
                String email = emailField.getText();
                String name = nameField.getText();

                try {
                    // Create an instance of EventFeedback and call addFeedback
                    EventFeedback eventFeedback = new EventFeedback();
                    eventFeedback.addFeedback(eventName, feedbackText, email, name);

                    JOptionPane.showMessageDialog(frame, "Feedback submitted successfully!");
                } catch (CustomException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(enterButton);

        // Back Button
        RoundedButton backButton = new RoundedButton("Back", 15);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 20, 60));
        backButton.addActionListener(e -> {
            // Disposes the current frame and returns to the feedback page
            frame.dispose(); // Disposes current frame
            feedbackPage.setVisible(true); // Make the previous feedback page visible
        });
        buttonPanel.add(backButton);

        // Add the button panel to the bottom of the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH); // Add button panel to bottom

    }

    // Method to display the frame
    public void display() {
        frame.setVisible(true);
    }
}
