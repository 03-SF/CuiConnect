package cuiconnect02;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Society currentSociety;  // Field to store the current society

    public Student(String email, String password) {
        super(email, password);
    }

    public String getRole() {
        return "Student";
    }

    public String getEmail() {
        return email;
    }

    // Corrected getSocietyName() method
    public String getSocietyName() {
        if (currentSociety != null) {
            return currentSociety.getSocietyName(); // Return the society name of the current society
        } else {
            return "No society joined"; // If the student is not part of any society
        }
    }

    // Method to set the current society
    public void setCurrentSociety(Society society) {
        this.currentSociety = society; // Assign the current society to the student
    }

    public List<String> getAllSocieties() {
        List<String> societiesList = new ArrayList<>();
        try {
            List<Object> societiesData = FileHandler.readFromFile("societies.dat");
            for (Object obj : societiesData) {
                if (obj instanceof Society) {
                    Society society = (Society) obj;
                    societiesList.add("Society: " + society.getSocietyName() + ", President: " + society.getPresidentEmail());
                }
            }
        } catch (Exception e) {
            societiesList.add("Error while fetching societies: " + e.getMessage());
        }
        return societiesList;
    }

    private boolean isSociety(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        try {
            List<Object> societiesData = FileHandler.readFromFile("societies.dat");
            for (Object obj : societiesData) {
                if (obj instanceof Society) {
                    Society society = (Society) obj;
                    if (society.getSocietyName().equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // Log the error
        }
        return false;
    }

    public String requestMembership(String societyName) {
        if (societyName == null || societyName.isEmpty()) {
            return "Invalid society name.";
        }
        if (isSociety(societyName)) {
            MembershipRequest request = new MembershipRequest(societyName, getEmail());
            try {
                List<Object> membershipRequests = FileHandler.readFromFile("membership_requests.dat");
                List<Object> members = FileHandler.readFromFile("society_members.dat");

                for (Object obj : members) {
                    if (obj instanceof SocietyMember) {
                        SocietyMember member = (SocietyMember) obj;
                        if (request.studentEmail.equals(member.getEmail())) {
                            return "You are already part of this society.";
                        }
                    }
                }

                if (membershipRequests.contains(request)) {
                    return "You have already requested membership for this society.";
                } else {
                    FileHandler.writeToFile("membership_requests.dat", request);
                    return "Membership requested for society: " + societyName;
                }
            } catch (Exception e) {
                return "Error while requesting membership: " + e.getMessage();
            }
        } else {
            return "The specified society does not exist.";
        }
    }

    public String leaveSociety(String societyName) {
        if (societyName == null || societyName.isEmpty()) {
            return "Invalid society name.";
        }
        String memberLine = societyName + ": " + getEmail();
        try {
            FileHandler.deleteFromFile("society_members.dat", memberLine);
            return "You have left the society: " + societyName;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public List<String> viewAllEvents() {
        List<String> eventsList = new ArrayList<>();
        try {
            List<Object> eventsData = FileHandler.readFromFile("events.dat");
            for (Object obj : eventsData) {
                if (obj instanceof Event) {
                    Event event = (Event) obj;
                    eventsList.add("Society: " + event.getSocietyName() + ", Event: " + event.getEventName());
                }
            }
        } catch (Exception e) {
            eventsList.add("Error while fetching events: " + e.getMessage());
        }
        return eventsList;
    }

    @Override
    public List<String> viewAllAnnouncements() {
        List<String> announcementsList = new ArrayList<>();
        try {
            List<Object> announcementsData = FileHandler.readFromFile("announcements.dat");
            for (Object obj : announcementsData) {
                if (obj instanceof Announcement) {
                    Announcement announcement = (Announcement) obj;
                    announcementsList.add("Society: " + announcement.getSocietyName() + ", Announcement: " + announcement.getAnnouncementDetails());
                }
            }
        } catch (Exception e) {
            announcementsList.add("Error while fetching announcements: " + e.getMessage());
        }
        return announcementsList;
    }

    @Override
    public void browseAllSocieties() {
        getAllSocieties(); // Can be directly used by a GUI method to populate a list or display.
    }

    @Override
    public String logout() {
        return "Student logging out";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        return email != null && email.equalsIgnoreCase(other.email);
    }

    @Override
    public int hashCode() {
        return email != null ? email.toLowerCase().hashCode() : 0;
    }
}
