package cuiconnect02;

import java.io.Serializable;
import java.util.List;

public abstract class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    // Abstract methods for managing feedback
    public abstract void addFeedback(String eventName, String feedbackText, String email, String name) throws CustomException;

    public abstract void deleteFeedback(String eventName, String email, String role, int feedbackIndex) throws CustomException;

    public abstract List<FeedbackData> viewFeedback(String eventName) throws CustomException;
}
