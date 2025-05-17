package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

/**
 * A custom JTextField with rounded corners and a transparent background.
 */
public class RoundedTextField extends JTextField {
    private int radius; // The radius of the rounded corners

    public RoundedTextField(int radius) {
        super(); // Call the parent class constructor
        this.radius = radius; // Set the radius for rounded corners
        setOpaque(false); // Make the background transparent
    }

    /**
     * Custom painting for the text field to render rounded corners and custom border.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create(); // Create a copy of the Graphics object
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing for smooth edges

        // Draw the rounded background of the text field
        g2.setColor(Color.WHITE); // Set the background color to white
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); // Draw rounded rectangle

        // Draw the border of the text field
        g2.setColor(Color.LIGHT_GRAY); // Set the border color to light gray
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius); // Draw the rounded border

        g2.dispose(); // Dispose of the Graphics object to free resources

        // Paint the text inside the text field
        super.paintComponent(g); // Call the parent method to paint the text and other components
    }

    
    @Override
    public void setBorder(Border border) {
    }
}