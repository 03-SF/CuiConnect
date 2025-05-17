package cuiconnect02;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class Society implements Serializable {
    private static final long serialVersionUID = 1L;

    final String societyName;
    final String presidentEmail;

    // Constructor
    public Society(String societyName, String presidentEmail) {
        this.societyName = societyName;
        this.presidentEmail = presidentEmail;
    }

    // Getters
    public String getSocietyName() {
        return societyName;
    }

    public String getPresidentEmail() {
        return presidentEmail;
    }

    // Override toString for meaningful output (optional for use in the GUI)
    @Override
    public String toString() {
        return societyName + ", " + presidentEmail;
    }

    // Adds a society by serializing the Society object and storing it in the file
    public static void addSociety(String societyName, String presidentEmail) throws CustomException {
        try {
            // Validate input
            if (societyName == null || societyName.isEmpty() || presidentEmail == null || presidentEmail.isEmpty()) {
                throw new CustomException("Society name or president email cannot be empty.");
            }

            // Check for duplicate societies
            List<Object> existingSocieties = FileHandler.readFromFile("societies.dat");
            for (Object obj : existingSocieties) {
                if (obj instanceof Society) {
                    Society existingSociety = (Society) obj;
                    if (existingSociety.getSocietyName().equalsIgnoreCase(societyName)) {
                        throw new CustomException("A society with the name '" + societyName + "' already exists.");
                    }
                }
            }

            // Write new society to file
            Society newSociety = new Society(societyName, presidentEmail);
            FileHandler.writeToFile("societies.dat", newSociety);
            // No output printed, success is handled externally

        } catch (CustomException e) {
            throw e;  // Propagate the custom exception for the GUI to handle
        } catch (Exception e) {
            throw new CustomException("Unexpected error occurred while adding society: " + e.getMessage());
        }
    }

    // Equals method to compare two societies based on name and president's email
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Society society = (Society) obj;
        return Objects.equals(societyName, society.societyName) &&
               Objects.equals(presidentEmail, society.presidentEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(societyName, presidentEmail);
    }
}
