package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePostScreen {

    public CreatePostScreen(JFrame postScreen) {
        // Create the main frame
        JFrame frame = new JFrame("Create Post");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(10, 25, 74)); // Entire background blue

        // Create components
        JPanel headingPanel = createHeadingPanel();
        JPanel centerPanel = createCenterPanel();

        // Add components to the frame
        frame.add(headingPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Add the back button
        JPanel backPanel = createBackButton(frame, postScreen);
        frame.add(backPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Create the heading panel
    private JPanel createHeadingPanel() {
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(10, 25, 74));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBorder(BorderFactory.createEmptyBorder(140, 20, 10, 20)); // Add extra space above heading to move it lower

        JLabel headingLabel = new JLabel("Create a Post", SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Make heading slightly larger
        headingPanel.add(headingLabel, BorderLayout.CENTER);

        return headingPanel;
    }

    // Create the center panel with input fields and button
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(10, 25, 74)); // Blue background
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label for input field
        JLabel inputLabel = new JLabel("Enter Post Details");
        inputLabel.setForeground(Color.WHITE);
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(inputLabel, gbc);

        // Input text field
        JTextField inputField = new JTextField(20);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBackground(new Color(220, 220, 220));
        inputField.setForeground(Color.BLACK);
        inputField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(inputField, gbc);

        // Rounded "Enter" button
        JButton enterButton = new JButton("Enter");
        enterButton.setPreferredSize(new Dimension(100, 40));
        enterButton.setFont(new Font("Arial", Font.BOLD, 14));
        enterButton.setBackground(Color.WHITE);
        enterButton.setForeground(new Color(0, 38, 77)); // Text color
        enterButton.setFocusPainted(false);
        enterButton.setBorder(BorderFactory.createLineBorder(new Color(0, 38, 77), 2, true)); // Rounded border

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(enterButton, gbc);

        // Add action listener to button
        enterButton.addActionListener(e -> {
            String content = inputField.getText().trim();
            String role = "User";  // You can modify this depending on the role logic
            String email = "user@example.com";  // You can modify this to get the actual email

            try {
                Post.addPost(content, role, email);
                JOptionPane.showMessageDialog(null, "Post added successfully!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return centerPanel;
    }

    // Create the back button and return to the previous screen
    private JPanel createBackButton(JFrame frame, JFrame postScreen) {
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
                // Do not draw any border
            }
        };

        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(new Color(10, 25, 74));
        backButton.setForeground(Color.WHITE);

        // Action to go back to the Admin screen
        backButton.addActionListener(e -> {
            frame.dispose();
            postScreen.setVisible(true); // Make the previous screen visible
        });

        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(10, 25, 74));
        backPanel.setLayout(new BorderLayout());
        backPanel.add(backButton, BorderLayout.CENTER);

        return backPanel;
    }
}
