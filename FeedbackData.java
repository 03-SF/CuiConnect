package cuiconnect02;

import java.io.Serializable;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class FeedbackData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventName;
    private String feedbackText;
    private String email;
    private String name;

    // Constructor
    public FeedbackData(String eventName, String feedbackText, String email, String name) {
        this.eventName = eventName;
        this.feedbackText = feedbackText;
        this.email = email;
        this.name = name;
    }
    public static List<FeedbackData> viewFeedback(String eventName) {
        return showFeedbackForEvent(eventName);
    }

    // Helper method to fetch feedback data from the file
    private static List<FeedbackData> showFeedbackForEvent(String eventName) {
        List<FeedbackData> feedbackList = FileHandler.readFromFile("eventFeedback.dat");
        List<FeedbackData> filteredFeedbackList = new ArrayList<>();

        // Loop through the feedbackList and add feedbacks that match the eventName to filteredFeedbackList
        for (FeedbackData feedback : feedbackList) {
            if (feedback.getEventName().equals(eventName)) {
                filteredFeedbackList.add(feedback);
            }
        }

        return filteredFeedbackList;
    }


    // Getters and Setters
    public String getEventName() {
        return eventName;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
//Checks if two FeedbackData objects are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackData that = (FeedbackData) o;
        return Objects.equals(eventName, that.eventName) &&
               Objects.equals(feedbackText, that.feedbackText) &&
               Objects.equals(email, that.email) &&
               Objects.equals(name, that.name);
    }

//This method computes and returns a hash code for a FeedbackData, which is used for quick object identification
    @Override
    public int hashCode() {
        return Objects.hash(eventName, feedbackText, email, name);
    }
    @Override
    public String toString() {
        return "Name: " + name + "\nEmail: " + email + "\nFeedback: " + feedbackText;
    }

}

