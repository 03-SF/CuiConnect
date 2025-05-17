package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteCommentScreen {

    // Static reference to Post
    private static Post post;

    // Static setter method to initialize the Post instance
    public static void setPost(Post postInstance) {
        post = postInstance;
    }

    public DeleteCommentScreen(JFrame postScreen) {
        // Create the main frame
        JFrame frame = new JFrame("Delete Comment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350,600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(10, 25, 74)); // Blue background

        // Panel for the heading
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(10, 25, 74));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 10, 20));

        JLabel headingLabel = new JLabel("Delete Comment", SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingPanel.add(headingLabel, BorderLayout.CENTER);

        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 1, 15, 15)); // 8 rows, 1 column, 15px vertical spacing
        inputPanel.setBackground(new Color(10, 25, 74));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // First input field
        JLabel label1 = new JLabel("Enter the ID of the post to delete a comment on:");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(label1);

        JTextField textField1 = new JTextField();
        textField1.setPreferredSize(new Dimension(300, 40));
        textField1.setBackground(Color.WHITE);  // White background for text field
        textField1.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2)); // Lighter border for elegance
        textField1.setFont(new Font("Arial", Font.PLAIN, 14)); // Font for text inside
        inputPanel.add(textField1);

        // Second input field
        JLabel label2 = new JLabel("Enter the index of the comment to delete:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(label2);

        JTextField textField2 = new JTextField();
        textField2.setPreferredSize(new Dimension(300, 40));
        textField2.setBackground(Color.WHITE); // White background for text field
        textField2.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2)); // Lighter border
        textField2.setFont(new Font("Arial", Font.PLAIN, 14)); // Font for text inside
        inputPanel.add(textField2);

        // Third input field
        JLabel label3 = new JLabel("Enter your Email:");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(label3);

        JTextField textField3 = new JTextField();
        textField3.setPreferredSize(new Dimension(300, 40));
        textField3.setBackground(Color.WHITE); // White background for text field
        textField3.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2)); // Lighter border
        textField3.setFont(new Font("Arial", Font.PLAIN, 14)); // Font for text inside
        inputPanel.add(textField3);

        // Fourth input field with dropdown
        JLabel label4 = new JLabel("Are You an Admin:");
        label4.setForeground(Color.WHITE);
        label4.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(label4);

        String[] adminOptions = {"Yes", "No"};
        JComboBox<String> adminDropdown = new JComboBox<>(adminOptions);
        adminDropdown.setPreferredSize(new Dimension(300, 40));
        adminDropdown.setBackground(Color.WHITE); // White background for dropdown
        adminDropdown.setFont(new Font("Arial", Font.PLAIN, 14)); // Font for dropdown items
        inputPanel.add(adminDropdown);

        // Panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(10, 25, 74));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Enter button with rounded corners
        RoundedButton enterButton = new RoundedButton("Enter", 15);
        enterButton.setFont(new Font("Arial", Font.BOLD, 16));
        enterButton.setPreferredSize(new Dimension(150, 50));
        enterButton.addActionListener(e -> {
            try {
                int postId = Integer.parseInt(textField1.getText().trim());
                int commentIndex = Integer.parseInt(textField2.getText().trim());
                String userEmail = textField3.getText().trim();
                boolean isAdmin = adminDropdown.getSelectedItem().toString().equals("Yes");

                // Call the deleteCommentFromPost method instead of post.deleteComment
                post.deleteCommentFromPost(postId, commentIndex, userEmail, isAdmin);

                JOptionPane.showMessageDialog(frame, "Comment deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back button to return to PostScreen
        RoundedButton backButton = new RoundedButton("Back", 15);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> {
            // Close the current screen
            frame.dispose();
            // Open the PostScreen
            if (postScreen != null) {
                postScreen.setVisible(true);
            }
        });

        // Add both buttons to the panel
        buttonPanel.add(enterButton);
        buttonPanel.add(backButton);

        // Add components to the frame
        frame.add(headingPanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        frame.setVisible(true);
    }
}
