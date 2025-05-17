package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewCommentScreen {

    public ViewCommentScreen(JFrame postScreen) {
        // Create the main frame
        JFrame frame = new JFrame("View Comment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(10, 25, 74)); // Entire background blue

        // Panel for the heading
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(10, 25, 74));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBorder(BorderFactory.createEmptyBorder(140, 20, 10, 20)); // Add extra space above heading to move it lower

        JLabel headingLabel = new JLabel("View Comment", SwingConstants.CENTER);
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

        // Label for input field
        JLabel inputLabel = new JLabel("Enter the ID of the post to view comment:");
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

        // Display comments area
        JTextArea commentsArea = new JTextArea(10, 20);
        commentsArea.setEditable(false);
        commentsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        commentsArea.setBackground(new Color(220, 220, 220));
        commentsArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(commentsArea);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(scrollPane, gbc);

        // Rounded "Submit" button
        RoundedButton enterButton = new RoundedButton("Submit", 15);
        enterButton.setPreferredSize(new Dimension(150, 40));
        enterButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(enterButton, gbc);

        // Add the back button
        JPanel backPanel = createBackButton(frame, postScreen);
        frame.add(backPanel, BorderLayout.SOUTH);

        // Add components to the frame
        frame.add(headingPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Add action listener to the button
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the post ID from the input field
                    int postId = Integer.parseInt(inputField.getText().trim());

                    // Fetch the post using findPostById method
                    List<Post> posts = FileHandler.readFromFile("posts.dat");
                    Post post = posts.stream()
                            .filter(p -> p.getId() == postId)
                            .findFirst()
                            .orElseThrow(() -> new CustomException("Post with ID " + postId + " not found."));

                    // Fetch the list of comments for the post
                    List<Comment> comments = post.getComments();

                    // Display the comments in the JTextArea
                    commentsArea.setText(""); // Clear previous comments
                    if (comments.isEmpty()) {
                        commentsArea.append("No comments found for this post.");
                    } else {
                        for (Comment comment : comments) {
                            commentsArea.append("ID: " + post.getId() + "\n");
                            commentsArea.append("Comment: " + comment.getContent() + "\n");
                            commentsArea.append("Email: " + comment.getEmail() + "\n");
                            commentsArea.append("------------\n");
                        }
                    }
                } catch (NumberFormatException ex) {
                    // Handle invalid input (non-integer ID)
                    JOptionPane.showMessageDialog(frame, "Please enter a valid post ID.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (CustomException ex) {
                    // Handle post not found
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    // Handle other exceptions
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Create the back button and return to the previous screen (Post screen)
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
