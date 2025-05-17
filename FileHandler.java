package cuiconnect02;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler implements Serializable {
    private static final long serialVersionUID = 1L;

    // Method to write data to a file using serialization
    public static void writeToFile(String fileName, Object data) {
        List<Object> existingData = readFromFile(fileName); // Read existing data
        existingData.add(data); // Add the new object

        // Write the updated list back to the file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Object obj : existingData) {
                oos.writeObject(obj);
            }
        } catch (IOException e) {
            // Handle exceptions gracefully (no output or logging here)
        }
    }

    // Generic method to read data from a file using deserialization
    public static <T> List<T> readFromFile(String fileName) {
        List<T> dataList = new ArrayList<>();
        File file = new File(fileName);

        // Create the file if it doesn't exist
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            // Handle exceptions gracefully (no output or logging here)
            return dataList;
        }

        // Handle deserialization
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Object obj = objectInputStream.readObject();
                    dataList.add((T) obj); // Cast the object to the expected type
                } catch (EOFException ex) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions gracefully (no output or logging here)
        }
        return dataList;
    }

    // General method to check if a user exists and credentials match
    public static boolean checkCredentials(String fileName, String email, String password) throws CustomException {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Email or password cannot be null or empty.");
        }

        List<Object> users = readFromFile(fileName);

        for (Object obj : users) {
            if (obj instanceof SocietyPresident) {
                SocietyPresident sp = (SocietyPresident) obj;
                if (sp.getEmail().equals(email) && sp.getPassword().equals(password)) {
                    return true; // Match found
                }
            } else if (obj instanceof Student) {
                Student student = (Student) obj;
                if (student.getEmail().equals(email) && student.getPassword().equals(password)) {
                    return true; // Match found
                }
            }
        }
        throw new CustomException("User with email " + email + " not found or incorrect password.");
    }

    // General method to check for duplicate email
    public static boolean checkDuplicateEmail(String fileName, String email) throws CustomException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        List<Object> users = readFromFile(fileName);

        for (Object obj : users) {
            if (obj instanceof User) {
                User user = (User) obj;
                if (user.getEmail().equals(email)) {
                    throw new CustomException("A user with email " + email + " already exists.");
                }
            }
        }
        return false; // No duplicate found
    }

    // General method to delete an object from a file
    public static void deleteFromFile(String fileName, Object objectToRemove) throws Exception {
        if (objectToRemove == null) {
            throw new IllegalArgumentException("Object to remove cannot be null.");
        }

        List<Object> allObjects = readFromFile(fileName);
        List<Object> updatedList = new ArrayList<>();

        boolean found = false;

        for (Object obj : allObjects) {
            if (obj.equals(objectToRemove)) {
                found = true; // Skip this object as we want to remove it
            } else {
                updatedList.add(obj);
            }
        }

        if (!found) {
            throw new Exception("Object not found in file " + fileName);
        }

        // Rewrite updated list to file
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Object obj : updatedList) {
                objectOutputStream.writeObject(obj);
            }
        } catch (IOException e) {
            // Handle exceptions gracefully (no output or logging here)
        }
    }

    // General login method
    public static boolean checkLogin(String role, String email, String password) throws CustomException {
        if (role == null || role.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Role, email, or password cannot be null or empty.");
        }

        String fileName = getFileNameForRole(role);
        if (fileName.isEmpty()) {
            throw new CustomException("Invalid role: " + role);
        }
        return checkCredentials(fileName, email, password);
    }

    // Get the file name based on the role
    private static String getFileNameForRole(String role) {
        switch (role) {
            case "Admin":
                return "admins.dat";
            case "SocietyPresident":
                return "society_presidents.dat";
            case "Student":
                return "students.dat";
            default:
                return "";
        }
    }

    // General method to check if an object exists in the file
    public static boolean checkInFile(String fileName, User userToFind) {
        if (userToFind == null) {
            throw new IllegalArgumentException("User to find cannot be null.");
        }

        List<Object> objects = readFromFile(fileName);

        for (Object obj : objects) {
            if (obj instanceof User) {
                User user = (User) obj;
                if (user.equals(userToFind)) {
                    return true; // Match found
                }
            }
        }
        return false; // No match found
    }

    // Method to clear the file content
    public static void clearFile(String fileName) {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            // Overwrite the file with no content to clear it
        } catch (IOException e) {
            // Handle exceptions gracefully (no output or logging here)
        }
    }
}
