package cuiconnect02;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the Society President screen with various management options.
 */
public class PresidentScreen {

    public PresidentScreen(String role, String email, String password, String societyName) {
        // Main frame setup
        JFrame frame = new JFrame("Society President");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);

        // Panel setup with dark blue background
        JPanel panel = new JPanel();
        panel.setBackground(new Color(3, 32, 64));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Title label
        JLabel titleLabel = new JLabel("Society President");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(titleLabel);

        // Instance of SocietyPresident for backend operations
        SocietyPresident societyPresident = new SocietyPresident(email, password, societyName);

        // Buttons for various actions
        String[] buttons = {
            "View All Societies", "View All Events", "View All Announcements",
            "List All Users", "Remove any Member", "Create Event",
            "Delete Event", "Create Announcement", "Delete Announcement",
            "View Membership Requests", "Posts", "Feedback"
        };

        // Add buttons dynamically
        for (String text : buttons) {
            RoundedButton button = new RoundedButton(text, 15); // Button with rounded corners
            styleButton(button);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Define actions for each button
            button.addActionListener(e -> {
                switch (text) {
                    case "View All Societies":
                        new AllSocietiesScreen();
                        break;
                    case "View All Events":
                        new AllEventsScreen(role, email, password, societyName);
                        break;
                    case "View All Announcements":
                        new AllAnnouncementsScreen(role, email, password, societyName);
                        break;
                    case "List All Users":
                        new AllUsersScreen(role, email, password, societyName);
                        break;
                    case "Remove any Member":
                        String studentEmail = JOptionPane.showInputDialog(frame, "Enter Member Email to Remove:");
                        if (societyPresident.removeMember(studentEmail)) {
                            JOptionPane.showMessageDialog(frame, "Member Removed Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to Remove Member.");
                        }
                        break;
                    case "Create Event":
                        String eventDetails = JOptionPane.showInputDialog(frame, "Enter Event Details:");
                        String eventName = JOptionPane.showInputDialog(frame, "Enter Event Name:");
                        if (societyPresident.createSocietyEvent(eventDetails, eventName)) {
                            JOptionPane.showMessageDialog(frame, "Event Created Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to Create Event.");
                        }
                        break;
                    case "Delete Event":
                        String eventToDelete = JOptionPane.showInputDialog(frame, "Enter Event Name to Delete:");
                        String eventDetailsToDelete = JOptionPane.showInputDialog(frame, "Enter Event Details:");
                        if (societyPresident.deleteSocietyEvent(eventToDelete, eventDetailsToDelete)) {
                            JOptionPane.showMessageDialog(frame, "Event Deleted Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to Delete Event.");
                        }
                        break;
                    case "Create Announcement":
                        String announcementDetails = JOptionPane.showInputDialog(frame, "Enter Announcement Details:");
                        if (societyPresident.createSocietyAnnouncement(announcementDetails)) {
                            JOptionPane.showMessageDialog(frame, "Announcement Created Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to Create Announcement.");
                        }
                        break;
                    case "Delete Announcement":
                        String announcementToDelete = JOptionPane.showInputDialog(frame, "Enter Announcement Details to Delete:");
                        if (societyPresident.deleteSocietyAnnouncement(announcementToDelete)) {
                            JOptionPane.showMessageDialog(frame, "Announcement Deleted Successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to Delete Announcement.");
                        }
                        break;
                    case "View Membership Requests":
                        new MembershipRequestsScreen(new SocietyPresident(email, password, societyName));
                        break;
                    case "Posts":
                        new PostScreen(frame, role, email, password, societyName).display();
                        break;

                    case "Feedback":
                        new FeedbackScreen(role, email, password, societyName).display(); // Open the FeedbackScreen
                        break;
                    default:
                        JOptionPane.showMessageDialog(frame, "Feature not implemented yet.");
                        break;
                }
            });

            panel.add(button);
        }

        // Logout button
        RoundedButton logoutButton = new RoundedButton("Logout", 15);
        styleButton(logoutButton);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> frame.dispose());
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(logoutButton);

        // Add panel to a scrollable frame
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);

        frame.setVisible(true);
    }

   
     // Styles buttons for consistency.
     
    private void styleButton(RoundedButton button) {
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(3, 32, 64));
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
    }
}