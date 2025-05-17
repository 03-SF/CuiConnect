package cuiconnect02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ADMIN_EMAIL = "admin@comsats.edu.pk"; // Admin's specific email
    public static final String ADMIN_PASSWORD = "admin123"; // Admin's specific password

    public Admin(String email, String password) {
        super(email, password);
    }

    public List<String> listAllUsers() {
        // Prepare a list of user details to display
        List<String> userList = new ArrayList<>();
        List<Object> usersData = FileHandler.readFromFile("users.dat");
        if (usersData.isEmpty()) {
            userList.add("No users registered.");
            return userList;
        }

        // Loop through the users and add their details to the list
        for (Object obj : usersData) {
            if (obj instanceof User) {
                User user = (User) obj;
                userList.add("Email: " + user.getEmail() + ", Role: " + user.getRole(user));
            }
        }
        return userList;
    }

    public String removeMember(String emailToRemove) {
        List<Object> usersData;
        boolean userFound = false;
        Object userToRemove = null;

        try {
            usersData = FileHandler.readFromFile("users.dat");
        } catch (Exception e) {
            return "Error reading users from file: " + e.getMessage();
        }

        try {
            for (Object obj : usersData) {
                if (obj instanceof Student) {
                    Student user = (Student) obj;

                    if (user.getEmail().equals(emailToRemove)) {
                        userToRemove = user;
                        FileHandler.deleteFromFile("users.dat", userToRemove);
                        removeStudentFromFile(emailToRemove);

                        userFound = true;
                        break;
                    }
                } else if (obj instanceof SocietyPresident) {
                    SocietyPresident user = (SocietyPresident) obj;
                    if (user.getEmail().equals(emailToRemove)) {
                        deleteSocietyByPresidentEmail(emailToRemove);
                        userFound = true;
                        break;
                    }
                }
            }

            if (!userFound) {
                return "User not found with email: " + emailToRemove;
            }

        } catch (Exception e) {
            return "Error deleting user: " + e.getMessage();
        }

        return "User " + emailToRemove + " removed successfully.";
    }

    private void removeStudentFromFile(String emailToRemove) throws Exception {
    List<Object> studentsData = FileHandler.readFromFile("students.dat");
    User studentToRemove = null;

    // Loop through each student and find the matching email
    for (Object obj : studentsData) {
        if (obj instanceof Student) {
            Student student = (Student) obj;
            if (student.getEmail().equals(emailToRemove)) {
                studentToRemove = student;
                break;
            }
        }
    }

    // Remove student from students.dat
    if (studentToRemove != null) {
        try {
            FileHandler.deleteFromFile("students.dat", studentToRemove);
        } catch (CustomException e) {
            throw new Exception("Error deleting student from the file: " + e.getMessage());
        }
    } else {
        throw new Exception("Student with email " + emailToRemove + " not found in students.dat.");
    }
}
private void deleteSocietyByPresidentEmail(String presidentEmail) throws Exception {
    if (presidentEmail == null || presidentEmail.trim().isEmpty()) {
        throw new Exception("Invalid president email. Operation canceled.");
    }

    List<Object> societiesData = FileHandler.readFromFile("societies.dat");
    List<Object> users = FileHandler.readFromFile("users.dat");
    List<Object> presidents = FileHandler.readFromFile("society_presidents.dat");

    // Remove society president from users
    boolean presidentFound = false;
    for (Object obj : users) {
        if (obj instanceof SocietyPresident) {
            SocietyPresident user = (SocietyPresident) obj;
            if (user.getEmail().equals(presidentEmail)) {
                FileHandler.deleteFromFile("users.dat", user);
                presidentFound = true;
                break;
            }
        }
    }

    // Remove society president from presidents file
    if (presidents != null && !presidents.isEmpty()) {
        for (Object obj : presidents) {
            if (obj instanceof SocietyPresident) {
                SocietyPresident president = (SocietyPresident) obj;
                if (president.getEmail().equals(presidentEmail)) {
                    FileHandler.deleteFromFile("society_presidents.dat", president);
                    presidentFound = true;
                    break;
                }
            }
        }
    }

    // Remove the society associated with the president
    boolean societyFound = false;
    for (Object obj : societiesData) {
        if (obj instanceof Society) {
            Society society = (Society) obj;
            if (society.getPresidentEmail().equals(presidentEmail)) {
                FileHandler.deleteFromFile("societies.dat", society);
                deleteSocietyRelatedData(society);
                societyFound = true;
                break;
            }
        }
    }

    if (!presidentFound || !societyFound) {
        throw new Exception("No society found for the president with email: " + presidentEmail);
    }
}
private void deleteSocietyRelatedData(Society societyToRemove) throws Exception {
    List<Object> eventsData = FileHandler.readFromFile("events.dat");
    List<Object> announcementsData = FileHandler.readFromFile("announcements.dat");

    try {
        // Remove events related to the society
        for (Object obj : eventsData) {
            if (obj instanceof Event) {
                Event event = (Event) obj;
                if (event.getSocietyName().equals(societyToRemove.getSocietyName())) {
                    FileHandler.deleteFromFile("events.dat", event);
                }
            }
        }

        // Remove announcements related to the society
        for (Object obj : announcementsData) {
            if (obj instanceof Announcement) {
                Announcement announcement = (Announcement) obj;
                if (announcement.getSocietyName().equals(societyToRemove.getSocietyName())) {
                    FileHandler.deleteFromFile("announcements.dat", announcement);
                }
            }
        }

        // Remove society members
        List<Object> membersData = FileHandler.readFromFile("society_members.dat");
        for (Object obj : membersData) {
            if (obj instanceof SocietyMember) {
                SocietyMember member = (SocietyMember) obj;
                if (member.getSocietyName().equals(societyToRemove.getSocietyName())) {
                    FileHandler.deleteFromFile("society_members.dat", member);
                }
            }
        }

        // Remove membership requests
        List<Object> membershipRequestsData = FileHandler.readFromFile("membership_requests.dat");
        for (Object obj : membershipRequestsData) {
            if (obj instanceof MembershipRequest) {
                MembershipRequest request = (MembershipRequest) obj;
                if (request.societyName.equals(societyToRemove.getSocietyName())) {
                    FileHandler.deleteFromFile("membership_requests.dat", request);
                }
            }
        }
    } catch (Exception e) {
        throw new Exception("Error occurred while deleting society related data: " + e.getMessage());
    }
}


    public String createGeneralEvent(String eventDetails, String name) {
        Event event = new Event("General", eventDetails, name);
        FileHandler.writeToFile("events.dat", event);
        return "General event created successfully.";
    }

    public String deleteEvent(String eventDetails) throws Exception {
        List<Object> events = FileHandler.readFromFile("events.dat");
        boolean eventDeleted = false;

        for (Object obj : events) {
            if (obj instanceof Event) {
                Event event = (Event) obj;
                if (event.getEventDetails().equals(eventDetails)) {
                    FileHandler.deleteFromFile("events.dat", event);
                    eventDeleted = true;
                    break;
                }
            }
        }

        return eventDeleted ? "Event deleted successfully." : "Event not found.";
    }

    public String createGeneralAnnouncement(String announcementDetails) {
        Announcement announcement = new Announcement("General", announcementDetails);
        FileHandler.writeToFile("announcements.dat", announcement);
        return "General announcement created successfully.";
    }

    public String deleteAnnouncement(String announcementDetails) throws Exception {
        List<Object> announcements = FileHandler.readFromFile("announcements.dat");
        boolean announcementDeleted = false;

        for (Object obj : announcements) {
            if (obj instanceof Announcement) {
                Announcement announcement = (Announcement) obj;
                if (announcement.getAnnouncementDetails().equals(announcementDetails)) {
                    FileHandler.deleteFromFile("announcements.dat", announcement);
                    announcementDeleted = true;
                    break;
                }
            }
        }

        return announcementDeleted ? "Announcement deleted successfully." : "Announcement not found.";
    }

    public String deleteSociety(String societyNameToDelete) throws Exception {
        List<Object> societies = FileHandler.readFromFile("societies.dat");
        Society societyToRemove = null;

        for (Object obj : societies) {
            if (obj instanceof Society) {
                Society society = (Society) obj;
                if (society.getSocietyName().equals(societyNameToDelete)) {
                    societyToRemove = society;
                    break;
                }
            }
        }

        if (societyToRemove != null) {
            FileHandler.deleteFromFile("societies.dat", societyToRemove);
            return "Society " + societyNameToDelete + " deleted successfully.";
        } else {
            return "Society not found.";
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
    try {
        // Reading the events data from the file
        List<Object> eventsData = FileHandler.readFromFile("events.dat");

        // Loop through each event object
        for (Object obj : eventsData) {
            if (obj instanceof Event) {
                Event event = (Event) obj;
                // Add society name, event name, and event details to the list
                String eventDetails = "Society: " + event.getSocietyName() + 
                                      ", Event: " + event.getEventName() + 
                                      ", Details: " + event.getEventDetails();
                eventsList.add(eventDetails);
            }
        }
    } catch (Exception e) {
        // Add error message if there's an exception while fetching events
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
        return "Admin logging out";
    }
}