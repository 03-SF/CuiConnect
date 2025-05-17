package cuiconnect02;
import java.io.Serializable;
import java.util.Objects;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String societyName;
    private final String eventDetails;
    private String name;

    public Event(String societyName, String eventDetails, String name) {
        this.societyName = societyName;
        this.eventDetails = eventDetails;
        this.name = name;
    }

    public String getSocietyName() {
        return societyName;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public String getEventName() {
        return name;
    }

    @Override
    public String toString() {
        return societyName + ": " + eventDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return societyName.equals(event.societyName) && name.equals(event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(societyName, name);
    }
}