package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewEventFeedback {
    private JFrame frame;
    private JTextField eventNameField; // Store the text field as a field for easier access

    public ViewEventFeedback(JFrame adminScreen) {
        // Create the main frame
        frame = new JFrame("View Event Feedback");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(10, 25, 74));

        // Create components
        JPanel headingPanel = createHeadingPanel();
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel(adminScreen);

        // Add components to frame
        frame.add(headingPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add keyboard Enter key functionality
        setupEnterKey();
    }

    // Create the heading panel
    private JPanel createHeadingPanel() {
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(10, 25, 74));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 10, 20));

        JLabel headingLabel = new JLabel("View Event Feedback", SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingPanel.add(headingLabel, BorderLayout.CENTER);

        return headingPanel;
    }

    // Create the input panel
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(new Color(10, 25, 74));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel eventNameLabel = new JLabel("Enter event name to view feedback:");
        eventNameLabel.setForeground(Color.WHITE);
        eventNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        eventNameField = new JTextField();
        eventNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Full width text field

        inputPanel.add(eventNameLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space after label
        inputPanel.add(eventNameField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space after text field

        return inputPanel;
    }

    // Create the button panel to align Enter and Back buttons
    private JPanel createButtonPanel(JFrame adminScreen) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(10, 25, 74));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create and add Enter button
        JButton enterButton = new RoundedButton("Enter", 15);
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setBackground(Color.WHITE);
        enterButton.setForeground(new Color(10, 25, 74));
        enterButton.setPreferredSize(new Dimension(100, 40)); // Rounded and white color

        // Add Enter button action listener
        enterButton.addActionListener(this::viewFeedbackAction);

        // Add Enter button to button panel
        buttonPanel.add(enterButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between buttons

        // Create and add Back button with similar style
        JButton backButton = new RoundedButton("Back", 15);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(100, 40)); // Rounded and white color
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(10, 25, 74));

        // Action to go back to the Admin screen
        backButton.addActionListener(e -> {
            frame.dispose();
            adminScreen.setVisible(true);
        });

        // Add Back button to button panel
        buttonPanel.add(backButton);

        return buttonPanel;
    }

    // View feedback action
    private void viewFeedbackAction(ActionEvent e) {
        String eventName = eventNameField.getText().trim();
        if (eventName.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an event name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<FeedbackData> feedbackList = FeedbackData.viewFeedback(eventName);
        if (feedbackList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No feedback found for the event: " + eventName, "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder feedbackDisplay = new StringBuilder("Feedback for " + eventName + ":\n\n");
            for (FeedbackData feedback : feedbackList) {
                feedbackDisplay.append(feedback.toString()).append("\n\n");
            }
            JOptionPane.showMessageDialog(frame, feedbackDisplay.toString(), "Event Feedback", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Set up Enter key functionality
    private void setupEnterKey() {
        frame.getRootPane().setDefaultButton(new JButton("Dummy")); // Placeholder to avoid issues
        eventNameField.addActionListener(this::viewFeedbackAction);
    }

    // Display the frame
    public void display() {
        frame.setVisible(true);
    }
}
