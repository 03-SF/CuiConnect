package cuiconnect02;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.logging.Logger;

public class EventFeedback extends Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

        @Override
        public void addFeedback(String eventName, String feedbackText, String email, String name) throws CustomException {
        // Check if the event exists in the "events.dat" file
        List<Event> events = FileHandler.readFromFile("events.dat");
        Event event = null;

        // Loop through the events to find the matching event
        for (Event e : events) {
            if (e.getEventName().equals(eventName)) {
                event = e;
                break;
            }
        }

        if (event == null) {
            throw new CustomException("Event not found.");
        }

        // Create the feedback object
        FeedbackData feedback = new FeedbackData(eventName, feedbackText, email, name);
        FileHandler.writeToFile("eventFeedback.dat", feedback);
    }


    @Override
    public void deleteFeedback(String eventName, String email, String userRole, int feedbackIndex) throws CustomException {
        List<FeedbackData> feedbackList = FileHandler.readFromFile("eventFeedback.dat");

        if (feedbackIndex < 0 || feedbackIndex >= feedbackList.size()) {
            throw new CustomException("Invalid feedback index.");
        }

        FeedbackData feedbackToDelete = feedbackList.get(feedbackIndex);

        // Admin can delete any feedback; others can only delete their own feedback
        if ("Admin".equals(userRole) || feedbackToDelete.getEmail().equals(email)) {
            try {
                FileHandler.deleteFromFile("eventFeedback.dat", feedbackToDelete);
            } catch (Exception ex) {
                Logger.getLogger(EventFeedback.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new CustomException("Permission denied to delete this feedback.");
        }
    }

    @Override
    public List<FeedbackData> viewFeedback(String eventName) {
        return showFeedbackForEvent(eventName);
    }

    private List<FeedbackData> showFeedbackForEvent(String eventName) {
    List<FeedbackData> feedbackList = FileHandler.readFromFile("eventFeedback.dat");
    List<FeedbackData> filteredFeedbackList = new ArrayList<>();

    // Loop through the feedbackList and add feedbacks that match the eventName
    for (FeedbackData feedback : feedbackList) {
        if (feedback.getEventName().equals(eventName)) {
            filteredFeedbackList.add(feedback);
        }
    }

    return filteredFeedbackList;
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    //This method computes and returns a hash code for a FeedbackData, which is used for quick object identification
    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
