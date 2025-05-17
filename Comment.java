
package cuiconnect02;


import java.io.Serializable;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String content; // Content of the comment
    private String email;   // Email of the user who commented
    private int likes;      // Number of likes for the comment

    // Constructor
    public Comment(String content, String email) {
        this.content = content;
        this.email = email;
        this.likes = 0; // Initialize likes to 0
    }

    // Getters
    public String getContent() {
        return content;
    }

    public String getEmail() {
        return email;
    }

    public int getLikes() {
        return likes;
    }

    // Increment likes for the comment
    public void likeComment() {
        this.likes++;
    }

    // To display the comment details
    @Override
    public String toString() {
        return content + " | By: " + email + " | Likes: " + likes;
    }
}