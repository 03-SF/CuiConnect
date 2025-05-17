package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AllEventsScreen {

    // Constructor to initialize and display the "All Events" screen
    public AllEventsScreen(String currentUserRole, String currentUserEmail, String currentUserPassword, String currentSocietyName) {

        // Frame setup
        JFrame frame = new JFrame("All Events");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLayout(new BorderLayout());

        // Setting background color for the frame
        frame.getContentPane().setBackground(new Color(0, 32, 64));

        // Title label setup
        JLabel titleLabel = new JLabel("Events", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 32, 64));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(350, 40));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel for displaying event details
        JPanel eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS)); // Vertical layout for event boxes
        eventsPanel.setBackground(new Color(0, 32, 64));
        eventsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Fetch events based on user role
        List<String> events = getEventsForRole(currentUserEmail, currentUserPassword, currentUserRole, currentSocietyName);

        // Create event boxes dynamically
        for (String event : events) {
            JPanel eventBox = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(Color.WHITE); // Background color for event box
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Rounded corners
                }
            };

            eventBox.setLayout(new BoxLayout(eventBox, BoxLayout.Y_AXIS)); // Vertical stacking of labels
            eventBox.setOpaque(false);
            eventBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            eventBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Event details split into labels
            JLabel nameLabel = new JLabel(event.split(",")[0]); // Event name
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            eventBox.add(nameLabel);

            JLabel societyLabel = new JLabel("Society: " + event.split(",")[1]); // Society name
            societyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            societyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            eventBox.add(societyLabel);

            JLabel detailsLabel = new JLabel("<html><center>" + event.split(",")[2] + "</center></html>"); // Event details
            detailsLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            eventBox.add(detailsLabel);

            // Adjust size of event box based on content
            eventBox.setMaximumSize(new Dimension(300, nameLabel.getPreferredSize().height +
                societyLabel.getPreferredSize().height + detailsLabel.getPreferredSize().height + 20));

            // Add spacing and add the event box to the panel
            eventsPanel.add(eventBox);
            eventsPanel.add(Box.createVerticalStrut(10));
        }

        // Wrap the panel in a scroll pane
        frame.add(new JScrollPane(eventsPanel), BorderLayout.CENTER);

        // Back button setup
        RoundedButton backButton = new RoundedButton("Back", 20);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 32, 64));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 32, 64));
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for Back button
        backButton.addActionListener(e -> {
            frame.dispose(); // Close current screen

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

        frame.setVisible(true); // Display the frame
    }

    // Method to fetch events based on the role
    private List<String> getEventsForRole(String email, String password, String role, String societyName) {
        if ("admin".equalsIgnoreCase(role) || "Student".equalsIgnoreCase(role)) {
            return new Admin(email, password).viewAllEvents(); // Admin events
        } else {
            return new SocietyPresident(email, password, societyName).viewAllEvents(); // Society President events
        }
    }
}