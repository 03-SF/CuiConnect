package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewPostScreen {

    public ViewPostScreen(JFrame previousScreen) {
        // Create the main frame
        JFrame frame = new JFrame("View Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(10, 25, 74)); // Entire background blue

        // Panel for the heading
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(10, 25, 74));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBorder(BorderFactory.createEmptyBorder(140, 20, 10, 20)); // Add extra space above heading to move it lower

        JLabel headingLabel = new JLabel("View Screen", SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Make heading slightly larger
        headingPanel.add(headingLabel, BorderLayout.CENTER);

        // Panel for the input field and button
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(10, 25, 74)); // Blue background
        centerPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label for "Press Enter" instruction
        JLabel inputLabel = new JLabel("Press Enter to View All Posts");
        inputLabel.setForeground(Color.WHITE);
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(inputLabel, gbc);

        // Rounded "Enter" button
        RoundedButton enterButton = new RoundedButton("Enter",15);
        enterButton.setPreferredSize(new Dimension(150, 40));
        enterButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(enterButton, gbc);

        // Create the Back button
        JPanel backPanel = createBackButton(frame, previousScreen);
        frame.add(backPanel, BorderLayout.SOUTH);

        // Add components to the frame
        frame.add(headingPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);

        // Add action listener to the "Enter" button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch and display all posts
                List<Post> posts = getAllPosts();
                if (posts.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No posts available.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Display all posts (You can format them as you wish)
                    StringBuilder postDetails = new StringBuilder("All Posts:\n\n");
                    for (Post post : posts) {
                        postDetails.append("ID: ").append(post.getId())
                                   .append(", Title: ").append(post.getContent())
                                   .append("\n");
                    }
                    JOptionPane.showMessageDialog(frame, postDetails.toString(), "Posts", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    // Create the back button and return to the previous screen
    private JPanel createBackButton(JFrame frame, JFrame previousScreen) {
        RoundedButton backButton = new RoundedButton("Back",15) {
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
                // Do not draw any border
            }
        };

        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(new Color(10, 25, 74));
        backButton.setForeground(Color.WHITE);

        // Action to go back to the previous screen
        backButton.addActionListener(e -> {
            frame.dispose(); // Dispose of the current frame
            previousScreen.setVisible(true); // Show the previous screen
        });

        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(10, 25, 74));
        backPanel.setLayout(new BorderLayout());
        backPanel.add(backButton, BorderLayout.CENTER);

        return backPanel;
    }

    // Method to get all posts from the file
    public static List<Post> getAllPosts() {
        return FileHandler.readFromFile("posts.dat");
    }
}
