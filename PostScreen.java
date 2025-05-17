package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostScreen {
    private JFrame frame;

    // Constructor accepts various parameters to customize the screen based on user role and other details
    public PostScreen(JFrame adminScreen, String currentUserRole, String currentUserEmail, String currentUserPassword, String currentSocietyName) {
        // Create the main frame for the Post screen
        frame = new JFrame("Post");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        frame.setSize(350, 600); 
        frame.setLayout(new BorderLayout()); // Set the layout to BorderLayout 
        frame.getContentPane().setBackground(new Color(10, 25, 74)); // Set a blue background for the window

        // Create panels for different sections of the window
        JPanel headingPanel = createHeadingPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel backPanel = createBackButtonPanel(adminScreen, currentUserRole, currentUserEmail, currentUserPassword, currentSocietyName);

        // Add the panels to the frame
        frame.add(headingPanel, BorderLayout.NORTH); // Heading at the top
        frame.add(buttonPanel, BorderLayout.CENTER); // Buttons in the center
        frame.add(backPanel, BorderLayout.SOUTH); // Back button at the bottom
    }

    // Create the heading panel with a centered "Post" label
    private JPanel createHeadingPanel() {
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(10, 25, 74)); // Blue background
        headingPanel.setLayout(new BorderLayout()); // Set layout to BorderLayout
        headingPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 10, 20)); // Add space above the heading

        JLabel headingLabel = new JLabel("Post", SwingConstants.CENTER); // Create label with "Post"
        headingLabel.setForeground(Color.WHITE); // Set text color to white
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size and style
        headingPanel.add(headingLabel, BorderLayout.CENTER); // Add label to the center of the panel

        return headingPanel; // Return the created heading panel
    }

    // Create the button panel with multiple action buttons for user interaction
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 10, 10)); // Create a grid layout for the buttons (8 rows, 1 column)
        buttonPanel.setBackground(new Color(10, 25, 74)); // Blue background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add space around the buttons

        // Array of button labels for various actions
        String[] buttonLabels = {
                "Create a Post",
                "Like a Post",
                "Add Comment",
                "View Comment",
                "Delete Comment",
                "Delete Post",
                "View All Posts"
        };

        // Create a rounded button for each action and add them to the button panel
        for (String label : buttonLabels) {
            RoundedButton button = new RoundedButton(label + " >", 15); // Create a rounded button with label
            button.addActionListener(new ButtonClickListener(label)); // Add an ActionListener to handle button clicks
            buttonPanel.add(button); // Add the button to the panel
        }

        return buttonPanel; // Return the button panel
    }

    // Create the back button panel with logic to navigate based on user role
    private JPanel createBackButtonPanel(JFrame adminScreen, String currentUserRole, String currentUserEmail, String currentUserPassword, String currentSocietyName) {
        // Create the back button with custom styling (rounded corners)
        RoundedButton backButton = new RoundedButton("Back", 15) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground()); // Set background color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50); // Draw rounded corners for the button
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // Do not draw any border around the button
            }
        };

        backButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for the button text
        backButton.setPreferredSize(new Dimension(100, 40)); // Set the size of the button
        backButton.setBackground(new Color(10, 25, 74)); // Blue background
        backButton.setForeground(Color.WHITE); // White text color

        // ActionListener to handle back button clicks
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current screen (PostScreen)
            System.out.println("Navigating back with role: " + currentUserRole);

            // Navigate to different screens based on the user role
            switch (currentUserRole.trim().toLowerCase()) {
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
                    JOptionPane.showMessageDialog(null, "Unknown user role: " + currentUserRole);
                    break;
            }
        });

        // Create a panel to hold the back button
        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(10, 25, 74)); // Set background color to blue
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20)); // Add space around the back button
        backPanel.setLayout(new BorderLayout()); // Set layout to BorderLayout
        backPanel.add(backButton, BorderLayout.CENTER); // Add back button to the center of the panel

        return backPanel; // Return the back button panel
    }

    // Display the frame (show the window on the screen)
    public void display() {
        frame.setVisible(true); // Make the frame visible to the user
    }

    // ActionListener class to handle button clicks
    static class ButtonClickListener implements ActionListener {
        private String buttonName; // Store the name of the button clicked

        // Constructor accepts the button name
        public ButtonClickListener(String buttonName) {
            this.buttonName = buttonName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform actions based on the button clicked
            switch (buttonName) {
                case "Create a Post":
                    new CreatePostScreen(null); // Open screen to create a new post
                    break;
                case "Like a Post":
                    new LikePostScreen(null); // Open screen to like an existing post
                    break;
                case "Add Comment":
                    new AddCommentScreen(null); // Open screen to add a comment to a post
                    break;
                case "View Comment":
                    new ViewCommentScreen(null); // Open screen to view comments on a post
                    break;
                case "Delete Comment": 
                    // Create a post object to pass to the delete comment screen
                    String title = "Post Title";
                    String content = "This is the content of the post.";
                    String authorEmail = "author@example.com";
                    Post myPost = new Post(title, content, authorEmail);
                    // Set the post instance for the delete comment screen
                    DeleteCommentScreen.setPost(myPost);
                    new DeleteCommentScreen(null); // Open the delete comment screen
                    break;
                case "Delete Post":
                    new DeletePostScreen(null); // Open screen to delete a post
                    break;
                case "View All Posts":
                    new ViewPostScreen(null); // Open screen to view all posts
                    break;
                default:
                    System.out.println("Unknown button clicked"); // Handle unexpected button clicks
                    break;
            }
        }
    }
}
