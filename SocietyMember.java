
package cuiconnect02;
import java.io.Serializable;

public class SocietyMember implements Serializable {
    private String societyName;
    private String email;

    public SocietyMember(String societyName, String email) {
        this.societyName = societyName;
        this.email = email;
    }

    public String getSocietyName() {
        return societyName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SocietyMember other = (SocietyMember) obj;
        return societyName.equals(other.societyName) && email.equals(other.email);
    }

    @Override
    public int hashCode() {
        return societyName.hashCode() + email.hashCode();
    }
}

