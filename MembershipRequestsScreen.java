package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class displays membership requests for a Society President and allows them to accept or reject requests.
 * A list of membership requests is shown with a back button to close the screen.
 */
public class MembershipRequestsScreen {

    private SocietyPresident societyPresident; // The current Society President
    private JFrame frame; // The frame for the UI
    private JPanel panel; // The main panel containing all components
    private JLabel label; // Heading label for the screen
    private JButton[] requestButtons; // Array to hold buttons for each membership request

    public MembershipRequestsScreen(SocietyPresident societyPresident) {
        this.societyPresident = societyPresident;

        // Create JFrame for the Membership Requests screen
        frame = new JFrame("Membership Requests");
        frame.setSize(350, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel setup with dark blue background
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new java.awt.Color(0, 20, 60));

        // Heading label setup
        label = new JLabel("Membership Requests", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(400, 50));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        // Panel to contain the membership requests
        JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBackground(new Color(0, 20, 60)); // Same background as main panel
        requestsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get the list of membership requests
        List<String> membershipRequests = societyPresident.viewMembershipRequests();

        // Dynamically create buttons for each membership request
        if (membershipRequests != null && !membershipRequests.isEmpty()) {
            requestButtons = new JButton[membershipRequests.size()];
            for (int i = 0; i < membershipRequests.size(); i++) {
                String studentEmail = membershipRequests.get(i);
                requestButtons[i] = new JButton(studentEmail); // Display the student email
                requestButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
                requestButtons[i].setPreferredSize(new Dimension(260, 40)); // Set preferred size for the button
                requestButtons[i].setBackground(new Color(255, 255, 255)); // White background
                requestButtons[i].setForeground(new Color(0, 20, 60)); // Blue text
                requestButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));

                // Add action listener to handle the request
                int finalI = i; // to use the index in the action listener
                requestButtons[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String email = membershipRequests.get(finalI);
                        handleMembershipRequest(email); // Handle request when button is clicked
                    }
                });

                // Add the button to the requests panel with spacing between buttons
                requestsPanel.add(requestButtons[i]);
                requestsPanel.add(Box.createVerticalStrut(10)); // Add spacing between requests
            }
        } else {
            // If no membership requests, show a message
            JLabel noRequestsLabel = new JLabel("No membership requests.", SwingConstants.CENTER);
            noRequestsLabel.setForeground(Color.WHITE);
            noRequestsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            requestsPanel.add(noRequestsLabel);
        }

        // Make the requestsPanel scrollable
        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10); // Smooth scrolling
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back button setup
        RoundedButton backButton = new RoundedButton("Back", 30);
        backButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size for the button
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 20, 60));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Back button action listener (closes the frame when clicked)
        backButton.addActionListener(e -> frame.dispose());

        // Button panel setup (holds the back button)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Transparent panel
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to the frame and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Handles the membership request (accept/reject) for a given student.
     *
     * @param studentEmail The email of the student whose request is being handled.
     */
    private void handleMembershipRequest(String studentEmail) {
        // Prompt the Society President to accept or reject the membership request
        int option = JOptionPane.showOptionDialog(frame,
                "Do you want to accept the membership request from " + studentEmail + "?",
                "Membership Request",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Accept", "Reject"},
                "Reject");

        if (option == JOptionPane.YES_OPTION) {
            // Accept the membership request
            boolean accepted = societyPresident.acceptMembershipRequest(studentEmail);
            if (accepted) {
                JOptionPane.showMessageDialog(frame, "You have accepted the membership request from " + studentEmail);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to accept the membership request from " + studentEmail);
            }
        } else if (option == JOptionPane.NO_OPTION) {
            // Reject the membership request
            JOptionPane.showMessageDialog(frame, "You have rejected the membership request from " + studentEmail);
        }
    }
}