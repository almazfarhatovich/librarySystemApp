package appLibrary.exceptions;

public class UserForbiddenException extends RuntimeException {

    public UserForbiddenException() {
    }

    public UserForbiddenException(String message) {
        super(message);
    }
}
