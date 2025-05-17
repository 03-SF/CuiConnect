
package cuiconnect02;

public class CustomException extends Exception {
        private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }

   
}
// Custom exception for user not found
class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}



