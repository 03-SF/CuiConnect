package cuiconnect02;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SocietyPresident extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String societyName;

    public static final String SOCIETY_PRESIDENT_PASSWORD = "society123"; // Predefined password

    public SocietyPresident(String email, String password, String societyName) {
        super(email, password);
        this.societyName = societyName;
    }

    public String getEmail() {
        return email;
    }

    // Check if the Society President is associated with a valid society
    public boolean isValidSociety() {
        List<Object> societiesData = FileHandler.readFromFile("societies.dat");
        for (Object obj : societiesData) {
            if (obj instanceof Society) {
                Society society = (Society) obj;
                if (society.getSocietyName().equals(societyName) && society.getPresidentEmail().equals(this.getEmail())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean createSocietyEvent(String eventDetails, String name) {
        if (isValidSociety()) { // Check if society is valid before creating event
            Event event = new Event(societyName, eventDetails, name);
            FileHandler.writeToFile("events.dat", event); // Write event as an object
            return true; // Event created successfully
        } else {
            return false; // No society associated with this president
        }
    }

    // Initialize transient fields after deserialization
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();  // Deserialize non-transient fields
    }

    public boolean deleteSocietyEvent(String eventName, String eventDetails) {
        if (isValidSociety()) { // Ensure society is valid before allowing event deletion
            Event eventToDelete = new Event(societyName, eventDetails, eventName); // Create event object
            try {
                //  delete the event from the file
                FileHandler.deleteFromFile("events.dat", eventToDelete); 
                return true; // Event deleted successfully
            } catch (Exception e) {
                
                return false; // Error deleting event
            }
        }
        return false; // No society associated with this president
    }

    public boolean createSocietyAnnouncement(String announcementDetails) {
        if (isValidSociety()) { 
            Announcement announcement = new Announcement(societyName, announcementDetails);
            FileHandler.writeToFile("announcements.dat", announcement);
            return true; // Announcement created successfully
        } else {
            return false; // No society associated with this president
        }
    }

    public boolean deleteSocietyAnnouncement(String announcementDetails) {
        if (isValidSociety()) { 
            Announcement announcement = new Announcement(societyName, announcementDetails); // Create announcement object
            try {
                // Attempt to delete the announcement from the file
                FileHandler.deleteFromFile("announcements.dat", announcement); // Delete the announcement
                return true; // Announcement deleted successfully
            } catch (Exception e) {
                return false; // Error deleting announcement
            }
        }
        return false; // No society associated with this president
    }

    public List<String> viewMembershipRequests() {
        if (isValidSociety()) { 
            List<String> requests = new ArrayList<>();
            List<Object> membershipRequests = FileHandler.readFromFile("membership_requests.dat");

            for (Object obj : membershipRequests) {
                if (obj instanceof MembershipRequest) {
                    MembershipRequest request = (MembershipRequest) obj;
                    if (request.getSocietyName().equals(societyName)) { // Match society name
                        requests.add(request.getStudentEmail());
                    }
                }
            }

            return requests.isEmpty() ? null : requests; 
        }
        return null;
    }

    public boolean acceptMembershipRequest(String studentEmail) {
    try {
        if (isValidSociety()) {
            List<Object> membershipRequests = FileHandler.readFromFile("membership_requests.dat");
            boolean requestFound = false;
            for (Object obj : membershipRequests) {
                if (obj instanceof MembershipRequest) {
                    MembershipRequest request = (MembershipRequest) obj;
                    if (request.getSocietyName().equals(societyName) && request.getStudentEmail().equals(studentEmail)) {
                        requestFound = true;
                        SocietyMember member = new SocietyMember(societyName, studentEmail);
                        FileHandler.writeToFile("society_members.dat", member);
                        FileHandler.deleteFromFile("membership_requests.dat", request);
                        return true; // Membership request accepted
                    }
                }
            }

            return false; // No matching membership request found
        } else {
            return false; // No society associated with this president
        }
    } catch (Exception e) {
        return false; 
    }
}


    public List<String> listMembers() {
        if (isValidSociety()) { 
            List<String> members = new ArrayList<>();
            List<Object> membersData = FileHandler.readFromFile("society_members.dat");

            for (Object obj : membersData) {
                if (obj instanceof SocietyMember) {
                    SocietyMember member = (SocietyMember) obj;
                    if (member.getSocietyName().equals(societyName)) { 
                        members.add(member.getEmail());
                    }
                }
            }

            return members.isEmpty() ? null : members; // Return null if no members
        }
        return null; // No society associated with this president
    }

    public boolean removeMember(String studentEmail) {
    try {
        if (isValidSociety()) { 
            List<SocietyMember> membersData = FileHandler.readFromFile("society_members.dat");

            for (Object obj : membersData) {
                if (obj instanceof SocietyMember) {
                    SocietyMember member = (SocietyMember) obj;
                    if (member.getEmail().equals(studentEmail)) {
                        FileHandler.deleteFromFile("society_members.dat", member);
                        return true; // Member removed successfully
                    }
                }
            }

            return false; // Member not found
        } else {
            return false; // No society associated with this president
        }
    } catch (Exception e) {
       
        return false; 
    }
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
     

  @Override
public List<String> viewAllEvents() {
    List<String> eventsList = new ArrayList<>();
    String currentSociety = societyName; 
    try {
        List<Object> eventsData = FileHandler.readFromFile("events.dat");
        for (Object obj : eventsData) {
            if (obj instanceof Event) {
                Event event = (Event) obj;
                if (event.getSocietyName().equals(currentSociety)) {
                    
                    String eventDetails = "Society: " + event.getSocietyName() +
                            ", Event: " + event.getEventName() +
                            ", Details: " + event.getEventDetails();
                    eventsList.add(eventDetails);
                }
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
    String currentSociety = societyName; 
    
    try {
        List<Object> announcementsData = FileHandler.readFromFile("announcements.dat");
        for (Object obj : announcementsData) {
            if (obj instanceof Announcement) {
                Announcement announcement = (Announcement) obj;
                if (announcement.getSocietyName().equals(currentSociety)) {
                    // Only adding announcements that belong to the current society
                    announcementsList.add("Society: " + announcement.getSocietyName() + ", Announcement: " + announcement.getAnnouncementDetails());
                }
            }
        }
    } catch (Exception e) {
        announcementsList.add("Error while fetching announcements: " + e.getMessage());
    }
    return announcementsList;
}


    @Override
    public void browseAllSocieties() {
        getAllSocieties();
    }

    @Override
    public String logout() {
        return "President logging out";
    }
    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    SocietyPresident that = (SocietyPresident) obj;
    return email.equals(that.email); 
}


}