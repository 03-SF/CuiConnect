package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main class to launch the CUIConnect welcome screen with buttons for "Register" and "Login".
 */
public class CuiConnect02 {

    public static void main(String[] args) {
        // Create the frame for the welcome screen
        JFrame frame = new JFrame("CUIConnect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        frame.setSize(350, 600); // Set the size of the frame
        frame.setLayout(null); // Use absolute positioning

        // Set dark blue background for the frame
        frame.getContentPane().setBackground(new Color(0, 38, 77));

        // Calculate center position dynamically for the logo
        int frameWidth = frame.getWidth();
        int centerX = frameWidth / 2;

        // Add the logo to the frame
        String logoPath = "C:\\Users\\ABC\\Desktop\\200.png"; // Path to the logo image
        try {
            BufferedImage logoImage = ImageIO.read(new File(logoPath)); // Read the logo image from the file
            BufferedImage resizedImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB); // Create resized image
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC); // Quality for resizing
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Anti-aliasing for smooth edges
            g2d.drawImage(logoImage, 0, 0, 200, 200, null); // Draw the resized image
            g2d.dispose();

            // Create a JLabel to display the logo and center it on the frame
            JLabel logoLabel = new JLabel(new ImageIcon(resizedImage));
            int logoWidth = 200; // Set width of the logo
            logoLabel.setBounds(centerX - logoWidth / 2, 30, logoWidth, 200); // Center the logo on the screen
            frame.add(logoLabel); // Add logo label to the frame
        } catch (IOException e) {
            System.err.println("Image not found: " + e.getMessage()); // Handle image loading failure
        }

        // Add the welcome text label
        JLabel welcomeLabel = new JLabel("Welcome to CUIConnect", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font style and size
        welcomeLabel.setForeground(Color.WHITE); // Set text color to white
        welcomeLabel.setBounds(0, 250, frameWidth, 30); // Center text horizontally
        frame.add(welcomeLabel);

        // Add "Register" button
        RoundedButton registerButton = new RoundedButton("Register", 30);
        int buttonWidth = 200; // Set button width
        registerButton.setBounds(centerX - buttonWidth / 2, 300, buttonWidth, 40); // Center button on the screen
        frame.add(registerButton); // Add register button to the frame

        // Add "Already have an Account?" label
        JLabel alreadyLabel = new JLabel("Already have an Account?", SwingConstants.CENTER);
        alreadyLabel.setFont(new Font("Arial", Font.ITALIC, 12)); // Set italic font for this label
        alreadyLabel.setForeground(Color.WHITE); // Set text color to white
        alreadyLabel.setBounds(0, 350, frameWidth, 30); // Center text horizontally
        frame.add(alreadyLabel);
        // Add "Login" button
        RoundedButton loginButton = new RoundedButton("Login", 30);
        loginButton.setBounds(centerX - buttonWidth / 2, 390, buttonWidth, 40); // Center button on the screen
        frame.add(loginButton); // Add login button to the frame

        // Action listener for "Register" button to open the Registration screen
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationScreen(); // Open Registration Screen
            }
        });

        // Action listener for "Login" button to open the Login screen
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginScreen(); // Open Login Screen
            }
        });

        // Make the frame visible and center it on the screen
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true); // Show the frame
    }
}