package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * This class displays a list of users (either all users or society members) depending on the role of the current user.
 * A scrollable list of users is shown with a back button to close the screen.
 */
public class AllUsersScreen {

    private String currentUserRole; // Role of the current logged-in user
    private String currentUserEmail; // Logged-in user's email
    private String currentUserPassword; // Logged-in user's password
    private String currentSocietyName; // Society name for Society President (if applicable)

    /**
     * Constructor to initialize the All Users screen with user details.
     * 
     * @param currentUserRole Role of the current user (e.g., "Admin" or "SocietyPresident").
     * @param currentUserEmail Email of the current logged-in user.
     * @param currentUserPassword Password of the current logged-in user.
     * @param currentSocietyName Name of the society for a Society President (if applicable).
     */
    public AllUsersScreen(String currentUserRole, String currentUserEmail, String currentUserPassword, String currentSocietyName) {
        this.currentUserRole = currentUserRole;
        this.currentUserEmail = currentUserEmail;
        this.currentUserPassword = currentUserPassword;
        this.currentSocietyName = currentSocietyName;

        // Create JFrame for the All Users screen
        JFrame frame = new JFrame("All Users");
        frame.setSize(350, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel setup with dark blue background
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new java.awt.Color(0, 20, 60));

        // Heading label setup
        JLabel label = new JLabel("All Users", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(400, 50));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        // Panel to contain the user list
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setBackground(new Color(0, 20, 60)); // Same background as main panel
        usersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Get the list of users based on the current role
        List<String> userList = getUserList();

        // Display users dynamically in rounded panels
        if (!userList.isEmpty()) {
            for (String user : userList) {
                JPanel userPanel = createRoundedPanel(user);
                userPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
                usersPanel.add(userPanel);
                usersPanel.add(Box.createVerticalStrut(5)); // Add spacing between user panels
            }
        } else {
            // If no users found, display message
            JLabel noUsersLabel = new JLabel("No users found.", SwingConstants.CENTER);
            noUsersLabel.setForeground(Color.WHITE);
            noUsersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            usersPanel.add(noUsersLabel);
        }

        // Make usersPanel scrollable
        JScrollPane scrollPane = new JScrollPane(usersPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10); // Smooth scrolling
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back button setup
        RoundedButton backButton = new RoundedButton("Back", 30);
        backButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size for the button
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 20, 60)); // Blue text
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Font size for the button

        // Back button action listener (closes the frame when clicked)
        backButton.addActionListener(e -> frame.dispose());

        // Button panel setup (holds the back button)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Transparent panel
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel); // Add main panel to the frame
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Creates a rounded panel for displaying a user's information.
     */
    private JPanel createRoundedPanel(String userText) {
        JPanel roundedPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE); // Pure white background for the rounded panel
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Smaller rounded corners
            }
        };

        roundedPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel(userText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font size
        label.setForeground(new Color(0, 20, 60)); // Blue text
        roundedPanel.add(label, BorderLayout.CENTER);

        // Set the size for the rounded panel
        roundedPanel.setPreferredSize(new Dimension(300, 40));
        roundedPanel.setMinimumSize(new Dimension(300, 40));
        roundedPanel.setMaximumSize(new Dimension(300, 40));
        roundedPanel.setOpaque(false); // Transparent for rounded edges
        return roundedPanel;
    }

    /**
     * Retrieves a list of users based on the logged-in user's role.
     *
     * @return A list of user names or a message indicating no users found.
     */
    private List<String> getUserList() {
        List<String> userList = new ArrayList<>();

        // Handle different roles for user listing
        if (currentUserRole.equals("SocietyPresident")) {
            // Society President's role - get society members
            SocietyPresident societyPresident = new SocietyPresident(currentUserEmail, currentUserPassword, currentSocietyName);
            List<String> societyMembers = societyPresident.listMembers();
            if (societyMembers != null) {
                userList.addAll(societyMembers); // Add society members to the list
            } else {
                userList.add("No members found.");
            }
        } else if (currentUserRole.equals("Admin")) {
            // Admin role - get all users
            Admin admin = new Admin(currentUserEmail, currentUserPassword);  // Pass email and password for Admin
            List<String> allUsers = admin.listAllUsers();
            userList.addAll(allUsers); // Add all users to the list
        } else {
            userList.add("Invalid role.");
        }

        return userList;
    }
}