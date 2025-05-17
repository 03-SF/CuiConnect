package cuiconnect02; 

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedbackScreen {
    private JFrame frame;
    private String currentUserRole;
    private String currentUserEmail;
    private String currentUserPassword;
    private String currentSocietyName;

    // Constructor accepting user details
    public FeedbackScreen(String currentUserRole, String currentUserEmail, String currentUserPassword, String currentSocietyName) {
        this.currentUserRole = currentUserRole;
        this.currentUserEmail = currentUserEmail;
        this.currentUserPassword = currentUserPassword;
        this.currentSocietyName = currentSocietyName;

        // Create the main frame
        frame = new JFrame("Feedback");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(10, 25, 74)); // Entire background blue

        // Panel for the heading
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(10, 25, 74));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 10, 20)); // Add space above heading

        JLabel headingLabel = new JLabel("Feedback", SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingPanel.add(headingLabel, BorderLayout.CENTER);

        // Panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(10, 25, 74)); // Blue background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add space around buttons

        // Create buttons with ">" icon
        String[] buttonLabels = {
                "Add Event Feedback",
                "View Event Feedback",
                "Delete Event Feedback"
        };

        for (String label : buttonLabels) {
            RoundedButton button = new RoundedButton(label + " >", 15);
            button.addActionListener(new ButtonClickListener(label));
            button.setPreferredSize(new Dimension(200, 50)); // Make buttons twice the width of old ones
            buttonPanel.add(button);
        }

        // Back button with rounded corners
        RoundedButton backButton = new RoundedButton("Back", 15) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); // Larger rounded corners
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No border for the back button
            }
        };

        // Back button action listener
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            frame.dispose();  // Close the feedback screen

            // Navigate based on user role
            switch (currentUserRole.toLowerCase()) {
                case "admin":
                    new AdminScreen(currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName);
                    break;
                case "student":
                    Student student = new Student(currentUserEmail, currentUserPassword);
                    new StudentScreen(student);
                    break;
                case "societypresident":
                    new PresidentScreen(currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unknown user role.");
                    break;
            }
        });

        // Panel for back button
        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(10, 25, 74));
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        backPanel.setLayout(new BorderLayout());
        backPanel.add(backButton, BorderLayout.CENTER);

        // Adjusting position of the back button to be a little higher from the frame's bottom
        JPanel paddingPanel = new JPanel();
        paddingPanel.setBackground(new Color(10, 25, 74));
        paddingPanel.setPreferredSize(new Dimension(350, 50));
        frame.add(paddingPanel, BorderLayout.SOUTH);

        // Add components to frame
        frame.add(headingPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(backPanel, BorderLayout.SOUTH);
    }

    // Method to display the frame
    public void display() {
        frame.setVisible(true);
    }

    // ActionListener for buttons
    static class ButtonClickListener implements ActionListener {
        private String buttonName;

        public ButtonClickListener(String buttonName) {
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(buttonName + " button clicked");

            // Handle button clicks and navigate to corresponding screens
            switch (buttonName) {
                case "Add Event Feedback":
                    new AddEventFeedback(null).display();
                    break;
                case "View Event Feedback":
                    new ViewEventFeedback(null).display();
                    break;
                case "Delete Event Feedback":
                    // Pass the user details to the DeleteEventFeedback constructor
                    new DeleteEventFeedback(null);
                    break;
                default:
                    break;
            }
        }
    }
}
