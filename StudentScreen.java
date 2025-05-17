package cuiconnect02;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the GUI screen for a student.
 */
public class StudentScreen {
    private Student currentStudent;

    public StudentScreen(Student student) {
        this.currentStudent = student; // Initialize the current student

        // Create and configure the main frame
        JFrame frame = new JFrame("Student");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);

        // Main panel with a dark blue background
        JPanel panel = new JPanel();
        panel.setBackground(new Color(3, 32, 64)); // Dark blue
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title label
        JLabel titleLabel = new JLabel("Student");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Spacing below title
        panel.add(titleLabel);

        // Buttons for different actions
        String[] buttons = {
            "View All Societies", "View All Events", "View All Announcements",
            "Request Membership", "Leave Society", "Post", "Feedback"
        };

        // Add buttons to the panel
        for (String text : buttons) {
            RoundedButton button = new RoundedButton(text, 15); // Create rounded button
            styleButton(button);
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
            panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing between buttons
            panel.add(button);

            // Add action listeners for each button
            if (text.equals("Request Membership")) {
                button.addActionListener(e -> {
                    String societyName = JOptionPane.showInputDialog(frame, "Enter the Society Name:");
                    if (societyName != null && !societyName.isEmpty()) {
                        String result = currentStudent.requestMembership(societyName);
                        JOptionPane.showMessageDialog(frame, result);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid society name.");
                    }
                });
            } else if (text.equals("Leave Society")) {
                button.addActionListener(e -> {
                    String societyName = JOptionPane.showInputDialog(frame, "Enter the Society Name to Leave:");
                    if (societyName != null && !societyName.isEmpty()) {
                        String result = currentStudent.leaveSociety(societyName);
                        JOptionPane.showMessageDialog(frame, result);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a valid society name.");
                    }
                });
            } else if (text.equals("View All Societies")) {
                button.addActionListener(e -> new AllSocietiesScreen());
            } else if (text.equals("View All Events")) {
                button.addActionListener(e -> new AllEventsScreen(
                        currentStudent.getRole(),
                        currentStudent.getEmail(),
                        currentStudent.getPassword(),
                        ""));
            } else if (text.equals("View All Announcements")) {
                button.addActionListener(e -> new AllAnnouncementsScreen(
                        currentStudent.getRole(),
                        currentStudent.getEmail(),
                        currentStudent.getPassword(),
                        ""));
            } else if (text.equals("Post")) {
                button.addActionListener(e -> {
                    // Navigate to the PostScreen
                    new PostScreen(frame, currentStudent.getRole(), currentStudent.getEmail(), currentStudent.getPassword(), currentStudent.getSocietyName()).display();
                });
            } else if (text.equals("Feedback")) {
                button.addActionListener(e -> {
                    // Navigate to the FeedbackScreen
                    new FeedbackScreen(
                            currentStudent.getRole(),
                            currentStudent.getEmail(),
                            currentStudent.getPassword(),
                            currentStudent.getSocietyName()).display();
                });
            }
        }

        // Logout button
        RoundedButton logoutButton = new RoundedButton("Logout", 15);
        styleButton(logoutButton);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment

        logoutButton.addActionListener(e -> {
            // Logout functionality
            JOptionPane.showMessageDialog(frame, "Logging out...");
            frame.dispose(); // Close the window
        });

        panel.add(Box.createRigidArea(new Dimension(0, 30))); // Spacing above logout button
        panel.add(logoutButton);

        // Add scroll functionality to the panel
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);

        frame.setVisible(true); // Make the frame visible
    }

    private void styleButton(RoundedButton button) {
        button.setPreferredSize(new Dimension(220, 40)); // Set button size
        button.setMaximumSize(new Dimension(220, 40)); // Ensure consistent size
        button.setBackground(Color.WHITE); // White background
        button.setForeground(new Color(3, 32, 64)); // Dark blue text
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Font settings
        button.setFocusPainted(false); // Remove focus outline
    }
}
