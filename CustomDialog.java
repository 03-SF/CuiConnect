package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CustomDialog extends JDialog {

    public CustomDialog(JFrame parent, String title, String message) {
        super(parent, title, true); // Modal dialog
        setSize(350, 200);
        setResizable(false);
        setLocationRelativeTo(parent);

        // Background panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(3, 32, 64), 3, true), // Blue border
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Inner padding
        ));
        add(panel);

        // Title label
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(3, 32, 64)); // Blue text
        panel.add(titleLabel, BorderLayout.NORTH);

        // Message label
        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(3, 32, 64)); // Blue text
        panel.add(messageLabel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // OK button
        RoundedButton okButton = new RoundedButton("OK", 20);
        okButton.setPreferredSize(new Dimension(100, 40));
        okButton.setBackground(new Color(3, 32, 64)); // Blue background
        okButton.setForeground(Color.WHITE); // White text
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.addActionListener((ActionEvent e) -> dispose());
        buttonPanel.add(okButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Display the dialog
        setUndecorated(true); 
        setVisible(true);
    }
}