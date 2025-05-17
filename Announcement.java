package cuiconnect02;
import java.io.Serializable;
import java.util.Objects;

public class Announcement implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String societyName;
    private String announcementDetails;
    private String name;

    public Announcement(String societyName, String announcementDetails) {
        this.societyName = societyName;
        this.announcementDetails = announcementDetails;
        this.name = name;
    }

    public String getSocietyName() {
        return societyName;
    }

    public String getAnnouncementDetails() {
        return announcementDetails;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return societyName + ": " + announcementDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Announcement that = (Announcement) obj;
        return societyName.equals(that.societyName) &&
                announcementDetails.equals(that.announcementDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(societyName, name);
    }
}
