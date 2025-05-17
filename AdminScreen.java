package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminScreen {

    private Admin admin;  // Admin instance to manage actions

    public AdminScreen(String currentUserRole, String currentUserEmail, String currentUserPassword, String currentSocietyName) {
        this.admin = new Admin(currentUserEmail, currentUserPassword);  // Initialize Admin instance

        // Create and configure main frame
        JFrame frame = new JFrame("Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);

        // Main panel setup
        JPanel panel = new JPanel();
        panel.setBackground(new Color(3, 32, 64));  // Dark blue theme
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add title to panel
        JLabel titleLabel = new JLabel("Admin");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));  // Add spacing
        panel.add(titleLabel);

        // Button labels for admin actions
        String[] buttons = {
            "View All Societies", "View All Events", "View All Announcements",
            "List All Users", "Remove any Member", "Create Event",
            "Delete Event", "Create Announcement", "Delete Announcement",
            "Delete Society", "Posts", "Feedback"
        };

        // Add buttons dynamically
        for (String text : buttons) {
            RoundedButton button = new RoundedButton(text, 15);  // Rounded style
            styleButton(button);  // Apply styling
            panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Add spacing
            panel.add(button);

            // Add action listener for each button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (text) {
                        case "View All Societies":
                            new AllSocietiesScreen();  // Navigate to societies view
                            break;
                        case "View All Events":
                            new AllEventsScreen(currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName);  // View events
                            break;
                        case "View All Announcements":
                            new AllAnnouncementsScreen(currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName);  // View announcements
                            break;
                        case "List All Users":
                            new AllUsersScreen(currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName);  // List users
                            break;
                        case "Remove any Member":
                            String emailToRemove = JOptionPane.showInputDialog(frame, "Enter the Email of the Member to Remove:");
                            if (emailToRemove != null && !emailToRemove.isEmpty()) {
                                String result = admin.removeMember(emailToRemove);  // Remove member
                                new CustomDialog(frame, "Remove Member", result);
                            }
                            break;
                        case "Create Event":
                            String eventDetails = JOptionPane.showInputDialog(frame, "Enter Event Details:");
                            String eventName = JOptionPane.showInputDialog(frame, "Enter Event Name:");
                            if (eventDetails != null && eventName != null) {
                                String result = admin.createGeneralEvent(eventDetails, eventName);  // Create event
                                new CustomDialog(frame, "Create Event", result);
                            }
                            break;
                        case "Delete Event":
                            String deleteEventDetails = JOptionPane.showInputDialog(frame, "Enter Event Details to Delete:");
                            if (deleteEventDetails != null) {
                                try {
                                    String result = admin.deleteEvent(deleteEventDetails);  // Delete event
                                    new CustomDialog(frame, "Delete Event", result);
                                } catch (Exception ex) {
                                    new CustomDialog(frame, "Delete Event", "Error deleting event: " + ex.getMessage());
                                }
                            }
                            break;
                        case "Create Announcement":
                            String announcementDetails = JOptionPane.showInputDialog(frame, "Enter Announcement Details:");
                            if (announcementDetails != null) {
                                String result = admin.createGeneralAnnouncement(announcementDetails);  // Create announcement
                                new CustomDialog(frame, "Create Announcement", result);
                            }
                            break;
                        case "Delete Announcement":
                            String deleteAnnouncementDetails = JOptionPane.showInputDialog(frame, "Enter Announcement Details to Delete:");
                            if (deleteAnnouncementDetails != null) {
                                try {
                                    String result = admin.deleteAnnouncement(deleteAnnouncementDetails);  // Delete announcement
                                    new CustomDialog(frame, "Delete Announcement", result);
                                } catch (Exception ex) {
                                    new CustomDialog(frame, "Delete Announcement", "Error deleting announcement: " + ex.getMessage());
                                }
                            }
                            break;
                        case "Delete Society":
                            String societyToDelete = JOptionPane.showInputDialog(frame, "Enter Society Name to Delete:");
                            if (societyToDelete != null && !societyToDelete.isEmpty()) {
                                try {
                                    String result = admin.deleteSociety(societyToDelete);  // Delete society
                                    new CustomDialog(frame, "Delete Society", result);
                                } catch (Exception ex) {
                                    new CustomDialog(frame, "Delete Society", "Error deleting society: " + ex.getMessage());
                                }
                            }
                            break;
                        case "Posts":
                            SwingUtilities.invokeLater(() -> {
                            frame.dispose();  // Dispose the admin screen
                            new PostScreen(frame, currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName).display();
                        });
                            break;
                        case "Feedback":
                            // Dispose of Admin screen and open Feedback screen
                            frame.dispose();  // Close Admin screen
                            new FeedbackScreen(currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName).display();
                            break;
                    }
                }
            });
        }

        // Logout button setup
        RoundedButton logoutButton = new RoundedButton("Logout", 15);
        styleLogoutButton(logoutButton);  // Apply smaller style
        logoutButton.addActionListener(e -> {
            new CustomDialog(frame, "Logout", "You have successfully logged out!");  // Confirm logout
            frame.dispose();  // Close frame
        });

        // Add logout button to a separate panel
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setBackground(new Color(3, 32, 64));  // Match theme
        logoutPanel.add(logoutButton);
        panel.add(Box.createVerticalGlue());
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(logoutPanel);

        // Add scrolling for the panel
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);

        frame.setVisible(true);  // Display frame
    }

    // Style for main buttons
    private void styleButton(RoundedButton button) {
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(180, 40));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(3, 32, 64));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusPainted(false);
    }

    // Style for logout button
    private void styleLogoutButton(RoundedButton button) {
        button.setPreferredSize(new Dimension(140, 35));
        button.setMaximumSize(new Dimension(140, 35));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(3, 32, 64));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setFocusPainted(false);
    }
}
