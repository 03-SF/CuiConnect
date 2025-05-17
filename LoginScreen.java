package cuiconnect02;
/**
 * GUI for the Login screen with various management options.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class LoginScreen {
    public LoginScreen() {
        // Setup the login window
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);

        // Create a panel for components
        JPanel panel = new JPanel();
        panel.setBackground(new Color(3, 32, 64)); // Dark blue theme
        panel.setLayout(null);

        // Add title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(130, 50, 100, 30);
        panel.add(titleLabel);

        // Email input field
        RoundedTextField emailField = new RoundedTextField(15);
        emailField.setBounds(75, 120, 200, 35);
        emailField.setText("   user email"); // Placeholder
        panel.add(emailField);

        // Password input field
        RoundedTextField passwordField = new RoundedTextField(15);
        passwordField.setBounds(75, 180, 200, 35);
        passwordField.setText("   password");
        panel.add(passwordField);

        // Dropdown for role selection
        String[] roles = { "Select Role", "Admin", "SocietyPresident", "Student" };
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(75, 240, 200, 35);
        roleComboBox.setBackground(Color.WHITE);
        roleComboBox.setForeground(new Color(3, 32, 64));
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        roleComboBox.setBorder(BorderFactory.createLineBorder(new Color(3, 32, 64), 2));
        panel.add(roleComboBox);

        // Login button
        RoundedButton loginButton = new RoundedButton("Login", 30);
        loginButton.setBounds(110, 320, 120, 40);
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(3, 32, 64));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(loginButton);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Collect user input
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();
                String role = (String) roleComboBox.getSelectedItem();

                try {
                    // Attempt login
                    User user = User.login(email, password, role);
                    new CustomDialog(frame, "Login Successful", "Welcome, " + role + "!"); // Success message
                    frame.dispose(); // Close current window

                    // Navigate based on role
                    switch (role) {
                        case "Admin":
                            new AdminScreen(role, email, password, ""); // Navigate to AdminScreen
                            break;
                        case "SocietyPresident":
                            String societyName = getSocietyNameByPresidentEmail(email);
                            if (societyName == null) throw new CustomException("No society found.");
                            new PresidentScreen(role, email, password, societyName); // Navigate to PresidentScreen
                            break;
                        case "Student":
                            Student student = new Student(email, password);
                            new StudentScreen(student); // Navigate to StudentScreen
                            break;
                        default:
                            throw new CustomException("Invalid role."); // Handle invalid role
                    }
                } catch (Exception ex) {
                    new CustomDialog(frame, "Error", ex.getMessage()); // Show error message
                }
            }
        });

        frame.add(panel); // Add panel to frame
        frame.setVisible(true); // Show window
    }

    // Fetch society name for the given president email
    private String getSocietyNameByPresidentEmail(String presidentEmail) throws IOException, ClassNotFoundException {
        File file = new File("societies.dat");
        if (!file.exists()) return null; // Return null if file doesn't exist

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Object> societies = FileHandler.readFromFile("societies.dat");
            for (Object obj : societies) {
                if (obj instanceof Society) {
                    Society society = (Society) obj;
                    if (society.getPresidentEmail().equals(presidentEmail)) {
                        return society.getSocietyName(); // Return matching society name
                    }
                }
            }
        }
        return null; // No match found
    }
}