package cuiconnect02;

import java.io.*;
import java.util.List;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String password;
     String email;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole(User user) {
        if (user instanceof Student) {
            return "Student";
        } else if (user instanceof SocietyPresident) {
            return "SocietyPresident";
        } else {
            return "Unknown Role";
        }
    }

    private boolean isAlreadyManagingSociety() {
        List<Object> societyPresidents = FileHandler.readFromFile("society_presidents.dat");
        for (Object obj : societyPresidents) {
            if (obj instanceof SocietyPresident) {
                SocietyPresident president = (SocietyPresident) obj;
                if (president.getEmail().equals(this.getEmail())) {
                    return true; // The president is already managing a society
                }
            }
        }
        return false;
    }

    public static User login(String email, String password, String role) throws CustomException {
        try {
            if (role == null || role.isEmpty()) {
                throw new CustomException("Role cannot be empty.");
            }

            if ("Admin".equals(role)) {
                if (email.equals(Admin.ADMIN_EMAIL) && password.equals(Admin.ADMIN_PASSWORD)) {
                    return new Admin(email, password);
                } else {
                    throw new CustomException("Invalid admin credentials.");
                }
            }

            if (FileHandler.checkLogin(role, email, password)) {
                switch (role) {
                    case "SocietyPresident":
                        List<Object> societyPresidents = FileHandler.readFromFile("society_presidents.dat");
                        for (Object obj : societyPresidents) {
                            if (obj instanceof SocietyPresident) {
                                SocietyPresident president = (SocietyPresident) obj;
                                if (president.getEmail().equals(email) && president.getPassword().equals(password)) {
                                    if (president.isValidSociety()) {
                                        return president;
                                    } else {
                                        throw new CustomException("No society associated with this president.");
                                    }
                                }
                            }
                        }
                        throw new CustomException("Invalid email or password.");

                    case "Student":
                        List<Student> students = FileHandler.readFromFile("students.dat");
                        for (Student student : students) {
                            if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                                return student;
                            }
                        }
                        throw new CustomException("Student not registered or incorrect credentials.");

                    default:
                        throw new CustomException("Invalid role: " + role);
                }
            }
        } catch (Exception e) {
            throw new CustomException("Unexpected error during login: " + e.getMessage());
        }
        return null;
    }

    private static boolean isAlreadyManagingSocietyForEmail(String email) {
        List<Object> societyPresidents = FileHandler.readFromFile("society_presidents.dat");
        for (Object obj : societyPresidents) {
            if (obj instanceof SocietyPresident) {
                SocietyPresident president = (SocietyPresident) obj;
                if (president.getEmail().equals(email)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean register(String email, String password, String userType, String societyName) throws CustomException {
        try {
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                throw new CustomException("Email and password cannot be empty.");
            }

            if (!email.endsWith("@comsats.edu.pk")) {
                throw new CustomException("Only COMSATS emails are allowed.");
            }

            FileHandler.checkDuplicateEmail("users.dat", email);

            switch (userType) {
                case "Student":
                    Student student = new Student(email, password);
                    FileHandler.writeToFile("students.dat", student);
                    FileHandler.writeToFile("users.dat", student);
                    break;

                case "SocietyPresident":
                    if (!password.equals(SocietyPresident.SOCIETY_PRESIDENT_PASSWORD)) {
                        throw new CustomException("Incorrect SocietyPresident password. Registration denied.");
                    }

                    if (isAlreadyManagingSocietyForEmail(email)) {
                        throw new CustomException("This president is already managing a society.");
                    }

                    if (societyName == null || societyName.isEmpty()) {
                        throw new CustomException("Society name cannot be empty.");
                    }

                    SocietyPresident newPresident = new SocietyPresident(email, password, societyName);
                    Society society = new Society(societyName, email);

                    FileHandler.writeToFile("societies.dat", society);
                    FileHandler.writeToFile("society_presidents.dat", newPresident);
                    FileHandler.writeToFile("users.dat", newPresident);
                    break;

                default:
                    throw new CustomException("Invalid user type: " + userType);
            }
            return true;
        } catch (Exception e) {
            throw new CustomException("Unexpected error during registration: " + e.getMessage());
        }
    }

    public abstract String logout();

    public abstract List<String> viewAllEvents();

    public abstract List<String> viewAllAnnouncements();

    public abstract void browseAllSocieties();
}
