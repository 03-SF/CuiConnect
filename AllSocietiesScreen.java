package cuiconnect02;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class displays all societies of CUI in a scrollable list.
 * Each society is presented in a rounded panel, with a back button to close the screen.
 */
public class AllSocietiesScreen {
    public AllSocietiesScreen() {
        // Create JFrame for the All Societies screen
        JFrame frame = new JFrame("All Societies of CUI");
        frame.setSize(350, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with dark blue background
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0, 20, 60)); // Dark blue background

        // Heading label at the top
        JLabel label = new JLabel("All Societies of CUI", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(400, 50));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 24)); // Bigger and bold font for the heading
        panel.add(label, BorderLayout.NORTH);

        // Container panel for displaying society names
        JPanel societiesPanel = new JPanel();
        societiesPanel.setLayout(new BoxLayout(societiesPanel, BoxLayout.Y_AXIS)); // Vertical stacking
        societiesPanel.setBackground(new Color(0, 20, 60)); // Same background as main panel
        societiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Retrieve the list of societies
        List<String> societies = getAllSocieties();

        // Dynamically display each society in a rounded panel
        if (!societies.isEmpty()) {
            for (String society : societies) {
                // Create a rounded panel for each society and add it to the list
                JPanel societyPanel = createRoundedPanel(society);
                societyPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
                societiesPanel.add(societyPanel);
                societiesPanel.add(Box.createVerticalStrut(5)); // Add space between societies
            }
        } else {
            // If no societies found, display a message
            JLabel noSocietiesLabel = new JLabel("No societies found.", SwingConstants.CENTER);
            noSocietiesLabel.setForeground(Color.WHITE);
            noSocietiesLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Display message with plain font
            societiesPanel.add(noSocietiesLabel);
        }

        // Make societiesPanel scrollable
        JScrollPane scrollPane = new JScrollPane(societiesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10); // Smooth scrolling
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Back button setup
        RoundedButton backButton = new RoundedButton("Back", 30);
        backButton.setPreferredSize(new Dimension(150, 30)); // Set button size
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 20, 60)); // Blue text
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Font size for the button

        // Back button action listener (closes the frame when clicked)
        backButton.addActionListener(e -> frame.dispose());

        // Button panel for back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Transparent panel
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel); // Add main panel to the frame
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Creates a rounded panel for displaying a society name.
     */
    private JPanel createRoundedPanel(String societyText) {
        JPanel roundedPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE); // White background for the rounded panel
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Rounded corners
            }
        };

        roundedPanel.setLayout(new BorderLayout());
        JLabel label = new JLabel(societyText, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font size for society name
        label.setForeground(new Color(0, 20, 60)); // Blue text for the label
        roundedPanel.add(label, BorderLayout.CENTER);

        // Set the size for the rounded panel
        roundedPanel.setPreferredSize(new Dimension(300, 40));
        roundedPanel.setMinimumSize(new Dimension(300, 40));
        roundedPanel.setMaximumSize(new Dimension(300, 40));
        roundedPanel.setOpaque(false); // Transparent for rounded edges
        return roundedPanel;
    }

    /**
     * Retrieves a list of all societies.
     *
     * @return A list of society names with president details.
     */
    public List<String> getAllSocieties() {
        List<String> societiesList = new java.util.ArrayList<>();
        try {
            List<Object> societiesData = FileHandler.readFromFile("societies.dat");
            // Iterate through the list of societies data and extract relevant info
            for (Object obj : societiesData) {
                if (obj instanceof Society) {
                    Society society = (Society) obj;
                    societiesList.add("Society: " + society.getSocietyName() + ", President: " + society.getPresidentEmail());
                }
            }
        } catch (Exception e) {
            // If error occurs while fetching societies, add error message
            societiesList.add("Error while fetching societies: " + e.getMessage());
        }
        return societiesList;
    }

    public void browseAllSocieties() {
        getAllSocieties(); 
    }
}