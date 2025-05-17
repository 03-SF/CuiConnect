package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class displays all announcements for the current user, based on their role.
 * The screen allows scrolling through multiple announcements and offers a back button.
 */
public class AllAnnouncementsScreen {
    public AllAnnouncementsScreen(String currentUserRole, String currentUserEmail, String currentUserPassword, String currentSocietyName) {
        // Create JFrame for the All Announcements screen
        JFrame frame = new JFrame("All Announcements");
        frame.setSize(350, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with dark blue background
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0, 20, 60));

        // Heading label at the top
        JLabel label = new JLabel("All Announcements", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(400, 50));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 26));
        panel.add(label, BorderLayout.NORTH);

        // Panel for displaying announcements
        JPanel announcementsPanel = new JPanel();
        announcementsPanel.setLayout(new BoxLayout(announcementsPanel, BoxLayout.Y_AXIS)); // Vertically stacked
        announcementsPanel.setBackground(new Color(0, 20, 60)); // Same background color
        announcementsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        List<String> announcementsList;

        // Fetch announcements based on user role
        if ("SocietyPresident".equalsIgnoreCase(currentUserRole)) {
            SocietyPresident president = new SocietyPresident(currentUserEmail, currentUserPassword, currentSocietyName);
            announcementsList = president.viewAllAnnouncements(); // Fetch for society president
        } else if ("Admin".equalsIgnoreCase(currentUserRole) || "Student".equalsIgnoreCase(currentUserRole)) {
            Admin admin = new Admin(currentUserEmail, currentUserPassword);
            announcementsList = admin.viewAllAnnouncements(); // Fetch for admin and student
        } else {
            announcementsList = List.of("Error: Invalid Role"); // Handle invalid role
        }

        // Dynamically create panels for each announcement
        if (!announcementsList.isEmpty()) {
            for (String announcement : announcementsList) {
                JPanel announcementPanel = createDynamicHeightPanel(currentSocietyName, announcement);
                announcementsPanel.add(announcementPanel);
                announcementsPanel.add(Box.createVerticalStrut(10)); // Space between announcement panels
            }
        } else {
            // If no announcements, show a label
            JLabel noAnnouncementsLabel = new JLabel("No announcements found.", SwingConstants.CENTER);
            noAnnouncementsLabel.setForeground(Color.WHITE);
            noAnnouncementsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            announcementsPanel.add(noAnnouncementsLabel);
        }

        // Make announcementsPanel scrollable
        JScrollPane scrollPane = new JScrollPane(announcementsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10); // Smooth scrolling
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back button setup
        RoundedButton backButton = new RoundedButton("Back", 30);
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 20, 60)); // Blue text
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Action listener for the back button (closes the frame)
        backButton.addActionListener(e -> frame.dispose());

        // Panel for back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Transparent panel
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel); // Add main panel to the frame
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Creates a dynamic height panel for each announcement.
     * The height adjusts based on the content length.
     */
    private JPanel createDynamicHeightPanel(String societyName, String announcementDetails) {
        JPanel dynamicPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Rounded corners
            }
        };

        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.Y_AXIS)); // Stack vertically
        dynamicPanel.setOpaque(false); // Transparent background for rounded edges
        dynamicPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding inside the panel

        // Society name label
        JLabel societyLabel = new JLabel("<html><b>" + societyName + "</b></html>");
        societyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        societyLabel.setForeground(new Color(0, 20, 60)); // Blue text
        societyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Announcement details label
        JLabel announcementLabel = new JLabel("<html>" + announcementDetails + "</html>");
        announcementLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        announcementLabel.setForeground(new Color(0, 20, 60));
        announcementLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add labels to the dynamic panel
        dynamicPanel.add(societyLabel);
        dynamicPanel.add(Box.createVerticalStrut(10)); // Space between labels
        dynamicPanel.add(announcementLabel);

        // Calculate dynamic height based on announcement content length
        int contentHeight = calculateDynamicHeight(announcementDetails);
        dynamicPanel.setPreferredSize(new Dimension(280, contentHeight)); // Set panel width and height

        return dynamicPanel;
    }

    /**
     * Calculates the height of the announcement panel based on content length.
     */
    private int calculateDynamicHeight(String announcementDetails) {
        int baseHeight = 50; // Base height for the panel
        int additionalHeight = (announcementDetails.length() / 40); // Height increases with content length

        int maxHeight = 150; // Maximum height for the panel
        int height = baseHeight + additionalHeight;

        return Math.min(height, maxHeight); // Ensure height does not exceed maxHeight
    }
}