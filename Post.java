package cuiconnect02;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures compatibility during serialization/deserialization.

    private static int postIdCounter = 1; // Counter for assigning unique post IDs.
    private int id; 
    private String content;
    private String role;
    private String email;
    private int likes; 
    private List<Comment> comments; 

    public Post(String content, String role, String email) {
        this.id = postIdCounter++; 
        this.content = content;
        this.role = role;
        this.email = email;
        this.likes = 0; 
        this.comments = new ArrayList<>();
    }

    // Getters for accessing private fields.
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public int getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    // Method to increase the like count of the post.
    public void likePost() {
        this.likes++;
    }

    // Static method to initialize the post ID counter from the highest ID in the data file.
    public static void initializePostIdCounter() {
        List<Object> objectList = FileHandler.readFromFile("posts.dat");
        int highestId = 0;

        // Loop through the objects in the file and find the highest post ID.
        for (Object obj : objectList) {
            if (obj instanceof Post) {
                Post post = (Post) obj;
                if (post.getId() > highestId) {
                    highestId = post.getId();
                }
            }
        }

        postIdCounter = highestId + 1; // Set the counter to one higher than the highest ID.
    }

    // Method to add a comment to the post.
    public void addComment(String commentContent, String commenterEmail) {
        if (commentContent == null || commentContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be null or empty.");
        }
        // Create a new Comment object and add it to the comments list.
        Comment newComment = new Comment(commentContent, commenterEmail);
        comments.add(newComment);
    }

    // Method to get all comments as a list of strings.
    public List<String> getCommentDetails() {
        List<String> commentDetails = new ArrayList<>();
        for (Comment comment : comments) {
            commentDetails.add(comment.toString()); // Use the comment's toString method.
        }
        return commentDetails;
    }

    // Method to delete a comment from the post.
    public void deleteComment(int commentIndex, boolean isAdmin, String userEmail) throws CustomException {
        if (commentIndex < 0 || commentIndex >= comments.size()) {
            throw new CustomException("Invalid comment index."); // Validate index.
        }

        Comment commentToDelete = comments.get(commentIndex);
        // Allow deletion only if the user is an admin or the comment belongs to the user.
        if (isAdmin || commentToDelete.getEmail().equalsIgnoreCase(userEmail)) {
            comments.remove(commentIndex);
        } else {
            throw new CustomException("You do not have permission to delete this comment.");
        }
    }

    // Static method to delete a comment from a post using the post ID and comment index.
        public static void deleteCommentFromPost(int postId, int commentIndex, String userEmail, boolean isAdmin) throws CustomException {
        // Read all posts from the file
        List<Post> posts = FileHandler.readFromFile("posts.dat");
        Post postToDeleteComment = null;

        // Find the post by ID
        for (Post post : posts) {
            if (post.getId() == postId) {
                postToDeleteComment = post;
                break;
            }
        }

        // If no post is found, throw an exception
        if (postToDeleteComment == null) {
            throw new CustomException("Post with the given ID not found.");
        }

        // Delete the comment from the found post
        postToDeleteComment.deleteComment(commentIndex, isAdmin, userEmail);

        // Save the updated posts list back to the file
        FileHandler.clearFile("posts.dat");
        for (Post post : posts) {
            FileHandler.writeToFile("posts.dat", post);
        }
    }


    // Static method to add a new post.
    public static Post addPost(String content, String role, String email) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Post content cannot be null or empty.");
        }

        // Create a new Post object and save it to the file.
        Post newPost = new Post(content, role, email);
        FileHandler.writeToFile("posts.dat", newPost);
        return newPost;
    }

    // Static method to retrieve posts created by a specific user.
    public static List<Post> getPostsByEmail(String emailToView) throws UserNotFoundException {
        List<Post> posts = FileHandler.readFromFile("posts.dat");
        List<Post> userPosts = new ArrayList<>();

        // Filter posts by the user's email.
        for (Post post : posts) {
            if (post.getEmail().equalsIgnoreCase(emailToView)) {
                userPosts.add(post);
            }
        }

        if (userPosts.isEmpty()) {
            throw new UserNotFoundException("No posts found for the provided email."); // Throw exception if no posts are found.
        }

        return userPosts;
    }

        // Static method to delete a post by its ID.
        public static void deletePost(int postId, String email, String role, boolean isAdmin) throws CustomException {
        // Read all posts from the file
        List<Post> posts = FileHandler.readFromFile("posts.dat");
        Post postToDelete = null;

        // Find the post by ID and permission check
        for (Post post : posts) {
            if (post.getId() == postId && (isAdmin || post.getEmail().equalsIgnoreCase(email))) {
                postToDelete = post;
                break;
            }
        }

        // If the post is not found or permission is invalid
        if (postToDelete == null) {
            throw new CustomException("Post not found or no permission to delete.");
        }

        // Remove the post from the list
        posts.remove(postToDelete);

        // Save the updated list back to the file
        FileHandler.clearFile("posts.dat");
        for (Post post : posts) {
            FileHandler.writeToFile("posts.dat", post);
        }
    }


    // Static method to like a post by its ID.
        public static void likePostById(int postId) throws CustomException {
        // Read all posts from the file
        List<Post> posts = FileHandler.readFromFile("posts.dat");
        Post postToLike = null;

        // Find the post by ID
        for (Post post : posts) {
            if (post.getId() == postId) {
                postToLike = post;
                break;
            }
        }

        // If no post is found, throw an exception
        if (postToLike == null) {
            throw new CustomException("Post with ID " + postId + " not found.");
        }

        // Increment the likes for the found post
        postToLike.likePost();

        // Save the updated posts list back to the file
        FileHandler.clearFile("posts.dat");
        for (Post post : posts) {
            FileHandler.writeToFile("posts.dat", post);
        }
    }


    public static void addCommentToPost(int postId, String commentContent, String commenterEmail) throws CustomException {
     // Read all posts from the file
     List<Post> posts = FileHandler.readFromFile("posts.dat");
     Post postToCommentOn = null;

     // Find the post by ID
     for (Post post : posts) {
         if (post.getId() == postId) {
             postToCommentOn = post;
             break;
         }
     }

     // If no post is found, throw an exception
     if (postToCommentOn == null) {
         throw new CustomException("Post with ID " + postId + " not found.");
     }

     // Add a comment to the found post
     postToCommentOn.addComment(commentContent, commenterEmail);

     // Save the updated posts list back to the file
     FileHandler.clearFile("posts.dat");
     for (Post post : posts) {
         FileHandler.writeToFile("posts.dat", post);
     }
 }


    // Static method to retrieve all posts.
    public static List<Post> getAllPosts() throws CustomException {
        List<Post> posts = FileHandler.readFromFile("posts.dat");
        if (posts.isEmpty()) {
            throw new CustomException("No posts available.");
        }
        return posts;
    }
}
