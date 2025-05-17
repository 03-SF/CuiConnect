package cuiconnect02;

import javax.swing.*;
import java.awt.*;

class RoundedButton extends JButton {
    private int cornerRadius; // Customizable corner radius

    public RoundedButton(String text, int cornerRadius) {
        super(text);
        this.cornerRadius = cornerRadius; // Set corner radius
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false); // Transparent background
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(new Color(0, 38, 77)); // Text color
        setBackground(Color.WHITE); // Button color
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // Use custom corner radius
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 38, 77)); // Border color
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius); // Use custom corner radius
        g2.dispose();
    }

    // Setter for corner radius to allow dynamic changes
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint(); // Repaint button to reflect changes
    }
}