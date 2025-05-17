package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCommentScreen {

    public AddCommentScreen(JFrame postScreen) {
        // Create the main frame
        JFrame frame = new JFrame("Add Comment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(10, 25, 74)); // Entire background blue

        // Create components
        JPanel headingPanel = createHeadingPanel();
        JPanel centerPanel = createCenterPanel(frame);

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

        JLabel headingLabel = new JLabel("Add Comment", SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Make heading slightly larger
        headingPanel.add(headingLabel, BorderLayout.CENTER);

        return headingPanel;
    }

    // Create the center panel with input fields and button
    private JPanel createCenterPanel(JFrame frame) {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(10, 25, 74)); // Blue background
        centerPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label for input field 1
        JLabel label1 = new JLabel("Enter the ID of the post to comment on:");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(label1, gbc);

        // Input text field 1
        JTextField textField1 = new JTextField(20);
        textField1.setFont(new Font("Arial", Font.PLAIN, 14));
        textField1.setBackground(new Color(220, 220, 220));
        textField1.setForeground(Color.BLACK);
        textField1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        centerPanel.add(textField1, gbc);

        // Label for input field 2
        JLabel label2 = new JLabel("Enter your Comment:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(label2, gbc);

        // Input text field 2
        JTextField textField2 = new JTextField(20);
        textField2.setFont(new Font("Arial", Font.PLAIN, 14));
        textField2.setBackground(new Color(220, 220, 220));
        textField2.setForeground(Color.BLACK);
        textField2.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the text field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(textField2, gbc);

        // Label for input field 3
        JLabel label3 = new JLabel("Enter your Email:");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        centerPanel.add(label3, gbc);

        // Input text field 3
        JTextField textField3 = new JTextField(20);
        textField3.setFont(new Font("Arial", Font.PLAIN, 14));
        textField3.setBackground(new Color(220, 220, 220));
        textField3.setForeground(Color.BLACK);
        textField3.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the text field
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        centerPanel.add(textField3, gbc);

        // Rounded "Submit" button
        RoundedButton submitButton = new RoundedButton("Submit", 15);
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(submitButton, gbc);

        // Add action listener to button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the post ID, comment, and email from the input fields
                    int postId = Integer.parseInt(textField1.getText().trim());
                    String commentContent = textField2.getText().trim();
                    String commenterEmail = textField3.getText().trim();

                    // Call the addCommentToPost method
                    Post.addCommentToPost(postId, commentContent, commenterEmail);

                    // Display success message
                    JOptionPane.showMessageDialog(frame, "Comment Submitted!");
                } catch (NumberFormatException ex) {
                    // Handle invalid input (non-integer ID)
                    JOptionPane.showMessageDialog(frame, "Please enter a valid post ID.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (CustomException ex) {
                    // Handle the case when the post is not found
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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

        // Action to go back to the previous screen (Post screen)
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
