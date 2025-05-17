
package cuiconnect02;
import java.io.Serializable;
import java.util.Objects;

public class MembershipRequest implements Serializable {
    private static final long serialVersionUID = 1L; // Ensure version consistency during serialization
     String societyName;
     String studentEmail;

    public MembershipRequest(String societyName, String studentEmail) {
        this.societyName = societyName;
        this.studentEmail = studentEmail;
    }

    public String getSocietyName() {
        return societyName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    MembershipRequest that = (MembershipRequest) obj;

    return societyName.equals(that.societyName) && studentEmail.equals(that.studentEmail);
}

@Override
public int hashCode() {
    return Objects.hash(societyName, studentEmail);
}

}
